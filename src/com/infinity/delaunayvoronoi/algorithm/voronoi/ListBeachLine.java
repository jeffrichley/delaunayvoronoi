package com.infinity.delaunayvoronoi.algorithm.voronoi;

import java.util.ArrayList;
import java.util.List;

import com.infinity.delaunayvoronoi.model.Point;

public class ListBeachLine implements BeachLine {
	
	private List<BeachEntry> beachLine = new ArrayList<BeachEntry>();

	@Override
	public Parabola getArcUnderPoint(Point point) {
		Parabola closest = null;
		// we aren't using the the square root because we 
		// can be more efficient without it
		double distance = Double.MAX_VALUE;
		
		for (int i = 0; i < beachLine.size(); i++) {
			BeachEntry entry = beachLine.get(i);
			if (entry instanceof Parabola) {
				Parabola p = (Parabola) entry;
				if (closest == null) {
					closest = p;
					double x = p.getSite().x - point.x;
					double y = p.getSite().y - point.y;
					distance = x*x + y*y;
				} else {
					double x = p.getSite().x - point.x;
					double y = p.getSite().y - point.y;
					double tmpDistance = x*x + y*y;
					if (tmpDistance < distance) {
						distance = tmpDistance;
						closest = p;
					}
				}
			}
		}
		
		return closest;
	}

	@Override
	public void replaceParabola(Parabola replaceMe, Parabola a, Edge leftEdge, Parabola b, Edge rightEdge, Parabola c) {
		int index = beachLine.indexOf(replaceMe);
		// don't forget to remove the old parabola
		// TODO: is it possible that there are multiple entries for this parabola?
		beachLine.remove(index);

		// add them in reverse order and they get shifted over one by one
		beachLine.add(index, c);
		beachLine.add(index, rightEdge);
		beachLine.add(index, b);
		beachLine.add(index, leftEdge);
		beachLine.add(index, a);
	}

	@Override
	public Parabola getParabolaLeftOfParabola(Parabola parabola) {
		int index = beachLine.indexOf(parabola) - 1;
		// keep going backwards through the list until we find a parabola
		while (index >= 0) {
			BeachEntry entry = beachLine.get(index--);
			// we found a parabola but make sure it isn't the one we started with
			if (entry instanceof Parabola && parabola != entry) {
				return (Parabola) entry;
			}
		}
		return null;
	}

	@Override
	public Parabola getParabolaRightOfParabola(Parabola parabola) {
		int index = beachLine.indexOf(parabola) + 1;
		// keep going forwards through the list until we find a parabola
		while (index < beachLine.size()) {
			BeachEntry entry = beachLine.get(index++);
			// we found a parabola but make sure it isn't the one we started with
			if (entry instanceof Parabola && entry != parabola) {
				return (Parabola) entry;
			}
		}
		return null;
	}

	@Override
	public void replaceSequenceWithEdge(Parabola leftArc, Parabola removingParabola, Parabola rightArc, Edge newEdge) {
		int index = -1;
		int tmpIndex = 0;
		
		// find the first parabola
		Parabola one = null;
		for (int i = 0; i < beachLine.size() && one == null; i++) {
			BeachEntry entry = beachLine.get(i);
			if (entry instanceof Parabola) {
				one = (Parabola) entry;
			}
		}
		
		// loop through the list until we find the sequence
		while (index < 0 && tmpIndex+2 < beachLine.size()) {
			Parabola two = getParabolaRightOfParabola(one);
			Parabola three = getParabolaRightOfParabola(two);
			if (one == leftArc && 
				two == removingParabola &&
				three == rightArc) {
				index = tmpIndex;
			} else {
				tmpIndex++;
				one = two;
			}
		}
		
		// get rid of the sequence
		beachLine.remove(index + 4);
		beachLine.remove(index + 3);
		beachLine.remove(index + 2);
		beachLine.remove(index + 1);
		beachLine.remove(index);
		
		// add the new edge
		beachLine.add(index, newEdge);
	}

	@Override
	public Edge getEdgeLeftOfParabola(Parabola parabola) {
		int index = beachLine.indexOf(parabola) - 1;
		// keep going backwards through the list until we find an edge
		while (index >= 0) {
			BeachEntry entry = beachLine.get(index--);
			if (entry instanceof Edge) {
				return (Edge) entry;
			}
		}
		return null;
	}

	@Override
	public Edge getEdgeRightOfParabola(Parabola parabola) {
		int index = beachLine.indexOf(parabola) + 1;
		// keep going forwards through the list until we find a parabola
		while (index < beachLine.size()) {
			BeachEntry entry = beachLine.get(index++);
			// we found a parabola but make sure it isn't the one we started with
			if (entry instanceof Edge) {
				return (Edge) entry;
			}
		}
		return null;
	}

	@Override
	public void addFirstParabola(Parabola parabola) {
		// if it is actually the first
		if (beachLine.isEmpty()) {
			beachLine.add(parabola);
		} else {
			// otherwise something weird happened
			throw new IllegalStateException("Unable to add Parabola, the beach line wasn't empty");
		}
	}

	@Override
	public boolean isEmpty() {
		return beachLine.isEmpty();
	}

	@Override
	public void removeParabola(Parabola parabola) {
		// TODO Auto-generated method stub
		
	}


}
