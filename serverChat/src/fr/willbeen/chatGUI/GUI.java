package fr.willbeen.chatGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.willbeen.chatClient.Client;

import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class GUI extends JFrame {
	private String host = "localhost";
	private int port = 2009;
	
	private GUIMenuBar menuBar;
	private GUIClientPanel panel;
	private GUIStatusBar statusBar;
	private JMenuItem mntmPuet;
	private JMenu mnPlop;
	private JMenuItem mntmConnect;
	
	Client c;
	
	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "connect" :
				connect();
				break;
			}
		}
	};
	
	public GUI() {
		this.setTitle("Willbeen's chat");
		this.setSize(800, 600);
		
		setClient();
		
		getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(menuBar, BorderLayout.NORTH);
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	public void setClient() {
		menuBar = new GUIMenuBar(actionListener);
		panel = new GUIClientPanel();
		statusBar = new GUIStatusBar();
	}
	public void connect() {
		c = new Client(host, port, panel.getOutputListener(), panel.getInputInterface());
		c.connectToServer(JOptionPane.showInputDialog("Enter your login"));
	}
	
	public String getUserInput(String message) {
		return panel.getInputInterface().getUserInput(message);
	}
}
