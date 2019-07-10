package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

	List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(Objects.requireNonNull(listener));
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	public void fire() {
		for(var l : listeners) {
			l.localizationChanged();
		}
	}

}
