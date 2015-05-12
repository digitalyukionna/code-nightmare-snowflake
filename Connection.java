package VNServer;
import java.io.*;
import java.net.*;
public class Connection extends Thread {
	private boolean running = true;
	private VNServer server;
	private Socket client;
	private BufferedReader bReader;
	private PrintWriter pWriter;
	private String input = "";
	
	public Connection(VNServer server, Socket sock){
		this.server = server;
		client = sock;
		
	}
	public void run(){
		try{
			bReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			pWriter = new PrintWriter(new BufferedOutputStream(client.getOutputStream()));
		}
		catch(IOException e){
			System.out.println("Problem with opening IO Streams.");
			running = false;
		}
		while (running){
			try{
				input = bReader.readLine();
			}
			catch(IOException e){
				//do nothing;
			}
			if (input == ""){
				//do nothingIt w
			}
			else{
				server.output(input);
				System.out.println("in" +input); //Testing code.
				input = "";
			}
			
		}
		try{
			bReader.close();
			pWriter.close();
		}
		catch(IOException e){
			System.out.println("Problem with closing IO Streams.");
		}
	}
	public void terminate(){
		running = false;
	}
	public void output(String s){
		System.out.println("out"+s); //Testing code.
		pWriter.println(s);
		pWriter.flush();
	}
}
