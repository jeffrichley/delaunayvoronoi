package com.infinity.delaunayvoronoi.algorithm;

import java.util.List;

import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;

/**
 * Creates a PAN graph from a given <code>List</code> of <code>Point</code>s
 * 
 * @author jeffreyrichley
 */
public interface PanGraphFactory {

	/**
	 * Create PAN Graph from the given <code>List</code> of <code>Point</code>s
	 * @param points The <code>Point</code>s that originate the PAN Graph
	 * @return The PAN Graph created from the original <code>Point</code>s
	 */
	PanGraph createPanGraph(List<Point> points);
	
}
