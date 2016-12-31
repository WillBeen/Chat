package fr.willbeen.chatProtocol;

import java.io.Serializable;

public class Packet implements Serializable{

	public static final int cmdPing = 0;
	public static final int cmdPong = 1;
	public static final int cmdMessage = 2;
	public static final int cmdGetUserInput = 3;
	public static final int cmdPushInformation = 4;
	public static final int cmdEndConnection = 5;

	private int command = -1;
	private String from = null;
	private String to = null;
	private String options = null;
	private Object arguments = null;
	
	public Packet(int command, String from, String to, String options, Object arguments) {
		this.command = command;
		this.from = from;
		this.to = to;
		this.options = options;
		this.arguments = arguments;
	}

	public int getCommand() {
		return command;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getOptions() {
		return options;
	}

	public Object getArguments() {
		return arguments;
	}
	
	public static class Builder {
		private int command = -1;
		private String from = null;
		private String to = null;
		private String options = null;
		private Object arguments = null;
		
		public Packet build() {
			return new Packet(command, from, to, options, arguments);
		}
		public Builder command(int command) {
			this.command = command;
			return this;
		}
		public Builder from(String from) {
			this.from = from;
			return this;
		}
		public Builder to(String to) {
			this.to = to;
			return this;
		}
		public Builder options(String options) {
			this.options = options;
			return this;
		}
		public Builder arguments(Object arguments) {
			this.arguments = arguments;
			return this;
		}
	}

}

















