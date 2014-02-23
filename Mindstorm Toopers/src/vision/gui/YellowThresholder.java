package vision.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vision.ImageProcessor;

public class YellowThresholder extends JPanel implements ItemListener, ChangeListener {
	private ImageProcessor imageProcessor;
	private JCheckBox chk_showLocation;
	private JCheckBox chk_showPixels;
	private ThresholdsPanel thresholds;

	public YellowThresholder(ImageProcessor imageProcessor){
		this.imageProcessor = imageProcessor;
		chk_showLocation = new JCheckBox("Show Yellow Location");
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
		this.add(thresholds);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if (source == chk_showLocation){
			if (chk_showLocation.isSelected()){
				System.out.println("Marking the yellow.");
				imageProcessor.showYellow(true);
			} else {
				System.out.println("Not marking the yellow.");
				imageProcessor.showYellow(false);
			}
		} else if (source == chk_showPixels){
			if (chk_showPixels.isSelected()){
				System.out.println("Showing yellow pixels.");
				imageProcessor.showYellowPixels(true);
			} else {
				System.out.println("Not showing yellow pixels.");
				imageProcessor.showYellowPixels(false);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource().equals(thresholds.getRedSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setYellowMinRed(source.getLowerValue());
			imageProcessor.setYellowMaxRed(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getBlueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setYellowMinBlue(source.getLowerValue());
			imageProcessor.setYellowMaxBlue(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getGreenSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setYellowMinGreen(source.getLowerValue());
			imageProcessor.setYellowMaxGreen(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getHueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setYellowMinHue(source.getLowerValue());
			imageProcessor.setYellowMaxHue(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getSaturationSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setYellowMinSaturation(source.getLowerValue());
			imageProcessor.setYellowMaxSaturation(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getValueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setYellowMinValue(source.getLowerValue());
			imageProcessor.setYellowMaxValue(source.getUpperValue());
		}
	}
}
