package vision;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import world.World;
import geometry.Point;
import geometry.Vector;

// The purpose of the ImageProcessor class is to find objects given an image.
// It should not draw anything onto the image itself (except maybe for testing)
// That is the job of the GUI.

public class ImageProcessor {

	static Color green = new Color(0,255,0);
	static Color red = new Color(255,0,0);
	static Color yellow = new Color(255,255,0);
	static Color blue = new Color(0,0,255);
	static Color white = new Color(255,255,255);
	
	// using those variables to calculate the speed of the ball
	static double pavgRX = 0;
	static double pavgRY = 0;
	static double speedX = 0;
	static double speedY = 0;
	
	// use these variable to easily change the threshold values for various colours
	// detecting red:
	private static int ballMinRed = 128;
	private static int ballMaxRed = 255;
	private static int ballMinBlue = 0;
	private static int ballMaxBlue = 255;
	private static int ballMinGreen = 0;
	private static int ballMaxGreen = 96;
	private static int ballMinHue = 0;
	private static int ballMaxHue = 255;
	private static int ballMinSaturation = 0;
	private static int ballMaxSaturation = 255;
	private static int ballMinValue = 0;
	private static int ballMaxValue = 255;
	// detecting yellow:
	private static int yellowMinRed = 0;
	private static int yellowMaxRed = 255;
	private static int yellowMinBlue = 0;
	private static int yellowMaxBlue = 255;
	private static int yellowMinGreen = 0;
	private static int yellowMaxGreen = 255;
	private static int yellowMinHue = 0;
	private static int yellowMaxHue = 255;
	private static int yellowMinSaturation = 0;
	private static int yellowMaxSaturation = 255;
	private static int yellowMinValue = 0;
	private static int yellowMaxValue = 255;
	// detecting blue:
	private static int blueMinRed = 0;
	private static int blueMaxRed = 255;
	private static int blueMinBlue = 0;
	private static int blueMaxBlue = 255;
	private static int blueMinGreen = 0;
	private static int blueMaxGreen = 255;
	private static int blueMinHue = 0;
	private static int blueMaxHue = 255;
	private static int blueMinSaturation = 0;
	private static int blueMaxSaturation = 255;
	private static int blueMinValue = 0;
	private static int blueMaxValue = 255;
	// detecting dots:
	private static int dotsMinRed = 0;
	private static int dotsMaxRed = 255;
	private static int dotsMinBlue = 0;
	private static int dotsMaxBlue = 255;
	private static int dotsMinGreen = 0;
	private static int dotsMaxGreen = 255;
	private static int dotsMinHue = 0;
	private static int dotsMaxHue = 255;
	private static int dotsMinSaturation = 0;
	private static int dotsMaxSaturation = 255;
	private static int dotsMinValue = 0;
	private static int dotsMaxValue = 64;
	// detecting plates:
	private static int plateMinRed = 0;
	private static int plateMaxRed = 255;
	private static int plateMinBlue = 0;
	private static int plateMaxBlue = 255;
	private static int plateMinGreen = 0;
	private static int plateMaxGreen = 255;
	private static int plateMinHue = 64;
	private static int plateMaxHue = 128;
	private static int plateMinSaturation = 64;
	private static int plateMaxSaturation = 255;
	private static int plateMinValue = 110;
	private static int plateMaxValue = 160;
	
	// These variables keep track of the last location of the various objects.
	// This is used to reduce noise in the values returned for their locations.
	private static Point[] lastDot;
	private static Point[] lastRobot;
	
	// This is the main world object which is passed to ImageProcessor1 when
	// it's initialised.
	private static World world;
	
	private static boolean ballVisible;
	private static boolean ballPixels;
	
	private static boolean yellowVisible;
	private static boolean yellowPixels;
	
	private static boolean blueVisible;
	private static boolean bluePixels;
	
	private static boolean dotsVisible;
	private static boolean dotsPixels;
	
	private static boolean plateVisible;
	private static boolean platePixels;
	private static boolean directionsVisible;
	
	// This constructor sets a default starting position for each of the objects.
	// The vision system will take a few seconds to stabilise after initialisation.
	public ImageProcessor(World newWorld){
		this.world = newWorld;
		Point start = new Point(320,240);
		lastDot = new Point[4];
		lastDot[0] = start;
		lastDot[1] = start;
		lastDot[2] = start;
		lastDot[3] = start;
		lastRobot = new Point[4];
		lastRobot[0] = start;
		lastRobot[1] = start;
		lastRobot[2] = start;
		lastRobot[3] = start;
	}
	
	// This is the main method which updates the state of the world.
	// It updates the static world object which was initialised in the constructor.
	public static Image trackWorld(BufferedImage img) {
		double redX = 0.0, redY = 0.0;
		double yellowLeftX = 0.0, yellowLeftY = 0.0;
		double yellowRightX = 0.0, yellowRightY = 0.0;
		double blueLeftX = 0.0, blueLeftY = 0.0;
		double blueRightX = 0.0, blueRightY = 0.0;
		int countRed = 0;
		int countYellowLeft = 0;
		int countYellowRight = 0;
		int countBlueLeft = 0;
		int countBlueRight = 0;
		int minWidth = world.getPitchLeft();
		int maxWidth = minWidth + world.getWidth();
		int minHeight = world.getPitchTop();
		int maxHeight = minHeight + world.getHeight();
		int middleWidth = world.getSecondSectionBoundary();
		int firstWidth = world.getFirstSectionBoundary();
		int thirdWidth = world.getThirdSectionBoundary();

		int smallest = 1000000;
		int smallestX = 100;
		int smallestY = 100;
		int largest = 0;
		int largestX = 100;
		int largestY = 100;
		int top = 100000000;
		int bottom = -1;
		int right = -1;
		int left = 1000000000;
		for (int w = minWidth; w < firstWidth; w++) {
			for (int h = minHeight; h < maxHeight; h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();
				float[] hsvColor = Color.RGBtoHSB(red, green, blue, null);
				hsvColor[0] = hsvColor[0] * 255;
				hsvColor[1] = hsvColor[1] * 255;
				hsvColor[2] = hsvColor[2] * 255;
				if (red > ballMinRed && red < ballMaxRed && blue > ballMinBlue && blue < ballMaxBlue && green > ballMinGreen && green < ballMaxGreen
						&& hsvColor[0] > ballMinHue && hsvColor[0] < ballMaxHue && hsvColor[1] > ballMinSaturation && hsvColor[1] < ballMaxSaturation && hsvColor[2] > ballMinValue && hsvColor[2] < ballMaxValue){
					if (ballPixels){
						img.setRGB(w, h, 0x00ffff);	
					}
					redX += (double)w;
					redY += (double)h;
					countRed++;
				}

				// finding coordinates for robot plates
				if (red > plateMinRed && red < plateMaxRed && blue > plateMinBlue && blue < plateMaxBlue && green > plateMinGreen && green < plateMaxGreen
						&& hsvColor[0] > plateMinHue && hsvColor[0] < plateMaxHue && hsvColor[1] > plateMinSaturation && hsvColor[1] < plateMaxSaturation && hsvColor[2] > plateMinValue && hsvColor[2] < plateMaxValue){
					if (platePixels){
						img.setRGB(w,h,0xff00ff);
					}
//					if (world.getDirection()){
//						if (world.getColor()){
//							yellowLeftX += (double)w;
//							yellowLeftY += (double)h;
//							countYellowLeft++;		
//						} else {
//							blueLeftX += (double)w;
//							blueLeftY += (double)h;
//							countBlueLeft++;
//						}
//					} else {
//						if (world.getColor()){
//							blueLeftX += (double)w;
//							blueLeftY += (double)h;
//							countBlueLeft++;		
//						} else {
//							yellowLeftX += (double)w;
//							yellowLeftY += (double)h;
//							countYellowLeft++;
//						}
//					}
					if (h < top){
						top = h;
					}
					if (h > bottom){
						bottom = h;
					}
					if (w < left){
						left = w;
					}
					if (w > right){
						right = w;
					}
				}
			}
		}
		Point yellowLeft = new Point(0,0);
		Point blueLeft = new Point(0,0);
		if (world.getDirection() && world.getColor()){
			yellowLeft = new Point((left + right)/2,(top+bottom)/2);
		} else if (!world.getDirection() && !world.getColor()){
			yellowLeft = new Point((left + right)/2,(top+bottom)/2);			
		} else {
			blueLeft = new Point((left + right)/2,(top+bottom)/2);			
		}
		top = 10000;
		bottom = 0;
		left = 100000;
		right = 0;
		for (int w = firstWidth; w < middleWidth; w++) {
			for (int h = minHeight; h < maxHeight; h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();
				float[] hsvColor = Color.RGBtoHSB(red, green, blue, null);
				hsvColor[0] = hsvColor[0] * 255;
				hsvColor[1] = hsvColor[1] * 255;
				hsvColor[2] = hsvColor[2] * 255;
				if (red > ballMinRed && red < ballMaxRed && blue > ballMinBlue && blue < ballMaxBlue && green > ballMinGreen && green < ballMaxGreen
						&& hsvColor[0] > ballMinHue && hsvColor[0] < ballMaxHue && hsvColor[1] > ballMinSaturation && hsvColor[1] < ballMaxSaturation && hsvColor[2] > ballMinValue && hsvColor[2] < ballMaxValue){
					if (ballPixels){
						img.setRGB(w, h, 0x00ffff);	
					}
					redX += (double)w;
					redY += (double)h;
					countRed++;
				}

				// finding coordinates for robot plates
				if (red > plateMinRed && red < plateMaxRed && blue > plateMinBlue && blue < plateMaxBlue && green > plateMinGreen && green < plateMaxGreen
						&& hsvColor[0] > plateMinHue && hsvColor[0] < plateMaxHue && hsvColor[1] > plateMinSaturation && hsvColor[1] < plateMaxSaturation && hsvColor[2] > plateMinValue && hsvColor[2] < plateMaxValue){
					if (platePixels){
						img.setRGB(w,h,0xff00ff);
					}
//					if (world.getDirection()){
//						if (world.getColor()){
//							blueLeftX += (double)w;
//							blueLeftY += (double)h;
//							countBlueLeft++;		
//						} else {
//							yellowLeftX += (double)w;
//							yellowLeftY += (double)h;
//							countYellowLeft++;
//						}
//					} else {
//						if (world.getColor()){
//							yellowLeftX += (double)w;
//							yellowLeftY += (double)h;
//							countYellowLeft++;		
//						} else {
//							blueLeftX += (double)w;
//							blueLeftY += (double)h;
//							countBlueLeft++;
//						}
//					}
					if (h < top){
						top = h;
					}
					if (h > bottom){
						bottom = h;
					}
					if (w < left){
						left = w;
					}
					if (w > right){
						right = w;
					}
				}
			}
		}
		if (world.getDirection() && world.getColor()){
			blueLeft = new Point((left + right)/2,(top+bottom)/2);
		} else if (!world.getDirection() && !world.getColor()){
			blueLeft = new Point((left + right)/2,(top+bottom)/2);			
		} else {
			yellowLeft = new Point((left + right)/2,(top+bottom)/2);			
		}
		top = 10000;
		bottom = 0;
		left = 100000;
		right = 0;
		for (int w = middleWidth; w < thirdWidth; w++) {
			for (int h = minHeight; h < maxHeight; h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();
				float[] hsvColor = Color.RGBtoHSB(red, green, blue, null);
				hsvColor[0] = hsvColor[0] * 255;
				hsvColor[1] = hsvColor[1] * 255;
				hsvColor[2] = hsvColor[2] * 255;
				if (red > ballMinRed && red < ballMaxRed && blue > ballMinBlue && blue < ballMaxBlue && green > ballMinGreen && green < ballMaxGreen
						&& hsvColor[0] > ballMinHue && hsvColor[0] < ballMaxHue && hsvColor[1] > ballMinSaturation && hsvColor[1] < ballMaxSaturation && hsvColor[2] > ballMinValue && hsvColor[2] < ballMaxValue){
					if (ballPixels){
						img.setRGB(w, h, 0x00ffff);	
					}
					redX += (double)w;
					redY += (double)h;
					countRed++;
				}

				// finding coordinates for robot plates
				if (red > plateMinRed && red < plateMaxRed && blue > plateMinBlue && blue < plateMaxBlue && green > plateMinGreen && green < plateMaxGreen
						&& hsvColor[0] > plateMinHue && hsvColor[0] < plateMaxHue && hsvColor[1] > plateMinSaturation && hsvColor[1] < plateMaxSaturation && hsvColor[2] > plateMinValue && hsvColor[2] < plateMaxValue){
					if (platePixels){
						img.setRGB(w,h,0xff00ff);
					}
//					if (world.getDirection()){
//						if (world.getColor()){
//							yellowRightX += (double)w;
//							yellowRightY += (double)h;
//							countYellowRight++;		
//						} else {
//							blueRightX += (double)w;
//							blueRightY += (double)h;
//							countBlueRight++;
//						}
//					} else {
//						if (world.getColor()){
//							blueRightX += (double)w;
//							blueRightY += (double)h;
//							countBlueRight++;		
//						} else {
//							yellowRightX += (double)w;
//							yellowRightY += (double)h;
//							countYellowRight++;
//						}
//					}
					if (h < top){
						top = h;
					}
					if (h > bottom){
						bottom = h;
					}
					if (w < left){
						left = w;
					}
					if (w > right){
						right = w;
					}
				}
			}
		}
		Point yellowRight = new Point(0,0);
		Point blueRight = new Point(0,0);
		if (world.getDirection() && world.getColor()){
			yellowRight = new Point((left + right)/2,(top+bottom)/2);
		} else if (!world.getDirection() && !world.getColor()){
			yellowRight = new Point((left + right)/2,(top+bottom)/2);			
		} else {
			blueRight = new Point((left + right)/2,(top+bottom)/2);			
		}
		top = 10000;
		bottom = 0;
		left = 100000;
		right = 0;
		for (int w = thirdWidth; w < maxWidth; w++) {
			for (int h = minHeight; h < maxHeight; h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();
				float[] hsvColor = Color.RGBtoHSB(red, green, blue, null);
				hsvColor[0] = hsvColor[0] * 255;
				hsvColor[1] = hsvColor[1] * 255;
				hsvColor[2] = hsvColor[2] * 255;
				if (red > ballMinRed && red < ballMaxRed && blue > ballMinBlue && blue < ballMaxBlue && green > ballMinGreen && green < ballMaxGreen
						&& hsvColor[0] > ballMinHue && hsvColor[0] < ballMaxHue && hsvColor[1] > ballMinSaturation && hsvColor[1] < ballMaxSaturation && hsvColor[2] > ballMinValue && hsvColor[2] < ballMaxValue){
					if (ballPixels){
						img.setRGB(w, h, 0x00ffff);	
					}
					redX += (double)w;
					redY += (double)h;
					countRed++;
				}

				// finding coordinates for robot plates
				if (red > plateMinRed && red < plateMaxRed && blue > plateMinBlue && blue < plateMaxBlue && green > plateMinGreen && green < plateMaxGreen
						&& hsvColor[0] > plateMinHue && hsvColor[0] < plateMaxHue && hsvColor[1] > plateMinSaturation && hsvColor[1] < plateMaxSaturation && hsvColor[2] > plateMinValue && hsvColor[2] < plateMaxValue){
					if (platePixels){
						img.setRGB(w,h,0xff00ff);
					}
//					if (world.getDirection()){
//						if (world.getColor()){
//							blueRightX += (double)w;
//							blueRightY += (double)h;
//							countBlueRight++;		
//						} else {
//							yellowRightX += (double)w;
//							yellowRightY += (double)h;
//							countYellowRight++;
//						}
//					} else {
//						if (world.getColor()){
//							yellowRightX += (double)w;
//							yellowRightY += (double)h;
//							countYellowRight++;		
//						} else {
//							blueRightX += (double)w;
//							blueRightY += (double)h;
//							countBlueRight++;
//						}
//					}
					if (h < top){
						top = h;
					}
					if (h > bottom){
						bottom = h;
					}
					if (w < left){
						left = w;
					}
					if (w > right){
						right = w;
					}
				}
			}
		}
		if (world.getDirection() && world.getColor()){
			blueRight = new Point((left + right)/2,(top+bottom)/2);
		} else if (!world.getDirection() && !world.getColor()){
			blueRight = new Point((left + right)/2,(top+bottom)/2);			
		} else {
			yellowRight = new Point((left + right)/2,(top+bottom)/2);			
		}
		// find red dots and get the average to find out the location of the ball
		Point ball = new Point (redX/((double)countRed), redY/((double)countRed));
//		}
		world.setBallXY(ball);
//		Point yellowLeft = new Point(yellowLeftX/((double)countYellowLeft),yellowLeftY/((double)countYellowLeft));
		if ((Math.abs(yellowLeft.getX() - lastRobot[0].getX()) + Math.abs(yellowLeft.getY() - lastRobot[0].getY())) > 10){
			if (yellowLeft.getX() > lastRobot[0].getX()){
				yellowLeft = new Point(lastRobot[0].getX() + 3, yellowLeft.getY());
			} else {
				yellowLeft = new Point(lastRobot[0].getX() - 3, yellowLeft.getY());
			}
			if (yellowLeft.getY() > lastRobot[0].getY()){
				yellowLeft = new Point(yellowLeft.getX(), lastRobot[0].getY() + 3);
			} else {
				yellowLeft = new Point(yellowLeft.getX(), lastRobot[0].getY() - 3);
			}
			lastRobot[0] = yellowLeft;
			world.setYellowLeft(yellowLeft);
		} else {
			lastRobot[0] = yellowLeft;
			world.setYellowLeft(yellowLeft);
		}
//		world.setYellowLeft(yellowLeft);
//		Point yellowRight = new Point (yellowRightX/((double)countYellowRight),yellowRightY/((double)countYellowRight));
		if ((Math.abs(yellowRight.getX() - lastRobot[1].getX()) + Math.abs(yellowRight.getY() - lastRobot[1].getY())) > 10){
			if (yellowRight.getX() > lastRobot[1].getX()){
				yellowRight = new Point(lastRobot[1].getX() + 3, yellowRight.getY());
			} else {
				yellowRight = new Point(lastRobot[1].getX() - 3, yellowRight.getY());
			}
			if (yellowRight.getY() > lastRobot[1].getY()){
				yellowRight = new Point(yellowRight.getX(), lastRobot[1].getY() + 3);
			} else {
				yellowRight = new Point(yellowRight.getX(), lastRobot[1].getY() - 3);
			}
			lastRobot[1] = yellowRight;
			world.setYellowRight(yellowRight);
		} else {
			lastRobot[1] = yellowRight;
			world.setYellowRight(yellowRight);
		}
//		world.setYellowRight(yellowRight);
		
		
//		blueLeft = new Point(blueLeftX/((double)countBlueLeft),blueLeftY/((double)countBlueLeft));
		if ((Math.abs(blueLeft.getX() - lastRobot[2].getX()) + Math.abs(blueLeft.getY() - lastRobot[2].getY())) > 10){
			if (blueLeft.getX() > lastRobot[2].getX()){
				blueLeft = new Point(lastRobot[2].getX() + 3, blueLeft.getY());
			} else {
				blueLeft = new Point(lastRobot[2].getX() - 3, blueLeft.getY());
			}
			if (blueLeft.getY() > lastRobot[2].getY()){
				blueLeft = new Point(blueLeft.getX(), lastRobot[2].getY() + 3);
			} else {
				blueLeft = new Point(blueLeft.getX(), lastRobot[2].getY() - 3);
			}
			lastRobot[2] = blueLeft;
			world.setBlueLeft(blueLeft);
		} else {
			lastRobot[2] = blueLeft;
			world.setBlueLeft(blueLeft);
		}
//		world.setBlueLeft(blueLeft);
		
//		Point blueRight = new Point(blueRightX/((double)countBlueRight),blueRightY/((double)countBlueRight));
		if ((Math.abs(blueRight.getX() - lastRobot[3].getX()) + Math.abs(blueRight.getY() - lastRobot[3].getY())) > 10){
			if (blueRight.getX() > lastRobot[3].getX()){
				blueRight = new Point(lastRobot[3].getX() + 3, blueRight.getY());
			} else {
				blueRight = new Point(lastRobot[3].getX() - 3, blueRight.getY());
			}
			if (blueRight.getY() > lastRobot[3].getY()){
				blueRight = new Point(blueRight.getX(), lastRobot[3].getY() + 3);
			} else {
				blueRight = new Point(blueRight.getX(), lastRobot[3].getY() - 3);
			}
			lastRobot[3] = blueRight;
			world.setBlueRight(blueRight);
		} else {
			lastRobot[3] = blueRight;
			world.setBlueRight(blueRight);
		}

		
		
		// get the dot for the left yellow robot after we have the coordinates for the yellow/blue pixels

		Point yellowLeftDot = getDot(img, yellowLeft);
		if ((Math.abs(yellowLeftDot.getX() - lastDot[0].getX()) + Math.abs(yellowLeftDot.getY() - lastDot[0].getY())) > 10){
			if (yellowLeftDot.getX() > lastDot[0].getX()){
				yellowLeftDot = new Point(lastDot[0].getX() + 3, yellowLeftDot.getY());
			} else {
				yellowLeftDot = new Point(lastDot[0].getX() - 3, yellowLeftDot.getY());
			}
			if (yellowLeftDot.getY() > lastDot[0].getY()){
				yellowLeftDot = new Point(yellowLeftDot.getX(), lastDot[0].getY() + 3);
			} else {
				yellowLeftDot = new Point(yellowLeftDot.getX(), lastDot[0].getY() - 3);
			}
			lastDot[0] = yellowLeftDot;
			world.setVectorYellowLeft(yellowLeftDot);
		} else {
			lastDot[0] = yellowLeftDot;
			world.setVectorYellowLeft(yellowLeftDot);
		}
		
		// get the dot for the right yellow robot

		Point yellowRightDot = getDot(img,yellowRight);
		if ((Math.abs(yellowRightDot.getX() - lastDot[1].getX()) + Math.abs(yellowRightDot.getY() - lastDot[1].getY())) > 5){
			if (yellowRightDot.getX() > lastDot[1].getX()){
				yellowRightDot = new Point(lastDot[1].getX() + 3, yellowRightDot.getY());
			} else {
				yellowRightDot = new Point(lastDot[1].getX() - 3, yellowRightDot.getY());
			}
			if (yellowRightDot.getY() > lastDot[1].getY()){
				yellowRightDot = new Point(yellowRightDot.getX(), lastDot[1].getY() + 3);
			} else {
				yellowRightDot = new Point(yellowRightDot.getX(), lastDot[1].getY() - 3);
			}
//			yellowLeftDot = new Point((yellowRightDot.getX() + lastDot[1].getX())/2, (yellowRightDot.getY() + lastDot[1].getY())/2);
			lastDot[1] = yellowRightDot;
			world.setVectorYellowRight(yellowRightDot);
		} else {
			lastDot[1] = yellowRightDot;
			world.setVectorYellowRight(yellowRightDot);
		}

		// get the dot for the left blue robot

		Point blueLeftDot = getDot(img,blueLeft);
		if ((Math.abs(blueLeftDot.getX() - lastDot[2].getX()) + Math.abs(blueLeftDot.getY() - lastDot[2].getY())) > 5){
			if (blueLeftDot.getX() > lastDot[2].getX()){
				blueLeftDot = new Point(lastDot[2].getX() + 3, blueLeftDot.getY());
			} else {
				blueLeftDot = new Point(lastDot[2].getX() - 3, blueLeftDot.getY());
			}
			if (blueLeftDot.getY() > lastDot[2].getY()){
				blueLeftDot = new Point(blueLeftDot.getX(), lastDot[2].getY() + 3);
			} else {
				blueLeftDot = new Point(blueLeftDot.getX(), lastDot[2].getY() - 3);
			}
//			blueLeftDot = new Point((blueLeftDot.getX() + lastDot[2].getX())/2, (blueLeftDot.getY() + lastDot[2].getY())/2);
			lastDot[2] = blueLeftDot;
			world.setBlueLeft(blueLeftDot);
		} else {
			lastDot[2] = blueLeftDot;
			world.setVectorBlueLeft(blueLeftDot);
		}
		
		// get the dot for the right blue robot

		Point blueRightDot = getDot(img,blueRight);
		if ((Math.abs(blueRightDot.getX() - lastDot[3].getX()) + Math.abs(blueRightDot.getY() - lastDot[3].getY())) > 5){
			if (blueRightDot.getX() > lastDot[3].getX()){
				blueRightDot = new Point(lastDot[3].getX() + 3, blueRightDot.getY());
			} else {
				blueRightDot = new Point(lastDot[3].getX() - 3, blueRightDot.getY());
			}
			if (blueRightDot.getY() > lastDot[3].getY()){
				blueRightDot = new Point(blueRightDot.getX(), lastDot[3].getY() + 3);
			} else {
				blueRightDot = new Point(blueRightDot.getX(), lastDot[3].getY() - 3);
			}
//			blueRightDot = new Point((blueRightDot.getX() + lastDot[3].getX())/2, (blueRightDot.getY() + lastDot[3].getY())/2);
			lastDot[3] = blueRightDot;
			world.setVectorBlueRight(blueRightDot);
		} else {
			lastDot[3] = blueRightDot;
			world.setVectorBlueRight(blueRightDot);
		}
				
		// after setting coordinates, draw the elements on the image
		Image image = drawEverything(img, ball, yellowLeft, yellowRight, blueLeft, blueRight, yellowLeftDot, yellowRightDot, blueLeftDot, blueRightDot);
//		image = drawBoundaries((BufferedImage) image);		
		
		// set the final image
		world.setBallDirection();
		world.setRobots();
		world.setImage(image);
		return image;
	}
	
	// This method returns the location of the black dot, given the location of the
	// robot which the dot is on.
	
	private static Point getDot(BufferedImage img, Point robot){
		double dotX = 0;
		double dotY = 0;
		int count = 0;
		if (robot.getX() > 16 && robot.getY() > 16 && robot.getX() < 610 && robot.getY() < 450) {
			for (int w = (int) (robot.getX() - 15); w < robot.getX() + 15; w++){
				for (int h = (int) (robot.getY() - 15); h < robot.getY() +15; h++){
					Color c = new Color(img.getRGB(w, h), true);
					int blue = c.getBlue();
					int green = c.getGreen();
					int red = c.getRed();
					float[] hsvColor = Color.RGBtoHSB(red, green, blue, null);
					hsvColor[0] = 255 * hsvColor[0];
					hsvColor[1] = 255 * hsvColor[1];
					hsvColor[2] = 255 * hsvColor[2];
					if (red > dotsMinRed && red < dotsMaxRed && blue > dotsMinBlue && blue < dotsMaxBlue && green > dotsMinGreen && green < dotsMaxGreen
							&& hsvColor[0] > dotsMinHue && hsvColor[0] < dotsMaxHue && hsvColor[1] > dotsMinSaturation && hsvColor[1] < dotsMaxSaturation && hsvColor[2] > dotsMinValue && hsvColor[2] < dotsMaxValue){
						count++;
						dotX += w;
						dotY += h;
						if (dotsPixels){
							img.setRGB(w,h,0xffffff);
						}
					}
				}
			}
		}
		Point robotDot = new Point(50,50);
		if (dotX > 50 && dotY > 50){
			robotDot = new Point(dotX/count,dotY/count);
		}
		return robotDot;
	}
	
	// This method is used to test new vision methods. It is of no interest
	// to someone working on something other than vision.
	public static Image test(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight){
		for (int w = minWidth; w < maxWidth; w++){
			for (int h = minHeight; h < maxHeight; h++){
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();
				float[] hsbColor = Color.RGBtoHSB(red, green, blue, null);
				if (red < 40 ){//&& hsbColor[0] > 0.5 && hsbColor[1] > 0.25 && hsbColor[2] > 0.31){
					img.setRGB(w,h,0xffffff);
				} else {
					img.setRGB(w,h,0x000000);
				}
			}
		}
		return img;
	}
	
	// Draws the locations of objects on the image. Uses the locations found in the
	// trackWorld method.
	private static BufferedImage drawEverything(BufferedImage img, Point ball, Point yellowLeft, Point yellowRight, Point blueLeft, Point blueRight, Point yellowLeftDot, Point yellowRightDot, Point blueLeftDot, Point blueRightDot){
		if (ballVisible){
			img = drawCross(img, red, ball);	
		}
		if (yellowVisible){
			img = drawCross(img, yellow, yellowLeft);
			img = drawCross(img, yellow, yellowRight);			
		}
		if (blueVisible){
			img = drawCross(img, blue, blueLeft);
			img = drawCross(img, blue, blueRight);			
		}
		if (dotsVisible){
			img = drawCross(img, white, yellowLeftDot);
			img = drawCross(img, white, yellowRightDot);
			img = drawCross(img, white, blueLeftDot);
			img = drawCross(img, white, blueRightDot);
		}
		if (directionsVisible){
			img = drawVector(img, yellow, new Vector(yellowLeftDot,yellowLeft));
			img = drawVector(img, yellow, new Vector(yellowRightDot,yellowRight));
			img = drawVector(img, blue, new Vector(blueLeftDot,blueLeft));
			img = drawVector(img, blue, new Vector(blueRightDot,blueRight));
		}
		return img;
	}
	
	// Used to draw the direction in which each robot is facing onto the image.
	private static BufferedImage drawVector(BufferedImage img, Color color, Vector vector){
		double startX = vector.getOrigin().getX();
		double startY = vector.getOrigin().getY();
		double endX = vector.getDestination().getX();
		double endY = vector.getDestination().getY();
		double m = 1;
		if (Math.floor(endX) != Math.floor(startX)){
			if (Math.abs(startX - endX) > Math.abs(startY - endY)){
				m = (endY - startY)/(endX - startX);
				double c = startY - (m * startX);
				int y = 0;
				if (startX > endX){
					for (int x = (int) startX; x > endX - 40; x--){
						y = (int) ((m * x) + c);
						if (y > world.getPitchTop() && y < (world.getPitchTop() + world.getHeight())){
							img.setRGB(x, y, color.getRGB());
						}
					}
				} else {
					for (int x = (int) startX; x < endX + 40; x++){
						y = (int) ((m * x) + c);
						if (y > world.getPitchTop() && y < (world.getPitchTop() + world.getHeight())){
							img.setRGB(x, y, color.getRGB());
						}
					}
				}
				return img;
			} else {
				m = (endX - startX)/(endY - startY);
				double c = startX - (m * startY);
				int x = 0;
				if (startY > endY){
					for (int y = (int) startY; y > endY - 40; y--){
						x = (int) ((m * y) + c);
						if (x > world.getPitchLeft() && x < (world.getPitchLeft() + world.getWidth())){
							img.setRGB(x, y, color.getRGB());
						}
					}
				} else {
					for (int y = (int) startY; y < endY + 40; y++){
						x = (int) ((m * y) + c);
						if (x > world.getPitchLeft() && x < (world.getPitchLeft() + world.getWidth())){
							img.setRGB(x, y, color.getRGB());
						}
					}
				}
				return img;
			}
		} else {
			for (int y = world.getPitchTop(); y < (world.getPitchTop() + world.getHeight()); y++){
				img.setRGB((int) startX, y, color.getRGB());
			}
			return img;
		}
	}
	
	// Used by drawEverything to mark locations on the image.
	private static BufferedImage drawCross(BufferedImage img, Color color, Point centre){
		int x = (int) centre.getX();
		int y = (int) centre.getY();
		if (x > 50 && y > 50 && x < 590 && y < 430){
			for (int w1 = x - 20; w1 < x + 20; w1++){
				img.setRGB(w1, y, color.getRGB());
			}
			for (int h1 = y - 20; h1 < y + 20; h1++){
				((BufferedImage) img).setRGB(x, h1, color.getRGB());
			}			
		}
		return img;
	}

	// Locates the boundaries of the pitch, including the different sections,
	// and updates the world accordingly.
	public static void trackBoundaries(BufferedImage img){
		int height = img.getHeight();
		int width = img.getWidth();
		int leftEdge = 0;
		int topEdge = 0;
		int bottomEdge = height - 1;
		int rightEdge = width - 1;
		int temp = 0;
		// Detecting left edge.
		for (int w = 1; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, height / 2));
			if (c.getBlue() > 75){
				temp = 0;
				for (int h = (height / 2) - 50; h < (height / 2) + 50; h++){
					c = new Color(img.getRGB(w, h));
					temp += c.getBlue();
				}
				if (temp / 100 > 100){
					leftEdge = w;
					break;
				}
			}
		}

		// Detecting right edge.
		for (int w = width - 1; w > 0; w--){
			Color c = new Color(img.getRGB(w, height / 2));
			if (c.getBlue() > 75){
				temp = 0;
				for (int h = (height / 2) - 50; h < (height / 2) + 50; h++){
					c = new Color(img.getRGB(w, h));
					temp += c.getBlue();
				}
				if (temp / 100 > 100){
					rightEdge = w;
					break;
				}
			}
		}

		// Detecting top edge.
		for (int h = 1; h < height; h++){
			Color c = new Color(img.getRGB(width / 2, h));
			if (c.getBlue() > 75){
				temp = 0;
				for (int w = (width / 2) - 50; w < (width / 2) + 50; w++){
					c = new Color(img.getRGB(w, h));
					temp += c.getBlue();
				}
				if (temp / 100 > 100){
					topEdge = h;
					break;
				}
			}
		}

		// Detecting bottom edge.
		for (int h = height - 1; h > 0; h--){
			Color c = new Color(img.getRGB(width / 2, h));
			if (c.getBlue() > 75){
				temp = 0;
				for (int w = (width / 2) - 50; w < (width / 2) + 50; w++){
					c = new Color(img.getRGB(w, h));
					temp += c.getBlue();
				}
				if (temp / 100 > 100){
					bottomEdge = h;
					break;
				}
			}
		}
		world.setPitchLeft(leftEdge);
		world.setPitchTop(topEdge);
		world.setWidth(rightEdge - leftEdge);
		world.setHeight(bottomEdge - topEdge);
		
		int[] sections = new int[6];
		for (int w = leftEdge + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (topEdge + bottomEdge) / 2));
			if (c.getBlue() > 120){
				sections[0] = w;
				break;
			}
		}
		for (int w = sections[0] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (topEdge + bottomEdge) / 2));
			if (c.getBlue() < 120){
				sections[1] = w;
				break;
			}
		}

		for (int w = sections[1] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (topEdge + bottomEdge) / 2));
			if (c.getBlue() > 120){
				sections[2] = w;
				break;
			}
		}
		for (int w = sections[2] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (topEdge + bottomEdge) / 2));
			if (c.getBlue() < 120){
				sections[3] = w;
				break;
			}
		}

		for (int w = sections[3] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (topEdge + bottomEdge) / 2));
			if (c.getBlue() > 120){
				sections[4] = w;
				break;
			}
		}
		for (int w = sections[4] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (topEdge + bottomEdge) / 2));
			if (c.getBlue() < 120){
				sections[5] = w;
				break;
			}
		}
		int firstBoundary = (sections[0] + sections[1]) / 2;
		int secondBoundary = (sections[2] + sections[3]) / 2;
		int thirdBoundary = (sections[4] + sections[5]) / 2;
		world.setFirstSectionBoundary(firstBoundary);
		world.setSecondSectionBoundary(secondBoundary);
		world.setThirdSectionBoundary(thirdBoundary);
	}
	
	// Draws a green outline of the pitch on the given BufferedImage
	public static Image drawBoundaries(BufferedImage img){
		int left = world.getPitchLeft();
		int top = world.getPitchTop();
		int right = left + world.getWidth();
		int bottom = top + world.getHeight();
		int first = world.getFirstSectionBoundary();
		int second = world.getSecondSectionBoundary();
		int third = world.getThirdSectionBoundary();
		for (int w = left; w < right; w++){
			img.setRGB(w, top, green.getRGB());
			img.setRGB(w, bottom, green.getRGB());
		}
		for (int h = top; h < bottom; h++){
			img.setRGB(left, h, green.getRGB());
			img.setRGB(right, h, green.getRGB());
			img.setRGB(first, h, green.getRGB());
			img.setRGB(second, h, green.getRGB());
			img.setRGB(third, h, green.getRGB());
		}
		return img;
	}

	public void showBall(boolean b) {
		this.ballVisible = b;
	}

	public void showBallPixels(boolean b) {
		this.ballPixels = b;
	}
	
	public void setBallMinRed(int min){
		this.ballMinRed = min;
	}
	
	public void setBallMaxRed(int max){
		this.ballMaxRed = max;
	}
	
	public void setBallMinBlue(int min){
		this.ballMinBlue = min;
	}
	
	public void setBallMaxBlue(int max){
		this.ballMaxBlue = max;
	}
	
	public void setBallMinGreen(int min){
		this.ballMinGreen = min;
	}
	
	public void setBallMaxGreen(int max){
		this.ballMaxGreen = max;
	}
	
	public void setBallMinHue(int min){
		this.ballMinHue = min;
	}
	
	public void setBallMaxHue(int max){
		this.ballMaxHue = max;
	}
	
	public void setBallMinSaturation(int min){
		this.ballMinSaturation = min;
	}
	
	public void setBallMaxSaturation(int max){
		this.ballMaxSaturation = max;
	}
	
	public void setBallMinValue(int min){
		this.ballMinValue = min;
	}
	
	public void setBallMaxValue(int max){
		this.ballMaxValue = max;
	}
	
	public void showYellow(boolean b) {
		this.yellowVisible = b;
	}

	public void showYellowPixels(boolean b) {
		this.yellowPixels = b;
	}
	
	public void setYellowMinRed(int min){
		this.yellowMinRed = min;
	}
	
	public void setYellowMaxRed(int max){
		this.yellowMaxRed = max;
	}
	
	public void setYellowMinBlue(int min){
		this.yellowMinBlue = min;
	}
	
	public void setYellowMaxBlue(int max){
		this.yellowMaxBlue = max;
	}
	
	public void setYellowMinGreen(int min){
		this.yellowMinGreen = min;
	}
	
	public void setYellowMaxGreen(int max){
		this.yellowMaxGreen = max;
	}
	
	public void setYellowMinHue(int min){
		this.yellowMinHue = min;
	}
	
	public void setYellowMaxHue(int max){
		this.yellowMaxHue = max;
	}
	
	public void setYellowMinSaturation(int min){
		this.yellowMinSaturation = min;
	}
	
	public void setYellowMaxSaturation(int max){
		this.yellowMaxSaturation = max;
	}
	
	public void setYellowMinValue(int min){
		this.yellowMinValue = min;
	}
	
	public void setYellowMaxValue(int max){
		this.yellowMaxValue = max;
	}
	
	public void showBlue(boolean b) {
		this.blueVisible = b;
	}

	public void showBluePixels(boolean b) {
		this.bluePixels = b;
	}
	
	public void setBlueMinRed(int min){
		this.blueMinRed = min;
	}
	
	public void setBlueMaxRed(int max){
		this.blueMaxRed = max;
	}
	
	public void setBlueMinBlue(int min){
		this.blueMinBlue = min;
	}
	
	public void setBlueMaxBlue(int max){
		this.blueMaxBlue = max;
	}
	
	public void setBlueMinGreen(int min){
		this.blueMinGreen = min;
	}
	
	public void setBlueMaxGreen(int max){
		this.blueMaxGreen = max;
	}
	
	public void setBlueMinHue(int min){
		this.blueMinHue = min;
	}
	
	public void setBlueMaxHue(int max){
		this.blueMaxHue = max;
	}
	
	public void setBlueMinSaturation(int min){
		this.blueMinSaturation = min;
	}
	
	public void setBlueMaxSaturation(int max){
		this.blueMaxSaturation = max;
	}
	
	public void setBlueMinValue(int min){
		this.blueMinValue = min;
	}
	
	public void setBlueMaxValue(int max){
		this.blueMaxValue = max;
	}
	
	public void showDots(boolean b) {
		this.dotsVisible = b;
	}

	public void showDotsPixels(boolean b) {
		this.dotsPixels = b;
	}
	
	public void setDotsMinRed(int min){
		this.dotsMinRed = min;
	}
	
	public void setDotsMaxRed(int max){
		this.dotsMaxRed = max;
	}
	
	public void setDotsMinBlue(int min){
		this.dotsMinBlue = min;
	}
	
	public void setDotsMaxBlue(int max){
		this.dotsMaxBlue = max;
	}
	
	public void setDotsMinGreen(int min){
		this.dotsMinGreen = min;
	}
	
	public void setDotsMaxGreen(int max){
		this.dotsMaxGreen = max;
	}
	
	public void setDotsMinHue(int min){
		this.dotsMinHue = min;
	}
	
	public void setDotsMaxHue(int max){
		this.dotsMaxHue = max;
	}
	
	public void setDotsMinSaturation(int min){
		this.dotsMinSaturation = min;
	}
	
	public void setDotsMaxSaturation(int max){
		this.dotsMaxSaturation = max;
	}
	
	public void setDotsMinValue(int min){
		this.dotsMinValue = min;
	}
	
	public void setDotsMaxValue(int max){
		this.dotsMaxValue = max;
	}
	
	public void showPlate(boolean b) {
		this.plateVisible = b;
	}

	public void showPlatePixels(boolean b) {
		this.platePixels = b;
	}
	
	public void setPlateMinRed(int min){
		this.plateMinRed = min;
	}
	
	public void setPlateMaxRed(int max){
		this.plateMaxRed = max;
	}
	
	public void setPlateMinBlue(int min){
		this.plateMinBlue = min;
	}
	
	public void setPlateMaxBlue(int max){
		this.plateMaxBlue = max;
	}
	
	public void setPlateMinGreen(int min){
		this.plateMinGreen = min;
	}
	
	public void setPlateMaxGreen(int max){
		this.plateMaxGreen = max;
	}
	
	public void setPlateMinHue(int min){
		this.plateMinHue = min;
	}
	
	public void setPlateMaxHue(int max){
		this.plateMaxHue = max;
	}
	
	public void setPlateMinSaturation(int min){
		this.plateMinSaturation = min;
	}
	
	public void setPlateMaxSaturation(int max){
		this.plateMaxSaturation = max;
	}
	
	public void setPlateMinValue(int min){
		this.plateMinValue = min;
	}
	
	public void setPlateMaxValue(int max){
		this.plateMaxValue = max;
	}
	
	public void showDirections(boolean b){
		this.directionsVisible = b;
	}
}