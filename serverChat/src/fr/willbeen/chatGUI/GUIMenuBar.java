package fr.willbeen.chatGUI;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GUIMenuBar extends JMenuBar{
	ActionListener actionlistener;
	
	public GUIMenuBar(ActionListener actionlistener) {
		this.actionlistener = actionlistener;
		
		JMenu client = new JMenu("Client");
		JMenuItem clientConnect = new JMenuItem("Connect to server");
		client.add(clientConnect);
		clientConnect.setActionCommand("connect");
		clientConnect.addActionListener(actionlistener);
		
		this.add(client);
	}
	
}
