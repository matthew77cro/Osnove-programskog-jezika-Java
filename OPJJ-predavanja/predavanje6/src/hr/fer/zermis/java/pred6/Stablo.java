package hr.fer.zermis.java.pred6;

import java.io.File;

public class Stablo {
	
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Wrong number of arguments!");
			return;
		}
		
		File f = new File(args[0]);
		
		tree(f);
		
	}
	
	public static void tree(File file) {
		
		if(!file.isDirectory()) {
			throw new IllegalArgumentException();
		}
		
		System.out.println(file.getName());
		treeRec(file, 1);
		
	}

	private static void treeRec(File file, int level) {
		File[] children = file.listFiles();
		if(children==null) return;
		
		for(File f : children) {
			
			for(int i=0; i<level; i++) System.out.print("  ");
			System.out.println(f.getName());
			
			if(f.isDirectory()) {
				treeRec(f, level+1);
			}
			
		}
	}

}
