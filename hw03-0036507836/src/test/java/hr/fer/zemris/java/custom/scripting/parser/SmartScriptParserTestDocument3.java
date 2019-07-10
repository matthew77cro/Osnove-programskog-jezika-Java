package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class SmartScriptParserTestDocument3 {
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
				int read = is.read(buffer);
				if(read<1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}

	@Test
	void assertThrowsException() {
		String docBody = new SmartScriptParserTestDocument3().loader("document3.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody)); 
	}

}
