package hr.fer.zermis.java.pred6;

import java.io.File;

public class StabloBolje {
	
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Wrong number of arguments!");
			return;
		}
		
		File f = new File(args[0]);
		
		Obilazak.obidi(f, new IspisStabla());
		
	}
	
	private static class IspisStabla extends DefaultObrada {
		
		private int level;

		@Override
		public void ulazimUDirektorij(File dir) {
			System.out.print("  ".repeat(level));
			System.out.println(dir.getName());
			level++;
		}

		@Override
		public void izlazimIzDirektorija(File dir) {
			level--;
		}

		@Override
		public void imamDatoteku(File file) {
			System.out.print("  ".repeat(level));
			System.out.println(file.getName());
		}
		
	}

}
