package fr.willbeen.chatProtocol;

import java.util.ArrayList;
import java.util.Iterator;

import fr.willbeen.chatUtils.Log;

public abstract class DataObservable {
	public ArrayList<DataObserver> observers = new ArrayList<DataObserver>();
	
	public DataObservable(DataObserver dataObserver) {
		registerObserver(dataObserver);
	}
	
	public void registerObserver(DataObserver obs) {
		observers.add(obs);
	}
	
	public void unregisterObserver(DataObserver obs) {
		observers.remove(obs);
	}
	
	public void notifyObervers(Packet packet) {
		try {
			Iterator<DataObserver> it = observers.iterator();
			while (it.hasNext()) {
				it.next().processData(packet);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}
