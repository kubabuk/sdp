import com.mydomain.Robot.CommandNames;




public class DefRobot {

	private static Communicator mCont;
	public static void main(String[] args) {
		final Robot nxt = new Robot(true);

		mCont = new BComm(new MessageListener() {
			public void receiveMessage(CommandNames op,int arg1, int arg2, Communicator controler)
			{
				nxt.addCommand(new Commands(op,arg1,arg2));
				System.out.println(op);
				System.out.println(arg1);
				System.out.println(arg2);
			}
		});

		nxt.run();     

		System.out.println("over");


	}

}
