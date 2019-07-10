package hr.fer.zermis.java.pred6;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class StabloNajbolje {
	
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Wrong number of arguments!");
			return;
		}
		
		Path path = Paths.get(args[0]);
		
		try {
			Files.walkFileTree(path, new IspisStabla());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private static class IspisStabla implements FileVisitor<Path> {
		
		private int level;

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			System.out.print("  ".repeat(level));
			System.out.println(dir.getFileName());
			level++;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			System.out.print("  ".repeat(level));
			System.out.println(file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}

}
