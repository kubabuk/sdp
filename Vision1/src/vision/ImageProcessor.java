package vision;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class ImageProcessor {
	
	static Color green = new Color(0,255,0);
	static Color red = new Color(255,0,0);
/*
     Currently this method finds the ball, and changes it to appear as black
    on the gui. It also calculates the center of the ball with pixel values.
    Needs to be refactored into two separate methods, ideally taking into 
    account computational cost and splitting the method at latest point possible.
    Also needs to take into consideration grid coordinate system, which is still
    to be implemented. -- Kuba  
*/
	public Image trackBall(BufferedImage img) {

		double redX = 0.0;
		double redY = 0.0;
		int count = 0;

		for (int w = 0; w < img.getWidth(); w++) {
			for (int h = 0; h < img.getHeight(); h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (red > (blue + 130) && red > (green + 130)){
					img.setRGB(w, h, 0);
					redX += (double)w;
					redY += (double)h;
					count++;
				}
			}
		}
		if (count > 0) 
			System.out.println("" + redX/((double)count) + "," + redY/((double)count));
		return img;
	}

	// Returns the x and y coordinates of the ball.
	public int[] getBallLocation(BufferedImage img) {
		
		double redX = 0.0;
		double redY = 0.0;
		int count = 0;

		for (int w = 0; w < img.getWidth(); w++) {
			for (int h = 0; h < img.getHeight(); h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (red > (blue + 130) && red > (green + 130)){
					redX += (double)w;
					redY += (double)h;
					count++;
				}
			}
		}
		int x = 0;
		int y = 0;
		if (count > 0){
			x = (int) (redX/((double)count));
			y = (int) (redY/((double)count));
		}
		int[] coords = {x,y};
		return coords;
	}	

	// Draws the location of the ball on the image
	public Image drawBall(BufferedImage img){
		int[] coords = getBallLocation(img);
		for (int w = 0; w < img.getWidth(); w++){
			img.setRGB(w, coords[1], red.getRGB());
		}
		for (int h = 0; h < img.getHeight(); h++){
			img.setRGB(coords[0], h, red.getRGB());
		}
		return img;
	}
	
	public Image trackYelowRobot(BufferedImage img) {

		double yellowX = 0.0;
		double yellowY = 0.0;
		int count = 0;

		for (int w = 0; w < img.getWidth(); w++) {
			for (int h = 0; h < img.getHeight(); h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (red > (blue+55) && green > (blue+55)){
					img.setRGB(w, h, 0);
					yellowX += (double)w;
					yellowY += (double)h;
					count++;
				}
			}
		}
		if (count > 0) 
			System.out.println("" + yellowX/((double)count) + "," + yellowY/((double)count));
		return img;
	}
	
	public Image trackBlueRobot(BufferedImage img) {

		double blueX = 0.0;
		double blueY = 0.0;
		int count = 0;

		for (int w = 0; w < img.getWidth(); w++) {
			for (int h = 0; h < img.getHeight(); h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (blue > (green) && blue > (red+30) && green > (red + 25)){
					img.setRGB(w, h, 0);
					blueX += (double)w;
					blueY += (double)h;
					count++;
				}
			}
		}
		if (count > 0) 
			System.out.println("" + blueX/((double)count) + "," + blueY/((double)count));
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
		
		// Detecting left edge.
		for (int w = 1; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, height / 2));
			if (c.getBlue() > 75){
				leftEdge = w;
				break;
			}
		}
		
		// Detecting right edge.
		for (int w = width - 1; w > 0; w--){
			Color c = new Color(img.getRGB(w, height / 2));
			if (c.getBlue() > 75){
				rightEdge = w;
				break;
			}
		}
		
		// Detecting top edge.
		for (int h = 1; h < height; h++){
			Color c = new Color(img.getRGB(width / 2, h));
			if (c.getBlue() > 75){
				topEdge = h;
				break;
			}
		}
		
		// Detecting bottom edge.
		for (int h = height - 1; h > 0; h--){
			Color c = new Color(img.getRGB(width / 2, h));
			if (c.getBlue() > 75){
				bottomEdge = h;
				break;
			}
		}
		
		output[0] = leftEdge;
		output[1] = topEdge;
		output[2] = rightEdge;
		output[3] = bottomEdge;
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
		int right = boundaries[2];
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
