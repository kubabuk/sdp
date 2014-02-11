package vision;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import world.World;
import geometry.Point;
import geometry.Vector;

public class ImageProcessor1 {

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
	
	private static World world;
/*
     Currently this method finds the ball, and changes it to appear as black
    on the gui. It also calculates the center of the ball with pixel values.
    Needs to be refactored into two separate methods, ideally taking into 
    account computational cost and splitting the method at latest point possible.
    Also needs to take into consideration grid coordinate system, which is still
    to be implemented. -- Kuba  
    
    
    more compact version - looping through the pixels less times -- Patricia
    note that instead of several methods there is just 1 method
    trackWorld that tracks all the objects
*/
	//public static World newWorld;
	public ImageProcessor1(World newWorld){
		this.world = newWorld;
	}
	
	public static Image trackWorld(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight) {

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
		int middleWidth = maxWidth - ((maxWidth - minWidth)/2);

		for (int w = minWidth; w < middleWidth; w++) {
			for (int h = minHeight; h < maxHeight; h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (red > (blue + 90) && red > (green + 90)){
//					img.setRGB(w, h, 0);
					redX += (double)w;
					redY += (double)h;
					countRed++;
				}

				// finding coordinates for YELLOW robots [ 0=leftx 1=lefty 2=rightx 3=righty ]
				if (red > (blue + 30) && green > (blue + 30))
				{
//					img.setRGB(w,h,0);
					yellowLeftX += (double)w;
					yellowLeftY += (double)h;
					countYellowLeft++;
				}

				// finding coordinates for BLUE robots [ 0=leftx 1=lefty 2=rightx 3=righty ]
				if (blue > (green) && blue > (red+40) && green > (red + 40))
				{
//					img.setRGB(w,h,0);
					blueLeftX += (double)w;
					blueLeftY += (double)h;
					countBlueLeft++;
				}
			}
		}
		for (int w = middleWidth; w < maxWidth; w++) {
			for (int h = minHeight; h < maxHeight; h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (red > (blue + 90) && red > (green + 90)){
//					img.setRGB(w, h, 0);
					redX += (double)w;
					redY += (double)h;
					countRed++;
				}

				// finding coordinates for YELLOW robots [ 0=leftx 1=lefty 2=rightx 3=righty ]
				if (red > (blue + 40) && green > (blue + 40))
				{
//					img.setRGB(w, h, 0);
					yellowRightX += (double)w;
					yellowRightY += (double)h;
					countYellowRight++;
				}

				// finding coordinates for BLUE robots [ 0=leftx 1=lefty 2=rightx 3=righty ]
				if (blue > (green) && blue > (red+40) && green > (red + 40))
				{
//					img.setRGB(w,h,0);
					blueRightX += (double)w;
					blueRightY += (double)h;
					countBlueRight++;
				}
			}
		}
		
		// find red dots and get the average to find out the location of the ball
		Point ball = new Point (redX/((double)countRed), redY/((double)countRed));
		world.setBallXY(ball);
		Point yellowLeft = new Point(yellowLeftX/((double)countYellowLeft),yellowLeftY/((double)countYellowLeft));
		world.setYellowLeft(yellowLeft);
		Point yellowRight = new Point (yellowRightX/((double)countYellowRight),yellowRightY/((double)countYellowRight));
		world.setYellowRight(yellowRight);
		Point blueLeft = new Point(blueLeftX/((double)countBlueLeft),blueLeftY/((double)countBlueLeft));
		world.setBlueLeft(blueLeft);
		Point blueRight = new Point(blueRightX/((double)countBlueRight),blueRightY/((double)countBlueRight));
		world.setBlueRight(blueRight);

		
		
		// get the dot for the left yellow robot after we have the coordinates for the yellow/blue pixels

		Point yellowLeftDot = getDot(img, yellowLeft);
		world.setVectorYellowLeft(yellowLeftDot);

		// get the dot for the right yellow robot

		Point yellowRightDot = getDot(img,yellowRight);
		world.setVectorYellowRight(yellowRightDot);

		// get the dot for the left blue robot

		Point blueLeftDot = getDot(img,blueLeft);
		world.setVectorBlueLeft(blueLeftDot);
		
		// get the dot for the right blue robot

		Point blueRightDot = getDot(img,blueRight);
		world.setVectorBlueRight(blueRightDot);
				
		// after setting coordinates, draw the elements on the image
		Image image = drawEverything(img, ball, yellowLeft, yellowRight, blueLeft, blueRight, yellowLeftDot, yellowRightDot, blueLeftDot, blueRightDot);
		
		// calculate the speed of the ball on axis
//		speedX = (pavgRX - redX/((double)countRed))*20;
//		speedY = (pavgRY - redY/((double)countRed))*20;
//		pavgRX = redX/((double)countRed);
//		pavgRY = redY/((double)countRed);
		
		// set the final image
		world.setBallDirection();
		world.setImage(image);
		return image;
	}
	
	private static Point getDot(BufferedImage img, Point robot){
		double totalBrightness = 0;
		int windowSize = world.getWidth() / 100;
		int xPosition = (int) (robot.getX() - (2.5*windowSize));
		int yPosition = (int) (robot.getY() - (2.5*windowSize));
		int bestX = xPosition;
		int bestY = yPosition;
		double bestBrightness = 1000000000;
		for (int widthCount = 1; widthCount <= 5; widthCount++){
			for (int heightCount = 1; heightCount <= 5; heightCount++){
				for(int w = xPosition; w < xPosition + windowSize; w++){
					for(int h = yPosition; h < yPosition + windowSize; h++){
						Color c = new Color(img.getRGB(w, h), true);
						int blue = c.getBlue();
						int green = c.getGreen();
						int red = c.getRed();
						float[] hsbColor = Color.RGBtoHSB(red, green, blue, null);
						totalBrightness += hsbColor[2];
//						if(blue<60 && green<60 && red<60)
//						if (hsbColor[2] < 0.35){
//							img.setRGB(w,h,255);
//							count++;
//						}
					}
				}
				if (totalBrightness < bestBrightness){
					bestX = xPosition;
					bestY = yPosition;
					bestBrightness = (int) totalBrightness;
				}
				totalBrightness = 0;
				yPosition += windowSize;
			}
			xPosition += windowSize;
			yPosition = (int) (robot.getY() - (2.5*windowSize));
		}
		
		Point robotDot = new Point(bestX + (windowSize/2), bestY + (windowSize/2));
		return robotDot;
	}
	
	private static BufferedImage drawEverything(BufferedImage img, Point ball, Point yellowLeft, Point yellowRight, Point blueLeft, Point blueRight, Point yellowLeftDot, Point yellowRightDot, Point blueLeftDot, Point blueRightDot){
		img = drawCross(img, red, ball);
		img = drawCross(img, yellow, yellowLeft);
//		img = drawCross(img, yellow, yellowRight);
//		img = drawCross(img, blue, blueLeft);
//		img = drawCross(img, blue, blueRight);
		img = drawCross(img, white, yellowLeftDot);
//		img = drawCross(img, white, yellowRightDot);
//		img = drawCross(img, white, blueLeftDot);
//		img = drawCross(img, white, blueRightDot);
//		img = drawVector(img, yellow, world.getyLeft());
		return img;
	}
	
	private static BufferedImage drawVector(BufferedImage img, Color color, Vector vector){
		int startX = (int) vector.getOrigin().getX();
		int startY = (int) vector.getOrigin().getY();
		int endX = (int) vector.getDestination().getX();
		int endY = (int) vector.getDestination().getY();
		double m = 1;
		if (endX != startX){
			m = (endY - startY)/(endX - startX);	
		}
		double c = startY - (m * startX);
		int y = 0;
		for (int x = world.getPitchLeft(); x < world.getWidth() + world.getPitchLeft(); x++){
			y = (int) ((m * x) + c);
			if (y > world.getPitchTop() && y < (world.getPitchTop() + world.getHeight())){
				img.setRGB(x, y, color.getRGB());
			}
		}
		return img;
	}
	
	private static BufferedImage drawCross(BufferedImage img, Color color, Point centre){
		int x = (int) centre.getX();
		int y = (int) centre.getY();
		if (x > 50 && y > 50){
			for (int w1 = x - 20; w1 < x + 20; w1++){
				img.setRGB(w1, y, color.getRGB());
			}
			for (int h1 = y - 20; h1 < y + 20; h1++){
				((BufferedImage) img).setRGB(x, h1, color.getRGB());
			}			
		}
		return img;
	}


	//Returns an array of integers:
	// output[0] - y value of left boundary
	// output[1] - x value of top boundary
	// output[2] - y value of right boundary
	// output[3] - x value of bottom boundary
	public int[] getBoundaries(BufferedImage img) {
		int[] output = {10,10,50,50};
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

		output[0] = leftEdge;
		output[1] = topEdge;
		output[2] = rightEdge;
		output[3] = bottomEdge;
		world.setPitchLeft(output[0]);
		world.setPitchTop(output[1]);
		world.setWidth(output[2] - output[0]);
		world.setHeight(output[3] - output[1]);
		return output;
	}

	// Draws a green outline of the pitch on the given BufferedImage
	public Image drawBoundaries(BufferedImage img){
		int[] boundaries = getBoundaries(img);
		int[] sections = getPitchSections(img);
		int firstSection = sections[0];
		int secondSection = sections[1];
		int thirdSection = sections[2];
		int fourthSection = sections[3];
		int fifthSection = sections[4];
		int sixthSection = sections[5];
		int left = boundaries[0];
		int top = boundaries[1];
		int right = boundaries[2];
		int bottom = boundaries[3];
		for (int w = left; w < right; w++){
			img.setRGB(w, top, green.getRGB());
			img.setRGB(w, bottom, green.getRGB());
		}
		for (int h = top; h < bottom; h++){
			img.setRGB(left, h, green.getRGB());
			img.setRGB(right, h, green.getRGB());
			img.setRGB(firstSection, h, green.getRGB());
			img.setRGB(secondSection, h, green.getRGB());
			img.setRGB(thirdSection, h, green.getRGB());
			img.setRGB(fourthSection, h, green.getRGB());
			img.setRGB(fifthSection, h, green.getRGB());
			img.setRGB(sixthSection, h, green.getRGB());
		}
		return img;
	}

	public int[] getPitchSections(BufferedImage img){
		int[] sections = {10,10,10,10,10,10};
		int[] boundaries = getBoundaries(img);
		int left = boundaries[0];
		int top = boundaries[1];
		int bottom = boundaries[3];

		for (int w = left + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (top + bottom) / 2));
			if (c.getBlue() > 100){
				sections[0] = w;
				break;
			}
		}
		for (int w = sections[0] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (top + bottom) / 2));
			if (c.getBlue() < 100){
				sections[1] = w;
				break;
			}
		}

		for (int w = sections[1] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (top + bottom) / 2));
			if (c.getBlue() > 100){
				sections[2] = w;
				break;
			}
		}
		for (int w = sections[2] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (top + bottom) / 2));
			if (c.getBlue() < 100){
				sections[3] = w;
				break;
			}
		}

		for (int w = sections[3] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (top + bottom) / 2));
			if (c.getBlue() > 100){
				sections[4] = w;
				break;
			}
		}
		for (int w = sections[4] + 50; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, (top + bottom) / 2));
			if (c.getBlue() < 100){
				sections[5] = w;
				break;
			}
		}
		return sections;
	}
}