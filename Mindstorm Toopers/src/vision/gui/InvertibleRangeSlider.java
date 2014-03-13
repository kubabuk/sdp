package vision.gui;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class InvertibleRangeSlider extends JPanel {
	protected final RangeSlider slider;
	protected final JCheckBox invertCheckBox;

	public InvertibleRangeSlider() {
		super();
		this.slider = new RangeSlider();
		this.invertCheckBox = new JCheckBox("Inv");
		this.add(slider);
		this.add(invertCheckBox);
	}

	public InvertibleRangeSlider(int min, int max) {
		super();
		this.slider = new RangeSlider(min, max);
		this.invertCheckBox = new JCheckBox();
		this.add(slider);
		this.add(invertCheckBox);
	}
	
	public RangeSlider getSlider(){
		return this.slider;
	}

	public int getLowerValue() {
		return slider.getLowerValue();
	}

	public void setLowerValue(int value) {
		slider.setLowerValue(value);
	}

	public int getUpperValue() {
		return slider.getUpperValue();
	}

	public void setUpperValue(int value) {
		slider.setUpperValue(value);
	}

	public boolean isInverted() {
		return invertCheckBox.isSelected();
	}

	public void setInverted(boolean inverted) {
		invertCheckBox.setSelected(inverted);
	}

	public ChangeListener[] getChangeListeners() {
		return slider.getChangeListeners();
	}

	public void addChangeListener(ChangeListener listener) {
		slider.addChangeListener(listener);
		invertCheckBox.addChangeListener(listener);
	}
}
