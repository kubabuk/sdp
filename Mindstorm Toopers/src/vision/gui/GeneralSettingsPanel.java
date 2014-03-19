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
import vision.PitchConstants;
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
	JPanel saveButton;
	JPanel readyButton;
	JPanel whichPitch;
	JRadioButton main, side;
	JLabel pitch;
	
	World world;
	ImageProcessor imageProcessor;
	private JCheckBox showBoundaries;
	private JButton saveState;
	private JButton loadState;
	private JButton ready;
	private PitchConstants pitchConstants;
	private VisionGUI visionGUI;
	
	public GeneralSettingsPanel(final ImageProcessor imageProcessor, final World world, PitchConstants pitchConstants, VisionGUI visionGUI){
		this.visionGUI = visionGUI;
		this.world = world;
		this.imageProcessor = imageProcessor;
		this.pitchConstants = pitchConstants;
		
		colors = new JPanel();
		directions = new JPanel();
		boundaries = new JPanel();
		boundariesButton = new JPanel();
		saveButton = new JPanel();
		readyButton = new JPanel();
		whichPitch = new JPanel();
		pitch = new JLabel("Which pitch are we playing on?");
		main = new JRadioButton("Main Pitch");
		side = new JRadioButton("Side Pitch");
		main.addActionListener(this);
		side.addActionListener(this);
		main.setSelected(true);
		side.setSelected(false);
		
		ourColor = new JLabel("Choose our teams color:");
		ourDirection = new JLabel("Choose our shooting direction:");
		right = new JRadioButton("Right");
		left = new JRadioButton("Left");
		blue = new JRadioButton("Blue");
		yellow = new JRadioButton("Yellow");
		yellow.addActionListener(this);
		if (pitchConstants.isColor()){
			yellow.setSelected(true);
			world.setColor(true);
		} else {
			blue.setSelected(true);
			world.setColor(false);
		}
		blue.addActionListener(this);
		right.addActionListener(this);
		left.addActionListener(this);
		if (pitchConstants.isDirection()){
			right.setSelected(true);
			world.setDirection(true);
		} else {
			left.setSelected(true);
			world.setDirection(false);
		}
		showBoundaries = new JCheckBox("Show boundaries");
		showBoundaries.addItemListener(this);
		
		saveState = new JButton("Save Thresholds");
		saveState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Saving threshold values...");
				saveState();
			}
		});
		
		loadState = new JButton("Load Thresholds");
		loadState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Loading threshold values...");
//				loadState();
			}
		});
		
		ready = new JButton("Ready!");
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if (world.getReady()){
					world.setReady(false);
				} else {
					world.setReady(true);
				}
			}
		});
		
		ButtonGroup btnGroupColor = new ButtonGroup();
		ButtonGroup btnGroupDirection = new ButtonGroup();
		ButtonGroup btnGroupPitch = new ButtonGroup();
		btnGroupPitch.add(main);
		btnGroupPitch.add(side);
		this.add(pitch);
		whichPitch.add(main);
		whichPitch.add(side);
		this.add(whichPitch);
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
		saveButton.add(saveState);
		this.add(saveButton);
		readyButton.add(ready);
		this.add(readyButton);
		
	}
	
	public void saveState(){
		pitchConstants.saveConstants("mainPitchDimensions");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(yellow)){
			world.setColor(true);
			pitchConstants.setColor(true);
		} else if (arg0.getSource().equals(blue)){
			world.setColor(false);
			pitchConstants.setColor(false);
		} else if (arg0.getSource().equals(right)){
			world.setDirection(true);
			pitchConstants.setDirection(true);
		} else if (arg0.getSource().equals(left)){
			world.setDirection(false);
			pitchConstants.setDirection(false);
		} else if (arg0.getSource().equals(main)){
			pitchConstants.setPitchNum(0);
			visionGUI.refreshThresholds();
		} else if (arg0.getSource().equals(side)){
			pitchConstants.setPitchNum(1);
			visionGUI.refreshThresholds();
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
