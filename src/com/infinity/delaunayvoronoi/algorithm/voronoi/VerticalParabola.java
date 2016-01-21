package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

/**
 * Represents the sweeting parabolas in the Voronoi algorithm
 * @author Jeffrey.Richley
 */
public class VerticalParabola {

	/**
	 * The top of the parabola
	 */
	private final Point site;
	
	/**
	 * Is this a leaf on the tree?
	 */
	boolean leaf;
	
	

	/**
	 * Creates a new VerticalParabola
	 * @param site
	 */
	public VerticalParabola(Point site) {
		this.site = site;
		leaf = true;
	}
	
}
