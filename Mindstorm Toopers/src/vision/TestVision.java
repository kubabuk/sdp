package vision;

import world.World;

public class TestVision {
	private static World world;
	public static void main(String[] args) throws InterruptedException {
		world = new World(true, true);
//		panel = new CameraSettingsPanel(world, world.);
		Thread.sleep(5000);
		while(true){
			Thread.sleep(200);
			System.out.println(world.getBallPos().toString());
		}
	}
}
