package vision.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

/**
 * A UI container for holding the contents of each of the threshold tabs
 * 
 * @author Alex Adams (s1046358)
 */
@SuppressWarnings("serial")
class ThresholdsPanel extends JPanel {
	private static final int SLIDER_MIN = 0;
	private static final int SLIDER_MAX = 257;

	private final int redMin = SLIDER_MIN;
	private final int redMax = SLIDER_MAX;
	private final JPanel redPanel = new JPanel();
	private final JLabel redLabel = new JLabel("Red:");
	private InvertibleRangeSlider redSlider;

	private final int greenMin = SLIDER_MIN;
	private final int greenMax = SLIDER_MAX;
	private final JPanel greenPanel = new JPanel();
	private final JLabel greenLabel = new JLabel("Green:");
	private InvertibleRangeSlider greenSlider;

	private final int blueMin = SLIDER_MIN;
	private final int blueMax = SLIDER_MAX;
	private final JPanel bluePanel = new JPanel();
	private final JLabel blueLabel = new JLabel("Blue:");
	private InvertibleRangeSlider blueSlider;

	private final int hueMin = SLIDER_MIN;
	private final int hueMax = SLIDER_MAX;
	private final JPanel huePanel = new JPanel();
	private final JLabel hueLabel = new JLabel("Hue:");
	private InvertibleRangeSlider hueSlider;

	private final int saturationMin = SLIDER_MIN;
	private final int saturationMax = SLIDER_MAX;
	private final JPanel saturationPanel = new JPanel();
	private final JLabel saturationLabel = new JLabel("Sat:");
	private InvertibleRangeSlider saturationSlider;

	private final int valueMin = SLIDER_MIN;
	private final int valueMax = SLIDER_MAX;
	private final JPanel valuePanel = new JPanel();
	private final JLabel valueLabel = new JLabel("Value:");
	private InvertibleRangeSlider valueSlider;

	/**
	 * Constructs a ThresholdsPanel with the default setting of all minimums to
	 * 0, all maximums to 255.
	 * 
	 * TODO: Possibly add functionality for different settings. (Currently not
	 * needed)
	 */
	public ThresholdsPanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		redSlider = new InvertibleRangeSlider(redMin, redMax + 1);
		redSlider.setLowerValue(SLIDER_MIN);
		redSlider.setUpperValue(SLIDER_MAX);
		redPanel.add(redLabel);
		redPanel.add(redSlider);
		this.add(redPanel);

		greenSlider = new InvertibleRangeSlider(greenMin, greenMax + 1);
		greenSlider.setLowerValue(SLIDER_MIN);
		greenSlider.setUpperValue(SLIDER_MAX);
		greenPanel.add(greenLabel);
		greenPanel.add(greenSlider);
		this.add(greenPanel);

		blueSlider = new InvertibleRangeSlider(blueMin, blueMax + 1);
		blueSlider.setLowerValue(SLIDER_MIN);
		blueSlider.setUpperValue(SLIDER_MAX);
		bluePanel.add(blueLabel);
		bluePanel.add(blueSlider);
		this.add(bluePanel);

		hueSlider = new InvertibleRangeSlider(hueMin, hueMax + 1);
		hueSlider.setLowerValue(SLIDER_MIN);
		hueSlider.setUpperValue(SLIDER_MAX);
		huePanel.add(hueLabel);
		huePanel.add(hueSlider);
		this.add(huePanel);

		saturationSlider = new InvertibleRangeSlider(saturationMin,
				saturationMax + 1);
		saturationSlider.setLowerValue(SLIDER_MIN);
		saturationSlider.setUpperValue(SLIDER_MAX);
		saturationPanel.add(saturationLabel);
		saturationPanel.add(saturationSlider);
		this.add(saturationPanel);

		valueSlider = new InvertibleRangeSlider(valueMin, valueMax + 1);
		valueSlider.setLowerValue(SLIDER_MIN);
		valueSlider.setUpperValue(SLIDER_MAX);
		valuePanel.add(valueLabel);
		valuePanel.add(valueSlider);
		this.add(valuePanel);
	}
	
	public RangeSlider getRedSlider(){
		return redSlider.getSlider();
	}
	
	public RangeSlider getBlueSlider(){
		return blueSlider.getSlider();
	}
	
	public RangeSlider getGreenSlider(){
		return greenSlider.getSlider();
	}
	
	public RangeSlider getHueSlider(){
		return hueSlider.getSlider();
	}
	
	public RangeSlider getSaturationSlider(){
		return saturationSlider.getSlider();
	}
	
	public RangeSlider getValueSlider(){
		return valueSlider.getSlider();
	}

	/**
	 * Gets the lower and upper values for the red threshold slider
	 * 
	 * @return An int[] in the format {lower, upper}
	 */
	public int[] getRedSliderValues() {
		int[] lowerUpper = new int[] { redSlider.getLowerValue(),
				redSlider.getUpperValue() };
		return lowerUpper;
	}

	/**
	 * Sets the lower and upper values for the red threshold slider
	 * 
	 * @param lower
	 *            The new lower value
	 * @param upper
	 *            The new upper value
	 */
	public void setRedSliderValues(int lower, int upper) {
		redSlider.setLowerValue(lower);
		redSlider.setUpperValue(upper);
	}

	/**
	 * Tests if the red slider is inverted
	 * 
	 * @return true if it is inverted, false otherwise
	 */
	public boolean isRedSliderInverted() {
		return redSlider.isInverted();
	}

	/**
	 * Sets whether the red slider is inverted
	 * 
	 * @param inverted
	 *            true if it should be inverted, false otherwise
	 */
	public void setRedSliderInverted(boolean inverted) {
		redSlider.setInverted(inverted);
	}

	/**
	 * Gets the lower and upper values for the green threshold slider
	 * 
	 * @return An int[] in the format {lower, upper}
	 */
	public int[] getGreenSliderValues() {
		int[] lowerUpper = new int[] { greenSlider.getLowerValue(),
				greenSlider.getUpperValue() };
		return lowerUpper;
	}

	/**
	 * Sets the lower and upper values for the green threshold slider
	 * 
	 * @param lower
	 *            The new lower value
	 * @param upper
	 *            The new upper value
	 */
	public void setGreenSliderValues(int lower, int upper) {
		greenSlider.setLowerValue(lower);
		greenSlider.setUpperValue(upper);
	}

	/**
	 * Tests if the green slider is inverted
	 * 
	 * @return true if it is inverted, false otherwise
	 */
	public boolean isGreenSliderInverted() {
		return greenSlider.isInverted();
	}

	/**
	 * Sets whether the green slider is inverted
	 * 
	 * @param inverted
	 *            true if it should be inverted, false otherwise
	 */
	public void setGreenSliderInverted(boolean inverted) {
		greenSlider.setInverted(inverted);
	}

	/**
	 * Gets the lower and upper values for the blue threshold slider
	 * 
	 * @return An int[] in the format {lower, upper}
	 */
	public int[] getBlueSliderValues() {
		int[] lowerUpper = new int[] { blueSlider.getLowerValue(),
				blueSlider.getUpperValue() };
		return lowerUpper;
	}

	/**
	 * Sets the lower and upper values for the blue threshold slider
	 * 
	 * @param lower
	 *            The new lower value
	 * @param upper
	 *            The new upper value
	 */
	public void setBlueSliderValues(int lower, int upper) {
		blueSlider.setLowerValue(lower);
		blueSlider.setUpperValue(upper);
	}

	/**
	 * Tests if the blue slider is inverted
	 * 
	 * @return true if it is inverted, false otherwise
	 */
	public boolean isBlueSliderInverted() {
		return blueSlider.isInverted();
	}

	/**
	 * Sets whether the blue slider is inverted
	 * 
	 * @param inverted
	 *            true if it should be inverted, false otherwise
	 */
	public void setBlueSliderInverted(boolean inverted) {
		blueSlider.setInverted(inverted);
	}

	/**
	 * Gets the lower and upper values for the hue threshold slider
	 * 
	 * @return An int[] in the format {lower, upper}
	 */
	public int[] getHueSliderValues() {
		int[] lowerUpper = new int[] { hueSlider.getLowerValue(),
				hueSlider.getUpperValue() };
		return lowerUpper;
	}

	/**
	 * Sets the lower and upper values for the hue threshold slider
	 * 
	 * @param lower
	 *            The new lower value
	 * @param upper
	 *            The new upper value
	 */
	public void setHueSliderValues(int lower, int upper) {
		hueSlider.setLowerValue(lower);
		hueSlider.setUpperValue(upper);
	}
	
	/**
	 * Tests if the hue slider is inverted
	 * 
	 * @return true if it is inverted, false otherwise
	 */
	public boolean isHueSliderInverted() {
		return hueSlider.isInverted();
	}

	/**
	 * Sets whether the hue slider is inverted
	 * 
	 * @param inverted
	 *            true if it should be inverted, false otherwise
	 */
	public void setHueSliderInverted(boolean inverted) {
		hueSlider.setInverted(inverted);
	}

	/**
	 * Gets the lower and upper values for the saturation threshold slider
	 * 
	 * @return An int[] in the format {lower, upper}
	 */
	public int[] getSaturationSliderValues() {
		int[] lowerUpper = new int[] { saturationSlider.getLowerValue(),
				saturationSlider.getUpperValue() };
		return lowerUpper;
	}

	/**
	 * Sets the lower and upper values for the saturation threshold slider
	 * 
	 * @param lower
	 *            The new lower value
	 * @param upper
	 *            The new upper value
	 */
	public void setSaturationSliderValues(int lower, int upper) {
		saturationSlider.setLowerValue(lower);
		saturationSlider.setUpperValue(upper);
	}
	
	/**
	 * Tests if the saturation slider is inverted
	 * 
	 * @return true if it is inverted, false otherwise
	 */
	public boolean isSaturationSliderInverted() {
		return saturationSlider.isInverted();
	}

	/**
	 * Sets whether the saturation slider is inverted
	 * 
	 * @param inverted
	 *            true if it should be inverted, false otherwise
	 */
	public void setSaturationSliderInverted(boolean inverted) {
		saturationSlider.setInverted(inverted);
	}

	/**
	 * Gets the lower and upper values for the colour value threshold slider
	 * 
	 * @return An int[] in the format {lower, upper}
	 */
	public int[] getValueSliderValues() {
		int[] lowerUpper = new int[] { valueSlider.getLowerValue(),
				valueSlider.getUpperValue() };
		return lowerUpper;
	}

	/**
	 * Sets the lower and upper values for the colour value threshold slider
	 * 
	 * @param lower
	 *            The new lower value
	 * @param upper
	 *            The new upper value
	 */
	public void setValueSliderValues(int lower, int upper) {
		valueSlider.setLowerValue(lower);
		valueSlider.setUpperValue(upper);
	}
	
	/**
	 * Tests if the value slider is inverted
	 * 
	 * @return true if it is inverted, false otherwise
	 */
	public boolean isValueSliderInverted() {
		return valueSlider.isInverted();
	}

	/**
	 * Sets whether the value slider is inverted
	 * 
	 * @param inverted
	 *            true if it should be inverted, false otherwise
	 */
	public void setValueSliderInverted(boolean inverted) {
		valueSlider.setInverted(inverted);
	}

	/**
	 * Used to set all the slider values at once
	 * 
	 * @param index
	 *            The PitchConstants index that corresponds to this
	 *            ThresholdsPanel
	 * @param pitchConstants
	 *            A PitchConstants object holding the threshold values the
	 *            sliders are to be set to
	 */
/*	public void setSliderValues(int index, PitchConstants pitchConstants) {
		setRedSliderValues(pitchConstants.getRedLower(index),
				pitchConstants.getRedUpper(index));
		setRedSliderInverted(pitchConstants.isRedInverted(index));
		
		setGreenSliderValues(pitchConstants.getGreenLower(index),
				pitchConstants.getGreenUpper(index));
		setGreenSliderInverted(pitchConstants.isGreenInverted(index));
		
		setBlueSliderValues(pitchConstants.getBlueLower(index),
				pitchConstants.getBlueUpper(index));
		setBlueSliderInverted(pitchConstants.isBlueInverted(index));

		setHueSliderValues((int) (255.0 * pitchConstants.getHueLower(index)),
				(int) (255.0 * pitchConstants.getHueUpper(index)));
		setHueSliderInverted(pitchConstants.isHueInverted(index));
		
		setSaturationSliderValues(
				(int) (255.0 * pitchConstants.getSaturationLower(index)),
				(int) (255.0 * pitchConstants.getSaturationUpper(index)));
		setSaturationSliderInverted(pitchConstants.isSaturationInverted(index));
		
		setValueSliderValues(
				(int) (255.0 * pitchConstants.getValueLower(index)),
				(int) (255.0 * pitchConstants.getValueUpper(index)));
		setValueSliderInverted(pitchConstants.isValueInverted(index));
	}
*/
	/**
	 * Sets one (and only one) ChangeListener for the red slider
	 * 
	 * @param listener
	 *            The ChangeListener
	 */
	public void setRedSliderChangeListener(ChangeListener listener) {
		assert (redSlider.getChangeListeners().length == 0);
		redSlider.addChangeListener(listener);
	}

	/**
	 * Sets one (and only one) ChangeListener for the green slider
	 * 
	 * @param listener
	 *            The ChangeListener
	 */
	public void setGreenSliderChangeListener(ChangeListener listener) {
		assert (greenSlider.getChangeListeners().length == 0);
		greenSlider.addChangeListener(listener);
	}

	/**
	 * Sets one (and only one) ChangeListener for the blue slider
	 * 
	 * @param listener
	 *            The ChangeListener
	 */
	public void setBlueSliderChangeListener(ChangeListener listener) {
		assert (blueSlider.getChangeListeners().length == 0);
		blueSlider.addChangeListener(listener);
	}

	/**
	 * Sets one (and only one) ChangeListener for the hue slider
	 * 
	 * @param listener
	 *            The ChangeListener
	 */
	public void setHueSliderChangeListener(ChangeListener listener) {
		assert (hueSlider.getChangeListeners().length == 0);
		hueSlider.addChangeListener(listener);
	}

	/**
	 * Sets one (and only one) ChangeListener for the saturation slider
	 * 
	 * @param listener
	 *            The ChangeListener
	 */
	public void setSaturationSliderChangeListener(ChangeListener listener) {
		assert (saturationSlider.getChangeListeners().length == 0);
		saturationSlider.addChangeListener(listener);
	}

	/**
	 * Sets one (and only one) ChangeListener for the value slider
	 * 
	 * @param listener
	 *            The ChangeListener
	 */
	public void setValueSliderChangeListener(ChangeListener listener) {
		assert (valueSlider.getChangeListeners().length == 0);
		valueSlider.addChangeListener(listener);
	}
}
