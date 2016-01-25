package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

/**
 * Represents the sweeping <code>Parabola</code>s in the Voronoi algorithm.
 * @author Jeffrey.Richley
 */
public class Parabola implements BeachEntry {

	/**
	 * The top of the <code>Parabola</code>
	 */
	private final Point site;
	
	/**
	 * The event created from this <code>Parabola</code>
	 */
	private CircleEvent circleEvent;
	
	private Edge edge;
	
	/**
	 * Creates a new VerticalParabola
	 * @param site
	 */
	public Parabola(Point site) {
		this.site = site;
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
	public CircleEvent getCircleEvent() {
		return circleEvent;
	}


	/**
	 * The <code>CircleEvent</code> for this <code>Parabola</code>
	 * @param circleEvent The <code>CircleEvent</code> for this <code>Parabola</code>
	 */
	public void setCircleEvent(CircleEvent circleEvent) {
		this.circleEvent = circleEvent;
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
	
}
