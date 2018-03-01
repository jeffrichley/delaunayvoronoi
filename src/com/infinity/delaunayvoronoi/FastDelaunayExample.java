package com.infinity.delaunayvoronoi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.infinity.delaunayvoronoi.algorithm.FastDelaunayTriangulation;
import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.SpecialTriangle;
import com.infinity.delaunayvoronoi.model.Triangle;
import com.infinity.delaunayvoronoi.util.Tick;
import com.infinity.delaunayvoronoi.visualization.GraphVisualizer;

public class FastDelaunayExample {

	public static void main(String[] args) {
		int width = 5000;
		int height = 5000;
		int numberOfRandomPoints = 100000;
		List<Point> originalPoints = new ArrayList<Point>();
		
		Random r = new Random(121794l);
		
		for (int i = 0; i < numberOfRandomPoints; i++) {
			double x = width * r.nextDouble();
			double y = height * r.nextDouble();
			originalPoints.add(new Point(x, y));
		}
		
		Tick t = new Tick();
		
		FastDelaunayTriangulation<SpecialTriangle, Arc, Node> factory = new FastDelaunayTriangulation<SpecialTriangle, Arc, Node>(500);
		
		PanGraph<Triangle, Arc, Node> graph = factory.createPanGraph(originalPoints);
		
		t.tock("Finished the graph");
		
		GraphVisualizer<Triangle, Arc, Node> visualizer = new GraphVisualizer<Triangle, Arc, Node>();
		visualizer.createImage(graph, "graph.png");
		
		t.tock("Finished the visualization");
	}

}
