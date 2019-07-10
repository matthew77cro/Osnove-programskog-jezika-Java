package hr.fer.zemris.java.hw17.trazilica.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw17.trazilica.math.Vector;
import hr.fer.zemris.java.hw17.trazilica.model.TfIdfDocumentCollection.TfIdfDocument;

public class Test {
	
	public static void main(String[] args) {
		
		Path dir = Paths.get(Test.class.getClassLoader().getResource("res/clanci").getPath().substring(1));
		Path stop = Paths.get(Test.class.getClassLoader().getResource("res/stop.txt").getPath().substring(1));
		
		TfIdfDocumentCollection t = new TfIdfDocumentCollection(dir, stop);
		
		Map<String, Integer> queryWordFreq = new HashMap<String, Integer>();
		queryWordFreq.put("darovit", 1);
		queryWordFreq.put("glumac", 1);
		queryWordFreq.put("zadnje", 1);
		queryWordFreq.put("akademske", 1);
		queryWordFreq.put("klase", 1);
		
		double[] components = new double[t.getVocabulary().size()];
		int i=0;
		for(String word : t.getVocabulary()) {
			components[i] = queryWordFreq.getOrDefault(word, 0) * t.getIdf().get(word);
			i++;
		}
		Vector vector = new Vector(components);
		
		TfIdfDocument queryDoc = new TfIdfDocument(Paths.get("query"), vector);
		
		Map<Double, TfIdfDocument> results = new HashMap<>();
		for(var d : t.getDocuments()) {
			double sim = queryDoc.similarityIndex(d);
			results.put(sim, d);
		}
		
		List<Map.Entry<Double, TfIdfDocument>> resList = new ArrayList<Map.Entry<Double,TfIdfDocument>>(results.entrySet());
		resList.sort((e1, e2) -> e2.getKey().compareTo(e1.getKey()));
		
		System.out.println(resList.get(0).getKey() + " <-> " + resList.get(0).getValue().getDocument().getFileName().toString());
		System.out.println(resList.get(1).getKey() + " <-> " + resList.get(1).getValue().getDocument().getFileName().toString());
		System.out.println(resList.get(2).getKey() + " <-> " + resList.get(2).getValue().getDocument().getFileName().toString());
		
	}

}
