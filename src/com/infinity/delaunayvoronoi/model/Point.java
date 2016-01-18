package com.infinity.delaunayvoronoi.model;

/**
 * A pair of x, y coordinates

 * @author jeffreyrichley
 */
public class Point {

	/**
	 * The x component of this <code>Point</code>.  Yes,
	 * this is public, but only for speed due to the number
	 * of times it is accessed while building the graph.
	 */
	public final double x;
	
	/**
	 * The y component of this <code>Point</code>.  Yes,
	 * this is public, but only for speed due to the number
	 * of times it is accessed while building the graph.
	 */
	public final double y; 
	
	/**
	 * Constructs a new <code>Point</code>
	 * @param x The x component of this <code>Point</code>
	 * @param y The y component of this <code>Point</code>
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
}
