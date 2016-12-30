package fr.willbeen.chatClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import fr.willbeen.chatProtocol.DataObserver;
import fr.willbeen.chatProtocol.Packet;
import fr.willbeen.chatUtils.Log;

public class Client implements DataObserver, Runnable {
	private String host;
	private int port;
	
	private Socket socket = null;
	private DataFromServerListener dataFromServerListener = null;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			Log.log("Starting the client");
			socket = new Socket(InetAddress.getByName(host), port);
			dataFromServerListener = new DataFromServerListener(socket, this);
			Thread t = new Thread(dataFromServerListener);
			t.start();
		} catch (IOException e) {
			Log.log(Log.typeError, this.getClass().toString(), "start()", "Unable to connect on " + host + ":" + port);
		}
	}

	

	@Override
	public void processData(Packet packet) {
		switch(packet.getCommand()) {
		case Packet.cmdDisplayMessage :
			displayMessage(packet.getArgs()[0]);
			break;
		case Packet.cmdGetUserInput :
			pushUserInput(packet.getArgs()[0]);
		}
	}

	public void displayMessage(String msg) {
		System.out.println(msg);
	}

	public void pushUserInput(String msg) {
		displayMessage(msg);
		Scanner sc = new Scanner(System.in);
		String userInput = sc.nextLine();
		sc.close();
		Packet packet = new Packet(Packet.cmdPushUserInput, new String[] {userInput});
		packet.send(socket);
	}
}
