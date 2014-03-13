package vision.gui;

// FROM: https://github.com/ernieyu/Swing-range-slider
/* The MIT License

 Copyright (c) 2010 Ernest Yu. All rights reserved.

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE. */

import javax.swing.JSlider;


/**
 * An extension of JSlider to select a range of values using two thumb controls.
 * The thumb controls are used to select the lower and upper value of a range
 * with predetermined minimum and maximum values.
 * 
 * Note that RangeSlider makes use of the default BoundedRangeModel, which
 * supports an inner range defined by a value and an extent. The upper value
 * returned by RangeSlider is simply the lower value plus the extent.
 */
@SuppressWarnings("serial")
class RangeSlider extends JSlider {	
	/**
	 * Constructs a RangeSlider with default minimum and maximum values of 0 and
	 * 100.
	 */
	public RangeSlider() {
		initSlider(10, 50);
	}

	/**
	 * Constructs a RangeSlider with the specified default minimum and maximum
	 * values.
	 * 
	 * @param min
	 *            The minimum value for the slider
	 * @param max
	 *            The maximum value for the slider
	 */
	public RangeSlider(int min, int max) {
		super(min, max);
		initSlider(Math.abs(max - min) / 8, Math.abs(max - min) / 4);
	}

	/**
	 * Initializes the slider by setting default properties.
	 * 
	 * @param minorTick
	 *            The value difference between the smaller ticks on the slider
	 * @param majorTick
	 *            The value difference between the larger ticks on the slider
	 */
	private void initSlider(int minorTick, int majorTick) {
		setOrientation(HORIZONTAL);

		setMinorTickSpacing(minorTick);
		setMajorTickSpacing(majorTick);
		setPaintTicks(true);
		setPaintLabels(true);
	}

	/**
	 * Overrides the superclass method to install the UI delegate to draw two
	 * thumbs.
	 */
	@Override
	public void updateUI() {
		setUI(new RangeSliderUI(this));
		// Update UI for slider labels. This must be called after updating the
		// UI of the slider. Refer to JSlider.updateUI()
		updateLabelUIs();
	}

	/**
	 * Provided only for UI code - sliders don't work correctly otherwise.
	 */
	@Override
	@Deprecated
	public int getValue() {
		return getLowerValue();
	}

	/**
	 * Gets the lower value in the range.
	 * 
	 * @return The lower value in the range.
	 */
	public int getLowerValue() {
		return super.getValue();
	}

	/**
	 * Provided only for UI code - sliders don't work correctly otherwise.
	 */
	@Override
	@Deprecated
	public void setValue(int value) {
		setLowerValue(value);
	}

	/**
	 * Sets the lower value in the range.
	 * 
	 * @param value
	 *            The new lower value.
	 */
	public void setLowerValue(int value) {
		int oldValue = getLowerValue();

		if (oldValue == value)
			return;

		// Compute new value and extent to maintain upper value
		int oldExtent = getExtent();
		int newValue = Math.min(Math.max(getMinimum(), value), oldValue
				+ oldExtent);
		int newExtent = oldExtent + oldValue - newValue;

		// Set new value and extent, and fire a single change event
		getModel().setRangeProperties(newValue, newExtent, getMinimum(),
				getMaximum(), getValueIsAdjusting());
	}

	/**
	 * Gets the upper value in the range.
	 * 
	 * @return The upper value in the range.
	 */
	public int getUpperValue() {
		return getLowerValue() + getExtent();
	}

	/**
	 * Sets the upper value in the range.
	 * 
	 * @param value
	 *            The new upper value.
	 */
	public void setUpperValue(int value) {
		// Compute new extent
		int lowerValue = getLowerValue();
		int newExtent = Math.min(Math.max(0, value - lowerValue), getMaximum()
				- lowerValue);

		// Set extent to set upper value.
		setExtent(newExtent);
	}
}
