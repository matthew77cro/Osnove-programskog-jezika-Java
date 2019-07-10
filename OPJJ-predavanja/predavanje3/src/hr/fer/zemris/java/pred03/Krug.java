package hr.fer.zemris.java.pred03;

import java.util.Objects;
import static java.lang.Math.*;

public class Krug extends GeometrijskiLik {

	private int centarX;
	private int centarY;
	private int radijus;
	
	public Krug(int centarX, int centarY, int radijus) {
		super();
		this.centarX = centarX;
		this.centarY = centarY;
		this.radijus = radijus;
	}

	public int getCentarX() {
		return centarX;
	}

	public int getCentarY() {
		return centarY;
	}

	public int getRadijus() {
		return radijus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(centarX, centarY, radijus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Krug))
			return false;
		Krug other = (Krug) obj;
		return centarX == other.centarX && centarY == other.centarY && radijus == other.radijus;
	}

	@Override
	public boolean sadrziTocku(int x, int y) {
		int dx = x-centarX;
		int dy = y-centarY;
		double udaljenost = sqrt(dx*dx + dy*dy);
		
		return (int)(udaljenost+0.5)<radijus;
	}
	
}
