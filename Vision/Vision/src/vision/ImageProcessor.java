package vision;

public class ImageProcessor {

	public Image trackBall(BufferedImage img) {
    // Currently this method just finds the pixels on the screen that have the highest RED value for RGB.
    // Will need to find center of the ball.
		//ArrayList<Integer[]> paths = new ArrayList<Integer[]>();
		//ArrayList<int[]> red = new ArrayList<int[]>();
		for (int w = 0; w < img.getWidth(); w++) {
			for (int h = 0; h < img.getHeight(); h++) {
				Color c = new Color(img.getRGB(w, h), true);
				int blue = c.getBlue();
				int green = c.getGreen();
				int red = c.getRed();
				
				if (red > (blue + 130) && red > (green + 130)){
					img.setRGB(w, h, 0);
				}
			}
		}
		return img;
		
	}
	
}
