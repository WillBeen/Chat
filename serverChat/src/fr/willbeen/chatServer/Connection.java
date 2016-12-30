package fr.willbeen.chatServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

import fr.willbeen.chatProtocol.Packet;
import fr.willbeen.chatUtils.Log;

public class Connection implements Runnable {
	public static final String newLine = System.getProperty("line.separator");
	public static final String usersFilePath = "D:\\Utilisateurs\\P093770\\workspace\\serverChat\\users.txt";
	
	private Socket socket;
	private ObjectInputStream ois = null;
	
	private String login;

	public Connection(Socket s) {
		socket = s;
	}
	
	@Override
	public void run() {		
			Log.log("New connection incoming ...");

			Log.log("Sending welcome message");
			sendToClient("Bienvenue");
			
			askClientUserInput("Entrer le login : ");
			Packet packet;
			try {
				InputStream is = socket.getInputStream();
				ois = new ObjectInputStream(is);
				packet = (Packet)ois.readObject();
				while (packet.getCommand() != Packet.cmdPushUserInput) {
					login = packet.getArgs()[0];
				}
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.log("Login = " + login);

//			askClientUserInput("Entrer le password : ");
//			String password = in.readLine();
//			Log.log("Password = " + password);
//			
//			in.close();
			
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
			Scanner sc = new Scanner(System.in);
			sc.next();
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
		Packet packet = new Packet(message);
		packet.send(socket);
	}
	
	public void askClientUserInput(String message) {
		Packet packet = new Packet(Packet.cmdGetUserInput, new String[]{message});
		packet.send(socket);
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
