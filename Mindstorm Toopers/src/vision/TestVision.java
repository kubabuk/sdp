package vision;

import world.World;

public class TestVision {
	private static World world;	
	public static void main(String[] args) throws InterruptedException {
		world = new World(true, true);
		Thread.sleep(5000);
		while(true){
			Thread.sleep(1000);
<<<<<<< HEAD
			System.out.println(world.getDefenderDir().toString());
=======
			System.out.println(world.getBallPos().toString());
>>>>>>> e7ef3b331d8a8c8fe739af78c04733966ada7b78
		}
	}
}
