package hr.fer.zemris.java.pred03;

public class Demo2 {
	
	interface Transformer {
		double transform(double value);
	}
	
	interface BiProcessor  {
		void process(double value, double transformedValue);
	}

	public static void main(String[] args) {
		
		double[] ulaz = {1,2,3,4,5,6,7,8,9,10};
		
		System.out.printf("------------------------------DODAJ3------------------------------%n");
		
		obradi(ulaz, value -> value+3, (value, transformedValue) ->
			System.out.printf("%.2f \t-+3->\t %.2f%n", value, transformedValue));
		
		System.out.printf("%n-----------------------------KVADRAT-----------------------------%n");
		
		obradi(ulaz, value -> value*value, (value, transformedValue) ->
			System.out.printf("%.2f \t-^2->\t %.2f%n", value, transformedValue));
		
		System.out.printf("%n------------------------------SINUS------------------------------%n");
		
		obradi(ulaz, Math::sin, (value, transformedValue) ->
			System.out.printf("%.2f \t-sin->\t %.2f%n", value, transformedValue));
		
	}
	
	public static void obradi(double[] ulaz, Transformer t, BiProcessor p) {
		double[] izlaz = new double[ulaz.length];
		for(int i=0; i<ulaz.length; i++) {
			izlaz[i] = t.transform(ulaz[i]);
		}
		
		for(int i=0; i<ulaz.length; i++) {
			p.process(ulaz[i], izlaz[i]);
		}
	}

}
