package strategy;


import world.World;
import geometry.Point;
import geometry.Vector;
import commands.AttackerQueue;;

public class StrategyA {
	//strategy for attacker
	
	public static void getAction(State s , World w, AttackerQueue aq)
	{
		// this functions takes the state and decide what robots should do

		
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
		
		
		
		switch (s.getState())
		{
		
		case 5:
		{
			
		
			// get the coordinate of the ball and the attacker
			Point b = w.getBallPos();
			Point r = w.getAttacker().getPos();
			// get the orientation of the attacker
			Vector o = w.getAttacker().getDir();
			

			// calculate the coordinate of the kick position
			
			Point g = new Point(100,0); // the middle point of the goal
			Vector bg = new Vector(b,g);
			
			double kickdistance = 10.0; // the distance the robot should be kept between the robot center and the ball
			
			Vector pg = new Vector(new Point(0,0), kickdistance + bg.getMagnitude(),bg.getOrientation());
			
			Point p = new Point(g.getX()-pg.getX(),g.getY()-pg.getY()); //kickpoint;
		
			Vector rp = new Vector(r,p);
			
			Vector Turning1 = new Vector(r,0,rp.getOrientation()-o.getOrientation()); 
			//generate the queue of commands
			//Queue c5 = new Queue(o.getOrientation());
			aq.add(Turning1);
			aq.add(rp);

			
		}
		case 6:
		{
			Point b = w.getBallPos();
			Point g = new Point(100,0); // the middle point of the goal
			Vector bg = new Vector(b,g);
			
			
			double kickdistance = 10.0; // the distance the robot should be kept between the robot center and the ball
			
			Vector pg = new Vector(new Point(0,0), kickdistance + bg.getMagnitude(),bg.getOrientation());
			// turn the robot toward the kick position

			Vector o = w.getAttacker().getDir();
			Point r = w.getAttacker().getPos();
			AttackerQueue x = new AttackerQueue(10.1);
			AttackerQueue c = new AttackerQueue(o.getOrientation());
			Vector Turning2 = new Vector(r,0,pg.getOrientation()-o.getOrientation());
		
			//generate the queue of commands
			//Queue c6 = new Queue(o.getOrientation());
			aq.add(Turning2);
			aq.addKick(); //kick
			
		
			
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
		
		
		
		
	}
	
	
	
}
