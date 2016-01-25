package com.infinity.delaunayvoronoi.algorithm.voronoi;

public class TreeHelper {

	public static Parabola getLeft(Parabola p) {
		return getLeftChild(getLeftParent(p));
	}
	
	public static Parabola getRight(Parabola p) {
		return getRightChild(getRightParent(p));
	}
	
	public static Parabola getLeftParent(Parabola p) {
		Parabola par = p.getParent();
		Parabola pLast	= p;
		while(par.getLeft() == pLast) { 
			if(par.getParent() == null) return null;
			pLast = par; 
			par = par.getParent(); 
		}
		return par;
	}
	
	public static Parabola getRightParent(Parabola p) {
		Parabola par = p.getParent();
		Parabola pLast = p;
		while(par.getRight() == pLast) { 
			if(par.getParent() == null) return null;
			pLast = par; par = par.getParent(); 
		}
		return par;
	}
	
	public static Parabola getLeftChild(Parabola p) {
		if(p == null) {
			return null;
		}
		Parabola par = p.getLeft();
		while(!par.isLeaf()) {
			par = par.getRight();
		}
		return par;
	}
	
	public static Parabola getRightChild(Parabola p) {
		if(p == null) {
			return null;
		}
		Parabola par = p.getRight();
		while(!par.isLeaf()) {
			par = par.getLeft();
		}
		return par;
	}
	
}
