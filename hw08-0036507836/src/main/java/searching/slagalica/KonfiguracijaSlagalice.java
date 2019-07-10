package searching.slagalica;

import java.util.Arrays;

public class KonfiguracijaSlagalice {
	
	private int[] polje;

	public KonfiguracijaSlagalice(int[] polje) {
		this.polje = polje;
	}
	
	public int[] getPolje() {
		return Arrays.copyOf(polje, polje.length);
	}
	
	public int indexOfSpace() {
		int index;
		for(index=0; polje[index]!=0; index++);
		return index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<polje.length; i++) {
			if(polje[i] == 0) sb.append("* ");
			else sb.append(polje[i] + " ");
			if((i+1)%3==0 && i!=8) sb.append(String.format("%n"));
		}
		
		return sb.toString();
	}

}
