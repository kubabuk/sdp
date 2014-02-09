package geometry;

import java.lang.Math;


public class Angle{

	public static double toRange2PI(double angle){
		while(angle<0 || angle> 2*Math.PI){
			
			if(angle<0){
				angle =+ 2*Math.PI;}
		
			if(angle >= 2*Math.PI){
				angle =- 2*Math.PI;
			}
		}
		return angle;
	}
	
	public static double toRangePI(double angle){
		while(angle<0 || angle> Math.PI){
			
			if(angle<0){
				angle =+ Math.PI;}
		
			if(angle >= Math.PI){
				angle =- Math.PI;
			}
		}
		return angle;
	}
	
}
