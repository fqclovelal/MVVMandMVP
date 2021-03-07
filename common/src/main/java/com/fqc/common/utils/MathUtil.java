package com.fqc.common.utils;

import android.graphics.Point;

public class MathUtil {

	/**
	 * 两点间距离
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static int getDistance(Point p1, Point p2){
		int x = Math.abs(p1.x - p2.x);
		int y = Math.abs(p1.y - p2.y);
		return (int) Math.sqrt(x*x + y*y);
	}
	
	

}
