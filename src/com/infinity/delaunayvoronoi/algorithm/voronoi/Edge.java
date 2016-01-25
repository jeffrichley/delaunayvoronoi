package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

public class Edge implements BeachEntry {

	public final Point start;
	private Point end;
	public final Point direction;
	private final Point left;
	private final Point right;
	
	// f, g	: directional coefficients satisfying equation y = f*x + g (edge lies on this line)
	public final double f;
	public final double g;
	
	private Edge neighbor;
	
//	public Edge(Point start /* s */, Point left /* a */, Point right /* b */) {
	public Edge(Point s, Point a, Point b) {
//		this.start = start;
//		this.left = left;
//		this.right = right;
//		
//		f = (right.x - left.x) / (left.y - right.y) ;
//		g = start.y - f * start.x ;
//		direction = new Point(right.y - left.y, -(right.x - left.x));
		
		
		start		= s;
		left		= a;
		right		= b;
		neighbor	= null;
		end			= null;

		f = (b.x - a.x) / (a.y - b.y) ;
		g = s.y - f * s.x ;
		direction = new Point(b.y - a.y, -(b.x - a.x));
	}
	
	/**
	 * @return the end
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Point end) {
		this.end = end;
	}

	public Point getLeft() {
		return left;
	}

	public Point getRight() {
		return right;
	}

	/**
	 * @return the neighbor
	 */
	public Edge getNeighbor() {
		return neighbor;
	}

	/**
	 * @param neighbor the neighbor to set
	 */
	public void setNeighbor(Edge neighbor) {
		this.neighbor = neighbor;
	}

}
