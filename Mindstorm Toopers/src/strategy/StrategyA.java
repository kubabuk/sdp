package strategy;


import world.World;
import geometry.Point;
import geometry.Vector;
import commands.AttackerQueue;
import commands.Command;

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
			System.out.println("The ball is at "+b.getX() +" , "+ b.getY());
			//Point b = new Point(10,5);
			//System.out.println(b.toString());
			Point r = w.getAttacker().getPos();
			System.out.println("The robot is at "+r.getX() +" , "+ r.getY());
			
			System.out.println(r.toString());
			// get the orientation of the attacker
			Vector o = w.getAttacker().getDir();
			

			// calculate the coordinate of the kick position
			
			Point g = new Point(474,114); // the middle point of the goal
			Vector bg = new Vector(b,g);
			
			
			
			double kickdistance = 10.0; // the distance the robot should be kept between the robot center and the ball
			
			Vector pg = new Vector(new Point(0,0), kickdistance + bg.getMagnitude(),bg.getOrientation());
			
			Point p = new Point(g.getX()-pg.getX(),g.getY()-pg.getY()); //kickpoint;
		
			System.out.println("The kick point is at "+p.getX() +" , "+ p.getY());
			Vector rp = new Vector(r,p);
			
			Vector Turning1 = new Vector(r,0,rp.getOrientation()); 
			Vector step = new Vector(r,10,rp.getOrientation());
			//generate the queue of commands
			//Queue c5 = new Queue(o.getOrientation());
			if (Point.pointDistance(r, s.getGoal())>5)
			
			{
			if (o.getOrientation()<Turning1.getOrientation()-0.1 || o.getOrientation()>Turning1.getOrientation()+0.1)
			{
				aq.add(Turning1);
				System.out.println("Turning sent");
			}
			aq.add(rp); //was step, now testing rp
			
			System.out.println("step sent move fowards");
			
			}
		}
		case 6:
		{
			Point b = w.getBallPos();
			//Point b = new Point(10,5);
			Point g = new Point(474,114); // the middle point of the goal
			Vector bg = new Vector(b,g);
			
			
			double kickdistance = 20.0; // the distance the robot should be kept between the robot center and the ball
			
			Vector pg = new Vector(new Point(0,0), kickdistance + bg.getMagnitude(), bg.getOrientation());
			// turn the robot toward the kick position

			Vector o = w.getAttacker().getDir();
			Point r = w.getAttacker().getPos();
			Vector Turning2 = new Vector(r,0,bg.getOrientation());
			
			//generate the queue of commands
			//Queue c6 = new Queue(o.getOrientation());
			
			if (Point.pointDistance(r, s.getGoal())>5)
			{
			if (o.getOrientation()<Turning2.getOrientation()-0.1 || o.getOrientation()>Turning2.getOrientation()+0.1)
			{
				aq.add(Turning2);
				System.out.println("Try to face the ball!");
			}
			aq.add(new Vector(r,5,Turning2.getOrientation()));
			aq.addKick(); //kick
			System.out.println("kick!");
			}
			
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
