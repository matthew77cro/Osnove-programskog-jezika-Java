package hr.fer.zemris.java.hw16.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a database of images. This is a singleton class and therefore cannot be
 * initialised. Used method ImageDB.getImageDB() to get one and only instance of this class. <br>
 * <br>
 * Before getting the instance of imageDB for the first time, path to the DB definition file must
 * be set by the ImageDB.setDBPath() method or else getImageDB() method will throw an IOException. <br>
 * <br>
 * Database definition file is a txt file which contains three rows for every entry:<br>
 * 1st row is an image name <br>
 * 2nd row is an image description <br>
 * 3rd row is a list of image tags splitted by the comma <br>
 * @author Matija
 *
 */
public class ImageDB {
	
	private static ImageDB imgdb;
	private static Path db;
	
	private Map<String, Image> database;
	private Set<String> tags;
 
	/**
	 * Creates and initialises an ImageDB instance.
	 */
	private ImageDB() {
		try {
			loadDB();
		} catch (IOException e) {
			throw new IllegalArgumentException("DB path is invalid!");
		}
	}
	
	/**
	 * Returns the one and only instance of this class.
	 * @return the one and only instance of this class.
	 */
	public static ImageDB getImageDB() {
		if(imgdb==null) {
			imgdb = new ImageDB();
		}
		return imgdb;
	}
	
	/**
	 * Sets the path to the database definition file. This method
	 * must be called before getImageDB() method.
	 * @param db path to the definition file
	 */
	public static void setDBPath(Path db) {
		if(imgdb!=null) throw new IllegalStateException("Singleton instance has already been created!");
		ImageDB.db = db;
	}
	
	/**
	 * Private method used for loading the database for the specified file
	 * @throws IOException if an IO exception occurs
	 */
	private void loadDB() throws IOException {
		List<String> dbLines = Files.readAllLines(db);
		database = new HashMap<String, ImageDB.Image>();
		tags = new HashSet<String>();
		
		int i=0;
		while(i<dbLines.size()) {
			String name = dbLines.get(i);
			String desc = dbLines.get(i+1);
			List<String> tags = Arrays.asList(dbLines.get(i+2).split("\\s*,\\s*"));
			
			database.put(name, new Image(name, desc, tags));
			this.tags.addAll(tags);
			i+=3;
		}
		
		tags = Collections.unmodifiableSet(tags);
	}
	
	/**
	 * Returns and image with specified name
	 * @param name name of the image
	 * @return image with the given name
	 */
	public Image getImage(String name) {
		return database.get(name);
	}
	
	/**
	 * Returns a list of images which contain specified tag.
	 * @param tag image tag
	 * @return list of images which contain given tag
	 */
	public List<Image> getImages(String tag) {
		List<Image> list = new ArrayList<ImageDB.Image>();
		for(Image img : database.values()) {
			if(img.getTags().contains(tag)) {
				list.add(img);
			}
		}
		return list;
	}
	
	/**
	 * Returns a set of all available tags
	 * @return a set of all available tags
	 */
	public Set<String> getTags() {
		return tags;
	}
	
	/**
	 * Class represents a model of an ImageDB entry with attributes : name, description and a list of tags.
	 * @author Matija
	 *
	 */
	public static class Image {
		
		private String name;
		private String desc;
		private List<String> tags;
		
		/**
		 * Creates and initialises a new image
		 * @param name name of the image
		 * @param desc description of the image
		 * @param tags image tags
		 */
		public Image(String name, String desc, List<String> tags) {
			this.name = name;
			this.desc = desc;
			this.tags = Collections.unmodifiableList(tags);
		}

		/**
		 * Returns the name of the image
		 * @return the name of the image
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns the description of the image
		 * @return the description of the image
		 */
		public String getDesc() {
			return desc;
		}

		/**
		 * Returns a list of tags for the image
		 * @return a list of tags for the image
		 */
		public List<String> getTags() {
			return tags;
		}
		
	}

}
