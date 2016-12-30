package fr.willbeen.chatServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import fr.willbeen.chatProtocol.Packet;
import fr.willbeen.chatUtils.Log;

public class Connection implements Runnable {
	public static final String newLine = System.getProperty("line.separator");
	public static final String usersFilePath = "D:\\Utilisateurs\\P093770\\workspace\\serverChat\\users.txt";
	
	private Socket socket;
	private ObjectOutputStream oos = null;
	
	private String login;

	public Connection(Socket s) {
		socket = s;
	}
	
	@Override
	public void run() {		
		try {
			Log.log(Log.typeInfo, getClass().toString(), "run()", "New connection incoming ...");
			oos = new ObjectOutputStream(socket.getOutputStream());
			Log.log(Log.typeInfo, getClass().toString(), "run()", "ObjectOutputStream initialized");
			while (true) {
				
			}

//			sendToClient("Bienvenue");
//			sendToClient();
//			sendToClient("Entrer le login : ");
////			login = in.readLine();
//
//			sendToClient("Entrer le password : ");
////			String password = in.readLine();
//			
//			sendToClient();
////			if (isValidPassword(password)) {
////				sendToClient("Connexion acceptee");
////				cnxListenChan = new ConnectionListenChannel(this);
////				Thread t = new Thread(cnxListenChan);
////				t.start();
////			} else {
////				sendToClient("Login / password incorrect");
////				stop();
////			}
//			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void stop() {
		sendToClient("Deconnexion du serveur");
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
	
	public void sendToClient() {
		sendToClient("");
	}
	public void sendToClient(String message) {
		try {
			oos.writeObject(new Packet(message));
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getLogin() {
		return login;
	}
	
	public void processInputFromClient(String inputString) {
		switch (inputString) {
		case "exit" : stop();
		break;
		}
	}
}
