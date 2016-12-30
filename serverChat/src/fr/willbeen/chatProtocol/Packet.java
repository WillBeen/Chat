package fr.willbeen.chatProtocol;

import java.io.Serializable;

public class Packet implements Serializable{
	
	public static final int cmdDisplayMessage = 1;
	public static final int cmdGetUserInput = 2;
	public static final int cmdPushUserInput = 3;
	public static final int cmdStopConnection = 4;
	
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
}
