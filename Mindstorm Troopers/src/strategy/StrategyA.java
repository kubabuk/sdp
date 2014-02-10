package strategy;
import world.World;
import geometry.Point;
import geometry.Vector;


public class StrategyA {
	//strategy for attacker
	
	public Action getAction(State s , World w)
	{
		// this functions takes the state and decide what robots should do
		Action a = new Action();
		
		//       State         ||       Situation   
		// 		 	0						*
		//			1				ball goes to defender    -- milestone 3
		//			2				defender near the ball *
		//			3				defender got the ball
		//			4						*
		//          The states below are for attacker
		//			5				ball goes to attacker
		//			6				attacker near the ball   -- milestone 3
		//			7				attacker got the ball  *
		//			8						*
		//			9						*		
		// There may be more states if needed, state with * are states that can possibly be removed
		
		
		
		switch (s.getState())
		{
		
		case 5:
		{
			// get the coordinate of the ball and the attacker
			Point b = w.getBall().getPos();
			Point r = w.getRobot().getPos();
			// get the orientation of the attacker
			Vector o = w.getRobot().getDir();
			// calculate the coordinate of the kick position
			
			Point g = new Point(100,0); // the middle point of the goal
			Vector bg = new Vector(b,g);
			
			double kickdistance = 10.0; // the distance the robot should be kept between the robot center and the ball
			
			Vector pg = new Vector(new Point(0,0), kickdistance + bg.getMagnitude(),bg.getOrientation());
			
			Point p = new Point(g.getX()-pg.getX(),g.getY()-pg.getY()); //kickpoint;
		
			Vector rp = new Vector(r,p);
			// turn the robot toward the kick position
			Vector Turning1 = new Vector(r,0,rp.getOrientation()-o.getOrientation());
			
			Command.add(Turning1);
			Command.add(rp);
			
			Vector o1 = w.getRobot().getDir();
			Point r1 = w.getRobot().getPos();
			
			Vector Turning2 = new Vector(r1,0,pg.getOrientation()-o1.getOrientation());
			Command.add(Turning2);
			
			Command.kick();
			
			
			
		}
		case 6:
		{
			
			
			
		}
		case 7:
		{
			
		}
		case 8:
		{
			
		}
		case 9:
		{
			
		}
		default:
		{
			
		}
		
		}
		
		
		
		return a;
	}
	
	
	
}
