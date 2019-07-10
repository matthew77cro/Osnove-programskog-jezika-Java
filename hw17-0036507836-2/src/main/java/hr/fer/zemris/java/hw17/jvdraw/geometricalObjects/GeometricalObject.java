package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Models an abstract geometric object
 * @author Matija
 *
 */
public abstract class GeometricalObject {
	
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/**
	 * Accepts a visitor
	 * @param v visitor that needs to be accepted.
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Creates are returns a {@link GeometricalObjectEditor}.
	 * @return a {@link GeometricalObjectEditor} for the specific geometric object.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	/**
	 * Adds a listener to the list of listeners.
	 * @param l listeners that needs to be added
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(Objects.requireNonNull(l));
	}
	
	/**
	 * Removes a listener from the list of listeners.
	 * @param l listeners that needs to be removed
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Notifies all listeners that this object changed
	 */
	protected void notifyListeners() {
		for(var l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}
	
}