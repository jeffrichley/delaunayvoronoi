package com.infinity.delaunayvoronoi.algorithm;

import java.util.Comparator;
import java.util.List;

import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.Point;

/**
 * Sorts the <code>Node</code>s in a clockwise order
 * @author jeffreyrichley
 */
public class ClockwisePointComparator implements Comparator<Node> {
	
	private Point center;

	/**
	 * Creates a <code>ClockwisePointComparator</code>
	 * @param nodes The <code>List</code> of <code>Node</code>s to calculate the center for
	 */
	public ClockwisePointComparator(List<Node> nodes) {
		double x = 0;
		double y = 0;
		for (Node node : nodes) {
			Point point = node.getPoint();
			x += point.x;
			y += point.y;
		}
		x = x / nodes.size();
		y = y / nodes.size();
		
		center = new Point(x, y);
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Node nodeA, Node nodeB) {
		Point a = nodeA.getPoint();
		Point b = nodeB.getPoint();
		
		if (a.x - center.x >= 0 && b.x - center.x < 0) {
	        return -1;
		}
		
	    if (a.x - center.x < 0 && b.x - center.x >= 0) {
	        return 1;
	    }
	    
	    if (a.x - center.x == 0 && b.x - center.x == 0) {
	        if (a.y - center.y >= 0 || b.y - center.y >= 0) {
	        	if (a.y > b.y) {
	        		return -1;
	        	} else {
		        	return 1;
	        	}
	        }
	        if (b.y > a.y) {
	        	return -1;
	        } else {
	        	return 1;
	        }
	    }

	    // compute the cross product of vectors (center -> a) x (center -> b)
	    double det = (a.x - center.x) * (b.y - center.y) - (b.x - center.x) * (a.y - center.y);
	    if (det < 0) {
	    	return -1;
	    }
	    
	    if (det > 0) {
	    	return 1;
	    }

	    // points a and b are on the same line from the center
	    // check which point is closer to the center
	    double d1 = (a.x - center.x) * (a.x - center.x) + (a.y - center.y) * (a.y - center.y);
	    double d2 = (b.x - center.x) * (b.x - center.x) + (b.y - center.y) * (b.y - center.y);
	    if (d1 > d2) {
	    	return -1;
	    } else {
	    	return 1;
	    }
	}

}
