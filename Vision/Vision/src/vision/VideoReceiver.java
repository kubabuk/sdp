package vision;

import java.awt.image.BufferedImage;

/**
 * An interface for classes which receive video frames from a video stream or
 * other source (current sources are VideoStream and DistortionFix).
 * 
 * @author Alex Adams (s1046358)
 */
public interface VideoReceiver {
	void sendFrame(BufferedImage frame, int frameRate, int frameCounter);
}
