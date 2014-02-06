import geometry.Point;
import geometry.Vector;

public class Ball {

	private Point coordinate;
	private Vector orientation;
	private int speed;
	private boolean moving,caught;
	
	public boolean isMoving()
	{
		return moving;
	}
	
	public setMoving (boolean m)
	{
		this.moving = m;
	}
	
	public Point getPos() 
	{
		return coordinate;
	}
	
	public void setPos(Point xy) 
	{
		 this.coordinate = xy;
	}
	
	public Vector getDir()
	{
		return orientation;
	}
	
	public void setDir(Vector xy)
	{
		this.orientation = xy;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(int v)
	{
		this.speed = v;
	}
	
	public boolean iscaught()
	{
		return caught;
	}
	
	public void setCaught(boolean a) 
	{ 
		this.caught = a; 
	}
	
}
