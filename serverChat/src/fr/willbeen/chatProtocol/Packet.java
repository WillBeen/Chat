package fr.willbeen.chatProtocol;

import java.io.Serializable;

public class Packet implements Serializable{
	private int type;
	private String txt;
	
	public Packet(int type, String str) {
		this.type = type;
		txt = str;
	}
	
	public int type() {
		return type;
	}
	
	public String getText() {
		return txt;
	}
}
