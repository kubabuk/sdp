package strategy;



import geometry.Point;
import geometry.Vector;
import world.World;

public class State {
	//This class represents which state the robot is in,
	//i.e. how the environment is like and what our robots are doing
	
	private int state,mode;
	private Point goal;
	private boolean test;
	
	//       State         ||       Situation   
	// 		 	0						*
	//			1				ball goes to defender    -- milestone 3
	//			2				defender near the ball *
	//			3				defender got the ball
	//			4						*
	//          The states below are for attacker
	//			5				ball goes to attacker    -- milestone 3
	//			6				attacker near the ball   -- milestone 3
	//			7				attacker got the ball  *
	//			8						*
	//			9						*		
	// There may be more states if needed, state with * are states that can possibly be removed
	
	public State()
	{
		this.state = 0;
		this.mode = 0;
		this.goal = new Point(0,0);
	}
	
	public State (int mode)
	{
		//1 for attacker, 0 for defender
		this.mode = mode;
		
	}
	
	public int getState()
	{
		return this.state;
	}
	
	public Point getGoal()
	{
		return this.goal;
	}
	
	public void update(World w)
	{
		// This function calculates and updates the current state 
		int newstate=0;
		//
		
		if (mode == 0)
		{
			//defender
			
		}
		else
		{
			//attacker
			Point b = w.getBallPos();
			//Point b = new Point(10,5);
			//System.out.println(b.toString());
			Point r = w.getAttacker().getPos();
			System.out.println(r.toString());
			// get the orientation of the attacker
			Vector o = w.getAttacker().getDir();
			// calculate the coordinate of the kick position
			Point g = new Point(50.0,0.0); // the middle point of the goal
			Vector bg = new Vector(b,g);
			double kickdistance = 10.0; // the distance the robot should be kept between the robot center and the ball
			Vector pg = new Vector(new Point(0,0), kickdistance + bg.getMagnitude(),bg.getOrientation());
			Point p = new Point(g.getX()-pg.getX(),g.getY()-pg.getY()); //kickpoint;
			
			
			
//			if (Point.pointDistance(r, p) < 5 )
//			{
//				this.goal = b;
//				System.out.println("State = 6");
//				newstate = 6;
//			}
//			else
//			{	
//				this.goal = p;
//				System.out.println("State = 5");
//				newstate = 5;
//			}
			newstate = 9;
			System.out.println("State = 9");
			this.goal = p;
			
			
			
		}
		
		
		
		
		// changes the state
		this.state = newstate;
	}
}
