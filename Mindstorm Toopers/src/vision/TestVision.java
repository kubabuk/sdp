package vision;

import world.World;
import vision.gui.*;

public class TestVision {
	private static World world;
	private static CameraSettingsPanel panel;
	public static void main(String[] args) throws InterruptedException {
		world = new World(true,true);
//		panel = new CameraSettingsPanel(world, world.);
		while(!world.getReady()){
			Thread.sleep(100);
		}
		while(true){
			Thread.sleep(500);
		}
	}
}
