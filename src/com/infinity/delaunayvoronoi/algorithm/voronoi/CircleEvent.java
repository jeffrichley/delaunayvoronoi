package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

/**
 * An event that happens when the sweep line passes circumcircle radii 
 * @author Jeffrey.Richley
 */
public class CircleEvent extends Event {

	/**
	 * The parabola involved in the event
	 */
	private final Parabola parabola;

	/**
	 * Creates a new <code>CircleEvent</code>
	 * @param location Where the event occurred
	 * @param parabola The parabola involved in the event
	 */
	public CircleEvent(Point location, Parabola parabola) {
		super(location, false);
		
		this.parabola = parabola;
	}

	/**
	 * Get the parabola involved in the event
	 * @return The parabola involved in the event
	 */
	public Parabola getParabola() {
		return parabola;
	}
	
}
