package fr.willbeen.chatClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import fr.willbeen.chatProtocol.DataStreamListener;
import fr.willbeen.chatProtocol.DataObserver;
import fr.willbeen.chatProtocol.Packet;
import fr.willbeen.chatUtils.InputInterface;
import fr.willbeen.chatUtils.Logger;
import fr.willbeen.chatUtils.OutputListener;

public class Client implements DataObserver, OutputListener {
	private String host;
	private int port;
	
	private Logger logger = null;
	private Socket socket = null;
	private ObjectOutputStream oos;
	private DataStreamListener dataStreamListener = null;
	Thread dataStreamListenerThread = null;
	
	private String login = null;
	
	private Packet packet = null;
	
	OutputListener outputListener = null;
	InputInterface inputInterface = null;
	
	public Client(String host, int port, OutputListener ol, InputInterface ii) {
		this.host = host;
		this.port = port;
		logger = new Logger(ol);
		logger.log("Starting the client");
		outputListener = ol;
		inputInterface = ii;
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
			logger.log(Logger.typeError, this.getClass().toString(), "start()", "Unable to connect on " + host + ":" + port);
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
			outputListener.consoleOutput((String)packet.getArguments());
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
		outputListener.consoleOutput(msg);
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

	@Override
	public void consoleOutput(String output) {
		System.out.println(output);
	}
	public OutputListener getOutputListener() {
		return this;
	}
	
	public String getUserInput(String message) {
		return inputInterface.getUserInput(message);
	}
}
