package hr.fer.zemris.java.hw17.jvdraw.model;

/**
 * Models a listener of the collection of geometric objects (aka DrawingModel).
 * @author Matija
 *
 */
public interface DrawingModelListener {
	/**
	 * Method fired when object has been added to the collection.
	 * @param source {@link DrawingModel} which changed
	 * @param index0 starting index of change
	 * @param index1 end index of change
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Method fired when object has been removed from the collection.
	 * @param source {@link DrawingModel} which changed
	 * @param index0 starting index of change
	 * @param index1 end index of change
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Method fired when object has been changed in the collection.
	 * @param source {@link DrawingModel} which changed
	 * @param index0 starting index of change
	 * @param index1 end index of change
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
