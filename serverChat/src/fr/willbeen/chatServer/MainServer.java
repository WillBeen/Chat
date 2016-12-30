package fr.willbeen.chatServer;

import javax.swing.JFrame;

import fr.willbeen.graphic.ServerWindow;

public class MainServer {
	public static void main(String[] args) {
		ServerWindow serverWindow = new ServerWindow();
		Server server = new Server(2009, serverWindow.getOutputListener());
		Thread t = new Thread(server);
		t.start();
	}
}
