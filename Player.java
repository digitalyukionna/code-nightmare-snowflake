package VNServer;

public class Player {
	private String name = "";
	public void setName(String s){
		name = s;
	}
	public String getName(){
		return "<"+name+">";
	}
}
