package com.infinity.delaunayvoronoi.algorithm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;

import com.infinity.delaunayvoronoi.algorithm.voronoi.BeachLine;
import com.infinity.delaunayvoronoi.algorithm.voronoi.Edge;
import com.infinity.delaunayvoronoi.algorithm.voronoi.Event;
import com.infinity.delaunayvoronoi.algorithm.voronoi.ListBeachLine;
import com.infinity.delaunayvoronoi.algorithm.voronoi.Parabola;
import com.infinity.delaunayvoronoi.algorithm.voronoi.TreeHelper;
import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;

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
	// TODO: this was the sweep line
	private double ly = 0;
	
	private List<Edge> edges = new ArrayList<Edge>();
	
	private List<Point> points = new ArrayList<Point>();
	
	private Parabola root;

	private int width;

	private int height;
	
	public VoronoiGraphFactory(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public PanGraph<Polygon, Arc, Node> createPanGraph(List<Point> points) {
		PanGraph<Polygon, Arc, Node> graph = new PanGraph<Polygon, Arc, Node>();
		
		// ** Initialize the event queue Q with all site events, initialize an 
		// ** empty status structure T and an empty doubly-connected edge list D.
		
		// set the queue to be ordered by y and then by x
		queue = new PriorityQueue<Event>(points.size(), new Comparator<Event>() {
			@Override
			public int compare(Event e1, Event e2) {
				Double y1 = e1.y;
				Double y2 = e2.y;
				
				if (y1 == y2) {
					Double x1 = e1.x;
					Double x2 = e2.x;
					return x1.compareTo(x2);
				}
				
				return y1.compareTo(y2);
			}
		});
		
		// for each site
		for (Point point : points) {
		//  create a site event e, add add it to the queue
			queue.offer(new Event(point, true));
		}
		
		// ** while Q is not empty
		//  while queue is not empty
		int circleCount = 0;
		while (!queue.isEmpty()) {
			// ** do Remove the event with largest y coordinate from Q
		//  get the first event from the queue
			Event event = queue.poll();

			// update how far the sweep line has gone
			ly = event.y;
			
			// ** if the event is site event occuring at the site p
		//  add a parabola if it is a site event 
			if (event.pe) {
				// ** then HandleSiteEvent(p)
				addParabola(event.point);
			} else {
				System.out.println("circle # " + ++circleCount);
				// ** else HandleCircleEvent(c),where c is the leaf of T representing the arc that will disappear
				// remove a parabola if it is a circle event
				removeParabola(event);
			}
		}
		//  done!!! 
		
		
		
		
		// TODO: do we need to do this?
//		FinishEdge(root);
//
//		for(Edges::iterator i = edges->begin(); i != edges->end(); ++i)
//		{
//			if( (*i)->neighbour) 
//			{
//				(*i)->start = (*i)->neighbour->end;
//				delete (*i)->neighbour;
//			}
//		}
		
		
		
		
		
		
		
		
		
		
		
		BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();

		g.setColor(Color.BLUE);
		for (Point point : points) {
			g.fillOval((int)point.x, (int)point.y, 2, 2);
		}
		
		g.setColor(Color.BLACK);
		for (Edge edge : edges) {
			if (edge.getEnd() != null) {
				g.drawLine((int)edge.start.x, (int)edge.start.y, (int)edge.getEnd().x, (int)edge.getEnd().y);
			}
		}
		
		File file = new File("lines.png");
		try {
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
		
		
		
		
		
		
		
		
		return graph;
	}

	private void addParabola(Point p) {
		if(root == null) {
			root = new Parabola(p); 
			return;
		}

		// degenerovaný pøípad - obì spodní místa ve stejné výšce
		if(root.isLeaf() && root.getSite().y - p.y < 1) {
			Point fp = root.getSite();
			root.setLeaf(false);
			root.setLeft( new Parabola(fp));
			root.setRight(new Parabola(p));
			// zaèátek hrany uprostøed míst
			Point s = new Point((p.x + fp.x)/2, height); 
			points.add(s);
			if(p.x > fp.x) {
				// rozhodnu, který vlevo, který vpravo
				root.setEdge(new Edge(s, fp, p)); 
			} else  {
				root.setEdge(new Edge(s, p, fp));
			}
			edges.add(root.getEdge());
			return;
		}

		Parabola par = getParabolaByX(p.x);
		
		if (par.getCircleEvent() != null){
			// TODO: fairly sure all I need to do is remove it from the queue
//			deleted.insert(par->cEvent);
			queue.remove(par.getCircleEvent());
			par.setCircleEvent(null);
		}
		
		Point start = new Point(p.x, getY(par.getSite(), p.x));
		points.add(start);

		Edge el = new Edge(start, par.getSite(), p);
		Edge er = new Edge(start, p, par.getSite());

		el.setNeighbor(er);
		edges.add(el);

		// pøestavuju strom .. vkládám novou parabolu
		par.setEdge(er);
		par.setLeaf(false);

		Parabola p0 = new Parabola(par.getSite());
		Parabola p1 = new Parabola(p);
		Parabola p2 = new Parabola(par.getSite());

		par.setRight(p2);
		par.setLeft(new Parabola());
		par.getLeft().setEdge(el);

		par.getLeft().setLeft(p0);
		par.getLeft().setRight(p1);
		
		checkCircleEvent(p0);
		checkCircleEvent(p2);
	}

	private void removeParabola(Event e) {
		Parabola p1 = e.getArch();

		Parabola xl = TreeHelper.getLeftParent(p1);
		Parabola xr = TreeHelper.getRightParent(p1);

		Parabola p0 = TreeHelper.getLeftChild(xl);
		Parabola p2 = TreeHelper.getRightChild(xr);

//		if(p0 == p2) std::cout << "chyba - pravá a levá parabola má stejné ohnisko!\n";

		if(p0.getCircleEvent() != null){
//			deleted.insert(p0->cEvent);
			queue.remove(p0.getCircleEvent());
//			p0->cEvent = 0; 
			p0.setCircleEvent(null);
		}
		if(p2.getCircleEvent() != null){ 
//			deleted.insert(p2->cEvent);
			queue.remove(p2.getCircleEvent());
//			p2->cEvent = 0; 
			p2.setCircleEvent(null);
		}

		Point p = new Point(e.point.x, getY(p1.getSite(), e.point.x));
		points.add(p);

		xl.getEdge().setEnd(p);
		xr.getEdge().setEnd(p);
		
		Parabola higher = null;
		Parabola par = p1;
		while(par != root) {
			par = par.getParent();
			if(par == xl) {
				higher = xl;
			}
			if(par == xr) {
				higher = xr;
			}
		}
		higher.setEdge(new Edge(p, p0.getSite(), p2.getSite()));
		edges.add(higher.getEdge());

		Parabola gparent = p1.getParent().getParent();
		if(p1.getParent().getLeft() == p1) {
			if(gparent.getLeft() == p1.getParent()) {
				gparent.setLeft(p1.getParent().getRight());
			}
			if(gparent.getRight() == p1.getParent()) {
				gparent.setRight(p1.getParent().getRight());
			}
		} else {
			if(gparent.getLeft() == p1.getParent()) {
				gparent.setLeft(p1.getParent().getLeft());
			}
			if(gparent.getRight() == p1.getParent()) {
				gparent.setRight(p1.getParent().getLeft());
			}
		}

		checkCircleEvent(p0);
		checkCircleEvent(p2);
	}

	private void checkCircleEvent(Parabola b) {
		Parabola lp = TreeHelper.getLeftParent(b);
		Parabola rp = TreeHelper.getRightParent(b);

		Parabola a = TreeHelper.getLeftChild(lp);
		Parabola c = TreeHelper.getRightChild(rp);

		if(a == null || c == null || a.getSite() == c.getSite()) {
			return;
		}

		Point s = null;
		s = getEdgeIntersection(lp.getEdge(), rp.getEdge());
		if(s == null) {
			return;
		}

		double dx = a.getSite().x - s.x;
		double dy = a.getSite().y - s.y;

		double d = Math.sqrt( (dx * dx) + (dy * dy) );

		if(s.y - d >= ly) { 
			return;
		}

		Event e = new Event(new Point(s.x, s.y - d), false);
		points.add(e.point);
		b.setCircleEvent(e);
		e.setArch(b);
		queue.offer(e);
	}

	private Point getEdgeIntersection(Edge a, Edge b) {
//		double x = (b->g-a->g) / (a->f - b->f);
		double x = (b.g-a.g) / (a.f - b.f);
//		double y = a->f * x + a->g;
		double y = a.f * x + a.g;

//		if((x - a->start->x)/a->direction->x < 0) return 0;
		if((x - a.start.x)/a.direction.x < 0) return null;
//		if((y - a->start->y)/a->direction->y < 0) return 0;
		if((y - a.start.y)/a.direction.y < 0) return null;
			
//		if((x - b->start->x)/b->direction->x < 0) return 0;
		if((x - b.start.x)/b.direction.x < 0) return null;
//		if((y - b->start->y)/b->direction->y < 0) return 0;
		if((y - b.start.y)/b.direction.y < 0) return null;	

		Point p = new Point(x, y);
		points.add(p);
		return p;
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
		double dp = 2 * (p.y - ly);
		double a1 = 1 / dp;
		double b1 = -2 * p.x / dp;
		double c1 = ly + dp / 4 + p.x * p.x / dp;
		
		return(a1*x*x + b1*x + c1);
	}

	private Parabola getParabolaByX(double xx) {
		Parabola par = root;
		double x = 0.0;

		// projdu stromem dokud nenarazím na vhodný list
		while(!par.isLeaf()) { 
			x = getXOfEdge(par, ly);
			if(x > xx) {
				par = par.getLeft();
			} else {
				par = par.getRight();				
			}
		}
		return par;
	}
	
	private double getXOfEdge(Parabola par, double y) {
		Parabola left = TreeHelper.getLeftChild(par);
		Parabola right= TreeHelper.getRightChild(par);

		Point p = left.getSite();
		Point r = right.getSite();

		double dp = 2.0 * (p.y - y);
		double a1 = 1.0 / dp;
		double b1 = -2.0 * p.x / dp;
		double c1 = y + dp / 4 + p.x * p.x / dp;
				
		dp = 2.0 * (r.y - y);
		double a2 = 1.0 / dp;
		double b2 = -2.0 * r.x/dp;
		double c2 = ly + dp / 4 + r.x * r.x / dp;
				
		double a = a1 - a2;
		double b = b1 - b2;
		double c = c1 - c2;
				
		double disc = b*b - 4 * a * c;
		double x1 = (-b + Math.sqrt(disc)) / (2*a);
		double x2 = (-b - Math.sqrt(disc)) / (2*a);

		double ry;
		if(p.y < r.y ) {
			ry =  Math.max(x1, x2);
		} else {
			ry = Math.min(x1, x2);
		}

		return ry;
	}
}
