package hr.fer.zemris.java.hw17.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectListener;

public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {
	
	private List<GeometricalObject> objects;
	private List<DrawingModelListener> listeners;
	private boolean modified = false;
	
	public DrawingModelImpl() {
		this.objects = new ArrayList<>();
		this.listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if(index<0 || index >= objects.size()) throw new IndexOutOfBoundsException();
		
		return objects.get(index);
	} 

	@Override
	public void add(GeometricalObject object) {
		objects.add(Objects.requireNonNull(object));
		notifyListenersAdded(objects.size()-1);
		object.addGeometricalObjectListener(this);
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		objects.remove(object);
		notifyListenersRemoved(index, index);
		object.removeGeometricalObjectListener(this);
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		if(!objects.contains(object)) throw new IllegalArgumentException("Object not in the model");
		int index = objects.indexOf(object);
		if(index+offset < 0 || index+offset >= objects.size()) return;
		objects.remove(object);
		objects.add(index+offset, object);
		notifyListenersChanged(index, index+offset);
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		int size = objects.size();
		for(var obj : objects) {
			obj.removeGeometricalObjectListener(this);
		}
		objects.clear();
		notifyListenersRemoved(0, size-1<0 ? 0 : size-1);
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}
	
	private void notifyListenersAdded(int index) {
		modified = true;
		for(var l : listeners) {
			l.objectsAdded(this, index, index);
		}
	}
	
	private void notifyListenersChanged(int index0, int index1) {
		modified = true;
		for(var l : listeners) {
			l.objectsChanged(this, index0, index1);
		}
	}
	
	private void notifyListenersRemoved(int index0, int index1) {
		modified = true;
		for(var l : listeners) {
			l.objectsRemoved(this, index0, index1);
		}
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = objects.indexOf(o);
		notifyListenersChanged(index, index);
	}

}
