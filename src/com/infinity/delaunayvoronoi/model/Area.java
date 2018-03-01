package com.infinity.delaunayvoronoi.model;

import java.util.ArrayList;
import java.util.List;

public class Area {

	private final double upperX;
	private final double upperY;
	private final double lowerX;
	private final double lowerY;
	
	private final List<Point> points = new ArrayList<>();
	
	private final List<Area> childAreas = new ArrayList<>();
	
	public Area(double upperX, double upperY, double lowerX, double lowerY) {
		this.upperX = upperX;
		this.upperY = upperY;
		this.lowerX = lowerX;
		this.lowerY = lowerY;
	}

	public void addPoint(Point point) {
		points.add(point);
	}
	
	public int getNumberOfPoints() {
		return points.size();
	}
	
	public boolean contains(Point point) {
		double x = point.x;
		double y = point.y;
		
		return upperX <= x && lowerX >= x && upperY <= y && lowerY >= y;
	}

	/**
	 * Splits this one area into 4 others.  Better known as a Quadtree datastructure
	 * @see https://en.wikipedia.org/wiki/Quadtree
	 * @return A list of the newly created <code>Area</code>s
	 */
	public void split(int maxPoints) {
		
		double width = lowerX - upperX;
		double height = lowerY - upperY;
		
		// lets first make the new areas
		Area areaOne = null;
		Area areaTwo = null;
		Area areaThree = null;
		Area areaFour = null;
		
		double midY = upperY + height/2;
		double midX = upperX + width/2;
		
		areaOne = new Area(upperX, upperY, midX, midY);
		areaTwo = new Area(midX, upperY, lowerX, midY);
		areaThree = new Area(upperX, midY, midX, lowerY);
		areaFour = new Area(midX, midY, lowerX, lowerY);
		
		childAreas.add(areaOne);
		childAreas.add(areaTwo);
		childAreas.add(areaThree);
		childAreas.add(areaFour);
		
		// now lets take the points and distribute them to the new areas
		for (Point point : points) {
			for (Area childArea : childAreas) {
				if (childArea.contains(point)) {
					childArea.addPoint(point);
				}
			}
		}
		
		// now we need to see if the children need to be split
		for (Area childArea : childAreas) {
			if (!childArea.isValid(maxPoints)) {
				childArea.split(maxPoints);
			}
		}
	}

	public List<Point> getPoints() {
		return points;
	}

	public boolean isValid(int maxPoints) {
		boolean answer = true;
		
		// if we don't have children, check if we have to many points
		if (childAreas.isEmpty()) {
			answer = points.size() <= maxPoints;
		} else {
			// if we have child areas, ask them if they are valid
			for (int i = 0; i < childAreas.size() && answer; i++) {
				Area child = childAreas.get(i);
				answer = child.isValid(maxPoints);
			}
		}
		
		return answer;
	}
	
}
