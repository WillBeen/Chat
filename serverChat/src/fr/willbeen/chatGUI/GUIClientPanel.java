package fr.willbeen.chatGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.willbeen.chatUtils.InputRequestListener;
import fr.willbeen.chatUtils.Logger;
import fr.willbeen.chatUtils.TextIOListener;

public class GUIClientPanel extends JPanel{
	private ActionListener actionListener;

	private JTextArea outputArea = new JTextArea();
	private JTextField inputArea = new JTextField();
	private JSplitPane splitIO = new JSplitPane(JSplitPane.VERTICAL_SPLIT, outputArea, inputArea);
	private JList<JTextField> clients = new JList<JTextField>();
	private JSplitPane splitClients = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitIO, clients);
	private TextIOListener userInputListener = null;
	private Logger logger = null;
	
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
	
	// listener allowing to display data received from the server
	private TextIOListener outputListener = new TextIOListener() {
		@Override
		public void processIO(String output) {
			outputArea.setText(outputArea.getText() + "\n" + output);
		}
	};
	
	// listener allowing the server to ask a specific user input
	private InputRequestListener inputOnDemandInterface = new InputRequestListener() {
		@Override
		public String getUserInput(String message) {
			return JOptionPane.showInputDialog(message);
		}
	};
	
	public GUIClientPanel(ActionListener actionListener) {
		this.actionListener = actionListener;
		addComponentListener(this.componentListener);
		this.setLayout(new BorderLayout());
		splitIO.setDividerSize(3);
		splitClients.setDividerSize(3);
		outputArea.setText("Client started");
		outputArea.setFocusable(false);
		inputArea.setBorder(null);
		add(splitClients, BorderLayout.CENTER);
		
		inputArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					processUserInput();
				}
			}
		});
		logger = new Logger(outputListener);
	}
	
	public TextIOListener getOutputListener() {
		return outputListener;
	}
	
	public InputRequestListener getInputInterface() {
		return inputOnDemandInterface;
	}
	
	public void setUserInputListener(TextIOListener userInputListener) {
		this.userInputListener = userInputListener;
	}

	private void processUserInput() {
		String userInput = inputArea.getText();
		if (userInputListener == null) {
			logger.log(Logger.typeError, "You are not connected !");
		} else {
			userInputListener.processIO(userInput);
		}
		inputArea.setText("");
	}
}
