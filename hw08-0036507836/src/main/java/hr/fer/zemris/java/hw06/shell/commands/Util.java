package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw08.shell.parser.FilterResult;

/**
 * Some basic utilities used for the MyShell program. This class contains one
 * static method for splitting the command argument
 * @author Matija
 *
 */
public class Util {
	
	/**
	 * This method splits all arguments of a single command user has entered.
	 * @param argument arguments that user has entered
	 * @return splitted arguments
	 * @throws IllegalArgumentException if string is not closed
	 */
	public static String[] splitArgs(String argument) {
		String argumentTrimmed = argument.trim();
		
		List<String> list = new ArrayList<String>();
		
		char[] strchr = argumentTrimmed.toCharArray();
		StringBuilder sb = new StringBuilder();
		boolean inString = false;
		int i = 0;
		while(i<strchr.length) {
			char c = strchr[i];
			
			if(c=='"') {
				inString = inString ? false : true;
				if(inString) {
					i++;
					continue;
				}
			} 
			
			if((c=='"' && !inString) || (c==' ' && !inString)) {
				list.add(sb.toString());
				sb.delete(0, sb.length());
				i++;
				while(i<strchr.length && strchr[i] == ' ') i++;
				continue;
			}
			
			if(c=='\\' && i+1<strchr.length && (strchr[i+1]=='"' || strchr[i+1]=='\\')) {
				sb.append(strchr[i+1]);
				i+=2;
				continue;
			}
			
			sb.append(c);
			i++;
		}
		
		if(inString) throw new IllegalArgumentException();
		
		if(sb.length()>0) {
			list.add(sb.toString());
		}
		
		return list.toArray(new String[list.size()]);
	}
	
	public static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		if(!Files.isDirectory(dir)) {
			throw new IOException("dir must be a directory!");
		}
		
		List<FilterResult> list = new ArrayList<FilterResult>();
		Files.walk(dir, 1)
			.filter((path) -> !Files.isDirectory(path))
			.forEach((path) -> {
				Pattern patt = Pattern.compile(pattern);
				Matcher matcher = patt.matcher(path.getFileName().toString());
				if(!matcher.matches()) return;
				
				List<String> groups = new ArrayList<String>();
				for(int i=0, end=matcher.groupCount(); i<=end; i++) {
					groups.add(matcher.group(i));
				}
				
				list.add(new FilterResult(path.getFileName().toString(), groups));
			});
		
		return list;
	}

}
