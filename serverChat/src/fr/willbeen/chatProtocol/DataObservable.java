package fr.willbeen.chatProtocol;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class DataObservable {
	public ArrayList<DataObserver> observers = null;
	
	public void registerObserver(DataObserver obs) {
		observers.add(obs);
	}
	
	public void unregisterObserver(DataObserver obs) {
		observers.remove(obs);
	}
	
	public void notifyObervers(Packet td) {
		Iterator<DataObserver> it = observers.iterator();
		while (it.hasNext()) {
			it.next().processData(td);
		}
	}

}
