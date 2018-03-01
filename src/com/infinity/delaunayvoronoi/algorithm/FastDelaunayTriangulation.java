package com.infinity.delaunayvoronoi.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.infinity.delaunayvoronoi.factory.SpecialDelaunayFactory;
import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Area;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;
import com.infinity.delaunayvoronoi.model.SpecialTriangle;
import com.infinity.delaunayvoronoi.model.Triangle;

public class FastDelaunayTriangulation<R extends Triangle, S extends Arc, T extends Node> implements PanGraphFactory<Triangle, Arc, Node> {
	
	private final int maxPointsPerArea;
	
	public FastDelaunayTriangulation() {
		this.maxPointsPerArea = 10000;
	}
	
	public FastDelaunayTriangulation(int maxPointsPerArea) {
		this.maxPointsPerArea = maxPointsPerArea;
	}

	@Override
	public PanGraph<Triangle, Arc, Node> createPanGraph(List<Point> points) {
		
		// need to break the points into quadrants
		List<Area> areas = findAreas(points);
		
		// once we have all the points in various quadrants, perform the triangulation
		DelaunayTriangulation<SpecialTriangle, Arc, Node> factory = new DelaunayTriangulation<SpecialTriangle, Arc, Node> ();
		factory.setModelFactory(new SpecialDelaunayFactory());
		
		List<PanGraph<Triangle, Arc, Node>> graphs = new ArrayList<>();
		for (Area area : areas) {
			PanGraph<Triangle, Arc, Node> graph = factory.createPanGraph(area.getPoints());
			graphs.add(graph);
		}
		
		// now that we have the triangulation, lets stitch the graphs together
		PanGraph<Triangle, Arc, Node> megaGraph = new PanGraph<>();
		factory.setOriginalGraph(megaGraph);
		
		Set<Point> addPoints = new HashSet<>();
		for (PanGraph<Triangle, Arc, Node> graph : graphs) {
			megaGraph.addArcs(graph.getArcs());
			megaGraph.addNodes(graph.getNodes());
			megaGraph.addPolygons(graph.getPolygons());
		}

		// need to find all arcs that don't have both triangles
		Set<Triangle> removeTriangles = new HashSet<>();
		for (Arc arc : megaGraph.getArcs()) {
			if (arc.getBorderingPolygons().size() < 2) {
				// then get the nodes that are on both ends of the arcs
				// then get all the shared triangles.  These are the
				// triangles to remove
				List<Node> nodes = arc.getEndPoints();
				for (Node node : nodes) {
					Triangle triangle1 = (Triangle) node.getTouchingPolygons().get(0);
					Triangle triangle2 = (Triangle) node.getTouchingPolygons().get(1);

					removeTriangles.add(triangle1);
					removeTriangles.add(triangle2);
					
					addPoints.add(node.getPoint());
					
//					System.out.println(megaGraph.getArcs().contains(arc));
//					System.out.println(megaGraph.getNodes().contains(node));
//					System.out.println(megaGraph.getPolygons().contains(triangle1) + " " + megaGraph.getPolygons().contains(triangle2));
				}
			}
		}
		
		// get rid of the triangles
		factory.removeTriangles(removeTriangles);
		
		System.out.println(megaGraph.isValid());
		
		return megaGraph;
	}

	private List<Area> findAreas(List<Point> points) {
		
		double minX = Integer.MAX_VALUE;
		double minY = Integer.MAX_VALUE;
		double maxX = 0;
		double maxY = 0;
		
		// set the upper and lower points
		for (Point point : points) {
			if (point.x < minX) {
				minX = point.x;
			} else if (point.x > maxX) {
				maxX = point.x;
			}
			
			if (point.y < minY) {
				minY = point.y;
			} else if (point.y > maxY) {
				maxY = point.y;
			}
		}
		
		
		List<Area> areas = new ArrayList<>();
		
		// This will be the primary area
		Area firstArea = new Area(minX, minY, maxX, maxY);
		
		// Add all points
		for (Point point : points) {
			firstArea.addPoint(point);
		}
		
		// split the area if it has too many points
		firstArea.split(maxPointsPerArea);
		
		// TODO: need to get all the leaf nodes
		
		return areas;
	}

}
