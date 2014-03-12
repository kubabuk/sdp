package vision;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A class that holds the constants for various values about the pitch, such as
 * thresholding values and dimension variables.
 * 
 * @author Alex Adams (s1046358)
 */
public class PitchConstants {
	/** The number of objects there are thresholds for */
	public static final int NUM_THRESHOLDS = 5;

	/** The threshold index associated with the ball */
	public static final int BALL = 0;
	/** The threshold index associated with the blue robot */
	public static final int BLUE = 1;
	/** The threshold index associated with the yellow robot */
	public static final int YELLOW = 2;
	/** The threshold index associated with the grey circles */
	public static final int GREY = 3;
	/** The threshold index associated with the green plate */
	public static final int GREEN = 4;

	/** The minimum value for the red, green, and blue colour components */
	public static final int RGBMIN = 0;
	/** The maximum value for the red, green, and blue colour components */
	public static final int RGBMAX = 255;
	/** The minimum value for the hue, saturation, and value colour components */
	public static final int HSVMIN = 0;
	/** The maximum value for the hue, saturation, and value colour components */
	public static final int HSVMAX = 255;

	// The pitch number. 0 is the main pitch, 1 is the side pitch
	private int pitchNum;

	// Threshold upper and lower values
	private int[] redLower = new int[NUM_THRESHOLDS];
	private int[] redUpper = new int[NUM_THRESHOLDS];
	private int[] greenLower = new int[NUM_THRESHOLDS];
	private int[] greenUpper = new int[NUM_THRESHOLDS];
	private int[] blueLower = new int[NUM_THRESHOLDS];
	private int[] blueUpper = new int[NUM_THRESHOLDS];
	private int[] hueLower = new int[NUM_THRESHOLDS];
	private int[] hueUpper = new int[NUM_THRESHOLDS];
	private int[] saturationLower = new int[NUM_THRESHOLDS];
	private int[] saturationUpper = new int[NUM_THRESHOLDS];
	private int[] valueLower = new int[NUM_THRESHOLDS];
	private int[] valueUpper = new int[NUM_THRESHOLDS];
	// Debug
	private boolean[] debug = new boolean[NUM_THRESHOLDS];

	// Pitch dimensions
	// When scanning the pitch we look at pixels starting from 0 + topBuffer and
	// 0 + leftBuffer, and then scan to pixels at 480 - bottomBuffer and 640 -
	// rightBuffer.
	private int topBuffer;
	private int bottomBuffer;
	private int leftBuffer;
	private int rightBuffer;
	
	// Color and direction we're shooting
	private boolean color;
	private boolean direction;
	
	private int brightness;
	private int contrast;
	private int hue;
	private int saturation;
	private int chroma_gain;

	/**
	 * Default constructor.
	 * 
	 * @param pitchNum
	 *            The pitch that we are on.
	 */
	public PitchConstants(int pitchNum) {
		for (int i = 0; i < NUM_THRESHOLDS; ++i)
			debug[i] = false;
		// Just call the setPitchNum method to load in the constants
		setPitchNum(pitchNum);
	}

	/**
	 * Gets the lower threshold value for red for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The lower red threshold value
	 */
	public int getRedLower(int i) {
		return redLower[i];
	}

	/**
	 * Sets the lower threshold value for red for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param lower
	 *            The value to set the threshold to
	 */
	public void setRedLower(int i, int lower) {
		this.redLower[i] = lower;
	}

	/**
	 * Gets the upper threshold value for red for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The upper red threshold value
	 */
	public int getRedUpper(int i) {
		return redUpper[i];
	}

	/**
	 * Sets the upper threshold value for red for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param upper
	 *            The value to set the threshold to
	 */
	public void setRedUpper(int i, int upper) {
		this.redUpper[i] = upper;
	}

	/**
	 * Gets the lower threshold value for green for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The lower green threshold value
	 */
	public int getGreenLower(int i) {
		return greenLower[i];
	}

	/**
	 * Sets the lower threshold value for green for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param lower
	 *            The value to set the threshold to
	 */
	public void setGreenLower(int i, int lower) {
		this.greenLower[i] = lower;
	}

	/**
	 * Gets the upper threshold value for green for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The upper green threshold value
	 */
	public int getGreenUpper(int i) {
		return greenUpper[i];
	}

	/**
	 * Sets the upper threshold value for green for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param upper
	 *            The value to set the threshold to
	 */
	public void setGreenUpper(int i, int upper) {
		this.greenUpper[i] = upper;
	}
	
	/**
	 * Gets the lower threshold value for blue for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The lower blue threshold value
	 */
	public int getBlueLower(int i) {
		return blueLower[i];
	}

	/**
	 * Sets the lower threshold value for blue for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param lower
	 *            The value to set the threshold to
	 */
	public void setBlueLower(int i, int lower) {
		this.blueLower[i] = lower;
	}

	/**
	 * Gets the upper threshold value for blue for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The upper blue threshold value
	 */
	public int getBlueUpper(int i) {
		return blueUpper[i];
	}

	/**
	 * Sets the upper threshold value for blue for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param upper
	 *            The value to set the threshold to
	 */
	public void setBlueUpper(int i, int upper) {
		this.blueUpper[i] = upper;
	}

	/**
	 * Gets the lower threshold value for hue for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The lower hue threshold value
	 */
	public int getHueLower(int i) {
		return hueLower[i];
	}

	/**
	 * Sets the lower threshold value for hue for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param lower
	 *            The value to set the threshold to
	 */
	public void setHueLower(int i, int lower) {
		this.hueLower[i] = lower;
	}

	/**
	 * Gets the upper threshold value for hue for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The upper hue threshold value
	 */
	public int getHueUpper(int i) {
		return hueUpper[i];
	}

	/**
	 * Sets the upper threshold value for hue for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param upper
	 *            The value to set the threshold to
	 */
	public void setHueUpper(int i, int upper) {
		this.hueUpper[i] = upper;
	}

	/**
	 * Gets the lower threshold value for colour saturation for the object
	 * specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The lower colour saturation threshold value
	 */
	public int getSaturationLower(int i) {
		return saturationLower[i];
	}

	/**
	 * Sets the lower threshold value for colour saturation for the object
	 * specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param lower
	 *            The value to set the threshold to
	 */
	public void setSaturationLower(int i, int lower) {
		this.saturationLower[i] = lower;
	}

	/**
	 * Gets the upper threshold value for colour saturation for the object
	 * specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The upper colour saturation threshold value
	 */
	public int getSaturationUpper(int i) {
		return saturationUpper[i];
	}

	/**
	 * Sets the upper threshold value for colour saturation for the object
	 * specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param upper
	 *            The value to set the threshold to
	 */
	public void setSaturationUpper(int i, int upper) {
		this.saturationUpper[i] = upper;
	}

	/**
	 * Gets the lower threshold value for colour value for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The lower colour value threshold value
	 */
	public int getValueLower(int i) {
		return valueLower[i];
	}

	/**
	 * Sets the lower threshold value for colour value for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param lower
	 *            The value to set the threshold to
	 */
	public void setValueLower(int i, int lower) {
		this.valueLower[i] = lower;
	}

	/**
	 * Gets the upper threshold value for colour value for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return The upper colour value threshold value
	 */
	public int getValueUpper(int i) {
		return valueUpper[i];
	}

	/**
	 * Sets the upper threshold value for colour value for the object specified
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param upper
	 *            The value to set the threshold to
	 */
	public void setValueUpper(int i, int upper) {
		this.valueUpper[i] = upper;
	}

	/**
	 * Gets the width of the pitch
	 * 
	 * @return the width of the pitch in pixels
	 */
	public int getPitchWidth() {
		return (640 - rightBuffer - leftBuffer);
	}

	/**
	 * Gets the height of the pitch
	 * 
	 * @return the height of the pitch in pixels
	 */
	public int getPitchHeight() {
		return (480 - bottomBuffer - topBuffer);
	}

	/**
	 * Gets the top buffer for the pitch
	 * 
	 * @return the distance from the top of the pitch to the top of the image
	 *         produced by the video device
	 */
	public int getTopBuffer() {
		return topBuffer;
	}

	/**
	 * Sets the top buffer for the pitch
	 * 
	 * @param topBuffer
	 *            The new value for the top buffer
	 */
	public void setTopBuffer(int topBuffer) {
		this.topBuffer = topBuffer;
	}

	/**
	 * Gets the bottom buffer for the pitch
	 * 
	 * @return the distance from the bottom of the pitch to the bottom of the
	 *         image produced by the video device
	 */
	public int getBottomBuffer() {
		return bottomBuffer;
	}

	/**
	 * Sets the bottom buffer for the pitch
	 * 
	 * @param bottomBuffer
	 *            The new value for the bottom buffer
	 */
	public void setBottomBuffer(int bottomBuffer) {
		this.bottomBuffer = bottomBuffer;
	}

	/**
	 * Gets the left buffer for the pitch
	 * 
	 * @return the distance from the left of the pitch to the left of the image
	 *         produced by the video device
	 */
	public int getLeftBuffer() {
		return leftBuffer;
	}

	/**
	 * Sets the left buffer for the pitch
	 * 
	 * @param leftBuffer
	 *            The new value for the left buffer
	 */
	public void setLeftBuffer(int leftBuffer) {
		this.leftBuffer = leftBuffer;
	}

	/**
	 * Gets the right buffer for the pitch
	 * 
	 * @return the distance from the right of the pitch to the right of the
	 *         image produced by the video device
	 */
	public int getRightBuffer() {
		return rightBuffer;
	}

	/**
	 * Sets the right buffer for the pitch
	 * 
	 * @param topBuffer
	 *            The new value for the right buffer
	 */
	public void setRightBuffer(int rightBuffer) {
		this.rightBuffer = rightBuffer;
	}

	/**
	 * Tests whether debug mode is enabled for the threshold set i refers to
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @return true if debug mode is enabled, false otherwise
	 */
	public boolean debugMode(int i) {
		return this.debug[i];
	}

	/**
	 * Enables or disables debug mode for the threshold set i refers to. This
	 * method permits multiple debug modes to be enabled
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param debug
	 *            A boolean value to enable debug mode if true, and disable
	 *            otherwise
	 */
	public void setDebugMode(int i, boolean debug) {
		this.debug[i] = debug;
	}

	/**
	 * Enables or disables debug mode for the threshold set i refers to. This
	 * method permits multiple debug modes to be enabled only if allowMultiple
	 * is set to true.
	 * 
	 * @param i
	 *            One of: BALL, BLUE, YELLOW, GREY, GREEN - other values will
	 *            cause an ArrayIndexOutOfBoundsException
	 * @param debug
	 *            A boolean value to enable debug mode if true, and disable
	 *            otherwise
	 * @param allowMultiple
	 *            A boolean value specifying whether to allow multiple debug
	 *            modes to be set
	 */
	public void setDebugMode(int i, boolean debug, boolean allowMultiple) {
		if (allowMultiple)
			setDebugMode(i, debug);
		else {
			for (int j = 0; j < 5; ++j)
				setDebugMode(j, (i == j) && debug);
		}
	}

	/**
	 * Gets the current pitch number
	 * 
	 * @return The pitch number
	 */
	public int getPitchNum() {
		return this.pitchNum;
	}

	/**
	 * Sets a new pitch number, loading in constants from the corresponding
	 * file.
	 * 
	 * @param newPitchNum
	 *            The pitch number to use.
	 */
	public void setPitchNum(int newPitchNum) {
		assert (newPitchNum == 0 || newPitchNum == 1) : "Invalid pitch number";
		this.pitchNum = newPitchNum;

		loadConstants(System.getProperty("user.dir") + "/constants/pitch"
				+ pitchNum);
	}

	/**
	 * Save the constants to a file.
	 * 
	 * @param fileName
	 *            The file to save the constants to
	 */
	public void saveConstants(String fileName) {
		try {
			// Update the pitch dimensions file
//			FileWriter pitchDimFile = new FileWriter(new File("constants/pitch"
//					+ pitchNum + "Dimensions"));
//			pitchDimFile.write(String.valueOf(getTopBuffer()) + "\n");
//			pitchDimFile.write(String.valueOf(getBottomBuffer()) + "\n");
//			pitchDimFile.write(String.valueOf(getLeftBuffer()) + "\n");
//			pitchDimFile.write(String.valueOf(getRightBuffer()) + "\n");
//			pitchDimFile.close();

			FileWriter pitchFile = new FileWriter(new File(System.getProperty("user.dir") + "/constants/pitch"
					+ pitchNum));

			// Iterate over the ball, blue robot, yellow robot, grey circles,
			// and green plates in the order they're defined above.
			for (int i = 0; i < NUM_THRESHOLDS; ++i) {
				pitchFile.write(String.valueOf(getRedLower(i)) + "\n");
				pitchFile.write(String.valueOf(getRedUpper(i)) + "\n");

				pitchFile.write(String.valueOf(getGreenLower(i)) + "\n");
				pitchFile.write(String.valueOf(getGreenUpper(i)) + "\n");

				pitchFile.write(String.valueOf(getBlueLower(i)) + "\n");
				pitchFile.write(String.valueOf(getBlueUpper(i)) + "\n");

				pitchFile.write(String.valueOf(getHueLower(i)) + "\n");
				pitchFile.write(String.valueOf(getHueUpper(i)) + "\n");

				pitchFile.write(String.valueOf(getSaturationLower(i)) + "\n");
				pitchFile.write(String.valueOf(getSaturationUpper(i)) + "\n");

				pitchFile.write(String.valueOf(getValueLower(i)) + "\n");
				pitchFile.write(String.valueOf(getValueUpper(i)) + "\n");
			}
			pitchFile.write(String.valueOf(getBrightness()) + '\n');
			pitchFile.write(String.valueOf(getContrast()) + '\n');
			pitchFile.write(String.valueOf(getHue()) + '\n');
			pitchFile.write(String.valueOf(getSaturation()) + '\n');
			pitchFile.write(String.valueOf(getChroma_gain()) + '\n');
			
			pitchFile.write(String.valueOf(isColor()) + '\n');
			pitchFile.write(String.valueOf(isDirection()) + '\n');
			pitchFile.close();

			System.out.println("Wrote successfully!");
		} catch (IOException e) {
			System.err.println("Cannot save constants file " + fileName + ":");
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Load in the constants from a file. Note that this assumes that the
	 * constants file is well formed.
	 * 
	 * @param fileName
	 *            The file to load the constants from.
	 */
	public void loadConstants(String fileName) {
		Scanner scannerDim;

		try {
			scannerDim = new Scanner(new File(fileName + "Dimensions"));
			assert (scannerDim != null);

			// Pitch Dimensions
			this.topBuffer = scannerDim.nextInt();
			this.bottomBuffer = scannerDim.nextInt();
			this.leftBuffer = scannerDim.nextInt();
			this.rightBuffer = scannerDim.nextInt();

			scannerDim.close();
		} catch (Exception e) {
			System.err.println("Cannot load pitch dimensions file " + fileName
					+ "Dimensions:");
			System.err.println(e.getMessage());
			loadDefaultConstants();
			return;
		}

		Scanner scanner;

		try {
			scanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("Cannot load thresholds file " + fileName + ":");
			System.err.println(e.getMessage());
			loadDefaultConstants();
			return;
		}

		assert (scanner != null);

		// Iterate over the ball, blue robot, yellow robot, grey circles, and
		// green plates in the order they're defined above.
		for (int i = 0; i < NUM_THRESHOLDS; ++i) {
			this.redLower[i] = scanner.nextInt();
			this.redUpper[i] = scanner.nextInt();
			this.greenLower[i] = scanner.nextInt();
			this.greenUpper[i] = scanner.nextInt();
			this.blueLower[i] = scanner.nextInt();
			this.blueUpper[i] = scanner.nextInt();
			this.hueLower[i] = scanner.nextInt();
			this.hueUpper[i] = scanner.nextInt();
			this.saturationLower[i] = scanner.nextInt();
			this.saturationUpper[i] = scanner.nextInt();
			this.valueLower[i] = scanner.nextInt();
			this.valueUpper[i] = scanner.nextInt();
		}
		
		this.brightness = scanner.nextInt();
		this.contrast = scanner.nextInt();
		this.hue = scanner.nextInt();
		this.saturation = scanner.nextInt();
		this.chroma_gain = scanner.nextInt();
		
		this.color = scanner.nextBoolean();
		this.direction = scanner.nextBoolean();

		scanner.close();
	}

	/**
	 * Loads default values for the constants, used when loading from a file
	 * fails.
	 */
	public void loadDefaultConstants() {
		// Iterate over the ball, blue robot, yellow robot, grey circles, and
		// green plates in the order they're defined above.
		for (int i = 0; i < 5; ++i) {
			this.redLower[i] = RGBMIN;
			this.redUpper[i] = RGBMAX;
			this.greenLower[i] = RGBMIN;
			this.greenUpper[i] = RGBMAX;
			this.blueLower[i] = RGBMIN;
			this.blueUpper[i] = RGBMAX;
			this.hueLower[i] = HSVMIN;
			this.hueUpper[i] = HSVMAX;
			this.saturationLower[i] = HSVMIN;
			this.saturationUpper[i] = HSVMAX;
			this.valueLower[i] = HSVMIN;
			this.valueUpper[i] = HSVMAX;
		}

		// Pitch Dimensions
		this.topBuffer = 40;
		this.bottomBuffer = 40;
		this.leftBuffer = 20;
		this.rightBuffer = 20;
	}

	public void setColor(boolean color) {
		this.color = color;
	}
	
	public boolean getColor() {
		return this.color;
	}
	
	public boolean getDirection() {
		return this.direction;
	}

	public boolean isColor() {
		return color;
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}

	public boolean isDirection() {
		return direction;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	public int getBrightness() {
		return brightness;
	}

	public void setContrast(int contrast) {
		this.contrast = contrast;
	}

	public int getContrast() {
		return contrast;
	}

	public void setHue(int hue) {
		this.hue = hue;
	}

	public int getHue() {
		return hue;
	}

	public void setSaturation(int saturation) {
		this.saturation = saturation;
	}

	public int getSaturation() {
		return saturation;
	}

	public void setChroma_gain(int chroma_gain) {
		this.chroma_gain = chroma_gain;
	}

	public int getChroma_gain() {
		return chroma_gain;
	}
}
