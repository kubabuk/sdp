package vision;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class ImageProcessor {
	
	static Color green = new Color(0,255,0);
	static Color red = new Color(255,0,0);
	static Color yellow = new Color(255,255,0);
	static Color blue = new Color(0,0,255);
/*
     Currently this method finds the ball, and changes it to appear as black
    on the gui. It also calculates the center of the ball with pixel values.
    Needs to be refactored into two separate methods, ideally taking into 
    account computational cost and splitting the method at latest point possible.
    Also needs to take into consideration grid coordinate system, which is still
    to be implemented. -- Kuba  
*/
	public Image trackBall(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight) {

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
	public int[] getBallLocation(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight) {
		
		double redX = 0.0;
		double redY = 0.0;
		int count = 0;

		for (int w = minWidth; w < maxWidth; w++) {
			for (int h = minHeight; h < maxHeight; h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (red > (blue + 120) && red > (green + 120)){
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
	public Image drawBall(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight){
		int[] coords = getBallLocation(img, minWidth, maxWidth, minHeight, maxHeight);
		if (coords[0] > 50 && coords[1] > 50){
			for (int w = coords[0] - 20; w < coords[0] + 20; w++){
				img.setRGB(w, coords[1], red.getRGB());
			}
			for (int h = coords[1] - 20; h < coords[1] + 20; h++){
				img.setRGB(coords[0], h, red.getRGB());
			}			
		}
		return img;
	}
	
	// Locates yellow pixels in the image.
	public Image trackYellowRobot(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight) {

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
	
	// Returns the locations of the yellow robots, leftmost robot first.
	// Returns an array: [leftx, lefty, rightx, righty]
	public int[] getYellowLocations(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight){
		double yellowX = 0.0;
		double yellowY = 0.0;
		int count = 0;
		int[] coords = {1,1,1,1};

		for (int w = minWidth; w < maxWidth; w++) {
			for (int h = minHeight; h < maxHeight; h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (red > (blue + 55) && green > (blue + 55)){
					yellowX += (double)w;
					yellowY += (double)h;
					count++;
					if (count > 5){
						if (coords[0] == 1){
							coords[0] = w;
							coords[1] = h;							
						} else {
							if (w - coords[0] > 100){
								coords[2] = w;
								coords[3] = h;
							}
						}
					}
				}
			}
		}
		return coords;
	}
	
	// Draws crosses on top of the yellow robots.
	public Image drawYellowRobots(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight){
		int[] coords = getYellowLocations(img, minWidth, maxWidth, minHeight, maxHeight);
		if (coords[0] > 50 && coords[1] > 50 && coords[2] > 50 && coords[3] > 50){
			for (int w = coords[0] - 20; w < coords[0] + 20; w++){
				img.setRGB(w, coords[1], yellow.getRGB());
			}
			for (int h = coords[1] - 20; h < coords[1] + 20; h++){
				img.setRGB(coords[0], h, yellow.getRGB());
			}
			for (int w = coords[2] - 20; w < coords[2] + 20; w++){
				img.setRGB(w, coords[3], yellow.getRGB());
			}
			for (int h = coords[3] - 20; h < coords[3] + 20; h++){
				img.setRGB(coords[2], h, yellow.getRGB());
			}
		}
		return img;
	}
	
	// Tracks area of blue in the image.
	public Image trackBlueRobot(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight) {

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
	
	// Returns the locations of the blue robots, leftmost robot first.
	// Returns an array: [leftx, lefty, rightx, righty]
	public int[] getBlueLocations(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight){
		double blueX = 0.0;
		double blueY = 0.0;
		int[] coords = {1,1,1,1};

		for (int w = minWidth; w < maxWidth; w++) {
			for (int h = minHeight; h < maxHeight; h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (blue > (green) && blue > (red+30) && green > (red + 25)){
					blueX += (double)w;
					blueY += (double)h;
					if (coords[0] == 1){
							coords[0] = w;
							coords[1] = h;							
					} else {
							if (w - coords[0] > 100){
								coords[2] = w;
								coords[3] = h;
							}
					}
				}
			}
		}
		return coords;
	}
	
	// Draws crosses on top of the blue robots.
	public Image drawBlueRobots(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight){
		int[] coords = getBlueLocations(img, minWidth, maxWidth, minHeight, maxHeight);
		if (coords[0] > 50 && coords[1] > 50 && coords[2] > 50 && coords[3] > 50){
			for (int w = coords[0] - 20; w < coords[0] + 20; w++){
				img.setRGB(w, coords[1], blue.getRGB());
			}
			for (int h = coords[1] - 20; h < coords[1] + 20; h++){
				img.setRGB(coords[0], h, blue.getRGB());
			}
			for (int w = coords[2] - 20; w < coords[2] + 20; w++){
				img.setRGB(w, coords[3], blue.getRGB());
			}
			for (int h = coords[3] - 20; h < coords[3] + 20; h++){
				img.setRGB(coords[2], h, blue.getRGB());
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
	
	public void pitchRatio(BufferedImage img){
		int[] boundaries = getBoundaries(img);
		System.out.println("Width: " + (boundaries[2] - boundaries[0]));
		System.out.println("Height: " + (boundaries[3] - boundaries[1]));
	}
}
