package com.infinity.delaunayvoronoi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds references to all of the components of the graph
 * 
 * @author jeffreyrichley
 */
public class PanGraph {

	/**
	 * All <code>Polygon</code>s in the graph
	 */
	private List<Polygon> polygons = new ArrayList<Polygon>();
	
	/**
	 * All <code>Arc</code>s in the graph
	 */
	private List<Arc> arcs = new ArrayList<Arc>();
	
	/**
	 * All <code>Node</code>s in the graph
	 */
	private List<Node> nodes = new ArrayList<Node>();

	/**
	 * Get the list of all <code>Polygon</code>s
	 * @return An unmodifiable <code>List</code> of all the <code>Polygon</code>s
	 */
	public List<Polygon> getPolygons() {
		return Collections.unmodifiableList(polygons);
	}

	/**
	 * Get the list of all <code>Arc</code>s
	 * @return An unmodifiable <code>List</code> of all the <code>Arc</code>s
	 */
	public List<Arc> getArcs() {
		return Collections.unmodifiableList(arcs);
	}

	/**
	 * Get the list of all <code>Node</code>s
	 * @return An unmodifiable <code>List</code> of all the <code>Node</code>s
	 */
	public List<Node> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	/**
	 * Add a <code>Node</code> to the graph
	 * @param node The <code>Node</code> to add
	 */
	public void addNode(Node node) {
		nodes.add(node);
	}

	/**
	 * Add a <code>Arc</code> to the graph
	 * @param arc The <code>Arc</code> to add
	 */
	public void addArc(Arc arc) {
		arcs.add(arc);
	}

	/**
	 * Removes the <code>Arc</code> from the graph
	 * @param arc The <code>Arc</code> to remove
	 */
	public void removeArc(Arc arc) {
		arcs.remove(arc);
	}

	/**
	 * Add a <code>Polygon</code> to the graph
	 * @param polygon The <code>Polygon</code> to add
	 */
	public void addPolygon(Polygon polygon) {
		polygons.add(polygon);
	}

	/**
	 * Removes the <code>Polygon</code> from the graph 
	 * @param polygon The <code>Polygon</code> to remove
	 */
	public void removePolygon(Polygon polygon) {
		polygons.remove(polygon);
	}

	/**
	 * Removes the <code>Node</code> from the graph 
	 * @param node The <code>Node</code> to remove
	 */
	public void removeNode(Node node) {
		nodes.remove(node);
	}
	
}
