package vision;

public class ImageProcessor {

    /* Currently this method finds the ball, and changes it to appear as black
       on the gui. It also calculates the center of the ball with pixel values.
       Needs to be refactored into two separate methods, ideally taking into 
       account computational cost and splitting the method at latest point possible.
       Also needs to take into consideration grid coordinate system, which is still
       to be implemented.*/

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
			if (count > 0) 
				System.out.println("" + redX/((double)count) + "," + redY/((double)count));
		}
		return img;
		
	}
	
}
