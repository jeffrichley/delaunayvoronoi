package com.infinity.delaunayvoronoi.model;

public class BSTNode {

	/**
	 * The parent in the binary tree
	 */
	private BSTNode parent;
	
	/**
	 * The right child in the binary tree
	 */
	private BSTNode rightChild;
	
	/**
	 * The left child in the binary tree
	 */
	private BSTNode leftChild;
	
	/**
	 * Get the parent in the binary tree
	 * @return The parent of this node
	 */
	public BSTNode getParent() {
		return parent;
	}


	/**
	 * Set the parent of this node
	 * @param parent The node that is the parent of this one
	 */
	public void setParent(BSTNode parent) {
		this.parent = parent;
	}


	/**
	 * Get the right child of this node
	 * @return The right child of this node
	 */
	public BSTNode getRightChild() {
		return rightChild;
	}


	/**
	 * Set the right child of this node
	 * @param rightChild The right child of this node
	 */
	public void setRightChild(BSTNode rightChild) {
		this.rightChild = rightChild;
	}


	/**
	 * Get the left child of this node
	 * @return The left child of this node
	 */
	public BSTNode getLeftChild() {
		return leftChild;
	}


	/**
	 * Set the left child of this node
	 * @param leftChild The left child of this node
	 */
	public void setLeftChild(BSTNode leftChild) {
		this.leftChild = leftChild;
	}
	
	/**
	 * Tells if this node is a leaf of the binary tree
	 */
	public boolean isLeaf() {
		return leftChild == null && rightChild == null;
	}
	
}
