package com.infinity.delaunayvoronoi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the <code>Arc</code>s between <code>Polygon</code>s. Contains the
 * two <code>Polygon</code>s it separates for the Delaunay and Voronoi shapes.
 * 
 * @author jeffreyrichley
 */
public class Arc {

	/**
	 * The two end points
	 */
	private final List<Node> endPoints = new ArrayList<Node>();
	
	/**
	 * The <code>Polygon</code>s that this <code>Arc</code> borders
	 */
	private final List<Polygon> borderingPolygons = new ArrayList<Polygon>();

//	/**
//	 * First touching Delaunay <code>Polygon</code>
//	 */
//	private Polygon firstDelaunayPolygon;
//
//	/**
//	 * Second touching Delaunay <code>Polygon</code>
//	 */
//	private Polygon secondDelaunayPolygon;

	/**
	 * Creates a new <code>Arc</code> with the given end points
	 * @param nodeOne The first end point
	 * @param nodeTwo The second end point
	 */
	public Arc(Node nodeOne, Node nodeTwo) {
		endPoints.add(nodeOne);
		endPoints.add(nodeTwo);
	}
	
	/**
	 * Get the two end points
	 * @return An unmodifiable <code>List</code> of the two end points
	 */
	public List<Node> getEndPoints() {
		return Collections.unmodifiableList(endPoints);
	}

	/**
	 * Get the bordering <code>Polygon</code>s of this <code>Arc</code>
	 * @return An unmodifiable <code>List</code> of bordering <code>Polygon</code>s
	 */
	public List<Polygon> getBorderingPolygons() {
		return Collections.unmodifiableList(borderingPolygons);
	}
	
	/**
	 * Add a bordering <code>Polygon</code>
	 * @param polygon The bordering <code>Polygon</code>
	 */
	public void addBorderingPolygon(Polygon polygon) {
		if (!borderingPolygons.contains(polygon)) {
			borderingPolygons.add(polygon);
		}
	}

	/**
	 * Remove a bordering <code>Polygon</code>
	 * @param triangle The bordering <code>Polygon</code> to remove
	 */
	public void removeBorderingPolygon(Polygon triangle) {
		borderingPolygons.remove(triangle);
	}
	
	public void removeEndPoint(Node node) {
		endPoints.remove(node);
	}

//	/**
//	 * First Voronoi <code>Node</code>
//	 */
//	private Node firstVoronoiNode;
//
//	/**
//	 * Second Voronoi <code>Node</code>
//	 */
//	private Node secondVoronoiNode;

//	/**
//	 * Get the first Delaunay <code>Polygon</code>
//	 * @return The first Delaunay <code>Polygon</code>
//	 */
//	public Polygon getFirstDelaunayPolygon() {
//		return firstDelaunayPolygon;
//	}
//
//	/**
//	 * Set the first Delaunay <code>Polygon</code>
//	 * @return The first Delaunay <code>Polygon</code>
//	 */
//	public void setFirstDelaunayPolygon(Polygon firstDelaunayPolygon) {
//		this.firstDelaunayPolygon = firstDelaunayPolygon;
//	}

//	/**
//	 * Get the second Delaunay <code>Polygon</code>
//	 * @return The second Delaunay <code>Polygon</code>
//	 */
//	public Polygon getSecondDelaunayPolygon() {
//		return secondDelaunayPolygon;
//	}
//
//	/**
//	 * Set the second Delaunay <code>Polygon</code>
//	 * @return The second Delaunay <code>Polygon</code>
//	 */
//	public void setSecondDelaunayPolygon(Polygon secondDelaunayPolygon) {
//		this.secondDelaunayPolygon = secondDelaunayPolygon;
//	}

//	/**
//	 * Get the first Voronoi <code>Polygon</code>
//	 * @return The first Voronoi <code>Polygon</code>
//	 */
//	public Node getFirstVoronoiNode() {
//		return firstVoronoiNode;
//	}
//
//	/**
//	 * Set the first Voronoi <code>Polygon</code>
//	 * @return The first Voronoi <code>Polygon</code>
//	 */
//	public void setFirstVoronoiNode(Node firstVoronoiNode) {
//		this.firstVoronoiNode = firstVoronoiNode;
//	}
//
//	/**
//	 * Get the second Voronoi <code>Polygon</code>
//	 * @return The second Voronoi <code>Polygon</code>
//	 */
//	public Node getSecondVoronoiNode() {
//		return secondVoronoiNode;
//	}
//
//	/**
//	 * Set the second Voronoi <code>Polygon</code>
//	 * @return The second Voronoi <code>Polygon</code>
//	 */
//	public void setSecondVoronoiNode(Node secondVoronoiNode) {
//		this.secondVoronoiNode = secondVoronoiNode;
//	}

}
