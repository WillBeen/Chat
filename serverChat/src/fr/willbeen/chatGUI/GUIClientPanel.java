package fr.willbeen.chatGUI;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.willbeen.chatUtils.InputInterface;
import fr.willbeen.chatUtils.OutputListener;

public class GUIClientPanel extends JPanel{

	private JTextArea outputArea = new JTextArea();
	private 	JTextField inputArea = new JTextField();
	private JSplitPane splitIO = new JSplitPane(JSplitPane.VERTICAL_SPLIT, outputArea, inputArea);
	private JList<JTextField> clients = new JList<JTextField>();
	private JSplitPane splitClients = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitIO, clients);
	
	private ComponentListener componentListener = new ComponentListener() {
		@Override
		public void componentShown(ComponentEvent e) {}
		@Override
		public void componentResized(ComponentEvent e) {
			splitClients.setDividerLocation(splitClients.getWidth() - 200);
			splitIO.setDividerLocation(splitIO.getHeight() - 30);
		}
		@Override
		public void componentMoved(ComponentEvent e) {}
		@Override
		public void componentHidden(ComponentEvent e) {}
	};
	
	private OutputListener outputListener = new OutputListener() {
		@Override
		public void consoleOutput(String output) {
			outputArea.setText(outputArea.getText() + "\n" + output);
		}
	};
	
	private InputInterface inputInterface = new InputInterface() {
		@Override
		public String getUserInput(String message) {
			return JOptionPane.showInputDialog(message);
		}
	};
	
	public GUIClientPanel() {
		addComponentListener(this.componentListener);
		this.setLayout(new BorderLayout());
		splitIO.setDividerSize(3);
		splitClients.setDividerSize(3);
		outputArea.setText("Client started");
		outputArea.setFocusable(false);
		inputArea.setBorder(null);
		add(splitClients, BorderLayout.CENTER);
	}
	
	public OutputListener getOutputListener() {
		return outputListener;
	}
	
	public InputInterface getInputInterface() {
		return inputInterface;
	}
}
