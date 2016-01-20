package com.infinity.delaunayvoronoi.model;

/**
 * Adds information about the Triangle's circumcircle to a Polygon
 * @author jeffreyrichley
 */
public class Triangle extends Polygon {

	/**
	 * The center of the circumcircle 
	 */
	private Point circumCircleCenter;
	
	/**
	 * The radius of the circumcircle
	 */
	private double circumCircleRadius;

	/**
	 * Get the center of the circumcircle
	 * @return The center of the circumcircle
	 */
	public Point getCircumCircleCenter() {
		return circumCircleCenter;
	}

	/**
	 * Set the center of the circumcircle
	 * @param circumCircleCenter The center of the circumcircle
	 */
	public void setCircumCircleCenter(Point circumCircleCenter) {
		this.circumCircleCenter = circumCircleCenter;
	}

	/**
	 * Get the radius of the circumcircle
	 * @return The radius of the circumcircle
	 */
	public double getCircumCircleRadius() {
		return circumCircleRadius;
	}

	/**
	 * Set the circumcircle's radius
	 * @param circumCircleRadius The circumcircle's radius
	 */
	public void setCircumCircleRadius(double circumCircleRadius) {
		this.circumCircleRadius = circumCircleRadius;
	}
	
	
}
