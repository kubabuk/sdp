package vision;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageProcessor {
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

				if (red > (blue + 70) && red > (green + 70)){
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
	
	public int[] getBoundaries(BufferedImage img) {
		int[] output = {0,0,0,0};
		int height = img.getHeight();
		int width = img.getWidth();
		int leftEdge = 0;
		int topEdge = 0;
		int bottomEdge = height;
		int rightEdge = width;
		
		// Detecting left edge.
		for (int w = 0; w < img.getWidth(); w++){
			Color c = new Color(img.getRGB(w, height / 2));
			if (c.getBlue() > 75){
				leftEdge = w;
				break;
			}
		}
		System.out.println("Left edge at y = " + leftEdge);
		
		// Detecting right edge.
		for (int w = width - 1; w > 0; w--){
			Color c = new Color(img.getRGB(w, height / 2));
			if (c.getBlue() > 75){
				rightEdge = w;
				break;
			}
		}
		System.out.println("Right edge at y = " + rightEdge);
		
		// Detecting top edge.
		for (int h = 0; h < height; h++){
			Color c = new Color(img.getRGB(width / 2, h));
			if (c.getBlue() > 75){
				topEdge = h;
				break;
			}
		}
		System.out.println("Top edge at y = " + topEdge);
		
		// Detecting bottom edge.
		for (int h = height - 1; h > 0; h--){
			Color c = new Color(img.getRGB(width / 2, h));
			if (c.getBlue() > 75){
				bottomEdge = h;
				break;
			}
		}
		System.out.println("Bottom edge at y = " + bottomEdge);
		
		output[0] = leftEdge;
		output[1] = topEdge;
		output[2] = rightEdge;
		output[3] = bottomEdge;
		return output;
	}
}
