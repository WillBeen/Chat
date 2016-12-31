package fr.willbeen.chatClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import fr.willbeen.chatProtocol.DataStreamListener;
import fr.willbeen.chatProtocol.DataObserver;
import fr.willbeen.chatProtocol.Packet;
import fr.willbeen.chatServer.OutputListener;
import fr.willbeen.chatUtils.Logger;

public class Client implements DataObserver, Runnable, OutputListener {
	private String host;
	private int port;
	
	private Logger logger = null;
	private Socket socket = null;
	private ObjectOutputStream oos;
	Scanner sc;
	private DataStreamListener dataStreamListener = null;
	Thread dataStreamListenerThread = null;
	
	private String login = null;
	
	private Packet packet = null;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		logger = new Logger(this);
	}
	
	@Override
	public void run() {
		try {
			logger.log("Starting the client");
			sc = new Scanner(System.in);
			// before everything, get the userlogin
			displayMessage("Enter your login : ");
			login = sc.nextLine();
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
		System.out.println(msg);
	}

	public void pushUserInput(String messageToUser) {
		displayMessage(messageToUser);
		String userInput = sc.nextLine();
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
}
