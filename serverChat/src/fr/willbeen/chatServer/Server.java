package fr.willbeen.chatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import fr.willbeen.chatGUI.ServerGUI;
import fr.willbeen.chatUtils.Logger;
import fr.willbeen.chatUtils.OutputListener;

public class Server implements Runnable {
	private int port;
	private ServerSocket ss = null;
	private Logger logger = null;
	
	private Hashtable<String, Connection> connectedUsers = new Hashtable<String, Connection>();
	
	public Server(int port, OutputListener ol) {
		this.port = port;
		logger = new Logger(ol);
	}
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(port);
			logger.log("Starting server");
			logger.log(connectedUsers.size() + " client(s) are connected or trying to");
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
		logger.log(connectedUsers.size() + " client(s) are connected or trying to");
	}
	
	public void removeConnection(String login) {
		connectedUsers.remove(login);
		logger.log(connectedUsers.size() + " client(s) are connected or trying to");
	}
	
	public Logger getLogger() {
		return logger;
	}
}
