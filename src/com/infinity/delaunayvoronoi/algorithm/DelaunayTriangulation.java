package com.infinity.delaunayvoronoi.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;
import com.infinity.delaunayvoronoi.util.Tick;

/**
 * Creates a <code>PanGraph</code> that represents Delaunay triangles.
 * See http://paulbourke.net/papers/triangulate/ for the algorithm used.
 * 
 * @author jeffreyrichley
 */
public class DelaunayTriangulation implements PanGraphFactory {
	
	/**
	 * Used to represent a number very close to 0 without being 0 
	 * for divide by 0 problems
	 */
	public static final double EPSILON = 1e-8f;
	
	/**
	 * Used to speed up the finding of <code>Node</code>s that represent a given <code>Point</code>
	 */
	private Map<Point, Node> pointsToNodes = new HashMap<Point, Node>();
	
	/**
	 * Maps <code>Polygon</code>s to their circumcenters
	 */
	private Map<Polygon, Point> circumCenters = new HashMap<Polygon, Point>();
	
	/**
	 * Maps <code>Polygon</code>s to their circumradii
	 */
	private Map<Polygon, Double> circumRadii = new HashMap<Polygon, Double>();
	
	/* (non-Javadoc)
	 * @see com.infinity.delaunayvoronoi.algorithm.PanGraphFactory#createPanGraph(java.util.List)
	 */
	@Override
	public PanGraph createPanGraph(List<Point> points) {
		List<Point> vertexList = new ArrayList<Point>(points);
		
		PanGraph graph = new PanGraph();
		
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
			for (Polygon triangle : tmpPolygons) {
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
		List<Polygon> badTriangles = new ArrayList<Polygon>();
		for (Polygon triangle : graph.getPolygons()) {
			if (triangle.sharesNode(superTriangleNodes)) {
				badTriangles.add(triangle);
			}
		}
		for (Polygon badTriangle : badTriangles) {
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
	private boolean pointInCircumCircle(Point point, Polygon triangle) {
		double dx, dy, rsqr, drsqr;

		List<Node> corners = triangle.getCorners();
		Point p1 = corners.get(0).getPoint();
		Point p2 = corners.get(1).getPoint();
		Point p3 = corners.get(2).getPoint();
		
		// check if it is in the triangle's bounding box
		double x = point.x;
		double y = point.y;
		if ((x < p1.x && x < p2.x && x < p3.x) || (x > p1.x && x > p2.x && x > p3.x) ||
			(y < p1.y && y < p2.y && y < p3.y) || (y > p1.y && y > p2.y && y > p3.y)) {
			return false;
		}
		
		Point circumCenter = getCircumCenter(triangle);
		double circumRadius = getCircumRadius(triangle);

		/* Check for coincident points */
		if (Math.abs(p1.y - p2.y) < EPSILON && 
			Math.abs(p2.y - p3.y) < EPSILON) {
			return false;
		}
		
		rsqr = circumRadius * circumRadius;

		dx = point.x - circumCenter.x;
		dy = point.y - circumCenter.y;
		drsqr = dx * dx + dy * dy;

		return drsqr <= rsqr;
	}

	/**
	 * Calculate the circumcenter of the given triangle
	 * @param triangle The <code>Polygon</code> to use for calculations
	 * @return The <code>Point</code> at the circumcenter
	 */
	private Point getCircumCenter(Polygon triangle) {
		Point center = circumCenters.get(triangle);
		if (center != null) {
			return center;
		}
		
		List<Node> corners = triangle.getCorners();
		Point p1 = corners.get(0).getPoint();
		Point p2 = corners.get(1).getPoint();
		Point p3 = corners.get(2).getPoint();
		
		double m1, m2, mx1, mx2, my1, my2;
		
		double x = -1;
		double y = -1;
		
		if (Math.abs(p2.y - p1.y) < EPSILON) {
			m2 = -(p3.x - p2.x) / (p3.y - p2.y);
			mx2 = (p2.x + p3.x) / 2.0f;
			my2 = (p2.y + p3.y) / 2.0f;
			x = (p2.x + p1.x) / 2.0f;
			y = m2 * (x - mx2) + my2;
		} else if (Math.abs(p3.y - p2.y) < EPSILON) {
			m1 = -(p2.x - p1.x) / (p2.y - p1.y);
			mx1 = (p1.x + p2.x) / 2.0f;
			my1 = (p1.y + p2.y) / 2.0f;
			x = (p3.x + p2.x) / 2.0f;
			y = m1 * (x - mx1) + my1;
		} else {
			m1 = -(p2.x - p1.x) / (p2.y - p1.y);
			m2 = -(p3.x - p2.x) / (p3.y - p2.y);
			mx1 = (p1.x + p2.x) / 2.0f;
			mx2 = (p2.x + p3.x) / 2.0f;
			my1 = (p1.y + p2.y) / 2.0f;
			my2 = (p2.y + p3.y) / 2.0f;
			x = (m1 * mx1 - m2 * mx2 + my2 - my1) / (m1 - m2);
			y = m1 * (x - mx1) + my1;
		}

		center = new Point(x, y);
		circumCenters.put(triangle, center);
		
		return center;
	}

	/**
	 * Calculate the circumradius of the given triangle
	 * @param triangle The <code>Polygon</code> to use for calculations
	 * @return The radius of the circumcircle
	 */
	private double getCircumRadius(Polygon triangle) {
		Double radius = circumRadii.get(triangle);
		if (radius != null) {
			return radius;
		}
		
		Point circumCenter = circumCenters.get(triangle);
		Point p = triangle.getCorners().get(0).getPoint();
		
		double dx = p.x - circumCenter.x;
		double dy = p.y - circumCenter.y;
		double rsqr = dx * dx + dy * dy;

		radius = Math.sqrt(rsqr);
		circumRadii.put(triangle, radius);
		
		return radius;
	}

	/**
	 * Removes the triangle from the <code>PanGraph</code> but also
	 * undoes all of the handshakes between the <code>Polygon</code>s, 
	 * <code>Arc</code>s, and <code>Node</code>s.
	 * 
	 * @param graph The <code>PanGraph</code> to remove the triangle from
	 * @param triangle The <code>Polygon</code> to remove from the graph
	 */
	private void removeTriangleFromGraph(PanGraph graph, Polygon triangle) {
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
	private void addTriangleToGraph(PanGraph graph, Point... points) {
		// add all the nodes
		List<Node> nodes = new ArrayList<Node>();
		for (Point p : points) {
			Node n = pointsToNodes.get(p);
			if (n == null) {
				n = new Node(p);
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
					edge = new Arc(one, two);
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
		Polygon polygon = new Polygon();
		graph.addPolygon(polygon);
		
		// make the handshake with the new polygon
		for (Arc arc : edges) {
			polygon.addBorder(arc);
			// before we connect the arc to polygon, 
			// get its other bordering polygon
			// and set it as a neighbor
			for (Polygon p : arc.getBorderingPolygons()) {
				polygon.addNeighboringPolygon(p);
			}
			arc.addBorderingPolygon(polygon);
		}
		for (Node node : nodes) {
			polygon.addCorner(node);
			node.addTouchingPolygon(polygon);
		}
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

}
