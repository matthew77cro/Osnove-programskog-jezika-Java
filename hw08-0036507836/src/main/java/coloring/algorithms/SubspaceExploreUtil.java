package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SubspaceExploreUtil {

	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, 
			Function<S,List<S>> succ, Predicate<S> acceptable) {
		
		List<S> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(s0.get());
		
		while(!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.remove(0);
			if(!acceptable.test(si)) continue;
			process.accept(si);
			zaIstraziti.addAll(succ.apply(si));
		}
		
	}
	
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, 
			Function<S,List<S>> succ, Predicate<S> acceptable) {
		
		List<S> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(s0.get());
		
		while(!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.remove(0);
			if(!acceptable.test(si)) continue;
			process.accept(si);
			zaIstraziti.addAll(0,succ.apply(si));
		}
		
	}
	
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, 
			Function<S,List<S>> succ, Predicate<S> acceptable) {
		
		List<S> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(s0.get());
		Set<S> posjeceno = new HashSet<S>();
		posjeceno.add(s0.get());
		
		while(!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.remove(0);
			if(!acceptable.test(si)) continue;
			process.accept(si);
			List<S> djeca = succ.apply(si);
			djeca.removeIf((s) -> posjeceno.contains(s));
			zaIstraziti.addAll(djeca);
			posjeceno.addAll(djeca);
		}
		
	}
	
}
