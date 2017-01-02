package fr.willbeen.chatGUI;

import javax.swing.JFrame;

import fr.willbeen.chatUtils.OutputListener;

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
	
	public OutputListener getOutputListener() {
		return consolePanel;
	}
}
