package com.infinity.delaunayvoronoi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.infinity.delaunayvoronoi.algorithm.VoronoiGraphFactory;
import com.infinity.delaunayvoronoi.algorithm.VoronoiGraphFactoryBST;
import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;
import com.infinity.delaunayvoronoi.util.Tick;
import com.infinity.delaunayvoronoi.visualization.GraphVisualizer;

public class VoronoiExample {

	public static void main(String[] args) {
		int width = 500;
		int height = 500;
		int numberOfRandomPoints = 100;
		List<Point> originalPoints = new ArrayList<Point>();
		
		Random r = new Random(121794l);
		
		for (int i = 0; i < numberOfRandomPoints; i++) {
			double x = width * r.nextDouble();
			double y = height * r.nextDouble();
			originalPoints.add(new Point(x, y));
		}
		
		Tick t = new Tick();
		
		VoronoiGraphFactory<Polygon, Arc, Node> factory = new VoronoiGraphFactory<Polygon, Arc, Node>();
		PanGraph<Polygon, Arc, Node> graph = factory.createPanGraph(originalPoints);
		
		t.tock("Finished the graph");
		
		GraphVisualizer<Polygon, Arc, Node> visualizer = new GraphVisualizer<Polygon, Arc, Node>();
//		visualizer.createImage(graph, "voronoi.png");
	}

}
