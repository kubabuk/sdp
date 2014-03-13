package vision.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import world.World;

public class StatePanel extends JPanel {

	private World world;
	private JLabel ballTxt;
	private JLabel ballLoc;
	private JLabel colorTxt;
	private JLabel colorVal;
	private JLabel directionTxt;
	private JLabel directionVal;
	private JLabel ourRobots;
	private JLabel theirRobots;
	private JLabel ourAttackerLocTxt;
	private JLabel ourAttackerLoc;
	private JLabel ourAttackerDirTxt;
	private JLabel ourAttackerDir;
	private JLabel ourDefenderLocTxt;
	private JLabel ourDefenderLoc;
	private JLabel ourDefenderDirTxt;
	private JLabel ourDefenderDir;
	
	private JLabel theirAttackerLocTxt;
	private JLabel theirAttackerLoc;
	private JLabel theirAttackerDirTxt;
	private JLabel theirAttackerDir;
	private JLabel theirDefenderLocTxt;
	private JLabel theirDefenderLoc;
	private JLabel theirDefenderDirTxt;
	private JLabel theirDefenderDir;
	
	public StatePanel(World world){
		this.setLayout(null);
		this.world = world;
		
		colorTxt = new JLabel("Our color:");
		this.add(colorTxt);
		colorTxt.setLocation(5, 5);
		colorTxt.setSize(100,10);
		
		colorVal = new JLabel("Yellow");
		this.add(colorVal);
		colorVal.setLocation(125, 5);
		colorVal.setSize(100,10);
		
		directionTxt = new JLabel("Our direction:");
		this.add(directionTxt);
		directionTxt.setLocation(5,20);
		directionTxt.setSize(120, 15);
		
		directionVal = new JLabel("Right");
		this.add(directionVal);
		directionVal.setLocation(125, 20);
		directionVal.setSize(100, 15);
		
		ballTxt = new JLabel("Ball location:");
		this.add(ballTxt);
		ballTxt.setLocation(210, 5);
		ballTxt.setSize(100,10);
		
		ballLoc = new JLabel("(0,0)");
		this.add(ballLoc);
		ballLoc.setLocation(315, 5);
		ballLoc.setSize(100, 10);
		
		ourRobots = new JLabel("Our Robots");
		this.add(ourRobots);
		ourRobots.setLocation(80,40);
		ourRobots.setSize(100,15);
		
		theirRobots = new JLabel("Opponents Robots");
		this.add(theirRobots);
		theirRobots.setLocation(320,40);
		theirRobots.setSize(200, 15);
		
		ourAttackerLocTxt = new JLabel("Attacker Location:");
		this.add(ourAttackerLocTxt);
		ourAttackerLocTxt.setLocation(5,60);
		ourAttackerLocTxt.setSize(200, 15);
		
		ourAttackerDirTxt = new JLabel("Attacker Direction:");
		this.add(ourAttackerDirTxt);
		ourAttackerDirTxt.setLocation(5,80);
		ourAttackerDirTxt.setSize(200, 15);
		
		ourDefenderLocTxt = new JLabel("Defender Location:");
		this.add(ourDefenderLocTxt);
		ourDefenderLocTxt.setLocation(5,100);
		ourDefenderLocTxt.setSize(200, 15);
		
		ourDefenderDirTxt = new JLabel("Defender Direction:");
		this.add(ourDefenderDirTxt);
		ourDefenderDirTxt.setLocation(5,120);
		ourDefenderDirTxt.setSize(200, 15);
		
		ourAttackerLoc = new JLabel("(0,0)");
		this.add(ourAttackerLoc);
		ourAttackerLoc.setLocation(160,60);
		ourAttackerLoc.setSize(200, 15);
		
		ourAttackerDir = new JLabel("(0,0)");
		this.add(ourAttackerDir);
		ourAttackerDir.setLocation(160,80);
		ourAttackerDir.setSize(200, 15);
		
		ourDefenderLoc = new JLabel("(0,0)");
		this.add(ourDefenderLoc);
		ourDefenderLoc.setLocation(160,100);
		ourDefenderLoc.setSize(200, 15);
		
		ourDefenderDir = new JLabel("(0,0)");
		this.add(ourDefenderDir);
		ourDefenderDir.setLocation(160,120);
		ourDefenderDir.setSize(200, 15);
		
		theirAttackerLocTxt = new JLabel("Attacker Location:");
		this.add(theirAttackerLocTxt);
		theirAttackerLocTxt.setLocation(300,60);
		theirAttackerLocTxt.setSize(200, 15);
		
		theirAttackerDirTxt = new JLabel("Attacker Direction:");
		this.add(theirAttackerDirTxt);
		theirAttackerDirTxt.setLocation(300,80);
		theirAttackerDirTxt.setSize(200, 15);
		
		theirDefenderLocTxt = new JLabel("Defender Location:");
		this.add(theirDefenderLocTxt);
		theirDefenderLocTxt.setLocation(300,100);
		theirDefenderLocTxt.setSize(200, 15);
		
		theirDefenderDirTxt = new JLabel("Defender Direction:");
		this.add(theirDefenderDirTxt);
		theirDefenderDirTxt.setLocation(300,120);
		theirDefenderDirTxt.setSize(200, 15);
		
		theirAttackerLoc = new JLabel("(0,0)");
		this.add(theirAttackerLoc);
		theirAttackerLoc.setLocation(455,60);
		theirAttackerLoc.setSize(200, 15);
		
		theirAttackerDir = new JLabel("(0,0)");
		this.add(theirAttackerDir);
		theirAttackerDir.setLocation(455,80);
		theirAttackerDir.setSize(200, 15);
		
		theirDefenderLoc = new JLabel("(0,0)");
		this.add(theirDefenderLoc);
		theirDefenderLoc.setLocation(455,100);
		theirDefenderLoc.setSize(200, 15);
		
		theirDefenderDir = new JLabel("(0,0)");
		this.add(theirDefenderDir);
		theirDefenderDir.setLocation(455,120);
		theirDefenderDir.setSize(200, 15);
	}
	
	public void updateState(){
		ballLoc.setText(world.getBallPos().toString());
		if (world.getColor()){
			colorVal.setText("Yellow");
		} else {
			colorVal.setText("Blue");
		}
		if (world.getDirection()){
			directionVal.setText("Right");
		} else {
			directionVal.setText("Left");
		}
		ourAttackerLoc.setText(world.getAttackerPos().toString());
		ourDefenderLoc.setText(world.getDefenderPos().toString());
		ourAttackerDir.setText(world.getAttackerDir().toString());
		ourDefenderDir.setText(world.getDefenderDir().toString());
		theirAttackerLoc.setText(world.getOtherAttackerPos().toString());
		theirDefenderLoc.setText(world.getOtherDefenderPos().toString());
		theirAttackerDir.setText(world.getOtherAttackerDir().toString());
		theirDefenderDir.setText(world.getOtherDefenderDir().toString());
	}
}
