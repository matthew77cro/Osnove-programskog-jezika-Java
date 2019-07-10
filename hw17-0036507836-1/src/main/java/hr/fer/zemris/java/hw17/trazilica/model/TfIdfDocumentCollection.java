package hr.fer.zemris.java.hw17.trazilica.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import hr.fer.zemris.java.hw17.trazilica.math.Vector;

/**
 * This class represents a collection of documents and a tfidf vector representation
 * of those documents (with collection of vocabulary, stop words, documents and idf
 * vectors for each vocabulary word). Additionally, each document has its tf vector.
 * @author Matija
 *
 */
public class TfIdfDocumentCollection {
	
	/**
	 * Vocabulary extracted from documents
	 */
	private List<String> vocabulary;
	
	/**
	 * Stop words
	 */
	private Set<String> stopWords;
	
	/**
	 * List of documents in this collection
	 */
	private List<TfIdfDocument> documents;
	
	/**
	 * List of idf values for each vocabulary word
	 */
	private Map<String, Double> idf;
	
	/**
	 * Initializes the tfidf collection. Searches the file tree begining from the
	 * documentsFolder path.
	 * @param documentsFolder start folder for searching the file tree for documents
	 * @param stopWordFile path to a file that contains stop words
	 */
	public TfIdfDocumentCollection(Path documentsFolder, Path stopWordFile) {
		vocabulary = new ArrayList<String>();
		stopWords = new HashSet<String>();
		documents = new ArrayList<TfIdfDocument>();
		idf = new HashMap<String, Double>();
		
		try {
			init(Objects.requireNonNull(documentsFolder), Objects.requireNonNull(stopWordFile));
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	/**
	 * Initializes the tfidf collection with found documents and their vectors.
	 * @param documentsFolder start folder for searching the file tree for documents
	 * @param stopWordFile path to a file that contains stop words
	 * @throws IOException if an IO exception occurs
	 */
	private void init(Path documentsFolder, Path stopWordFile) throws IOException {
		if(!Files.isDirectory(documentsFolder)) throw new IllegalArgumentException("Given path DIR is not a directory!");
		if(!Files.isRegularFile(stopWordFile)) throw new IllegalArgumentException("Given path STOP is not a regulat file!");
		
		Files.readAllLines(stopWordFile).forEach((str) -> {if(!str.isEmpty()) stopWords.add(str.toLowerCase());});
				
		VocabVisitor visitor = new VocabVisitor();
		Files.walkFileTree(documentsFolder, visitor);
		vocabulary.addAll(visitor.getVocab());
		
		TfVisitor visitor2 = new TfVisitor();
		Files.walkFileTree(documentsFolder, visitor2);
		int numOfDocs = visitor2.getNumOfDocs();
		visitor2.getWordDocFreq().forEach((key, val) -> {
			idf.put(key, Math.log(numOfDocs/val));
		});
		visitor2.getWordPerDocFreq().forEach((pathStr, wordFreq) -> {
			Path p = Paths.get(pathStr);
			double[] components = new double[vocabulary.size()];
			int i=0;
			for(String word : vocabulary) {
				components[i] = wordFreq.getOrDefault(word, 0) * idf.get(word);
				i++;
			}
			Vector vector = new Vector(components);
			documents.add(new TfIdfDocument(p, vector));
		});
		
		vocabulary = Collections.unmodifiableList(vocabulary);
		stopWords = Collections.unmodifiableSet(stopWords);
		documents = Collections.unmodifiableList(documents);
		idf = Collections.unmodifiableMap(idf);
	}
	
	/**
	 * Returns an unmodifiable list of vocabulary
	 * @return an unmodifiable list of vocabulary
	 */
	public List<String> getVocabulary() {
		return vocabulary;
	}
	
	/**
	 * Returns an unmodifiable set of stop words
	 * @return an unmodifiable set of stop words
	 */
	public Set<String> getStopWords() {
		return stopWords;
	}
	
	/**
	 * Returns an unmodifiable list of documents
	 * @return an unmodifiable list of documents
	 */
	public List<TfIdfDocument> getDocuments() {
		return documents;
	}
	
	/**
	 * Returns an unmodifiable map of idf values for each string of vocabulary
	 * @return an unmodifiable map of idf values for each string of vocabulary
	 */
	public Map<String, Double> getIdf() {
		return idf;
	}

	/**
	 * Models a document for the tfidf collection. Each document has its own
	 * tfIdfVector {@link Vector} and the {@link Path} to that document.
	 * @author Matija
	 *
	 */
	public static class TfIdfDocument {
		
		private Vector tfIdfVector;
		private Path document;
		
		/**
		 * Initializes the tfidf document
		 * @param document path to the document
		 * @param tfIdfVector tfidf vector of the document
		 */
		public TfIdfDocument(Path document, Vector tfIdfVector) {
			this.tfIdfVector = Objects.requireNonNull(tfIdfVector);
			this.document = Objects.requireNonNull(document);
		}

		/**
		 * Returns the tfidf vector of the document
		 * @return the tfidf vector of the document
		 */
		public Vector getTfIdfVector() {
			return tfIdfVector;
		}

		/**
		 * Returns the path to the document
		 * @return the path to the document
		 */
		public Path getDocument() {
			return document;
		}
		
		/**
		 * Calculates and returns the similarity index between this document
		 * and other document. Similarity index is calculated as a cosine of
		 * the angle between tfidf vectors of two documents.
		 * @param other other documents for which the similarity index needs to be calculated
		 * @return the similarity index between this document and other document
		 */
		public double similarityIndex(TfIdfDocument other) {
			return other.tfIdfVector.cosAngle(this.tfIdfVector);
		}

		@Override
		public int hashCode() {
			return Objects.hash(document, tfIdfVector);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof TfIdfDocument))
				return false;
			TfIdfDocument other = (TfIdfDocument) obj;
			return Objects.equals(document, other.document) && Objects.equals(tfIdfVector, other.tfIdfVector);
		}
		
	}
	
	/**
	 * This class represents a file visitor for building the vocabulary list
	 * @author Matija
	 *
	 */
	private class VocabVisitor implements FileVisitor<Path> {
		
		private Set<String> vocab = new LinkedHashSet<String>();
		
		/**
		 * Returns the vocabulary as a set of strings
		 * @return the vocabulary as a set of strings
		 */
		public Set<String> getVocab() {
			return vocab;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if(!Files.isRegularFile(file)) return FileVisitResult.CONTINUE;

			BufferedReader buff = Files.newBufferedReader(file);
			
			StringBuilder sb = new StringBuilder();
			
			while(true) {
				int r = buff.read();
				if(Character.isAlphabetic((char)r)) {
					sb.append((char)r);
					continue;
				} else {
					if(r==-1) {
						if(!sb.toString().isEmpty()) vocab.add(sb.toString().toLowerCase());
						break;
					}
					if(sb.toString().isEmpty()) continue;
					String toAdd = sb.toString().toLowerCase();
					if(!stopWords.contains(toAdd)) vocab.add(toAdd);
					sb.delete(0, sb.length());
				}
			}
			
			buff.close();
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}
	
	/**
	 * This class represents a file visitor for building the tfIdf vectors.
	 * @author Matija
	 *
	 */
	private class TfVisitor implements FileVisitor<Path> {
		
		private int numOfDocs = 0;
		private Map<String, Integer> wordDocFreq = new HashMap<String, Integer>(); // word -> #docs
		private Map<String, Map<String, Integer>> wordPerDocFreq = new HashMap<String, Map<String,Integer>>(); // doc -> (word -> #freq)

		/**
		 * Returns the number of documents visited
		 * @return the number of documents visited
		 */
		public int getNumOfDocs() {
			return numOfDocs;
		}
		
		/**
		 * Returns the total count frequency of vocabulary words throughout all files visited
		 * @return the total count frequency of vocabulary words throughout all files visited
		 */
		public Map<String, Integer> getWordDocFreq() {
			return wordDocFreq;
		}
		
		/**
		 * Returns frequency of vocabulary words per file. Keys -> file paths as strings;
		 * Values -> frequency of each word in that file
		 * @return frequency of vocabulary words per file
		 */
		public Map<String, Map<String, Integer>> getWordPerDocFreq() {
			return wordPerDocFreq;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if(!Files.isRegularFile(file)) return FileVisitResult.CONTINUE;

			numOfDocs++;
			Map<String, Integer> termFreq = new HashMap<String, Integer>();
			
			BufferedReader buff = Files.newBufferedReader(file);
			StringBuilder sb = new StringBuilder();
			while(true) {
				int r = buff.read();
				if(Character.isAlphabetic((char)r)) {
					sb.append((char)r);
					continue;
				} else {
					if(r==-1) {
						if(!sb.toString().isEmpty()) termFreq.merge(sb.toString().toLowerCase(), 1, (oldVal, newVal) -> oldVal+1);
						break;
					}
					if(sb.toString().isEmpty()) continue;
					String toAdd = sb.toString().toLowerCase();
					if(!stopWords.contains(toAdd)) termFreq.merge(toAdd, 1, (oldVal, newVal) -> oldVal+1);
					sb.delete(0, sb.length());
				}
			}
			buff.close();
			
			wordPerDocFreq.put(file.toString(), termFreq);
			termFreq.forEach((key, val) -> {
				wordDocFreq.merge(key, 1, (oldVal, newVal) -> oldVal+1);
			});
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}

}
