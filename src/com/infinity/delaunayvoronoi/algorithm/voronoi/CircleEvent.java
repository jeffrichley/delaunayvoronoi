package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Circle;
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
	 * The circumcircle of the three points that created this event 
	 */
	private final Circle circumCircle;

	/**
	 * Creates a new <code>CircleEvent</code>
	 * @param location Where the event occurred
	 * @param parabola The parabola involved in the event
	 * @param circumCircle The circumcircle of the three <code>Point</code>s
	 */
	public CircleEvent(Point location, Parabola parabola, Circle circumCircle) {
		super(location, false);
		
		this.parabola = parabola;
		this.circumCircle = circumCircle;
	}

	/**
	 * Get the parabola involved in the event
	 * @return The parabola involved in the event
	 */
	public Parabola getParabola() {
		return parabola;
	}

	/**
	 * Get the circumcircle of this event
	 * @return The circumcircle of this event
	 */
	public Circle getCircumCircle() {
		return circumCircle;
	}
	
}
