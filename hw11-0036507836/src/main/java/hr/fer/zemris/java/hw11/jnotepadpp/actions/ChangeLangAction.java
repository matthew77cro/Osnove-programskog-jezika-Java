package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Action for changing the language of the application.
 * @author Matija
 *
 */
public class ChangeLangAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private String lang;

	/**
	 * Creates and initialises the action
	 * @param lang language to be set
	 * @param name name of the action
	 * @param shortDescription short description of the action
	 */
	public ChangeLangAction(String lang, String name, String shortDescription) {
		putValue(Action.NAME, name);
		putValue(Action.SHORT_DESCRIPTION, shortDescription);
		this.lang = Objects.requireNonNull(lang);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(lang);
	}

}
