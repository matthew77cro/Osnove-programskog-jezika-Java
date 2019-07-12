package hr.fer.zemris.java.hw17.jvdraw.model;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;

/**
 * Models a collection of geometric objects.
 * @author Matija
 *
 */
public interface DrawingModel {
	
	/**
	 * Returns the size of the collection
	 * @return
	 */
	public int getSize();

	/**
	 * Returns the {@link GeometricalObject} at the given index
	 * @param index index of a {@link GeometricalObject} that needs to be returned
	 * @return the {@link GeometricalObject} at the given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds the given {@link GeometricalObject} to the collection
	 * @param object {@link GeometricalObject} that needs to be added to the collection
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes the given {@link GeometricalObject} from the collection
	 * @param object {@link GeometricalObject} that needs to be removed from the collection
	 */
	public void remove(GeometricalObject object);

	/**
	 * Shifts the object for the given offset. If index(object)+offset
	 * less than 0 or greater or equal to the size of the collection,
	 * no changes are made.
	 * @param object object that needs to be shifted
	 * @param offset offset for which the object needs to be shifter
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Returns the index of the given object
	 * @param object objects for which the index needs to be returned
	 * @return the index of the given object
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Removes all objects from the collection
	 */
	public void clear();

	/**
	 * Clears the modification flag
	 */
	public void clearModifiedFlag();

	/**
	 * Returns the value of the modification flag
	 * @return the value of the modification flag
	 */
	public boolean isModified();

	/**
	 * Adds a listener to the list of listeners.
	 * @param l listeners that needs to be added
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes a listener from the list of listeners.
	 * @param l listeners that needs to be removed
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
