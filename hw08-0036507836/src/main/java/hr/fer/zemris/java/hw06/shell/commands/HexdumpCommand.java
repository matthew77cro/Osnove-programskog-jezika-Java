package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell which writes content of a file to a user in a hex format.
 * @author Matija
 *
 */
public class HexdumpCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public HexdumpCommand() {
		if(desc!=null) return;
		List<String> tmp = new ArrayList<String>();
		tmp.add("The hexdump command expects a single argument: file name, and produces hex-output.");
		desc = Collections.unmodifiableList(tmp);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] args;
		
		try {
			args = Util.splitArgs(arguments);
		} catch(IllegalArgumentException ex) {
			env.writeln("String is not closed!");
			return ShellStatus.CONTINUE;
		}
		
		if(arguments.length()==0 || args.length!=1) {
			env.writeln("Command takes one arguments.");
			return ShellStatus.CONTINUE;
		}
		 
		BufferedInputStream in;
		Path p = env.getCurrentDirectory().resolve(Paths.get(args[0]));
		try {
			in = new BufferedInputStream(Files.newInputStream(p));
			
			byte[] line = new byte[16];
			
			int lineNumber = 0;
			boolean hasMoreChars = true;
			while(hasMoreChars) {
				env.write(hexLineNumber(lineNumber));
				env.write(":");
				
				int r = in.read(line, 0, 16);
				if(r<1) break;
				
				hasMoreChars = r==line.length;
				
				for(int i=0; i<line.length; i++) {
					if(i==line.length/2) env.write("|");
					if(i<line.length/2) env.write(" ");
					if(i<r) {
						env.write(hexNumber(line[i]));
					} else {
						env.write("  ");
					}
					if(i>=line.length/2) env.write(" ");
				}
				
				env.write(" | ");
				
				env.write(byteToString(line, r));
				
				env.writeln("");
				
				lineNumber+=16;
			}
			
			in.close();
		} catch (IOException | InvalidPathException e) {
			env.writeln("Error reading file.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}
	
	private String byteToString(byte[] byteArr, int len) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = len; i>0; i--) {
			byte b = byteArr[len-i];
			if(b<32 || b>127) {
				sb.append(".");
				continue;
			}
			sb.append((char)b);
		}
		
		return sb.toString();
	}
	
	public String hexNumber(byte number) {
		byte lowerEnd = (byte) (number & 0x0F);
		byte upperEnd = (byte) ((number >> 4) & 0x0F);
		
		String hex = Character.toString(decToHex(upperEnd)) + decToHex(lowerEnd);
		
		return "0".repeat(2-hex.length()) + hex.toUpperCase();
	}
	
	private static char decToHex(byte dec) {
		if(dec<0 || dec>15) throw new IllegalArgumentException();
		
		if(dec>=0 && dec<=9) return Byte.toString(dec).charAt(0);
		
		char value;
		switch(dec) {
			case 10 :
				value = 'a';
				break;
			case 11 :
				value = 'b';
				break;
			case 12 :
				value = 'c';
				break;
			case 13 :
				value = 'd';
				break;
			case 14 :
				value = 'e';
				break;
			case 15 :
				value = 'f';
				break;
			default :
				throw new IllegalArgumentException();
		}
		return value;
		
	}
	
	private String hexLineNumber(int lineNumber) {
		String intHex = Integer.toHexString(lineNumber);
		return "0".repeat(8-intHex.length()) + intHex;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
