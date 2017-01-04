package fr.willbeen.chatGUI;

import javax.swing.JFrame;

import fr.willbeen.chatUtils.TextIOListener;

public class ServerGUI extends JFrame {

	private ConsolePanel consolePanel;
	
	public ServerGUI() {
		consolePanel = new ConsolePanel();
		this.setTitle("Chat Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setContentPane(consolePanel);
		this.setVisible(true);
	}
	
	public TextIOListener getOutputListener() {
		return consolePanel;
	}
}
