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
import com.infinity.delaunayvoronoi.util.BSTUtil;

/**
 * Generates a graph representing Voronoi spaces
 * 
 * See http://www.ams.org/samplings/feature-column/fcarc-voronoi for details
 * 
 * @author Jeffrey.Richley
 *
 * @param <R> Something that extends Polygon
 * @param <S> Something that extends Arc
 * @param <T> Something that extends Node
 */
public class VoronoiGraphFactoryBST<R extends Polygon, S extends Arc, T extends Node> implements PanGraphFactory<Polygon, Arc, Node> {
	
	/**
	 * The events that will be processed during the building of the graph
	 */
	private PriorityQueue<Event> queue;
	
	/**
	 * The top of the binary tree
	 */
	private Parabola root = null;
	
	/**
	 * Width of the graph 
	 */
	private long width;
	
	/**
	 * Height of the graph 
	 */
	private long height;
	
	/**
	 * Creates a new VoronoiGraphFactory
	 * @param width Width of the area to work with
	 * @param height Height of the area to work with
	 */
	public VoronoiGraphFactoryBST(long width, long height) {
		this.width = width;
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see com.infinity.delaunayvoronoi.algorithm.PanGraphFactory#createPanGraph(java.util.List)
	 */
	@Override
	public PanGraph<Polygon, Arc, Node> createPanGraph(List<Point> points) {
		PanGraph<Polygon, Arc, Node> graph = new PanGraph<Polygon, Arc, Node>();
		
		// ** Initialize the event queue Q with all site events, initialize an 
		// ** empty status structure T and an empty doubly-connected edge list D.
		
		// set the queue to be ordered by y and then by x
		queue = new PriorityQueue<Event>(points.size(), new Comparator<Event>() {
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
			
		// ** while Q is not empty
		//  while queue is not empty
		while (!queue.isEmpty()) {
			// ** do Remove the event with largest y coordinate from Q
		//  get the first event from the queue
			Event event = queue.poll();
			
			// ** if the event is site event occuring at the site p
		//  add a parabola if it is a site event 
			if (event.isSiteEvent()) {
				// ** then HandleSiteEvent(p)
				addParabola(event.getLocation());
			} else {
				// ** else HandleCircleEvent(c),where c is the leaf of T representing the arc that will disappear
				// remove a parabola if it is a circle event
				CircleEvent e = (CircleEvent) event;
				removeParabola(e.getParabola());
			}
		}
		//  done!!! 
		   
		return graph;
	}

	/**
	 * Handle an event where there was a new site encountered
	 * @param insertedPoint The new site that was encountered
	 */
	private void addParabola(Point insertedPoint) {
		// parabola = arc under point insertedPoint;
		Parabola parabola = arcUnderPoint(insertedPoint);
		
		// if (parabola has its circle event, then it is removed from the beachline)
			// remove this event from the queue
		// new arcs a, b, c;
		// b.site = insertedPoint;
		// a.site = c.site = parabola.site; // site of arc is a focus of arc
		// leftEdge, rightEdge  = left and right edges, which comes from point on parabola under insertedPoint
		// leftEdge is a normal to  (a.site, b.site);
		// rightEdge is a normal to (b.site, c.site);
		// replace parabola by the sequence a, leftEdge, b, rightEdge, c
		// CheckCircleEvent(a);
		// CheckCircleEvent(c);
	}

	private Parabola arcUnderPoint(Point insertedPoint) {
		// TODO Auto-generated method stub
		return null;
	}

	private void removeParabola(Parabola removingParabola) {
		// leftArc = an arc left of removingParabola;
		// rightArc = an arc on the right of removingParabola;
		// if (leftArc or rightArc have their CircleEvents) remove these events from the queue
		// center = the circumcenter between leftArc.site, removingParabola.site and rightArc.site
		// newEdge = new edge, starts at center, normal to (leftArc.site, rightArc.site)
		// finish two neighbour edges leftEdge, rightEdge at point center
		// replace the sequence leftEdge, removingParabola, rightEdge by new edge newEdge
		// CheckCircleEvent(leftArc);
		// CheckCircleEvent(rightArc);
	}

	/**
	 * Check to see if we actually need to create a new <code>CircleEvent</code>
	 * @param parabola The parabola that is used to check for new <code>CircleEvent</code>s
	 */
	private void checkCircleEvent(Parabola parabola) {
		// leftArc = arc on the left of parabola;
//		Parabola leftArc = (Parabola) BSTUtil.findPredecessor(parabola);
		
		// rightArc = arc on the right of parabola;
//		Parabola rightArc = (Parabola) BSTUtil.findPredecessor(parabola);
		
		// leftEdge, rightEdge = edges by the parabola
		// when there is no leftArc  OR no rightArc  OR  leftArc.site = rightArc.site  RETURN
		// midPoint = middle point, where leftEdge and rightEdge cross each other
		// when there is no midPoint (edges go like\ /) RETURN
		// radius = distance between midPoint an parabola.site (radius of curcumcircle)
		// if midPoint.y + radius is still under the sweepline  RETURN
		// circleEvent = new circle event
		// circleEvent.parabola = parabola;
		// circleEvent.y = midPoint.y + radius;
		// add circleEvent into queue 
	}
}
