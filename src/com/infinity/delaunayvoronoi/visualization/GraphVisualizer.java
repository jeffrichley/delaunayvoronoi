package com.infinity.delaunayvoronoi.visualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import com.infinity.delaunayvoronoi.algorithm.ClockwisePointComparator;
import com.infinity.delaunayvoronoi.model.Arc;
import com.infinity.delaunayvoronoi.model.Node;
import com.infinity.delaunayvoronoi.model.PanGraph;
import com.infinity.delaunayvoronoi.model.Point;
import com.infinity.delaunayvoronoi.model.Polygon;

/**
 * Visualize the <code>Polygon</code>s in a given <code>PanGraph</code>
 * @author jeffreyrichley
 */
public class GraphVisualizer<R extends Polygon, S extends Arc, T extends Node> {

	/**
	 * Write the visualization of the given <code>PanGraph</code> to the designated file
	 * @param graph The <code>PanGraph</code> to visualize
	 * @param fileName Name of the file to write the image to
	 */
	public void createImage(PanGraph<R, S, T> graph, String fileName) {
		int width = 0;
		int height = 0;
		
		for (Node node : graph.getNodes()) {
			Point point = node.getPoint();
			width = Math.max(width, (int) point.x);
			height = Math.max(height, (int) point.y);
		}
		
		// create the image
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		
		g.setColor(Color.BLACK);
		
		for (Polygon polygon : graph.getPolygons()) {
			List<Node> corners = new ArrayList<Node>(polygon.getCorners());
			Collections.sort(corners, new ClockwisePointComparator(corners));
			java.awt.Polygon shape = new java.awt.Polygon();
			for (Node corner : corners) {
				Point pt = corner.getPoint();
				shape.addPoint((int) pt.x, (int) pt.y);
			}
			g.drawPolygon(shape);
		}
		
		// write the image
		File file = new File(fileName);
		try {
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			throw new RuntimeException("Unable to write file: " + fileName, e);
		}
	}

}
