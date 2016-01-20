package com.infinity.delaunayvoronoi.factory;

import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;

/**
 * Creates models for <code>ModelFactory</code> implementations.  Typically
 * used for injecting outside models into the system.
 * @author Jeffrey.Richley
 *
 * @param <R> A class that extends Polygon
 * @param <S> A class that extends Arc
 * @param <T> A class that extends Node
 */
public interface ModelFactory<R extends Polygon, S extends Arc, T extends Node> {

	/**
	 * Get a new instance of a <code>Polygon</code> type
	 * @return A new instance of a <code>Polygon</code> type
	 */
	R polygon();
	
	/**
	 * Get a new instance of a <code>Arc</code> type
	 * @return A new instance of a <code>Arc</code> type
	 */
	S arc(Node one, Node two);
	
	/**
	 * Get a new instance of a <code>Node</code> type
	 * @return A new instance of a <code>Node</code> type
	 */
	T node(Point point);
	
}
