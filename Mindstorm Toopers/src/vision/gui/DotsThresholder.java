package vision.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vision.ImageProcessor;

public class DotsThresholder extends JPanel implements ItemListener, ChangeListener {
	private ImageProcessor imageProcessor;
	private JCheckBox chk_showLocation;
	private JCheckBox chk_showDirection;
	private JCheckBox chk_showPixels;
	private ThresholdsPanel thresholds;

	public DotsThresholder(ImageProcessor imageProcessor){
		this.imageProcessor = imageProcessor;
		chk_showLocation = new JCheckBox("Show Dots Location");
		chk_showLocation.addItemListener(this);
		this.add(chk_showLocation);
		
		chk_showDirection = new JCheckBox("Show Robot Directions");
		chk_showDirection.addItemListener(this);
		this.add(chk_showDirection);
		
		chk_showPixels = new JCheckBox("Show detected pixels");
		chk_showPixels.addItemListener(this);
		this.add(chk_showPixels);
		
		thresholds = new ThresholdsPanel();
		thresholds.setRedSliderChangeListener(this);
		thresholds.setBlueSliderChangeListener(this);
		thresholds.setGreenSliderChangeListener(this);
		thresholds.setHueSliderChangeListener(this);
		thresholds.setSaturationSliderChangeListener(this);
		thresholds.setValueSliderChangeListener(this);
		thresholds.setValueSliderValues(0, 64);
		this.add(thresholds);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if (source == chk_showLocation){
			if (chk_showLocation.isSelected()){
				System.out.println("Marking the dots.");
				imageProcessor.showDots(true);
			} else {
				System.out.println("Not marking the dots.");
				imageProcessor.showDots(false);
			}
		} else if (source == chk_showPixels){
			if (chk_showPixels.isSelected()){
				System.out.println("Showing dots pixels.");
				imageProcessor.showDotsPixels(true);
			} else {
				System.out.println("Not showing dots pixels.");
				imageProcessor.showDotsPixels(false);
			}
		} else if (source == chk_showDirection){
			if (chk_showDirection.isSelected()){
				System.out.println("Showing robot directions.");
				imageProcessor.showDirections(true);
			} else {
				System.out.println("Not showing robot directions.");
				imageProcessor.showDirections(false);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource().equals(thresholds.getRedSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinRed(source.getLowerValue());
			imageProcessor.setDotsMaxRed(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getBlueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinBlue(source.getLowerValue());
			imageProcessor.setDotsMaxBlue(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getGreenSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinGreen(source.getLowerValue());
			imageProcessor.setDotsMaxGreen(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getHueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinHue(source.getLowerValue());
			imageProcessor.setDotsMaxHue(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getSaturationSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinSaturation(source.getLowerValue());
			imageProcessor.setDotsMaxSaturation(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getValueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinValue(source.getLowerValue());
			imageProcessor.setDotsMaxValue(source.getUpperValue());
		}
	}
}
