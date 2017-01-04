package fr.willbeen.chatGUI;

import java.awt.TextArea;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JTextArea;

import fr.willbeen.chatUtils.TextIOListener;

public class ConsolePanel extends JPanel implements TextIOListener{
	private JTextArea textArea;
	
	public ConsolePanel() {
		setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		add(textArea, BorderLayout.CENTER);
	}
	@Override
	public void processIO(String output) {
		textArea.setText(textArea.getText() + "\n" + output);
	}

}
