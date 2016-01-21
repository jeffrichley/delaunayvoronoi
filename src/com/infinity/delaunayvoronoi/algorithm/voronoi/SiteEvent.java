package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

/**
 * Convenience class for creating site events
 * @author Jeffrey.Richley
 */
public class SiteEvent extends Event {

	/**
	 * Creates a new <code>SiteEvent</code>
	 * @param location Where the event occurs
	 */
	public SiteEvent(Point location) {
		super(location, true);
	}

}
