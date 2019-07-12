package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * A JLabel component which displays currently selected fg and bg colors in its text.
 * @author Matija
 *
 */
@SuppressWarnings("serial")
public class JColorAreaListener extends JLabel implements ColorChangeListener {
	
	private IColorProvider fgColorProvider;
	private IColorProvider bgColorProvider;
	
	/**
	 * Initializes the swing label
	 * @param fgColorProvider foreground color provider
	 * @param bgColorProvider background color provider
	 */
	public JColorAreaListener(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		super(getLabelText(fgColorProvider, bgColorProvider));
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		this.setText(getLabelText(fgColorProvider, bgColorProvider));
	}
	
	/**
	 * Returns the text of that label needs to display.
	 * @param fgColorProvider foreground color provider
	 * @param bgColorProvider background color provider
	 * @return the text of that label needs to display.
	 */
	private static String getLabelText(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		return String.format(
				"Foreground color: (%d, %d, %d), background color: (%d, %d, %d).",
				fgColorProvider.getCurrentColor().getRed(),
				fgColorProvider.getCurrentColor().getGreen(),
				fgColorProvider.getCurrentColor().getBlue(),
				bgColorProvider.getCurrentColor().getRed(),
				bgColorProvider.getCurrentColor().getGreen(),
				bgColorProvider.getCurrentColor().getBlue()
		);
	}
	
}
