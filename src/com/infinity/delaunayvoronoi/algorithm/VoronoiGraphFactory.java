package com.infinity.delaunayvoronoi.algorithm;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.infinity.delaunayvoronoi.algorithm.voronoi.CircleEvent;
import com.infinity.delaunayvoronoi.algorithm.voronoi.Event;
import com.infinity.delaunayvoronoi.algorithm.voronoi.SiteEvent;
import com.infinity.delaunayvoronoi.algorithm.voronoi.Parabola;
import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;

/**
 * Generates a graph representing Voronoi spaces
 * @author Jeffrey.Richley
 *
 * @param <R> Something that extends Polygon
 * @param <S> Something that extends Arc
 * @param <T> Something that extends Node
 */
public class VoronoiGraphFactory<R extends Polygon, S extends Arc, T extends Node> implements PanGraphFactory<Polygon, Arc, Node> {
	
	/**
	 * The top of the binary tree
	 */
	private Parabola root = null;

	/* (non-Javadoc)
	 * @see com.infinity.delaunayvoronoi.algorithm.PanGraphFactory#createPanGraph(java.util.List)
	 */
	@Override
	public PanGraph<Polygon, Arc, Node> createPanGraph(List<Point> points) {
		PanGraph<Polygon, Arc, Node> graph = new PanGraph<Polygon, Arc, Node>();
		
		// set the queue to be ordered by y and then by x
		PriorityQueue<Event> queue = new PriorityQueue<Event>(points.size(), new Comparator<Event>() {
			@Override
			public int compare(Event e1, Event e2) {
				Double y1 = e1.getLocation().y;
				Double y2 = e2.getLocation().y;
				
				if (y1 == y2) {
					Double x1 = e1.getLocation().x;
					Double x2 = e2.getLocation().x;
					return x1.compareTo(x2);
				}
				
				return y1.compareTo(y2);
			}
		});
		
		// for each site
		for (Point point : points) {
		//  create a site event e, add add it to the queue
			queue.offer(new SiteEvent(point));
		}
			
		//  while queue is not empty
		while (!queue.isEmpty()) {
		//  get the first event from the queue
			Event event = queue.poll();
			
		//  add a parabola if it is a site event 
			if (event.isSiteEvent()) {
				addParabola(event.getLocation());
			} else {
				// remove a parabola if it is a circle event
				CircleEvent e = (CircleEvent) event;
				removeParabola(e.getParabola());
			}
		}
		//  done!!! 
		   
		return graph;
	}

	private void addParabola(Point point) {
		// create the parabola as an arc under point
		Parabola parabola = new Parabola(point);
		
		// check if this should be the top of the tree
		if (root == null) {
			root = parabola;
			return;
		}
		
		// we need to add the new parabola to the binary tree
		
		
		
//	      if (par has its circle event, when it is removed form the beachline)
//	         remove this event form the queue
//	      new arcs a, b, c;
//	      b.site = u;
//	      a.site = c.site = par.site; // site of arc is a focus of arc
//	      xl, xr  = left and right edges, which comes from point on par under u
//	      xl is a normal to  (a.site, b.site);
//	      xr is a normal to (b.site, c.site);
//	      replace par by the sequence a, xl, b, xr, c
//	      CheckCircleEvent(a);
//	      CheckCircleEvent(c);
	}

	private void removeParabola(Parabola parabola) {
		// TODO Auto-generated method stub
		
	}

}
