package vision.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vision.ImageProcessor;

public class PlateThresholder extends JPanel implements ItemListener, ChangeListener {
	private ImageProcessor imageProcessor;
	private JCheckBox chk_showYellowLocation;
	private JCheckBox chk_showBlueLocation;
	private JCheckBox chk_showPixels;
	private ThresholdsPanel thresholds;

	public PlateThresholder(ImageProcessor imageProcessor){
		this.imageProcessor = imageProcessor;
		
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
		thresholds.setHueSliderValues(64, 128);
		thresholds.setSaturationSliderValues(45, 256);
		thresholds.setValueSliderValues(105, 160);
		this.add(thresholds);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if (source == chk_showYellowLocation){
			if (chk_showYellowLocation.isSelected()){
				System.out.println("Marking the yellow robots.");
				imageProcessor.showYellow(true);
			} else {
				System.out.println("Not marking the yellow robots.");
				imageProcessor.showYellow(false);
			}
		} else if (source == chk_showBlueLocation){
			if (chk_showBlueLocation.isSelected()){
				System.out.println("Marking the blue robots.");
				imageProcessor.showBlue(true);
			} else {
				System.out.println("Not marking the blue robots.");
				imageProcessor.showBlue(false);
			}
		} else if (source == chk_showPixels){
			if (chk_showPixels.isSelected()){
				System.out.println("Showing plate pixels.");
				imageProcessor.showPlatePixels(true);
			} else {
				System.out.println("Not showing plate pixels.");
				imageProcessor.showPlatePixels(false);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource().equals(thresholds.getRedSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setPlateMinRed(source.getLowerValue());
			imageProcessor.setPlateMaxRed(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getBlueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setPlateMinBlue(source.getLowerValue());
			imageProcessor.setPlateMaxBlue(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getGreenSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setPlateMinGreen(source.getLowerValue());
			imageProcessor.setPlateMaxGreen(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getHueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setPlateMinHue(source.getLowerValue());
			imageProcessor.setPlateMaxHue(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getSaturationSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setPlateMinSaturation(source.getLowerValue());
			imageProcessor.setPlateMaxSaturation(source.getUpperValue());
		} else if (arg0.getSource().equals(thresholds.getValueSlider())){
			RangeSlider source = (RangeSlider) arg0.getSource();
			imageProcessor.setPlateMinValue(source.getLowerValue());
			imageProcessor.setPlateMaxValue(source.getUpperValue());
		}
	}
}
