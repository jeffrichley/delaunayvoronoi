package com.infinity.delaunayvoronoi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a multi-edged, closed <code>Polygon</code>.  Contains lists for its
 * neighboring <code>Polygon</code>, border <code>Arc</code>s, and <code>Node</code>s.
 * @author jeffreyrichley
 */
public class Polygon {

	/**
	 * Adjacent <code>Polygon</code>s
	 */
	private List<Polygon> neighbors = new ArrayList<Polygon>();

	/**
	 * Bordering <code>Arc</code>s
	 */
	private List<Arc> borders = new ArrayList<Arc>();

	/**
	 * Corners of this <code>Polygon</code>
	 */
	private List<Node> corners = new ArrayList<Node>();

	/**
	 * Get the <code>Polygon</code>s that neighbor this <code>Polygon</code>
	 * @return An unmodifiable <code>List</code> of the <code>Polygon</code>s that neighbor this <code>Polygon</code>
	 */
	public List<Polygon> getNeighbors() {
		return Collections.unmodifiableList(neighbors);
	}

	/**
	 * Get the <code>Arc</code>s that border this <code>Polygon</code>
	 * @return An unmodifiable <code>List</code> of the bordering <code>Arc</code>s of this <code>Polygon</code>
	 */
	public List<Arc> getBorders() {
		return Collections.unmodifiableList(borders);
	}

	/**
	 * Get the corner <code>Node</code>s of this <code>Polygon</code>
	 * @return An unmodifiable <code>List</code> of the corner <code>Node</code>s of this <code>Polygon</code>
	 */
	public List<Node> getCorners() {
		return Collections.unmodifiableList(corners);
	}

	/**
	 * Add an <code>Arc</code> as a border
	 * @param arc The <code>Arc</code> to add to the borders
	 */
	public void addBorder(Arc arc) {
		if (!borders.contains(arc)) {
			borders.add(arc);
		}
	}

	/**
	 * Add a <code>Node</code> as a corner
	 * @param node The <code>Node</code> to add as a corner
	 */
	public void addCorner(Node node) {
		if (!corners.contains(node)) {
			corners.add(node);
		}
	}
	
	/**
	 * Add a neighboring <code>Polygon</code>
	 * @param other The neighboring <code>Polygon</code>
	 */
	public void addNeighboringPolygon(Polygon other) {
		if (this != other && !neighbors.contains(other)) {
			neighbors.add(other);
		}
	}

	/**
	 * Given a <code>List</code> of <code>Point</code>s, do any 
	 * of them make up the corners of this <code>Polygon</code>
	 * @param otherCorners A <code>List</code> of <code>Point</code>s to check
	 * @return True if any of the corners are the same
	 */
	public boolean sharesNode(List<Point> otherCorners) {
		for (Point point : otherCorners) {
			for (Node node : corners) {
				if (point == node.getPoint()) {
					return true;
				}
			}
		}
		return false;
	}
}
