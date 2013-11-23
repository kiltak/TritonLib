/**
 * This class is a debugging helper.  It will dump a byte array into an ascii file.  The format is something
 * that can be read by the staged data utilities.
 * This class doesn't throw exceptions, because it's a debug tool.  It won't be part of the final version.  As
 * if there is ever a final version in software developed for fun...
 */

package controller.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MessageFileDumper {
	public MessageFileDumper (String filename) {
		open (filename);
	}
	
	public boolean open (String filename) {
		try {
			writer = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			return false;
		}
		return true;
	}
	
	public void writeBytes (byte[] bytes) {
		if (writer == null) {
			return;  // should throw exception
		}
		
		for (int i = 0; i < bytes.length; ++i) {
			writer.print (String.format("%02X ", bytes[i]));
			if (i % 20 == 19) {
				writer.println();
			}
		}
		writer.println();
		writer.println();
	}
	
	public void close () {
		writer.close();
	}
	
	private PrintWriter writer = null;
}
