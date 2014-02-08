import geometry.Vector;


public class Action {
	private Vector v1,v2;
	//The action includes the command to both robots, and it will be sent to communication part;
	
	public Action()
	{
		
	}
	
	public void setV1(Vector v1)
	{
		this.v1 = v1;
	}
	
	public void setV2(Vector v2)
	{
		this.v2 = v2;
	}
	
	
}
