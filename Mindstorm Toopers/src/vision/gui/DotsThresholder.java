package vision.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vision.ImageProcessor;
import vision.PitchConstants;

public class DotsThresholder extends JPanel implements ItemListener, ChangeListener {
	private ImageProcessor imageProcessor;
	private JCheckBox chk_showLocation;
	private JCheckBox chk_showDirection;
	private JCheckBox chk_showPixels;
	private ThresholdsPanel thresholds;
	private PitchConstants pitchConstants;

	public DotsThresholder(ImageProcessor imageProcessor, PitchConstants pitchConstants){
		this.imageProcessor = imageProcessor;
		this.pitchConstants = pitchConstants;
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
		
		updateNewValues();
		this.add(thresholds);
	}
	
	public void updateNewValues(){
		thresholds.setRedSliderValues(pitchConstants.getRedLower(pitchConstants.GREY), pitchConstants.getRedUpper(pitchConstants.GREY));
		thresholds.setBlueSliderValues(pitchConstants.getBlueLower(pitchConstants.GREY), pitchConstants.getBlueUpper(pitchConstants.GREY));
		thresholds.setGreenSliderValues(pitchConstants.getGreenLower(pitchConstants.GREY), pitchConstants.getGreenUpper(pitchConstants.GREY));
		thresholds.setHueSliderValues(pitchConstants.getHueLower(pitchConstants.GREY), pitchConstants.getHueUpper(pitchConstants.GREY));
		thresholds.setValueSliderValues(pitchConstants.getValueLower(pitchConstants.GREY), pitchConstants.getValueUpper(pitchConstants.GREY));
		thresholds.setSaturationSliderValues(pitchConstants.getSaturationLower(pitchConstants.GREY), pitchConstants.getSaturationUpper(pitchConstants.GREY));
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
			pitchConstants.setRedLower(pitchConstants.GREY, source.getLowerValue());
			pitchConstants.setRedUpper(pitchConstants.GREY, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getBlueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinBlue(source.getLowerValue());
			imageProcessor.setDotsMaxBlue(source.getUpperValue());
			pitchConstants.setBlueLower(pitchConstants.GREY, source.getLowerValue());
			pitchConstants.setBlueUpper(pitchConstants.GREY, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getGreenSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinGreen(source.getLowerValue());
			imageProcessor.setDotsMaxGreen(source.getUpperValue());
			pitchConstants.setGreenLower(pitchConstants.GREY, source.getLowerValue());
			pitchConstants.setGreenUpper(pitchConstants.GREY, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getHueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinHue(source.getLowerValue());
			imageProcessor.setDotsMaxHue(source.getUpperValue());
			pitchConstants.setHueLower(pitchConstants.GREY, source.getLowerValue());
			pitchConstants.setHueUpper(pitchConstants.GREY, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getSaturationSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinSaturation(source.getLowerValue());
			imageProcessor.setDotsMaxSaturation(source.getUpperValue());
			pitchConstants.setSaturationLower(pitchConstants.GREY, source.getLowerValue());
			pitchConstants.setSaturationUpper(pitchConstants.GREY, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getValueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setDotsMinValue(source.getLowerValue());
			imageProcessor.setDotsMaxValue(source.getUpperValue());
			pitchConstants.setValueLower(pitchConstants.GREY, source.getLowerValue());
			pitchConstants.setValueUpper(pitchConstants.GREY, source.getUpperValue());
		}
	}
}
