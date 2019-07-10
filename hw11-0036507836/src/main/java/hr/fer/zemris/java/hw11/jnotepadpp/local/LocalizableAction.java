package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class LocalizableAction extends AbstractAction{

	public LocalizableAction(String key, ILocalizationProvider lp) {
		putValue(NAME, lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
