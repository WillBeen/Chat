package fr.willbeen.chatProtocol;

import fr.willbeen.chatServer.OutputListener;

public interface DataObserver {
	public void processData(Packet packet);
	public OutputListener getOutputListener();
}
