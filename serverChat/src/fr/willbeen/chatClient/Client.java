package fr.willbeen.chatClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import fr.willbeen.chatProtocol.DataObserver;
import fr.willbeen.chatProtocol.Packet;
import fr.willbeen.chatUtils.Log;

public class Client implements DataObserver, Runnable {
	private String host;
	private int port;
	
	private Socket socket = null;
	private DataFromServerListener dataObservable = null;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(InetAddress.getByName(host), port);
			dataObservable = new DataFromServerListener(socket);
			Thread t = new Thread(dataObservable);
			t.start();
		} catch (IOException e) {
			Log.log(Log.typeError, this.getClass().toString(), "start()", "Unable to connect on " + host + ":" + port);
		}
	}

	

	@Override
	public void processData(Packet td) {
		Log.log(Log.typeInfo, getClass().toString(), "processData()", "Object received");
	}

}
