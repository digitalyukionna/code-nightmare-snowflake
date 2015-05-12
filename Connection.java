package VNServer;
import java.io.*;
import java.net.*;
public class Connection extends Thread {
	private VNServer server;
	private Socket client;
	private BufferedReader bReader;
	private PrintWriter pWriter;
	private String input = "";
	private Player player;
	
	public Connection(VNServer server, Socket sock){
		this.server = server;
		client = sock;
		player = new Player ();
	}
	public void run(){
		try{
			bReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			pWriter = new PrintWriter(new BufferedOutputStream(client.getOutputStream()));
			setPlayerName();
			input = "";
			server.output(player.getName()+"has entered the room.");
		}
		catch(IOException e){
			System.out.println("Problem with opening IO Streams.");
			server.endConnection(this);
		}
		while (true){
			try{
				input = bReader.readLine();
				System.out.println(input);
				if (input==null||input.matches("/quit")){
					break;
				}
				
			}
			catch(IOException e){
				//do nothing;
			}
			if (input == ""){
				//do nothingIt w
			}
			else if (testInput(input)==false){
				output("That contains invalid characters.");
				input ="";
			}
			else{
				server.output(player.getName()+input);
				System.out.println("in" +input); //Testing code.
				input = "";
			}
			
		}
		output("Goodbye!");
		server.endConnection(this);
		server.output(player.getName()+ "has exited.");
		try{
			bReader.close();
			pWriter.close();
		}
		catch(IOException e){
			System.out.println("Problem with closing IO Streams.");
		}
	}
	
	public void output(String s){
		//System.out.println("out"+s); //Testing code.
		pWriter.println(s);
		pWriter.flush();
	}
	public void setPlayerName()throws IOException{
		output("Please input a valid name.");
		String name = bReader.readLine();
		if (testInput(name)){
			player.setName(name);
		}
		else {
			setPlayerName();
		}
	}
	public String getPlayerName(){
		return player.getName();
	}
	//This is to test to make sure that the input isn't crap or unprintable characters.
	//Thanks to pmfr for this bit of help.
	private boolean testInput(String s){
		for (int counter =0; counter<s.length();counter++){
			if (s.charAt(counter)<32 || s.charAt(counter)>127){
				return false;
			}
		}
		return true;
	}
}
