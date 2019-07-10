package hr.fer.zemris.java.hw08.shell.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NameBuilderParser {
	
	private String izraz;
	private NameBuilder builder;

	public NameBuilderParser(String izraz) {
		this.izraz = Objects.requireNonNull(izraz);
		//try {
			parse();
		/*} catch (Exception ex) {
			throw new ParserException(ex.getMessage());
		}*/
	}
	
	private void parse() {
		List<String> splitted = split(izraz);
		List<NameBuilder> builders = new ArrayList<NameBuilder>();
		
		for(String s : splitted) {
			
			if(s.startsWith("$")) {
				String pattern = "\\$\\{\\d+(,\\d+)?}";
				if(!s.matches(pattern)) throw new ParserException("Invalid substituion format. " + s);
				if(s.contains(",")) {
					String[] parts = s.split(",");
					String firstPart = parts[0].substring(2, parts[0].length()); //skip $ and { to get digits before ,
					String secondPart = parts[1].substring(0, parts[1].length()-1); //skip last }
					char padding = ' ';
					if(secondPart.startsWith("0") && secondPart.length()>1) {
						padding = '0';
						secondPart = secondPart.substring(1); //skip that 0 that is used as padding
					}
					builders.add(DefaultNameBuilders.group(Integer.parseInt(firstPart), padding, Integer.parseInt(secondPart)));
				} else {
					builders.add(DefaultNameBuilders.group(Integer.parseInt(s.substring(2, s.length()-1))));
				}
			} else {
				builders.add(DefaultNameBuilders.text(s));
			}
			
		}
		
		builder = new DefaultNameBuilders.NameBuilderCompozit(builders);
	}
	
	private List<String> split(String izraz) {
		List<String> list = new ArrayList<String>();
		
		char[] strchr = izraz.toCharArray();
		int index = 0;
		
		StringBuilder sb = new StringBuilder();
		while(index<strchr.length) {
			if(strchr[index] != '$') {
				sb.append(strchr[index]);
				index++;
				continue;
			}
			
			list.add(sb.toString());
			sb = new StringBuilder();
			sb.append(strchr[index]);
			if(strchr[++index]!='{') throw new ParserException("Invalid substituion format.");
			sb.append(strchr[index]);
			
			while(strchr[++index] != '}') {
				sb.append(strchr[index]);
			}
			sb.append(strchr[index]);
			
			list.add(sb.toString());
			sb = new StringBuilder();
			index++;
		}
		list.add(sb.toString());
		
		return list;
	}

	public NameBuilder getNameBuilder() {
		return builder;
	}
	
}
