package com.infinity.delaunayvoronoi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.infinity.delaunayvoronoi.algorithm.DelaunayTriangulation;
import com.infinity.delaunayvoronoi.algorithm.PanGraphFactory;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.visualization.GraphVisualizer;

public class DelaunayExample {

	public static void main(String[] args) {
		int width = 2500;
		int height = 2500;
		int numberOfRandomPoints = 5000;
		List<Point> originalPoints = new ArrayList<Point>();
		
		Random r = new Random(121794l);
		
		for (int i = 0; i < numberOfRandomPoints; i++) {
			double x = width * r.nextDouble();
			double y = height * r.nextDouble();
			originalPoints.add(new Point(x, y));
		}
		
		PanGraphFactory factory = new DelaunayTriangulation();
		PanGraph graph = factory.createPanGraph(originalPoints);
		
		GraphVisualizer visualizer = new GraphVisualizer();
		visualizer.createImage(graph, "graph.png");
	}

}
