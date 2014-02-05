package vision;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

<<<<<<< HEAD
public class ImageProcessor {
=======

public class ImageProcessor1 {
	
	static Color green = new Color(0,255,0);
	static Color red = new Color(255,0,0);
	static Color yellow = new Color(255,255,0);
	static Color blue = new Color(0,0,255);
>>>>>>> 4c84a8810acbfdd71e525d9ecbb615aa1f4e40e7
/*
     Currently this method finds the ball, and changes it to appear as black
    on the gui. It also calculates the center of the ball with pixel values.
    Needs to be refactored into two separate methods, ideally taking into 
    account computational cost and splitting the method at latest point possible.
    Also needs to take into consideration grid coordinate system, which is still
    to be implemented. -- Kuba  
<<<<<<< HEAD
*/
	// Copy this into whatever creates an imageProcesser object.
	double pavgRX = 0;
	double pavgRY = 0;
	double speedX = 0;
	double speedY = 0;
	
	public Image trackBall(BufferedImage img) {
		
		double redX = 0.0;
		double redY = 0.0;
		double avgRX = -1.0;
		double avgRY = -1.0;
		int count = 0;
			
=======
    
    
    more compact version - looping through the pixels less times -- Patricia
    note that instead of several methods there is just 1 method
    trackWorld that tracks all the objects
*/
	public World trackWorld(BufferedImage img, int minWidth, int maxWidth, int minHeight, int maxHeight) {

		double redX = 0.0, redY = 0.0;
		double yellowX = 0.0, yellowY = 0.0;
		double blueX = 0.0, blueY = 0.0;
		int countRed = 0;
		int[] coords_yellow = {1,1,1,1};
		int[] coords_blue = {1,1,1,1};

		World newWorld = new World(img);
		
>>>>>>> 4c84a8810acbfdd71e525d9ecbb615aa1f4e40e7
		for (int w = 0; w < img.getWidth(); w++) {
			for (int h = 0; h < img.getHeight(); h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();

				if (red > (blue + 130) && red > (green + 130)){
<<<<<<< HEAD
					img.setRGB(w, h, 0);
					redX += (double)w;
					redY += (double)h;
					count++;
				}
			}
		}
		if (count > 0) {
			avgRX = redX/((double)count);
			avgRY = redY/((double)count);
			//System.out.println("" + avgRX + "," + avgRY);
		}
		
			speedX = (pavgRX - avgRX)*20;
			speedY = (pavgRY - avgRY)*20;
			pavgRX = avgRX;
			pavgRY = avgRY;
			
			System.out.println(speedX + " pixels per second X," + speedY + " pixels per second Y");
		
		return img;

	}	
=======
//					img.setRGB(w, h, 0);
					redX += (double)w;
					redY += (double)h;
					countRed++;
				}
				
				// finding coordinates for YELLOW robots [ 0=leftx 1=lefty 2=rightx 3=righty ]
				if (red > (blue + 55) && green > (blue + 55))
				{
					yellowX += (double)w;
					yellowY += (double)h;
					if (coords_yellow[0] == 1)
					{
						coords_yellow[0] = w;
						coords_yellow[1] = h;							
					} 
					else if (w - coords_yellow[0] > 100)
					{
							coords_yellow[2] = w;
							coords_yellow[3] = h;
					}
				}
				
				// finding coordinates for BLUE robots [ 0=leftx 1=lefty 2=rightx 3=righty ]
				if (blue > (green) && blue > (red+30) && green > (red + 25))
				{
					blueX += (double)w;
					blueY += (double)h;
					if (coords_blue[0] == 1)
					{
							coords_blue[0] = w;
							coords_blue[1] = h;							
					}
					else if (w - coords_blue[0] > 100)
					{
							coords_blue[2] = w;
							coords_blue[3] = h;
					}
				}
			}
		}
		// find red dots and get the average to find out the location of the ball
		if (countRed > 0) 
			newWorld.setBallXY(redX/((double)countRed), redY/((double)countRed));
		
		newWorld.setYellowXY_left(coords_yellow[0],coords_yellow[1]);
		newWorld.setYellowXY_right(coords_yellow[2],coords_yellow[3]);
		
		newWorld.setBlueXY_left(coords_yellow[0],coords_yellow[1]);
		newWorld.setBlueXY_right(coords_yellow[2],coords_yellow[3]);
		
		// after seeting coordinates, draw the elements on the image
		
		// draw the ball
		int x=(int) newWorld.getBallX();
		int y=(int) newWorld.getBallY();
		
		if (x > 50 && y > 50){
			for (int w1 = x - 20; w1 < x + 20; w1++){
				img.setRGB(w1, y, red.getRGB());
			}
			for (int h1 = y - 20; h1 < y + 20; h1++){
				img.setRGB(x, h1, red.getRGB());
			}			
		}
		
		
		//draw crosses on top of the yellow robots
		if (coords_yellow[0] > 50 && coords_yellow[1] > 50 && coords_yellow[2] > 50 && coords_yellow[3] > 50){
			for (int w2 = coords_yellow[0] - 20; w2 < coords_yellow[0] + 20; w2++){
				img.setRGB(w2, coords_yellow[1], yellow.getRGB());
			}
			for (int h2 = coords_yellow[1] - 20; h2 < coords_yellow[1] + 20; h2++){
				img.setRGB(coords_yellow[0], h2, yellow.getRGB());
			}
			for (int w2 = coords_yellow[2] - 20; w2 < coords_yellow[2] + 20; w2++){
				img.setRGB(w2, coords_yellow[3], yellow.getRGB());
			}
			for (int h2 = coords_yellow[3] - 20; h2 < coords_yellow[3] + 20; h2++){
				img.setRGB(coords_yellow[2], h2, yellow.getRGB());
			}
		}

		// set the final image
		newWorld.setImage(img);
		return newWorld;
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
>>>>>>> 4c84a8810acbfdd71e525d9ecbb615aa1f4e40e7
}
