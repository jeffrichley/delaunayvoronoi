package com.infinity.delaunayvoronoi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the corners of shapes.  Includes the list of <code>Polygon</code>s that
 * contain this <code>Node</code> as corners, <code>Arc</code>s that contain it as an endpoint,
 * and the <code>Node</code>s that can be accessed through the <code>Arc</code>s.
 * 
 * @author jeffreyrichley
 */
public class Node {

	/**
	 * All <code>Polygon</code>s that have this <code>Node</code> as a corner
	 */
	private List<Polygon> touchingPolygons = new ArrayList<Polygon>();

	/**
	 * All <code>Edge</code>s that have this <code>Node</code> as an endpoint
	 */
	private List<Arc> protrudingArcs = new ArrayList<Arc>();

	/**
	 * All <code>Node</code>s that are connected by the protruding <code>Arc</code>s
	 */
	private List<Node> adjacentNodes = new ArrayList<Node>();

	/**
	 * The location of this <code>Node</code>
	 */
	private final Point point;
	
	/**
	 * Creates a new <code>Node</code> 

	 * @param point The location of this <code>Node</code>
	 */
	public Node(Point point) {
		this.point = point;
	}

	/**
	 * Get the list of all <code>Polygon</code> that touch this <code>Nodes</code>
	 * @return An unmodifiable <code>List</code> of the <code>Polygon</code>s that touch this <code>Node</code>
	 */
	public List<Polygon> getTouchingPolygons() {
		return Collections.unmodifiableList(touchingPolygons);
	}

	/**
	 * Get the list of all <code>Arc</code> that protrude from this <code>Nodes</code>
	 * @return An unmodifiable <code>List</code> of the <code>Arc</code>s that protrude from this <code>Node</code>
	 */
	public List<Arc> getProtrudingArcs() {
		return Collections.unmodifiableList(protrudingArcs);
	}

	/**
	 * Get the list of all <code>Nodes</code> that are adjacent to this <code>Nodes</code> 
	 * @return An unmodifiable <code>List</code> of the <code>Node</code>s that are adjacent to this <code>Node</code>
	 */
	public List<Node> getAdjacentNodes() {
		return Collections.unmodifiableList(adjacentNodes);
	}

	/**
	 * Get the location of this <code>Node</code>
	 * @return The location of this <code>Node</code>
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * Add a <code>Polygon</code> that touches this <code>Node</code>
	 * @param polygon
	 */
	public void addTouchingPolygon(Polygon polygon) {
		if (!touchingPolygons.contains(polygon)) {
			touchingPolygons.add(polygon);
		}
	}

	/**
	 * Add a <code>Arc</code> that protrudes from this one
	 * @param other The new <code>Arc</code>
	 */
	public void addProtrudingArc(Arc other) {
		if (!protrudingArcs.contains(other)) {
			protrudingArcs.add(other);
		}
	}

	/**
	 * Add a <code>Node</code> that is reachable from this one
	 * @param other The new <code>Node</code> 
	 */
	public void addAdjacentNode(Node other) {
		if (!adjacentNodes.contains(other)) {
			adjacentNodes.add(other);
		}
	}

	/**
	 * Remove the <code>Arc</code> from the protruding list
	 * @param arc The <code>Arc</code> to remove
	 */
	public void removeArc(Arc arc) {
		protrudingArcs.remove(arc);
	}
	
	/**
	 * Remove the <code>Polygon</code> from the touching list
	 * @param arc The <code>Polygon</code> to remove
	 */
	public void removeTouchingPolygon(Polygon polygon) {
		touchingPolygons.remove(polygon);
	}
	
}
