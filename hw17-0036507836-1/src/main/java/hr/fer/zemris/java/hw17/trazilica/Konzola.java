package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.java.hw17.trazilica.console.Console;
import hr.fer.zemris.java.hw17.trazilica.model.TfIdfDocumentCollection;

/**
 * This is the main class of the trazilica application.
 * @author Matija
 *
 */
public class Konzola {

	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.err.println("Expected exactly one argument!");
			return;
		}
		Path txtFiles = Paths.get(args[0]);
		if(!Files.isDirectory(txtFiles)) {
			System.err.println("Path provided is not a directory!");
			return;
		}
		Path stopWordsFile = Paths.get(Konzola.class.getClassLoader().getResource("res/stop.txt").getPath().substring(1));
		
		TfIdfDocumentCollection documents = new TfIdfDocumentCollection(txtFiles, stopWordsFile);
		Scanner sc = new Scanner(System.in);
		new Console(documents, sc).start();
		sc.close();
		
	}
	
}
