package com.infinity.delaunayvoronoi.factory;

import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;

/**
 * Implements the default behavior by instantiating <code>Polygon</code>, <code>Arc</code>, and <code>Node</code> instances
 * @author Jeffrey.Richley
 *
 * @param <R> Defaults to a Polygon
 * @param <S> Defaults to an Arc
 * @param <T> Defaults to a Node
 */
public class BaseModelFactory<R extends Polygon, S extends Arc, T extends Node> implements ModelFactory<Polygon, Arc, Node> {

	/* (non-Javadoc)
	 * @see com.infinity.delaunayvoronoi.factory.ModelFactory#polygon()
	 */
	@Override
	public Polygon polygon() {
		return new Polygon();
	}

	/* (non-Javadoc)
	 * @see com.infinity.delaunayvoronoi.factory.ModelFactory#arc(com.infinity.delaunayvoronoi.model.Node, com.infinity.delaunayvoronoi.model.Node)
	 */
	@Override
	public Arc arc(Node one, Node two) {
		return new Arc(one, two);
	}

	/* (non-Javadoc)
	 * @see com.infinity.delaunayvoronoi.factory.ModelFactory#node(com.infinity.delaunayvoronoi.model.Point)
	 */
	@Override
	public Node node(Point point) {
		return new Node(point);
	}

}
