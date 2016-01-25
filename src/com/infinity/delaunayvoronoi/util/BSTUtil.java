package com.infinity.delaunayvoronoi.util;

import com.infinity.delaunayvoronoi.model.BSTNode;

public class BSTUtil {
	
	public static BSTNode findSuccessor(BSTNode node) {
	    if (node == null) {
	        return null;
	    }
	    
	    if (node.getRightChild() != null) {
	        return findMinimum(node.getRightChild());
	    }
	   
	    BSTNode y = node.getParent();
	    BSTNode x = node;
	    while (y != null && x == y.getRightChild())
	    {
	        x = y;
	        y = y.getParent();
	    }

	    return y;
	}
	
	public static BSTNode findPredecessor(BSTNode node) {
	    if (node == null) {
	        return null;
	    }
	    
	    if (node.getLeftChild() != null) {
	        return findMaximum(node.getLeftChild());
	    }
	   
	    BSTNode y = node.getParent();
	    BSTNode x = node;
	    while (y != null && x == y.getLeftChild()) {
	        x = y;
	        y = y.getParent();
	    }

	    return y;
	}

	public static BSTNode findMinimum(BSTNode root) {
	    if (root == null) {
	        return null;
	    }
	  
	    if (root.getLeftChild() != null) {
	        return findMinimum(root.getLeftChild());
	    }
	  
	    return root;
	}
	
	public static BSTNode findMaximum(BSTNode root) {
	    if (root == null)
	        return null;
	   
	    if (root.getRightChild() != null)
	        return findMaximum(root.getRightChild());
	   
	    return root;
	}
}
