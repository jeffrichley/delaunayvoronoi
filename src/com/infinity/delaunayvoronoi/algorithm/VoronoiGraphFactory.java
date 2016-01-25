package com.infinity.delaunayvoronoi.algorithm;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.infinity.delaunayvoronoi.algorithm.voronoi.BeachLine;
import com.infinity.delaunayvoronoi.algorithm.voronoi.CircleEvent;
import com.infinity.delaunayvoronoi.algorithm.voronoi.Edge;
import com.infinity.delaunayvoronoi.algorithm.voronoi.Event;
import com.infinity.delaunayvoronoi.algorithm.voronoi.ListBeachLine;
import com.infinity.delaunayvoronoi.algorithm.voronoi.Parabola;
import com.infinity.delaunayvoronoi.algorithm.voronoi.SiteEvent;
import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Circle;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;
import com.infinity.delaunayvoronoi.util.MathUtil;

/**
 * See following sites for details
 * http://blog.ivank.net/fortunes-algorithm-and-implementation.html (code hidden and shown by links)
 * http://www.ams.org/samplings/feature-column/fcarc-voronoi
 * 
 * @param <R>
 * @param <S>
 * @param <T>
 * @author jeffreyrichley
 */
public class VoronoiGraphFactory<R extends Polygon, S extends Arc, T extends Node> implements PanGraphFactory<Polygon, Arc, Node> {

	/**
	 * The events that will be processed during the building of the graph
	 */
	private PriorityQueue<Event> queue;
	
	/**
	 * The arrangements of <code>Parabola</code>s that make the beach line
	 */
	private BeachLine beachLine = new ListBeachLine();
	
	/**
	 * Keeps track of how far along the sweep line has gone through the area
	 */
	private double sweepLine = 0;
	
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
		int circleCount = 0;
		while (!queue.isEmpty()) {
			// ** do Remove the event with largest y coordinate from Q
		//  get the first event from the queue
			Event event = queue.poll();
			
			// ** if the event is site event occuring at the site p
		//  add a parabola if it is a site event 
			if (event.isSiteEvent()) {
				// update how far the sweep line has gone
				sweepLine = event.getLocation().y;
				
				// ** then HandleSiteEvent(p)
				addParabola(event.getLocation());
			} else {
				System.out.println("circle # " + ++circleCount);
				// ** else HandleCircleEvent(c),where c is the leaf of T representing the arc that will disappear
				// remove a parabola if it is a circle event
				CircleEvent e = (CircleEvent) event;
				
				// update how far the sweep line has gone
				sweepLine = event.getLocation().y;
				
				removeParabola(e);
			}
		}
		//  done!!! 
		
		return graph;
	}

	private void addParabola(Point insertedPoint) {
		// if this is the first parabola we have added, just add it
		if (beachLine.isEmpty()) {
			beachLine.addFirstParabola(new Parabola(insertedPoint));
			return;
		}
		
		// parabola = arc under point insertedPoint;
		Parabola parabola = beachLine.getArcUnderPoint(insertedPoint);
		
		// TODO: make this check
		// if (parabola has its circle event, then it is removed from the beach line)
		if (parabola.getCircleEvent() != null) {
			// remove this event from the queue
			queue.remove(parabola.getCircleEvent());
			// disassociate the parabola and the event
			parabola.setCircleEvent(null);
		}
		
		Point start = new Point(insertedPoint.x, getY(parabola.getSite(), insertedPoint.x));
		
		// new arcs a, b, c;
		// b.site = insertedPoint;
		Parabola b = new Parabola(insertedPoint);

		// a.site = c.site = parabola.site; // site of arc is a focus of arc
		Parabola a = new Parabola(parabola.getSite());
		Parabola c = new Parabola(parabola.getSite());
		
		// TODO: how do we get the starting point of these edges
		// leftEdge, rightEdge  = left and right edges, which comes from point on parabola under insertedPoint
		// 264
//		Edge leftEdge = new Edge(a.getSite(), b.getSite());
//		Edge rightEdge = new Edge(b.getSite(), c.getSite());
		Edge leftEdge = new Edge(start, parabola.getSite(), insertedPoint);
		Edge rightEdge = new Edge(start, insertedPoint, parabola.getSite());
		
		// handshake for doubly linked list
		leftEdge.setNeighbor(rightEdge);
		parabola.setEdge(rightEdge);
		
		// TODO: need to figure this part out
		// TODO: should be done in edgeFromSites()
		// leftEdge is a normal to  (a.site, b.site);
//		leftEdge.addRegions(a.getSite(), b.getSite());
		// rightEdge is a normal to (b.site, c.site);
//		rightEdge.addRegions(b.getSite(), c.getSite());
		
		// replace parabola by the sequence a, leftEdge, b, rightEdge, c
		beachLine.replaceParabola(parabola, a, leftEdge, b, rightEdge, c);
		
		// CheckCircleEvent(a);
		checkCircleEvent(a);
		// CheckCircleEvent(c);
		checkCircleEvent(b);
	}

	private void removeParabola(CircleEvent circleEvent) {
		Parabola removingParabola = circleEvent.getParabola();
		
		// leftArc = an arc left of removingParabola;
		Parabola leftArc = beachLine.getParabolaLeftOfParabola(removingParabola);
		
		// rightArc = an arc on the right of removingParabola;
		Parabola rightArc = beachLine.getParabolaRightOfParabola(removingParabola);
		
		// if (leftArc or rightArc have their CircleEvents) remove these events from the queue
		if (leftArc.getCircleEvent() != null) {
			queue.remove(leftArc.getCircleEvent());
			leftArc.setCircleEvent(null);
		}
		if (rightArc.getCircleEvent() != null) {
			queue.remove(rightArc.getCircleEvent());
			rightArc.setCircleEvent(null);
		}
		
		// center = the circumcenter between leftArc.site, removingParabola.site and rightArc.site
		// TODO: other code calculates it differently
		Point center = circleEvent.getCircumCircle().getCenter();
		
		// newEdge = new edge, starts at center, normal to (leftArc.site, rightArc.site)
//		Edge newEdge = edgeFromSites(leftArc.getSite(), rightArc.getSite());
//		newEdge.setStart(center);
		Edge newEdge = new Edge(center, leftArc.getSite(), rightArc.getSite());
		
		// finish two neighbor edges leftEdge, rightEdge at point center
		Edge leftEdge = beachLine.getEdgeLeftOfParabola(removingParabola);
		Edge rightEdge = beachLine.getEdgeRightOfParabola(removingParabola);
		leftEdge.setEnd(center);
		rightEdge.setEnd(center);
		
		
		// replace the sequence leftEdge, removingParabola, rightEdge by new edge newEdge
		beachLine.replaceSequenceWithEdge(leftArc, removingParabola, rightArc, newEdge);
		
		// CheckCircleEvent(leftArc);
		checkCircleEvent(leftArc);
		
		// CheckCircleEvent(rightArc);
		checkCircleEvent(rightArc);
	}

	private void checkCircleEvent(Parabola parabola) {

		// leftArc = arc on the left of parabola;
		Parabola leftArc = beachLine.getParabolaLeftOfParabola(parabola);
		if (leftArc == null) {
			return;
		}
		
		// rightArc = arc on the right of parabola;
		Parabola rightArc = beachLine.getParabolaRightOfParabola(parabola);
		if (rightArc == null) {
			return;
		}
		
		// when there is no leftArc OR no rightArc OR leftArc.site = rightArc.site RETURN
		if (leftArc.getSite() == rightArc.getSite()) {
			return;
		}

		// leftEdge, rightEdge = edges by the parabola
		Edge leftEdge = beachLine.getEdgeLeftOfParabola(parabola);
		Edge rightEdge = beachLine.getEdgeRightOfParabola(parabola);

		// TODO: calculate the midpoint
		// midPoint = middle point, where leftEdge and rightEdge cross each other
		Point midPoint = findMidPoint(leftEdge, rightEdge);
		
		// when there is no midPoint (edges go like\ /) RETURN
		if (midPoint == null) {
			return;
		}
		
		// radius = distance between midPoint and parabola.site (radius of circumcircle)
		// TODO: other code uses leftEdge instead of parabola
		double radius = MathUtil.calculateDistance(midPoint, parabola.getSite()); 
				
		// if midPoint.y + radius is still under the sweep line  RETURN
		// TODO: should this be + radius or -, if changed, change the + below also
		if (midPoint.y - radius >= sweepLine) {
			return;
		}
		
		// circleEvent = new circle event
		// circleEvent.parabola = parabola;
		Circle circumCircle = MathUtil.calculateCircumCircle(leftArc.getSite(), 
				 											 parabola.getSite(), 
				 											 rightArc.getSite());
		Point circumCenter = circumCircle.getCenter();
		// circleEvent.y = midPoint.y + radius;
		// TODO: if I changed the + to - above, change it here too
		Point location = new Point(circumCenter.x, circumCenter.y - radius);
		CircleEvent circleEvent = new CircleEvent(location, parabola, circumCircle);
		// add circleEvent into queue 
		queue.offer(circleEvent);
		parabola.setCircleEvent(circleEvent);
	}

	private Point findMidPoint(Edge leftEdge /* a */, Edge rightEdge /* b */) {
		double x = (rightEdge.g - leftEdge.g) / (leftEdge.f - rightEdge.f);
		double y = leftEdge.f * x + leftEdge.g;

		if((x - leftEdge.start.x) / leftEdge.direction.x < 0) return null;
		if((y - leftEdge.start.y) / leftEdge.direction.y < 0) return null;
			
		if((x - rightEdge.start.x) / rightEdge.direction.x < 0) return null;
		if((y - rightEdge.start.y) / rightEdge.direction.y < 0) return null;	

		Point point = new Point(x, y);		
		return point;
	}
	
	private double getY(Point p, double x) {
		double dp = 2 * (p.y - sweepLine);
		double a1 = 1 / dp;
		double b1 = -2 * p.x / dp;
		double c1 = sweepLine + dp / 4 + p.x * p.x / dp;
		
		return(a1*x*x + b1*x + c1);
	}

}
