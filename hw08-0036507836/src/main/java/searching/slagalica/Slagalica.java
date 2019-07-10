package searching.slagalica;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

public class Slagalica implements Supplier<KonfiguracijaSlagalice>, 
	Function<KonfiguracijaSlagalice,List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

	private KonfiguracijaSlagalice pocetnaKonfiguracija;
	private KonfiguracijaSlagalice ciljnaKonfiguracija;
	
	public Slagalica(KonfiguracijaSlagalice pocetnaKonfiguracija) {
		this.pocetnaKonfiguracija = pocetnaKonfiguracija;
		this.ciljnaKonfiguracija = new KonfiguracijaSlagalice(new int[] {1,2,3,4,5,6,7,8,0});
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return ciljnaKonfiguracija.equals(t);
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		var list = new ArrayList<Transition<KonfiguracijaSlagalice>>();
		
		int space = t.indexOfSpace();
		//row move
		if(space==0 || space==3 || space==6) {
			int[] polje = t.getPolje();
			list.add(new Transition<>(swapped(space, space+1, polje), 1));
		} else if(space==1 || space==4 || space==7) {
			int[] polje = t.getPolje();
			list.add(new Transition<>(swapped(space, space+1, polje), 1));
			
			polje = t.getPolje();
			list.add(new Transition<>(swapped(space, space-1, polje), 1));
		} else {
			int[] polje = t.getPolje();
			list.add(new Transition<>(swapped(space, space-1, polje), 1));
		}
		
		//column move
		if(space==0 || space==1 || space==2) {
			int[] polje = t.getPolje();
			list.add(new Transition<>(swapped(space, space+3, polje), 1));
		} else if(space==3 || space==4 || space==5) {
			int[] polje = t.getPolje();
			list.add(new Transition<>(swapped(space, space+3, polje), 1));
			
			polje = t.getPolje();
			list.add(new Transition<>(swapped(space, space-3, polje), 1));
		} else {
			int[] polje = t.getPolje();
			list.add(new Transition<>(swapped(space, space-3, polje), 1));
		}
		
		return list;
	}
	
	private KonfiguracijaSlagalice swapped(int index1, int index2, int[] polje) {
		int holder = polje[index1];
		polje[index1] = polje[index2];
		polje[index2] = holder;
		return new KonfiguracijaSlagalice(polje);
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return pocetnaKonfiguracija;
	}

}
