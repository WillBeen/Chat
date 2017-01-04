package fr.willbeen.chatClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import fr.willbeen.chatProtocol.DataStreamListener;
import fr.willbeen.chatProtocol.DataObserver;
import fr.willbeen.chatProtocol.Packet;
import fr.willbeen.chatUtils.InputRequestListener;
import fr.willbeen.chatUtils.Logger;
import fr.willbeen.chatUtils.TextIOListener;

public class Client implements DataObserver {
	private String host;
	private int port;
	
	private Logger logger = null;
	private Socket socket = null;
	private ObjectOutputStream oos;
	private DataStreamListener dataStreamListener = null;
	private Thread dataStreamListenerThread = null;
	
	private String login = null;
	
	private Packet packet = null;

	private TextIOListener outputListener = null;
	private InputRequestListener inputOnRequestInterface = null;
	
	private TextIOListener userInputListener = new TextIOListener() {
		@Override
		public void processIO(String io) {
			processUserInput(io);
		}
	};
	
	public Client(String host, int port, TextIOListener ol, InputRequestListener ii) {
		this.host = host;
		this.port = port;
		logger = new Logger(ol);
		logger.log("Starting the client");
		outputListener = ol;
		inputOnRequestInterface = ii;
	}

	public void connectToServer(String login) {
		try {
			this.login = login;
			// establishing socket connection with the server
			displayMessage("Connection to the server ...");
			socket = new Socket(InetAddress.getByName(host), port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();			
			dataStreamListener = new DataStreamListener(socket, this);
			dataStreamListenerThread = new Thread(dataStreamListener);
			dataStreamListenerThread.start();
			// Sending the user login for this client
			packet = new Packet.Builder().command(Packet.cmdPushInformation).arguments(login).build();
			send(packet);
		} catch (IOException e) {
			logger.log(Logger.typeError, "Unable to connect on " + host + ":" + port);
		}
	}
	public void stop() {
		try {
			socket.close();
		} catch (IOException e) {}
	}

	@Override
	public void processData(Packet packet) {
		switch(packet.getCommand()) {
		case Packet.cmdMessage :
			if (packet.getFrom() == null)
				outputListener.processIO((String)packet.getArguments());
			else
				outputListener.processIO("<" + (String)packet.getFrom() + "> " + (String)packet.getArguments());
			break;
		case Packet.cmdGetUserInput :
			pushUserInput((String)packet.getArguments());
			break;
		case Packet.cmdEndConnection :
			stop();
			break;
		}
	}

	public void displayMessage(String msg) {
		outputListener.processIO(msg);
	}

	public void pushUserInput(String messageToUser) {
		String userInput = getUserInput(messageToUser);
		Packet packet = new Packet.Builder().command(Packet.cmdPushInformation).arguments(userInput).build();
		send(packet);
	}
	
	private void send(Packet packet) {
		try {
			oos.writeObject(packet);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TextIOListener getOutputListener() {
		return outputListener;
	}
	
	public String getUserInput(String message) {
		return inputOnRequestInterface.getUserInput(message);
	}
	
	public TextIOListener getUserInputListener() {
		return userInputListener;
	}
	
	private void processUserInput(String userInput) {
		switch (userInput.split(" ")[0]) {
		case "/time" :
			outputListener.processIO(" >> Il est l'heure qu'il est et c'est tout !");
			break;
		case "/disconnect" : 
			try {socket.close();} catch (IOException e) {}
			break;
		case "/help" :
			outputListener.processIO(help());
			break;
		case "/pv" :
		case "/private" :
			String[] userInputWords = userInput.split(" ");
			// if the input has at least 3 fields (command, login and message)
			if (userInputWords.length >= 3) {
				String to = userInputWords[1];
				// if the message is sent to itself
				if (to == login) {
					outputListener.processIO("ERROR : you can't send a message to yourself");
				} else {
					String message = userInputWords[2];
					try {
						for (int i = 3; i < userInputWords.length; i++) {
							message += " " + userInputWords[i];
						}
					} catch (IndexOutOfBoundsException e) {
					}
					Packet packet = new Packet.Builder().command(Packet.cmdMessage).to(to).arguments(message).build();
					send(packet);
					outputListener.processIO(message);
				}
			} else {
				outputListener.processIO("ERROR : you must specify a user and a message to send");
			}
			break;
		default :
			outputListener.processIO("<" + login + "> " + userInput);
			Packet packet = new Packet.Builder().command(Packet.cmdMessage).arguments(userInput).build();
			send(packet);
		}
	}
	
	private String help() {
		String help =
				"\n commands available :" +
						"\n\t /help : display this help" +
						"\n\t /pv or /private *login* : send a private message to login"
						;
		return help;
	}
}
