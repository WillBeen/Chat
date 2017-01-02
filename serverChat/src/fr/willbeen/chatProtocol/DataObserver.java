package fr.willbeen.chatProtocol;

import fr.willbeen.chatUtils.OutputListener;

public interface DataObserver {
	public void processData(Packet packet);
	public OutputListener getOutputListener();
}
