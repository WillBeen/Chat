package fr.willbeen.graphic;

import javax.swing.JFrame;

import fr.willbeen.chatServer.OutputListener;

public class ServerWindow extends JFrame {

	private ConsolePanel consolePanel;
	
	public ServerWindow() {
		consolePanel = new ConsolePanel();
		this.setTitle("Chat Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setContentPane(consolePanel);
		this.setVisible(true);
	}
	
	public OutputListener getOutputListener() {
		return consolePanel;
	}
}
