package fr.willbeen.chatProtocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class DataStreamListener extends DataObservable implements Runnable {

	private ObjectInputStream ois = null;

	public DataStreamListener(ObjectInputStream ois, DataObserver dataObserver) {
		super(dataObserver);
		this.ois = ois;
	}
	
	public DataStreamListener(Socket s, DataObserver dataObserver) {
		super(dataObserver);
		dataObserver.processData(new Packet.Builder().command(Packet.cmdMessage).arguments("pouet").build());;
		try {
			ois = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			Packet packet;
			while (true) {
				packet = (Packet)ois.readObject();
				notifyObervers(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

    
    public void stop() {
        try {
            ois.close();
        } catch (IOException e) {}
    }

}
