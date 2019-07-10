package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SearchUtil {

	public static <S> Node<S> bfs(Supplier<S> s0, 
			Function<S, List<Transition<S>>> succ, 
			Predicate<S> goal) {
		
		var zaIstraziti = new LinkedList<Node<S>>();
		zaIstraziti.add(new Node<S>(null, s0.get(), 0));
		
		while(!zaIstraziti.isEmpty()) {
			Node<S> ni = zaIstraziti.remove(0);
			if(goal.test(ni.getState())) return ni;
			List<Transition<S>> list = succ.apply(ni.getState());
			list.forEach((transition) -> zaIstraziti.add(new Node<S>(ni, transition.getState(), ni.getCost()+transition.getCost())));
		}
		
		return null;
		
	}
	
	public static <S> Node<S> bfsv(Supplier<S> s0, 
			Function<S, List<Transition<S>>> succ, 
			Predicate<S> goal) {
		
		var zaIstraziti = new LinkedList<Node<S>>();
		zaIstraziti.add(new Node<S>(null, s0.get(), 0));
		var posjeceno = new HashSet<S>();
		posjeceno.add(s0.get());
		
		while(!zaIstraziti.isEmpty()) {
			Node<S> ni = zaIstraziti.remove(0);
			if(goal.test(ni.getState())) return ni;
			List<Transition<S>> list = succ.apply(ni.getState());
			list.removeIf((s) -> posjeceno.contains(s.getState()));
			list.forEach((transition) -> {
				zaIstraziti.add(new Node<S>(ni, transition.getState(), ni.getCost()+transition.getCost()));
				posjeceno.add(transition.getState());
			});
		}
		
		return null;
		
	}

}
