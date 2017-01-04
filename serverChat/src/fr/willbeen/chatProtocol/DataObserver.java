package fr.willbeen.chatProtocol;

import fr.willbeen.chatUtils.TextIOListener;

public interface DataObserver {
	public void processData(Packet packet);
	public TextIOListener getOutputListener();
}
