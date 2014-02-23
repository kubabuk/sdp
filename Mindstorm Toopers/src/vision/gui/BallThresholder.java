package vision.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vision.ImageProcessor;

public class BallThresholder extends JPanel implements ItemListener, ChangeListener {
	private ImageProcessor imageProcessor;
	private JCheckBox chk_showLocation;
	private JCheckBox chk_showPixels;
	private ThresholdsPanel thresholds;

	public BallThresholder(ImageProcessor imageProcessor){
		this.imageProcessor = imageProcessor;
		chk_showLocation = new JCheckBox("Show Ball Location");
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
		thresholds.setRedSliderValues(128, 256);
		thresholds.setGreenSliderValues(0, 96);
		this.add(thresholds);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if (source == chk_showLocation){
			if (chk_showLocation.isSelected()){
				System.out.println("Marking the ball.");
				imageProcessor.showBall(true);
			} else {
				System.out.println("Not marking the ball.");
				imageProcessor.showBall(false);
			}
		} else if (source == chk_showPixels){
			if (chk_showPixels.isSelected()){
				System.out.println("Showing ball pixels.");
				imageProcessor.showBallPixels(true);
			} else {
				System.out.println("Not showing ball pixels.");
				imageProcessor.showBallPixels(false);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource().equals(thresholds.getRedSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBallMinRed(source.getLowerValue());
			imageProcessor.setBallMaxRed(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getBlueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBallMinBlue(source.getLowerValue());
			imageProcessor.setBallMaxBlue(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getGreenSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBallMinGreen(source.getLowerValue());
			imageProcessor.setBallMaxGreen(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getHueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBallMinHue(source.getLowerValue());
			imageProcessor.setBallMaxHue(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getSaturationSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBallMinSaturation(source.getLowerValue());
			imageProcessor.setBallMaxSaturation(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getValueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setBallMinValue(source.getLowerValue());
			imageProcessor.setBallMaxValue(source.getUpperValue());
		}
	}
}
