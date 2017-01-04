package fr.willbeen.chatGUI;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GUIMenuBar extends JMenuBar{
	public static final String actionConnect = "connect";
	
	ActionListener actionlistener;
	
	public GUIMenuBar(ActionListener actionlistener) {
		this.actionlistener = actionlistener;
		
		JMenu menuClient = new JMenu("Client");
		JMenuItem menuItemClientConnect = new JMenuItem("Connect to server");
		menuClient.add(menuItemClientConnect);
		menuItemClientConnect.setActionCommand(actionConnect);
		menuItemClientConnect.addActionListener(actionlistener);
		
		this.add(menuClient);
	}
	
}
