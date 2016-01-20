package com.infinity.delaunayvoronoi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds references to all of the components of the graph
 * 
 * @author jeffreyrichley
 */
public class PanGraph<R extends Polygon, S extends Arc, T extends Node> {

	/**
	 * All <code>Polygon</code>s in the graph
	 */
	private List<R> polygons = new ArrayList<R>();
	
	/**
	 * All <code>Arc</code>s in the graph
	 */
	private List<S> arcs = new ArrayList<S>();
	
	/**
	 * All <code>Node</code>s in the graph
	 */
	private List<T> nodes = new ArrayList<T>();

	/**
	 * Get the list of all <code>Polygon</code>s
	 * @return An unmodifiable <code>List</code> of all the <code>Polygon</code>s
	 */
	public List<R> getPolygons() {
		return Collections.unmodifiableList(polygons);
	}

	/**
	 * Add a <code>Polygon</code> to the graph
	 * @param polygon The <code>Polygon</code> to add
	 */
	public void addPolygon(R polygon) {
		polygons.add(polygon);
	}

	/**
	 * Removes the <code>Polygon</code> from the graph 
	 * @param polygon The <code>Polygon</code> to remove
	 */
	public void removePolygon(R polygon) {
		polygons.remove(polygon);
	}

	/**
	 * Get the list of all <code>Arc</code>s
	 * @return An unmodifiable <code>List</code> of all the <code>Arc</code>s
	 */
	public List<S> getArcs() {
		return Collections.unmodifiableList(arcs);
	}

	/**
	 * Add a <code>Arc</code> to the graph
	 * @param arc The <code>Arc</code> to add
	 */
	public void addArc(S arc) {
		arcs.add(arc);
	}

	/**
	 * Removes the <code>Arc</code> from the graph
	 * @param arc The <code>Arc</code> to remove
	 */
	public void removeArc(S arc) {
		arcs.remove(arc);
	}

	/**
	 * Get the list of all <code>Node</code>s
	 * @return An unmodifiable <code>List</code> of all the <code>Node</code>s
	 */
	public List<T> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	/**
	 * Add a <code>Node</code> to the graph
	 * @param node The <code>Node</code> to add
	 */
	public void addNode(T node) {
		nodes.add(node);
	}

	/**
	 * Removes the <code>Node</code> from the graph 
	 * @param node The <code>Node</code> to remove
	 */
	public void removeNode(T node) {
		nodes.remove(node);
	}
	
}
