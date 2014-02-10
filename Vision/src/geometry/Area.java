package geometry;

import java.lang.Math;


public class Area {
	
	private Vector[] edges; 

	public Area( Point a, Point b, Point c){
		
		this.setEdges(new Vector[] {new Vector(a,b), new Vector(b,c), new Vector (c,a)});
	}
	
	public Area( Point a, Point b, Point c, Point d){
		
		this.setEdges(new Vector[] {new Vector(a,b), new Vector(b,c), new Vector (c,d), new Vector (d,a)});
	}
	
	public Area ( Area a, Area b){
		
		//TODO
		this.edges = a.getEdges();
	}

	/**
	 * @param edges the edges to set
	 */
	public void setEdges(Vector[] edges) {
		this.edges = edges;
	}

	/**
	 * @return the edges
	 */
	public Vector[] getEdges() {
		return edges;
	}
}
