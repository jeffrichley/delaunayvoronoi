package com.infinity.delaunayvoronoi.util;

import java.util.List;

import com.infinity.delaunayvoronoi.model.Circle;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Triangle;

/**
 * Reusable math utilities
 * @author Jeffrey.Richley
 */
public class MathUtil {
	
	/**
	 * Used to represent a number very close to 0 without being 0 
	 * for divide by 0 problems
	 */
	public static final double EPSILON = 1e-8f;

	/**
	 * Calculates the circumcircle of a given <code>Triangle</code>
	 * @param triangle The <code>Triangle</code> to calculate a circumcircle for
	 * @return A <code>Circle</code> describing the circumcircle of the <code>Triangle</code>
	 */
	public static Circle calculateCircumCircle(Triangle triangle) {
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

		Point center = new Point(x, y);
		
		double dx = p1.x - center.x;
		double dy = p1.y - center.y;
		double rsqr = dx * dx + dy * dy;

		double radius = Math.sqrt(rsqr);
		
		return new Circle(center, radius);
	}
	
}
