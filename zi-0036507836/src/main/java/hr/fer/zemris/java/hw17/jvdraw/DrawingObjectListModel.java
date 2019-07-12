package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelListener;

/**
 * Represents a model of a JList used for displaying a list of geometric components
 * rendered on the canvas.
 * @author Matija
 *
 */
@SuppressWarnings("serial")
public class DrawingObjectListModel extends AbstractListModel<String> implements DrawingModelListener {

	private DrawingModel model;
	
	/**
	 * Initializes the list model using the given DrawingModel
	 * @param model model used for displaying information in the JList
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}
	
	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public String getElementAt(int index) {
		return model.getObject(index).toString();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
