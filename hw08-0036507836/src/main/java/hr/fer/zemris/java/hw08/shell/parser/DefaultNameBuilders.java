package hr.fer.zemris.java.hw08.shell.parser;

import java.util.List;

public class DefaultNameBuilders {

	public static NameBuilder text(String t) { 
		return (result, sb) -> sb.append(t); 
	}
	
	public static NameBuilder group(int index) { 
		return (result, sb) -> sb.append(result.group(index)); 
	}
	
	public static NameBuilder group(int index, char padding, int minWidth) { 
		return (result, sb) -> {
			String s = result.group(index);
			int paddingWidth = minWidth-s.length() > 0 ? minWidth-s.length() : 0;
			sb.append(Character.toString(padding).repeat(paddingWidth) + s);
		};
	}
	
	public static class NameBuilderCompozit implements NameBuilder {
		
		private List<NameBuilder> builders;
		
		public NameBuilderCompozit(List<NameBuilder> builders) {
			this.builders = builders;
		}

		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			builders.forEach((nb) -> nb.execute(result, sb));
		}
		
	}

}
