package hr.fer.zemris.java.hw06.shell;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.Util;
import hr.fer.zemris.java.hw08.shell.parser.FilterResult;

public class Test {

	public static void main(String[] args) {
		List<FilterResult> l = null;
		try {
			l = Util.filter(Paths.get("C:\\Users\\Matija\\Desktop\\Test"), "slika(\\d+)-([^.]+)\\.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(FilterResult r : l) {
			System.out.print(r);
			for(int i=0; i<r.numberOfGroups(); i++) {
				System.out.print(" " + i + ": " + r.group(i));
			}
			System.out.println();
		}
		
		String pattern = "\\$\\{\\d+(,\\d+)?}";
		System.out.println("${5,05}".matches(pattern) + " " + "${5}".matches(pattern) + " " + "${5,A6}".matches(pattern));
	
		System.out.println(Paths.get("C:\\Users\\Matija\\Desktop").toString());
	}

}
