package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

public interface BeachLine {

	Parabola getArcUnderPoint(Point insertedPoint);

	void replaceParabola(Parabola replaceMe, Parabola a, Edge leftEdge, Parabola b, Edge rightEdge, Parabola c);

	Parabola getParabolaLeftOfParabola(Parabola parabola);

	Parabola getParabolaRightOfParabola(Parabola parabola);

	void replaceSequenceWithEdge(Parabola leftArc, Parabola removingParabola, Parabola rightArc, Edge newEdge);

	Edge getEdgeLeftOfParabola(Parabola parabola);

	Edge getEdgeRightOfParabola(Parabola parabola);

	void addFirstParabola(Parabola parabola);

	boolean isEmpty();

	void removeParabola(Parabola parabola);

}
