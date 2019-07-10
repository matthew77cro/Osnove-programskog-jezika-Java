package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This class models a model in MVC pattern for JList components. It models a list of prime numbers.
 * @author Matija
 *
 */
public class PrimListModel implements ListModel<Long>{
	
	private List<Long> primeNumbers;
	private List<ListDataListener> listeners;

	/**
	 * Creates and initialises prime numbers list
	 */
	public PrimListModel() {
		primeNumbers = new ArrayList<Long>();
		listeners = new ArrayList<>();
		primeNumbers.add(1L);
	}
	
	/**
	 * Generates and adds next prime to the list. Also notifies all listeners
	 * registered to this model.
	 */
	public void next() {
		int pos = primeNumbers.size();
		long last = primeNumbers.get(pos-1);
		last++;
		while(!isPrime(last)) last++;
		primeNumbers.add(last);
		
		var evt = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(var listener : listeners) {
			listener.intervalAdded(evt);
		}
	}
	
	private boolean isPrime(long l) {
		if(l==1L) return true;
		
		for(long i=2; i<=l/2; i++) {
			if(l%i==0) return false;
		}
		return true;
	}

	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public Long getElementAt(int index) {
		return primeNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		if(l==null) return;
		listeners.remove(l);
	}

}
