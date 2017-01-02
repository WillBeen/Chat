package fr.willbeen.chatGUI;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIStatusBar extends JPanel {

	JTextField txtStatus = new JTextField();
	
	public GUIStatusBar() {
		txtStatus.setText("am I connected ?");
	}
}
