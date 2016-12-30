package fr.willbeen.chatClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import fr.willbeen.chatProtocol.DataObservable;
import fr.willbeen.chatProtocol.Packet;
import fr.willbeen.chatUtils.Log;

public class DataFromServerListener extends DataObservable implements Runnable {

	private Socket socket = null;
	private ObjectInputStream ois = null;
	
	public DataFromServerListener(Socket s) {
		socket = s;
	}
	
	@Override
	public void run() {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			Packet td;
			while (true) {
				td = (Packet)ois.readObject();
				notifyObervers(td);
			}
		} catch (IOException e) {
			Log.log(Log.typeError, getClass().toString(), "run()", "Unable to initialize the ObjectInputStream");
		} catch (ClassNotFoundException e) {
			Log.log(Log.typeError, getClass().toString(), "run()", "The object received isnt from the expected class");
		}
	}

}
