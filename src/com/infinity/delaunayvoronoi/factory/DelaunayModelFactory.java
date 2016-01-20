package com.infinity.delaunayvoronoi.factory;

import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.Triangle;

/**
 * Extends the base <code>ModelFactory</code> but instantiates a <code>Triangle</code> instead of <code>Polygon</code>
 * @author Jeffrey.Richley
 */
public class DelaunayModelFactory<R extends Triangle, S extends Arc, T extends Node> extends BaseModelFactory<Triangle, Arc, Node> {

	/* (non-Javadoc)
	 * @see com.infinity.delaunayvoronoi.factory.BaseModelFactory#polygon()
	 */
	@Override
	public Triangle polygon() {
		return new Triangle();
	}
	
}
