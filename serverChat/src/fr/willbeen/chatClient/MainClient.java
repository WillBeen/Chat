package fr.willbeen.chatClient;

public class MainClient {
	public static void main(String[] args) {
		String host = "localhost";
		int port = 2009;
		Client c = new Client(host, port);
		c.run();
	}
}
