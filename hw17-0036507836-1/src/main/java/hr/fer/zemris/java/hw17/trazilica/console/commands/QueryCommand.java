package hr.fer.zemris.java.hw17.trazilica.console.commands;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw17.trazilica.console.CommandResult;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleCommand;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleContext;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleContext.ConsoleResult;
import hr.fer.zemris.java.hw17.trazilica.math.Vector;
import hr.fer.zemris.java.hw17.trazilica.model.TfIdfDocumentCollection;
import hr.fer.zemris.java.hw17.trazilica.model.TfIdfDocumentCollection.TfIdfDocument;

/**
 * Command which queries the documents with a specific query and prints
 * results to the screen.
 * @author Matija
 *
 */
public class QueryCommand implements ConsoleCommand {

	@Override
	public CommandResult execute(String parameters, TfIdfDocumentCollection documents, ConsoleContext context) {
		
		List<String> query = new ArrayList<String>();
		for(String word : parameters.split("\\s+")) {
			if(documents.getStopWords().contains(word)) continue;
			query.add(word);
		}
		
		//Print query
		System.out.print("Query is: [");
		boolean first = true;
		for(String word : query) {
			if(first) {
				first = false;
			} else {
				System.out.print(", ");
			}
			System.out.print(word);
		}
		System.out.println("]");
		
		//Get query word freq
		Map<String, Integer> queryWordFreq = new HashMap<String, Integer>();
		for(String word : query) {
			queryWordFreq.merge(word, 1, (oldVal, newVal) -> oldVal+1);
		}
		
		//build a tfidf vector
		double[] components = new double[documents.getVocabulary().size()];
		int i=0;
		for(String word : documents.getVocabulary()) {
			components[i] = queryWordFreq.getOrDefault(word, 0) * documents.getIdf().get(word);
			i++;
		}
		Vector vector = new Vector(components);
		
		TfIdfDocument queryDoc = new TfIdfDocument(Paths.get("query"), vector);
		
		//calculate doc similarity
		Map<Double, TfIdfDocument> results = new HashMap<>();
		for(var d : documents.getDocuments()) {
			double sim = queryDoc.similarityIndex(d);
			results.put(sim, d);
		}
		
		//sort results by similarity index
		List<Map.Entry<Double, TfIdfDocument>> resList = new ArrayList<Map.Entry<Double,TfIdfDocument>>(results.entrySet());
		resList.sort((e1, e2) -> e2.getKey().compareTo(e1.getKey()));
		
		//store results as ConsoleResult list
		List<ConsoleResult> consoleResults = new ArrayList<>();
		i=0;
		for(var e : resList) {
			if(e.getKey()==0.0) break;
			consoleResults.add(new ConsoleResult(i, e.getKey(), e.getValue().getDocument()));
			i++;
		}
		context.storeResults(consoleResults);
		
		//print results
		System.out.println("Najboljih 10 rezultata:");
		for(ConsoleResult res : consoleResults) {
			System.out.printf("[%2d] (%.4f) %s%n", res.getId(), res.getSimilarityIndex(), res.getPath());
		}
		System.out.println();
		
		return CommandResult.CONTINUE;
	}

}
