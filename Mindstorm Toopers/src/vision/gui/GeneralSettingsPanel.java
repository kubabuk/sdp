package vision.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import vision.ImageProcessor;
import world.World;

public class GeneralSettingsPanel extends JPanel implements ActionListener, ItemListener {
	
	JRadioButton blue, yellow;
	JRadioButton right, left;
	JLabel ourDirection;
	JLabel ourColor;
	JPanel colors;
	JPanel directions;
	JPanel boundaries;
	JPanel boundariesButton;
	
	World world;
	ImageProcessor imageProcessor;
	private JCheckBox showBoundaries;
	private JButton calculateBoundaries;
	
	public GeneralSettingsPanel(final ImageProcessor imageProcessor, World world){
		this.world = world;
		this.imageProcessor = imageProcessor;
		
		colors = new JPanel();
		directions = new JPanel();
		boundaries = new JPanel();
		boundariesButton = new JPanel();
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
		
		showBoundaries = new JCheckBox("Show boundaries");
		showBoundaries.addItemListener(this);
		calculateBoundaries = new JButton("Recalculate Boundaries");
		calculateBoundaries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Recalculating boundaries.");
				imageProcessor.recalculateBoundaries(true);
			}
		});
		
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
		boundaries.add(showBoundaries);
		this.add(boundaries);
		boundariesButton.add(calculateBoundaries);
		this.add(boundariesButton);
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

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		Object source = arg0.getItemSelectable();
		if (source == showBoundaries){
			if (showBoundaries.isSelected()){
				imageProcessor.showBoundaries(true);
			} else {
				imageProcessor.showBoundaries(false);
			}
		}
	}
}
