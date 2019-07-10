package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider{
	
	private static LocalizationProvider provider;
	private String language;
	private ResourceBundle bundle;

	private LocalizationProvider() {
		this.language = "en";
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations.translation", Locale.forLanguageTag(language));
	}
	
	public static LocalizationProvider getInstance() {
		if(provider==null) {
			provider = new LocalizationProvider();
		}
		return provider;
	}
	
	public void setLanguage(String language) {
		this.language = language;
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations.translation", Locale.forLanguageTag(language));
		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
