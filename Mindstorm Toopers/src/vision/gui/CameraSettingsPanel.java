package vision.gui;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import world.World;
import vision.VisionRunner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A GUI panel for adjusting video device settings
 * 
 */
@SuppressWarnings("serial")
public class CameraSettingsPanel extends JPanel {
	// Values loaded when the settings file is missing
	private final static int DEFAULT_BRIGHTNESS = 128;
	private final static int DEFAULT_CONTRAST = 64;
	private final static int DEFAULT_SATURATION = 64;
	private final static int DEFAULT_HUE = 0;
	private final static int DEFAULT_CHROMA_GAIN = 0;
	private final static boolean DEFAULT_CHROMA_AGC = true;

	// need to check the values so that the world is initialized correctly
//	private World world;
	
	private VisionRunner vStream;

	private final int brightnessMin = 0;
	private final int brightnessMax = 255;
	private final JPanel brightnessPanel = new JPanel();
	private final JLabel brightnessLabel = new JLabel("Brightness:");
	private final JSlider brightnessSlider = new JSlider(brightnessMin,
			brightnessMax + 1);
	
	private final int contrastMin = 0;
	private final int contrastMax = 127;
	private final JPanel contrastPanel = new JPanel();
	private final JLabel contrastLabel = new JLabel("Contrast:");
	private JSlider contrastSlider = new JSlider(contrastMin, contrastMax + 1);
	
	private final int saturationMin = 0;
	private final int saturationMax = 127;
	private final JPanel saturationPanel = new JPanel();
	private final JLabel saturationLabel = new JLabel("Saturation:");
	private JSlider saturationSlider = new JSlider(saturationMin,
			saturationMax + 1);
	
	private final int hueMin = -128;
	private final int hueMax = 127;
	private final JPanel huePanel = new JPanel();
	private final JLabel hueLabel = new JLabel("Hue:");
	private JSlider hueSlider = new JSlider(hueMin, hueMax + 1);
	
	private final int chromaGainMin = 0;
	private final int chromaGainMax = 127;
	private final JPanel chromaGainPanel = new JPanel();
	private final JLabel chromaGainLabel = new JLabel("Chroma Gain:");
	private final JSlider chromaGainSlider = new JSlider(chromaGainMin,
			chromaGainMax + 1);

	public CameraSettingsPanel(VisionRunner vStream){
		this.vStream = vStream;
		brightnessPanel.add(brightnessLabel);
		brightnessPanel.add(brightnessSlider);
		brightnessSlider.addChangeListener(new BrightnessChangeListener());
		this.add(brightnessPanel);
		
		contrastPanel.add(contrastLabel);
		contrastPanel.add(contrastSlider);
		contrastSlider.addChangeListener(new ContrastChangeListener());
		contrastSlider.setValue(127);
		this.add(contrastPanel);
		
		huePanel.add(hueLabel);
		huePanel.add(hueSlider);
		hueSlider.addChangeListener(new HueChangeListener());
		this.add(huePanel);
		
		saturationPanel.add(saturationLabel);
		saturationPanel.add(saturationSlider);
		saturationSlider.addChangeListener(new SaturationChangeListener());
		saturationSlider.setValue(127);
		this.add(saturationPanel);
		
		chromaGainPanel.add(chromaGainLabel);
		chromaGainPanel.add(chromaGainSlider);
		chromaGainSlider.addChangeListener(new ChromaGainChangeListener());
		this.add(chromaGainPanel);
		
		loadDefaultSettings();
	}
	/**
	 * A ChangeListener to update the video stream's brightness setting when the
	 * brightness slider is adjusted
	 */
	private class BrightnessChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			vStream.setBrightness(Math.min(brightnessMax,
					brightnessSlider.getValue()));
			vStream.updateVideoSettings();
		}
	}

	/**
	 * A ChangeListener to update the video stream's contrast setting when the
	 * contrast slider is adjusted
	 */
	private class ContrastChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			vStream.setContrast(Math.min(contrastMax, contrastSlider.getValue()));
			vStream.updateVideoSettings();
		}
	}

	/**
	 * A ChangeListener to update the video stream's saturation setting when the
	 * saturation slider is adjusted
	 */
	private class SaturationChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			vStream.setSaturation(Math.min(saturationMax,
					saturationSlider.getValue()));
			vStream.updateVideoSettings();
		}
	}

	/**
	 * A ChangeListener to update the video stream's hue setting when the hue
	 * slider is adjusted
	 */
	private class HueChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			vStream.setHue(Math.min(hueMax, hueSlider.getValue()));
			vStream.updateVideoSettings();
		}
	}

	/**
	 * A ChangeListener to update the video stream's chroma gain setting when
	 * the chroma gain slider is adjusted
	 */
	private class ChromaGainChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			vStream.setChromaGain(Math.min(chromaGainMax,
					chromaGainSlider.getValue()));
			vStream.updateVideoSettings();
		}
	}

	private final JPanel chromaAGCPanel = new JPanel();
	private final JCheckBox chromaAGCCheckBox = new JCheckBox("Chroma AGC");

	/**
	 * An ActionListener to update the video stream's chroma AGC setting when
	 * the chroma AGC checkbox is activated either by mouse or keyboard
	 */
	private class ChromaAGCActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (chromaAGCCheckBox.isSelected())
				vStream.setChromaAGC(true);
			else
				vStream.setChromaAGC(false);

			vStream.updateVideoSettings();
		}
	}

	public CameraSettingsPanel(final VisionRunner vStream, String settingsFile) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.vStream = vStream;

		initialiseSlider(brightnessSlider, 16, 64);
		brightnessSlider.addChangeListener(new BrightnessChangeListener());
		brightnessPanel.add(brightnessLabel);
		brightnessPanel.add(brightnessSlider);
		this.add(brightnessPanel);

		initialiseSlider(contrastSlider, 8, 32);
		contrastSlider.addChangeListener(new ContrastChangeListener());
		contrastPanel.add(contrastLabel);
		contrastPanel.add(contrastSlider);
		this.add(contrastPanel);

		initialiseSlider(saturationSlider, 8, 32);
		saturationSlider.addChangeListener(new SaturationChangeListener());
		saturationPanel.add(saturationLabel);
		saturationPanel.add(saturationSlider);
		this.add(saturationPanel);

		initialiseSlider(hueSlider, 16, 64);
		hueSlider.addChangeListener(new HueChangeListener());
		huePanel.add(hueLabel);
		huePanel.add(hueSlider);
		this.add(huePanel);

		initialiseSlider(chromaGainSlider, 8, 32);
		chromaGainSlider.addChangeListener(new ChromaGainChangeListener());
		chromaGainPanel.add(chromaGainLabel);
		chromaGainPanel.add(chromaGainSlider);
		this.add(chromaGainPanel);

		chromaAGCCheckBox.addActionListener(new ChromaAGCActionListener());
		chromaAGCPanel.add(chromaAGCCheckBox);
		this.add(chromaAGCPanel);

		loadDefaultSettings();
	}

	/**
	 * Sets up initial settings for one of the sliders
	 * 
	 * @param slider
	 *            The slider to set up
	 * @param minorTick
	 *            The value difference between the smaller ticks on the slider
	 * @param majorTick
	 *            The value difference between the larger ticks on the slider
	 */
	private static void initialiseSlider(JSlider slider, int minorTick,
			int majorTick) {
		slider.setOrientation(JSlider.HORIZONTAL);
		slider.setMinorTickSpacing(minorTick);
		slider.setMajorTickSpacing(majorTick);

		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
	}

	/**
	 * Loads default video device settings in the event loadSettings fails
	 */
	private void loadDefaultSettings() {
		brightnessSlider.setValue(DEFAULT_BRIGHTNESS);
		vStream.setBrightness(DEFAULT_BRIGHTNESS);

		contrastSlider.setValue(DEFAULT_CONTRAST);
		vStream.setContrast(DEFAULT_CONTRAST);

		saturationSlider.setValue(DEFAULT_SATURATION);
		vStream.setSaturation(DEFAULT_SATURATION);

		hueSlider.setValue(DEFAULT_HUE);
		vStream.setHue(DEFAULT_HUE);

		chromaGainSlider.setValue(DEFAULT_CHROMA_GAIN);
		vStream.setChromaGain(DEFAULT_CHROMA_GAIN);

		chromaAGCCheckBox.setSelected(DEFAULT_CHROMA_AGC);
		vStream.setChromaAGC(DEFAULT_CHROMA_AGC);

		vStream.updateVideoSettings();
	}
}
