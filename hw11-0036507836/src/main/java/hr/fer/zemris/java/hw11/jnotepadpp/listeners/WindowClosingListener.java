package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.Action;

/**
 * Performs the action when the window is closing
 * @author Matija
 *
 */
public class WindowClosingListener extends WindowAdapter{

	private Action windowClosingAction;
	
	/**
	 * Creates the object
	 * @param windowClosingAction action that needs to be performed on window closing
	 * @throws NullPointerException if windowclosingaction is null
	 */
	public WindowClosingListener(Action windowClosingAction) {
		this.windowClosingAction = Objects.requireNonNull(windowClosingAction);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		windowClosingAction.actionPerformed(null);
	}
}
