package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	private ILocalizationProvider provider;
	private ILocalizationListener listener;
	private boolean connected;
	private String oldLang;
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = Objects.requireNonNull(provider);
		this.listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				LocalizationProviderBridge.this.fire();
			}
		};
	}
	
	public void connect() {
		if(connected) return;
		provider.addLocalizationListener(listener);
		connected = true;
		if(oldLang!=null && !oldLang.equals(provider.getCurrentLanguage())) {
			this.fire();
		}
	}
	
	public void disconnect() {
		if(!connected) return;
		provider.removeLocalizationListener(listener);
		connected = false;
		oldLang = provider.getCurrentLanguage();
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}

}
