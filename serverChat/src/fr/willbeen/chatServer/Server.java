package fr.willbeen.chatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import fr.willbeen.chatUtils.Log;

public class Server implements Runnable {
	private ServerSocket ss;
	private int port;
	
	private Hashtable<String, Connection> connectedUsers = new Hashtable<String, Connection>();
	
	public Server(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(port);
			Log.log("Starting server");
			Log.log(connectedUsers.size() + " client(s) are connected or trying to");
			Thread t;
			Connection c;
			while (true) {
				c = new Connection(this, ss.accept());
				t = new Thread(c);
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addConnection(String login, Connection c) {
		connectedUsers.put(login, c);
		Log.log(connectedUsers.size() + " client(s) are connected or trying to");
	}
	
	public void removeConnection(String login) {
		connectedUsers.remove(login);
		Log.log(connectedUsers.size() + " client(s) are connected or trying to");
	}
}
