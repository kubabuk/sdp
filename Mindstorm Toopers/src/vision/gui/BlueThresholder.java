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

public class BlueThresholder extends JPanel implements ItemListener, ChangeListener {
	private ImageProcessor imageProcessor;
	private JCheckBox chk_showLocation;
	private JCheckBox chk_showPixels;
	private ThresholdsPanel thresholds;
	private PitchConstants pitchConstants;

	public BlueThresholder(ImageProcessor imageProcessor, PitchConstants pitchConstants){
		this.imageProcessor = imageProcessor;
		this.pitchConstants = pitchConstants;
		chk_showLocation = new JCheckBox("Show Blue Location");
		chk_showLocation.addItemListener(this);
		this.add(chk_showLocation);
		
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
		thresholds.setRedSliderValues(pitchConstants.getRedLower(pitchConstants.BLUE), pitchConstants.getRedUpper(pitchConstants.BLUE));
		thresholds.setBlueSliderValues(pitchConstants.getBlueLower(pitchConstants.BLUE), pitchConstants.getBlueUpper(pitchConstants.BLUE));
		thresholds.setGreenSliderValues(pitchConstants.getGreenLower(pitchConstants.BLUE), pitchConstants.getGreenUpper(pitchConstants.BLUE));
		thresholds.setHueSliderValues(pitchConstants.getHueLower(pitchConstants.BLUE), pitchConstants.getHueUpper(pitchConstants.BLUE));
		thresholds.setValueSliderValues(pitchConstants.getValueLower(pitchConstants.BLUE), pitchConstants.getValueUpper(pitchConstants.BLUE));
		thresholds.setSaturationSliderValues(pitchConstants.getSaturationLower(pitchConstants.BLUE), pitchConstants.getSaturationUpper(pitchConstants.BLUE));
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if (source == chk_showLocation){
			if (chk_showLocation.isSelected()){
				System.out.println("Marking the blue.");
				imageProcessor.showBlue(true);
			} else {
				System.out.println("Not marking the blue.");
				imageProcessor.showBlue(false);
			}
		} else if (source == chk_showPixels){
			if (chk_showPixels.isSelected()){
				System.out.println("Showing blue pixels.");
				imageProcessor.showBluePixels(true);
			} else {
				System.out.println("Not showing blue pixels.");
				imageProcessor.showBluePixels(false);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource().equals(thresholds.getRedSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBlueMinRed(source.getLowerValue());
			imageProcessor.setBlueMaxRed(source.getUpperValue());
			pitchConstants.setRedLower(pitchConstants.BLUE, source.getLowerValue());
			pitchConstants.setRedUpper(pitchConstants.BLUE, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getBlueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBlueMinBlue(source.getLowerValue());
			imageProcessor.setBlueMaxBlue(source.getUpperValue());
			pitchConstants.setBlueLower(pitchConstants.BLUE, source.getLowerValue());
			pitchConstants.setBlueUpper(pitchConstants.BLUE, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getGreenSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBlueMinGreen(source.getLowerValue());
			imageProcessor.setBlueMaxGreen(source.getUpperValue());
			pitchConstants.setGreenLower(pitchConstants.BLUE, source.getLowerValue());
			pitchConstants.setGreenUpper(pitchConstants.BLUE, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getHueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBlueMinHue(source.getLowerValue());
			imageProcessor.setBlueMaxHue(source.getUpperValue());
			pitchConstants.setHueLower(pitchConstants.BLUE, source.getLowerValue());
			pitchConstants.setHueUpper(pitchConstants.BLUE, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getSaturationSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBlueMinSaturation(source.getLowerValue());
			imageProcessor.setBlueMaxSaturation(source.getUpperValue());
			pitchConstants.setSaturationLower(pitchConstants.BLUE, source.getLowerValue());
			pitchConstants.setSaturationUpper(pitchConstants.BLUE, source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getValueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBlueMinValue(source.getLowerValue());
			imageProcessor.setBlueMaxValue(source.getUpperValue());
			pitchConstants.setValueLower(pitchConstants.BLUE, source.getLowerValue());
			pitchConstants.setValueUpper(pitchConstants.BLUE, source.getUpperValue());
		}
	}
}
