package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

/**
 * Represents the sweeping <code>Parabola</code>s in the Voronoi algorithm.
 * @author Jeffrey.Richley
 */
public class Parabola implements BeachEntry {
	
	private boolean isLeaf;

	/**
	 * The top of the <code>Parabola</code>
	 */
	private final Point site;
	
	/**
	 * The event created from this <code>Parabola</code>
	 */
	private Event cEvent;
	
	private Edge edge;
	
	private Parabola parent;
	
	private Parabola left;
	
	private Parabola right;
	
	
	public Parabola() {
		this.site = null;
		this.isLeaf = false;
	}
	
	/**
	 * Creates a new VerticalParabola
	 * @param site
	 */
	public Parabola(Point site) {
		this.site = site;
		this.isLeaf = true;
	}

	public void setLeft(Parabola p) {
		this.left = p;
		p.setParent(this);
	}
	
	public void setRight(Parabola p) {
		this.right = p;
		p.setParent(this);
	}
	
	public Parabola getLeft() {
		return left;
	}
	
	public Parabola getRight() {
		return right;
	}

	/**
	 * Get the site that originated the <code>Parabola</code>
	 * @return
	 */
	public Point getSite() {
		return site;
	}


	/**
	 * Get the <code>CircleEvent</code>
	 * @return The <code>CircleEvent</code>
	 */
	public Event getCircleEvent() {
		return cEvent;
	}


	/**
	 * The <code>CircleEvent</code> for this <code>Parabola</code>
	 * @param circleEvent The <code>CircleEvent</code> for this <code>Parabola</code>
	 */
	public void setCircleEvent(Event circleEvent) {
		this.cEvent = circleEvent;
	}


	/**
	 * @return the edge
	 */
	public Edge getEdge() {
		return edge;
	}


	/**
	 * @param edge the edge to set
	 */
	public void setEdge(Edge edge) {
		this.edge = edge;
	}


	public Parabola getParent() {
		return parent;
	}


	public void setParent(Parabola parent) {
		this.parent = parent;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
}
