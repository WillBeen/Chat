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
import fr.willbeen.chatUtils.Log;

public class Client implements DataObserver, Runnable {
	private String host;
	private int port;
	
	private Socket socket = null;
	private ObjectOutputStream oos;
	Scanner sc;
	private DataStreamListener dataStreamListener = null;
	Thread dataStreamListenerThread = null;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			Log.log("Starting the client");
			socket = new Socket(InetAddress.getByName(host), port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();			
			dataStreamListener = new DataStreamListener(socket, this);
			dataStreamListenerThread = new Thread(dataStreamListener);
			dataStreamListenerThread.start();
			sc = new Scanner(System.in);
		} catch (IOException e) {
			Log.log(Log.typeError, this.getClass().toString(), "start()", "Unable to connect on " + host + ":" + port);
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
		case Packet.cmdDisplayMessage :
			displayMessage(packet.getArgs()[0]);
			break;
		case Packet.cmdGetUserInput :
			pushUserInput(packet.getArgs()[0]);
			break;
		case Packet.cmdStopConnection :
			stop();
			break;
		}
	}

	public void displayMessage(String msg) {
		System.out.println(msg);
	}

	public void pushUserInput(String msg) {
		displayMessage(msg);
		String userInput = sc.nextLine();
		Packet packet = new Packet(Packet.cmdPushUserInput, new String[] {userInput});
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
}
