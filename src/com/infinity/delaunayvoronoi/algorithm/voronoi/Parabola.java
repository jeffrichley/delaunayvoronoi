package com.infinity.delaunayvoronoi.algorithm.voronoi;

import com.infinity.delaunayvoronoi.model.Point;

/**
 * Represents the sweeting parabolas in the Voronoi algorithm.
 * Used a binary tree data structure
 * @author Jeffrey.Richley
 */
public class Parabola {

	/**
	 * The top of the <code>Parabola</code>
	 */
	private final Point site;
	
	/**
	 * The parent in the binary tree
	 */
	private Parabola parent;
	
	/**
	 * The right child in the binary tree
	 */
	private Parabola rightChild;
	
	/**
	 * The left child in the binary tree
	 */
	private Parabola leftChild;

	/**
	 * Creates a new VerticalParabola
	 * @param site
	 */
	public Parabola(Point site) {
		this.site = site;
	}


	/**
	 * Get the site that originated the <code>Parabola</code>
	 * @return
	 */
	public Point getSite() {
		return site;
	}


	/**
	 * Get the parent in the binary tree
	 * @return The parent of this node
	 */
	public Parabola getParent() {
		return parent;
	}


	/**
	 * Set the parent of this node
	 * @param parent The node that is the parent of this one
	 */
	public void setParent(Parabola parent) {
		this.parent = parent;
	}


	/**
	 * Get the right child of this node
	 * @return The right child of this node
	 */
	public Parabola getRightChild() {
		return rightChild;
	}


	/**
	 * Set the right child of this node
	 * @param rightChild The right child of this node
	 */
	public void setRightChild(Parabola rightChild) {
		this.rightChild = rightChild;
	}


	/**
	 * Get the left child of this node
	 * @return The left child of this node
	 */
	public Parabola getLeftChild() {
		return leftChild;
	}


	/**
	 * Set the left child of this node
	 * @param leftChild The left child of this node
	 */
	public void setLeftChild(Parabola leftChild) {
		this.leftChild = leftChild;
	}
	
	/**
	 * Tells if this node is a leaf of the binary tree
	 */
	public boolean isLeaf() {
		return leftChild == null && rightChild == null;
	}
	
}
