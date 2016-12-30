package fr.willbeen.chatProtocol;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Packet implements Serializable{
	
	public static final int cmdDisplayMessage = 1;
	public static final int cmdGetUserInput = 2;
	public static final int cmdPushUserInput = 3;
	
	private int command;
	private String[] args;
	
	public Packet(String str) {
		this(Packet.cmdDisplayMessage, new String[]{str});
	}
	
	public Packet(int command, String[] args) {
		this.command = command;
		this.args = args;
	}
	
	public int getCommand() {
		return command;
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public void send(Socket s) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(this);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
