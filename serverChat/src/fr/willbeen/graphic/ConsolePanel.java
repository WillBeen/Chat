package fr.willbeen.graphic;

import java.awt.TextArea;

import javax.swing.JPanel;

import fr.willbeen.chatServer.OutputListener;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

public class ConsolePanel extends JPanel implements OutputListener{
	private JTextArea textArea;
	
	public ConsolePanel() {
		setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		add(textArea, BorderLayout.CENTER);
	}
	@Override
	public void consoleOutput(String output) {
		textArea.setText(textArea.getText() + "\n" + output);
	}

}
