package com.infinity.delaunayvoronoi.factory;

import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.SpecialTriangle;

public class SpecialDelaunayFactory extends DelaunayModelFactory<SpecialTriangle, Arc, Node> {

	@Override
	public SpecialTriangle polygon() {
		return new SpecialTriangle();
	}
	
}
