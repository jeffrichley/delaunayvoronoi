package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

/**
 * Holds event information for site and circle events for the Voronoi algorithm
 * @author Jeffrey.Richley
 */
public class Event {

	/**
	 * The location that this event occurs
	 */
	public final Point point;
	
	// TODO: think this is for if it is a site event or circle event
	public final boolean pe;

	public final double y;
	
	public final double x;
	
	private Parabola arch;

	/**
	 * Something needs to be taken care of at a certain location
	 * @param location Where the event occured
	 * @param siteEvent True if it is a site event, false if it is a circle event
	 */
	public Event(Point p, boolean pev) {
		this.point = p;
		this.pe = pev;
		this.y = p.y;
		this.x = p.x;
		this.arch = null;
	}

	public Parabola getArch() {
		return arch;
	}

	public void setArch(Parabola arch) {
		this.arch = arch;
	}

	
}
