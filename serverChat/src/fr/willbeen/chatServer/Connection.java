package fr.willbeen.chatServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import fr.willbeen.chatProtocol.DataObserver;
import fr.willbeen.chatProtocol.DataStreamListener;
import fr.willbeen.chatProtocol.Packet;
import fr.willbeen.chatUtils.Logger;
import fr.willbeen.chatUtils.TextIOListener;

public class Connection implements Runnable, DataObserver {
	public static final String newLine = System.getProperty("line.separator");
	public static final String usersFilePath = "users.txt";
	
	private Logger logger = null;
	private Server server = null;
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private DataStreamListener dataStreamListener = null;
	
	private String login;

	public Connection(Server srv, Socket s) {
		server = srv;
		logger = server.getLogger();
		socket = s;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {		
			logger.log("New connection incoming ...");
			String password = "";
			Packet packet;
			try {
				// when socket connection established, the client sends the login
				packet = (Packet)ois.readObject();
				login = getUserInput(packet);
				sendMessageToClient("Bienvenue " + login);
				askClientUserInput("Entrer le password");
				packet = (Packet)ois.readObject();
				password = getUserInput(packet);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isValidPassword(password)) {
				logger.log("Connexion accepted for user : " + login);
				sendMessageToClient("Connexion acceptee");
				server.addConnection(login, this);
				dataStreamListener = new DataStreamListener(ois, this);
				Thread t = new Thread(dataStreamListener);
				t.start();
			} else {
				logger.log("Wrong password for user : " + login);
				sendMessageToClient("Login / password incorrect");
				stop();
			}
	}
	
	private void stop() {
		sendMessageToClient("Deconnexion du serveur");
		Packet packet = new Packet.Builder().command(Packet.cmdEndConnection).build();
		send(packet);
		try {
			socket.close();
		} catch (IOException e) {}
	}
	
	public boolean isValidPassword(String password) {
		boolean valid = false;
		try {
			Scanner sc = new Scanner(new File(usersFilePath));
			while (!valid && sc.hasNext()) {
				valid = sc.nextLine().equals(login + " " + password);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return valid;
	}
	
	public void sendMessageToClient() {
		sendMessageToClient("");
	}
	public void sendMessageToClient(String message) {
		Packet packet = new Packet.Builder().command(Packet.cmdMessage).arguments(message).build();
		send(packet);
	}
	
	public void askClientUserInput(String message) {
		Packet packet = new Packet.Builder().command(Packet.cmdGetUserInput).arguments(message).build();
		send(packet);
	}
	
	public String getLogin() {
		return login;
	}
	
	public void processInputFromClient(String inputString) {
		switch (inputString) {
		case "/exit" : stop();
		break;
		}
	}
	
	public void send(Packet packet) {
		try {
			oos.writeObject(packet);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void processData(Packet packet) {
		switch (packet.getCommand()) {
		case Packet.cmdMessage :
			if (packet.getTo() == null)
				logger.log("<" + login + "> " + (String)packet.getArguments());
			else
				try {
					Packet p = new Packet.Builder().command(Packet.cmdMessage).from(login).arguments((String)packet.getArguments()).build();
					server.getConnection((String)packet.getTo()).send(p);
				} catch (Exception e) {
					Packet p = new Packet.Builder().command(Packet.cmdMessage).arguments(e.getMessage()).build();
					send(p);
				}
			break;
		}
	}

	@Override
	public TextIOListener getOutputListener() {
		return server.getLogger().getOutputListener();
	}
	
	private String getUserInput(Packet packet) {
		String userInput = null;
		if (packet.getCommand() == Packet.cmdPushInformation) {
			userInput = (String)packet.getArguments();
		}
		return userInput;
	}
}
