package com.infinity.delaunayvoronoi.algorithm;

import java.util.List;

import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;

/**
 * Creates a PAN graph from a given <code>List</code> of <code>Point</code>s
 * 
 * @author jeffreyrichley
 */
public interface PanGraphFactory<R extends Polygon, S extends Arc, T extends Node> {

	/**
	 * Create PAN Graph from the given <code>List</code> of <code>Point</code>s
	 * @param points The <code>Point</code>s that originate the PAN Graph
	 * @return The PAN Graph created from the original <code>Point</code>s
	 */
	PanGraph<R, S, T> createPanGraph(List<Point> points);
	
}
