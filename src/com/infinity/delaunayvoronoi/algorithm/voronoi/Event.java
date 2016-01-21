package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

/**
 * Holds event information for site and circle events for the Voronoi algorithm
 * @author Jeffrey.Richley
 */
public abstract class Event {

	/**
	 * The location that this event occurs
	 */
	private final Point location;
	
	/**
	 * Is this an site event? True if it is, false if it is a circle event 
	 */
	private final boolean siteEvent;

	/**
	 * Something needs to be taken care of at a certain location
	 * @param location Where the event occured
	 * @param siteEvent True if it is a site event, false if it is a circle event
	 */
	public Event(Point location, boolean siteEvent) {
		this.location = location;
		this.siteEvent = siteEvent;
	}

	/**
	 * Get the location that this event occurs
	 * @return The location that this event occurs
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Tells if this is a site event or not
	 * @return True if it is a site event, false if it is a circle event
	 */
	public boolean isSiteEvent() {
		return siteEvent;
	}
	
}
