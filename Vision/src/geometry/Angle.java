package geometry;

import java.lang.Math;


public class Angle{

	public static double toRange(double angle){
		while(angle<0){
			angle =+ 2*Math.PI;
		}
		while(angle >= 2*Math.PI){
			angle =- 2*Math.PI;
		}
		return angle;
	}
}
