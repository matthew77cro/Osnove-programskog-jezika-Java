package hr.fer.zemris.java.pred02;

public class Primjer2 {

	public static void main(String[] args) {
		Pravokutnik p1 = new Pravokutnik("l1", 1, 1, 20, 10);
		System.out.printf("Povrsina pravokutnika je %f.%n", p1.getPovrsina());
		System.out.println(p1);
	}
	
}
