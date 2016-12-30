package fr.willbeen.chatServer;

public class MainServer {
	public static void main(String[] args) {
		Thread t = new Thread(new Server(2009));
		t.start();
	}
}
