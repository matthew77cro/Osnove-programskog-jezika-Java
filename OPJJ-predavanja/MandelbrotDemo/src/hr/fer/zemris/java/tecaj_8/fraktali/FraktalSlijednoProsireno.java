package hr.fer.zemris.java.tecaj_8.fraktali;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

public class FraktalSlijednoProsireno {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FractalViewer.show(new MojProducer());	
	}

	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			int offset = 0;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					double zre = 0;
					double zim = 0;
					double module = 0;
					int iters = 0;
					do {
						double zn1re = zre*zre - zim*zim + cre;
						double zn1im = 2*zre*zim +cim;
						module = zn1re*zn1re + zn1im*zn1im;
						zre = zn1re;
						zim = zn1im;
						iters++;
					} while(iters < m && module < 4.0);
					data[offset] = (short)iters;
					offset++;
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)m, requestNo);
		}
	}
}
