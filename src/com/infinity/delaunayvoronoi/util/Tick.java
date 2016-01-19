package com.infinity.delaunayvoronoi.util;

public class Tick {

	private long start;
	private long last;

	public Tick() {
		this.start = System.currentTimeMillis();
		this.last = start;
	}
	
	public void tock(String msg) {
		long end = System.currentTimeMillis();
		long sinceStart = end - start;
		long sinceLast = end - last;
		
		last = end;
		
		System.out.println(msg + ": start=" + sinceStart + " last=" + sinceLast);
	}
}
