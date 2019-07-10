package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

@SuppressWarnings("serial")
public class LJMenu extends JMenu{

	public LJMenu(String key, ILocalizationProvider lp) {
		super(lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				LJMenu.this.setText(lp.getString(key));
			}
		});
	}

}
