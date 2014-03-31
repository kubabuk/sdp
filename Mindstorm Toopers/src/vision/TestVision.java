package vision;

import world.World;

public class TestVision {
	public static void main(String[] args) throws InterruptedException {
		World world = new World(true,true); // A whole new world...
		while (true){
			Thread.sleep(500);
			System.out.println(world.getThirdBoundary());
			System.out.println(world.getSecondBoundary());
			System.out.println(world.getFirstBoundary());
		}
	}
}
