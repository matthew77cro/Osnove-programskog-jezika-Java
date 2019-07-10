package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

public class SlagalicaMain {

	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.err.println("Program expects one argument!");
			return;
		}
		
		if(args[0].length()!=9) {
			System.err.println("Argument must be of length 9!");
			return;
		}
		
		int[] slagalicaPocetna = new int[9];
		char[] strchr = args[0].toCharArray();
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(0);
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(4);
		numbers.add(5);
		numbers.add(6);
		numbers.add(7);
		numbers.add(8);
		
		for(int i=0; i<9; i++) {
			slagalicaPocetna[i] = Integer.parseInt(Character.toString(strchr[i]));
			numbers.remove(Integer.valueOf(slagalicaPocetna[i]));
		}
		
		if(numbers.size()!=0) {
			System.err.println("All number from list l must be present! l = [0,1,2,3,4,5,6,7,8]");
			return;
		}
		
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(slagalicaPocetna));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			SlagalicaViewer.display(rješenje);
		}
		
	}

}
