package hr.fer.zemris.java.hw08.shell.parser;

import java.util.List;
import java.util.Objects;

public class FilterResult {

	private String fileName;
	private List<String> groups;
	private int numberOfGroups;
	
	public FilterResult(String fileName, List<String> groups) {
		this.fileName = Objects.requireNonNull(fileName);
		this.groups = Objects.requireNonNull(groups);
		this.numberOfGroups = groups.size();
	}
	
	@Override
	public String toString() {
		return fileName;
	}
	
	public int numberOfGroups() {
		return numberOfGroups;
	}
	
	public String group(int index) {
		if(index<0 || index>=numberOfGroups) {
			throw new IndexOutOfBoundsException();
		}
		
		return groups.get(index);
	}

}
