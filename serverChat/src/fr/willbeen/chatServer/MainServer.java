package fr.willbeen.chatServer;

import fr.willbeen.chatGUI.ServerGUI;

public class MainServer {
	public static void main(String[] args) {
		ServerGUI serverWindow = new ServerGUI();
		Server server = new Server(2009, serverWindow.getOutputListener());
		Thread t = new Thread(server);
		t.start();
	}
}
