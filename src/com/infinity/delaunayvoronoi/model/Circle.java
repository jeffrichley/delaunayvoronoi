package com.infinity.delaunayvoronoi.model;

/**
 * Represents the circumcircle of a <code>Triangle</code>
 * @author Jeffrey.Richley
 */
public class Circle {

	/**
	 * The center of the <code>Circle</code>
	 */
	private final Point center;
	
	/**
	 * The radius of the circle 
	 */
	private final double radius;

	/**
	 * Creates a new <code>Circle</code>
	 * @param center The center of the <code>Circle</code>
	 * @param radius The radius of the <code>Circle</code>
	 */
	public Circle(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}

	/**
	 * Get the center of the <code>Code</code>
	 * @return The <code>Point</code> that is the center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Get the radius of the <code>Circle</code>
	 * @return The radius of the <code>Circle</code>
	 */
	public double getRadius() {
		return radius;
	}
	
}
