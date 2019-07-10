package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

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

}
