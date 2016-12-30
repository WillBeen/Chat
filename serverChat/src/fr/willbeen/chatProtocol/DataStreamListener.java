package fr.willbeen.chatProtocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import fr.willbeen.chatUtils.Log;

public class DataStreamListener extends DataObservable implements Runnable {

	private Socket socket = null;
	private ObjectInputStream ois = null;
	
	public DataStreamListener(Socket s, DataObserver dataObserver) {
		super(dataObserver);
		socket = s;
	}
	
	@Override
	public void run() {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			Packet packet;
			while (true) {
				packet = (Packet)ois.readObject();
				notifyObervers(packet);
			}
		} catch (IOException e) {
			Log.log(Log.typeError, getClass().toString(), "run()", "Unable to initialize the ObjectInputStream");
		} catch (ClassNotFoundException e) {
			Log.log(Log.typeError, getClass().toString(), "run()", "The object received isnt from the expected class");
		}
	}

    
    public void stop() {
        try {
            ois.close();
        } catch (IOException e) {}
    }

}
