package com.infinity.delaunayvoronoi.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infinity.delaunayvoronoi.factory.DelaunayModelFactory;
import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Circle;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;
import com.infinity.delaunayvoronoi.model.Triangle;
import com.infinity.delaunayvoronoi.util.MathUtil;

/**
 * Creates a <code>PanGraph</code> that represents Delaunay triangles.
 * See http://paulbourke.net/papers/triangulate/ for the algorithm used.
 * 
 * @author jeffreyrichley
 */
public class DelaunayTriangulation<R extends Triangle, S extends Arc, T extends Node> implements PanGraphFactory<Triangle, Arc, Node> {
	
	/**
	 * Used to speed up the finding of <code>Node</code>s that represent a given <code>Point</code>
	 */
	private Map<Point, Node> pointsToNodes = new HashMap<Point, Node>();

	/**
	 * Used to create new instances of the model objects
	 */
	private DelaunayModelFactory<R, S, T> modelFactory;
	
	/**
	 * Sets up the algorithm's initial needs
	 */
	public DelaunayTriangulation() {
		this.modelFactory = new DelaunayModelFactory<R, S, T>();
	}
	
	/* (non-Javadoc)
	 * @see com.infinity.delaunayvoronoi.algorithm.PanGraphFactory#createPanGraph(java.util.List)
	 */
	@Override
	public PanGraph<Triangle, Arc, Node> createPanGraph(List<Point> points) {
		List<Point> vertexList = new ArrayList<Point>(points);
		
		PanGraph<Triangle, Arc, Node> graph = new PanGraph<Triangle, Arc, Node>();
		
		// determine the super triangle
		List<Point> superTriangleNodes = findSuperTriangleNodes(vertexList);
		
		// add super triangle vertices to the end of the vertex list
		// TODO: don't think we need to do this, blows up if we actually do it
//		vertexList.addAll(superTriangleNodes);
		
		// add the super triangle to the triangle list
		addTriangleToGraph(graph, superTriangleNodes.get(0), 
								  superTriangleNodes.get(1), 
								  superTriangleNodes.get(2));
		
		// for each sample point in the vertex list
		for (Point point : vertexList) {
			// initialize the edge buffer
			List<Arc> edgeBuffer = new ArrayList<>();
			
			// for each triangle currently in the triangle list
			List<Polygon> tmpPolygons = new ArrayList<Polygon>(graph.getPolygons());
			for (Polygon polygon : tmpPolygons) {
				Triangle triangle = (Triangle) polygon;
				// calculate the triangle circumcircle center and radius - previously calculated
				
				// if the point lies in the triangle circumcircle then
				if (pointInCircumCircle(point, triangle)) {
					// add the three triangle edges to the edge buffer
					edgeBuffer.addAll(triangle.getBorders());
					
					// remove the triangle from the triangle list
					removeTriangleFromGraph(graph, triangle);
				}
			}
			
			// delete all doubly specified edges from the edge buffer
	        // this leaves the edges of the enclosing polygon only
			List<Arc> removedEdges = new ArrayList<Arc>();
			for (int i = 0; i < edgeBuffer.size() - 1; i++) {
				for (int j = i + 1; j < edgeBuffer.size(); j++) {
					Arc e1 = edgeBuffer.get(i);
					Arc e2 = edgeBuffer.get(j);
					if (e1.equals(e2)) {
						removedEdges.add(e1);
						removedEdges.add(e2);
					}
				}
			}
			edgeBuffer.removeAll(removedEdges);
			
			// add to the triangle list all triangles formed between the point 
	        // and the edges of the enclosing polygon
			for (Arc edge : edgeBuffer) {
				Point p2 = edge.getEndPoints().get(0).getPoint();
				Point p3 = edge.getEndPoints().get(1).getPoint();
				addTriangleToGraph(graph, point, p2, p3);
			}
		}
		
		// remove any triangles from the triangle list that use the super triangle vertices
		List<Triangle> badTriangles = new ArrayList<Triangle>();
		for (Polygon polygon : graph.getPolygons()) {
			Triangle triangle = (Triangle)polygon;
			if (triangle.sharesNode(superTriangleNodes)) {
				badTriangles.add(triangle);
			}
		}
		for (Triangle badTriangle : badTriangles) {
			graph.removePolygon(badTriangle);
		}
		
		// remove the super triangle vertices from the vertex list
		vertexList.removeAll(superTriangleNodes);
		
		return graph;
	}

	/**
	 * Determines if the <code>Point</code> is in the triangle's circumcircle
	 * @param point The location to check if it is in the circumcircle
	 * @param triangle The <code>Polygon</code> that is the triangle
	 * @return The radius of the triangle's circumcircle
	 */
	private boolean pointInCircumCircle(Point point, Triangle triangle) {
		double dx, dy, rsqr, drsqr;

		List<Node> corners = triangle.getCorners();
		Point p1 = corners.get(0).getPoint();
		Point p2 = corners.get(1).getPoint();
		Point p3 = corners.get(2).getPoint();
		
		// check if it is in the triangle's bounding box
		double circumRadius = triangle.getCircumCircleRadius();
		Point circumCenter = triangle.getCircumCircleCenter();
		double x = point.x;
		double y = point.y;
		double left = circumCenter.x - circumRadius;
		double right = circumCenter.x + circumRadius;
		double top = circumCenter.y - circumRadius;
		double bottom = circumCenter.y + circumRadius;
		if (x < left || x > right || y < top || y > bottom) {
			return false;
		}

		/* Check for coincident points */
		if (Math.abs(p1.y - p2.y) < MathUtil.EPSILON && 
			Math.abs(p2.y - p3.y) < MathUtil.EPSILON) {
			return false;
		}
		
		rsqr = circumRadius * circumRadius;

		dx = point.x - circumCenter.x;
		dy = point.y - circumCenter.y;
		drsqr = dx * dx + dy * dy;

		return drsqr <= rsqr;
	}

	/**
	 * Removes the triangle from the <code>PanGraph</code> but also
	 * undoes all of the handshakes between the <code>Polygon</code>s, 
	 * <code>Arc</code>s, and <code>Node</code>s.
	 * 
	 * @param graph The <code>PanGraph</code> to remove the triangle from
	 * @param triangle The <code>Polygon</code> to remove from the graph
	 */
	private void removeTriangleFromGraph(PanGraph<Triangle, Arc, Node> graph, Triangle triangle) {
		// remove the triangle from the graph
		graph.removePolygon(triangle);
		
		// unwire the triangle's edges
		for (Arc arc : triangle.getBorders()) {
			arc.removeBorderingPolygon(triangle);
		}
		
		// unwire the triangle's nodes
		for (Node node : triangle.getCorners()) {
			node.removeTouchingPolygon(triangle);
		}

		// remove dead arcs
		List<Arc> deadArcs = new ArrayList<Arc>();
		for (Arc arc : triangle.getBorders()) {
			if (arc.getBorderingPolygons().isEmpty()) {
				deadArcs.add(arc);
			}
		}
		for (Arc arc : deadArcs) {
			graph.removeArc(arc);
			// unwire polygons - done previously
			// unwire nodes
			for (Node node : arc.getEndPoints()) {
				node.removeArc(arc);
			}
		}
		
		// remove dead nodes
		List<Node> deadNodes = new ArrayList<Node>();
		for (Node node : triangle.getCorners()) {
			if (node.getTouchingPolygons().isEmpty()) {
				deadNodes.add(node);
			}
		}
		for (Node node : deadNodes) {
			graph.removeNode(node);
			// unwire polygons - done previously
			// unwire arcs
			// TODO: not sure if we need to unwire the arcs and nodes
//			for (Arc arc : node.getProtrudingArcs()) {
//				arc.removeEndPoint(node);
//			}
		}
	}

	/**
	 * Add the given <code>Point</code>s to the graph as <code>Node</code>s,
	 * <code>Arc</code>s, and a <code>Polygon</code> along with all of the 
	 * "handshakes" needed to keep the <code>PanGraph</code> in sync with 
	 * all of its components.
	 * @param graph The <code>PanGraph</code> to add the information to
	 * @param points The <code>Point</code>s to create the information from
	 */
	private void addTriangleToGraph(PanGraph<Triangle, Arc, Node> graph, Point... points) {
		// add all the nodes
		List<Node> nodes = new ArrayList<Node>();
		for (Point p : points) {
			Node n = pointsToNodes.get(p);
			if (n == null) {
				n = modelFactory.node(p);
				pointsToNodes.put(p, n);
				graph.addNode(n);
			}
			nodes.add(n);
		}
		
		// add all the edges
		List<Arc> edges = new ArrayList<Arc>();
		for (int i = 0; i < points.length - 1; i++) {
			for (int j = i + 1; j < points.length; j++) {
				Node one = nodes.get(i);
				Node two = nodes.get(j);
				
				Arc edge = null;
				// check to see if the edge already exists
				for (Arc a : one.getProtrudingArcs()) {
					if (a.getEndPoints().contains(two)) {
						edge = a;
						break;
					}
				}
				
				// if the edge is null, we need to create it
				if (edge == null) {
					edge = modelFactory.arc(one, two);
					graph.addArc(edge);
					
					// make the handshake with the new arc
					one.addAdjacentNode(two);
					two.addAdjacentNode(one);
					one.addProtrudingArc(edge);
					two.addProtrudingArc(edge);
				}
				edges.add(edge);
			}
		}
		
		// add the polygon
		Triangle triangle = modelFactory.polygon();
		graph.addPolygon(triangle);
		
		// make the handshake with the new polygon
		for (Arc arc : edges) {
			triangle.addBorder(arc);
			// before we connect the arc to polygon, 
			// get its other bordering polygon
			// and set it as a neighbor
			for (Polygon p : arc.getBorderingPolygons()) {
				triangle.addNeighboringPolygon(p);
			}
			arc.addBorderingPolygon(triangle);
		}
		for (Node node : nodes) {
			triangle.addCorner(node);
			node.addTouchingPolygon(triangle);
		}
		
		Circle circumCircle = MathUtil.calculateCircumCircle(triangle);
		triangle.setCircumCircleCenter(circumCircle.getCenter());
		triangle.setCircumCircleRadius(circumCircle.getRadius());
	}

	/**
	 * Find the nodes of the super triangle of the given <code>Point</code>s
	 * @param points The <code>List</code> of <code>Point</code>s to find a super triangle for
	 * @return The <code>List</code> of <code>Point</code>s for the edges of the super triangle
	 */
	private List<Point> findSuperTriangleNodes(List<Point> points) {
		// find the minimum and maximum x and y value
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = -Double.MAX_VALUE;
		double maxY = -Double.MAX_VALUE;
		
		for (Point p : points) {
			minX = Math.min(minX, p.x);
			minY = Math.min(minY, p.y);
			maxX = Math.max(maxX, p.x);
			maxY = Math.max(maxY, p.y);
		}
		
		// now that we have the minimum and maximum x and y,
		// we can make the bounding triangle's corners.  Take
		// the minimums and go a bit more negative and the 
		// maximums and double them.
		minX -= 10;
		minY -= 10;
		maxX *= 2;
		maxY *= 2;
		List<Point> corners = new ArrayList<Point>();
		
		corners.add(new Point(minX, minY));
		corners.add(new Point(minX, maxY));
		corners.add(new Point(maxX, minY));
		
		return corners;
	}

	/**
	 * Used to inject a factory that creates custom model instances
	 * @param modelFactory A custom factory to use for creating model instances
	 */
	public void setModelFactory(DelaunayModelFactory<R, S, T> modelFactory) {
		this.modelFactory = modelFactory;
	}

}