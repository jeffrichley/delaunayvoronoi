package com.infinity.delaunayvoronoi.util;

/**
 * Used to time actions in the code for speed increases and benchmarking
 * @author Jeffrey.Richley
 */
public class Tick {

	private long start;
	private long last;

	/**
	 * Creates a new <code>Tick</code>
	 */
	public Tick() {
		this.start = System.currentTimeMillis();
		this.last = start;
	}
	
	/**
	 * Print that we have accomplished something and how long it took
	 * @param msg The message to pring
	 */
	public void tock(String msg) {
		long end = System.currentTimeMillis();
		long sinceStart = end - start;
		long sinceLast = end - last;
		
		last = end;
		
		System.out.println(msg + ": start=" + sinceStart + " last=" + sinceLast);
	}
}
