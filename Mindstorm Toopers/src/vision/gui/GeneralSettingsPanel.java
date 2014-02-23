package vision.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import vision.ImageProcessor;
import world.World;

public class GeneralSettingsPanel extends JPanel implements ActionListener {
	
	JRadioButton blue, yellow;
	JRadioButton right, left;
	JLabel ourDirection;
	JLabel ourColor;
	JPanel colors;
	JPanel directions;
	
	World world;
	ImageProcessor imageProcessor;
	
	public GeneralSettingsPanel(ImageProcessor imageProcessor, World world){
		this.world = world;
		this.imageProcessor = imageProcessor;
		
		colors = new JPanel();
		directions = new JPanel();
		ourColor = new JLabel("Choose our teams color:");
		ourDirection = new JLabel("Choose our shooting direction:");
		right = new JRadioButton("Right");
		left = new JRadioButton("Left");
		blue = new JRadioButton("Blue");
		yellow = new JRadioButton("Yellow");
		yellow.addActionListener(this);
		yellow.setSelected(true);
		blue.addActionListener(this);
		right.addActionListener(this);
		left.addActionListener(this);
		right.setSelected(true);
		
		ButtonGroup btnGroupColor = new ButtonGroup();
		ButtonGroup btnGroupDirection = new ButtonGroup();
		btnGroupColor.add(yellow);
		btnGroupColor.add(blue);
		btnGroupDirection.add(right);
		btnGroupDirection.add(left);
		this.add(ourColor);
		colors.add(yellow);
		colors.add(blue);
		this.add(colors);
		this.add(ourDirection);
		directions.add(right);
		directions.add(left);
		this.add(directions);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(yellow)){
			world.setColor(true);
		} else if (arg0.getSource().equals(blue)){
			world.setColor(false);
		} else if (arg0.getSource().equals(right)){
			world.setDirection(true);
		} else if (arg0.getSource().equals(left)){
			world.setDirection(false);
		}
	}
}
