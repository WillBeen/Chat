package fr.willbeen.chatServer;

import java.io.IOException;
import java.net.ServerSocket;

import fr.willbeen.chatUtils.Log;

public class Server implements Runnable {
	private ServerSocket ss;
	private int port;
	
	public Server(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(port);
			Log.log(Log.typeInfo, getClass().toString(), "run()", "Lancement du serveur");
			Thread t;
			while (true) {
				t = new Thread(new Connection(ss.accept()));
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
}
