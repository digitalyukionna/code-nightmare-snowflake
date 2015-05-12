package VNServer;

import java.util.*;
import java.net.*;
import java.io.*;

public class VNServer implements Runnable {
	private boolean running = true;
	private ArrayList<Connection> clients = new ArrayList<Connection>();
	int connection = 0;
	private ServerSocket sock;

	public static void main(String[] args) {
		VNServer server = new VNServer();
		server.run();
	}

	public VNServer() {
		try {
			sock = new ServerSocket(8088);
			sock.setSoTimeout(10000);
		} catch (IOException e) {
			System.out
					.println("Could not use that socket!  Could not make server!");
			System.exit(1);
		}
	}
/*
 * Sends the raw output to all connected clients.
 */
	public void output(String s) {
		for (Connection c : clients) {
			c.output(s);
		}
	}
	/*
	 * removes a connection from the list.
	 */
	public void endConnection(Connection conn){
		clients.remove(conn);
		System.out.println(conn.getPlayerName()+"has disconnected.");
	}

	public void run() {
		while (running) {
			// System.out.println("Beep!");
			if (sock == null) {
				// do nothing
			} else {
				try {
					clients.add((new Connection(this, sock.accept())));
					System.out.println("Added a new connection!");
					connection = clients.size() - 1;
					clients.get(connection).start();
				} catch (IOException e) {
					// System.out.println("Failed to make a connection.");
				}
			}
		}
	}
}
