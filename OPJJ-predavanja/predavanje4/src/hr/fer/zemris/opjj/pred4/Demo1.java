package hr.fer.zemris.opjj.pred4;

public class Demo1 {
	
	public static void main(String[] args) {
		Kvartet<String> k1 = new Kvartet<>("Ana", "Jasna", "Ivana", "Petra");
		Kvartet<Integer> k2 = new Kvartet<>(5, 1, 4, 42);
		
		for(String s : k1) {
			System.out.println(s);
		}
		
		for(Integer s : k2) {
			System.out.println(s);
		}
	}

}
