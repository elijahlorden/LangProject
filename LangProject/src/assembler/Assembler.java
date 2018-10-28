package assembler;

import java.io.IOException;
import java.util.ArrayList;

import assembler.containers.ASMObject;
import assembler.containers.ObjectChunk;
import main.Util;

public class Assembler {
	private ArrayList<String> rawLines;
	private String filename;
	private ASMObject obj;
	
	private String currentLabel;
	
	/***
	 * Creates a new Assembler object
	 * @param filename
	 */
	public Assembler(String filename) {
		try {
			this.rawLines = Util.readFile(filename);
		} catch (IOException e) {
			Util.error("Assembler", "Could not read file " + filename);
		}
		this.filename = filename;
		this.obj = new ASMObject();
	}
	
	private void log(String s) {
		System.out.println("[Assembler] (" + filename + ") " + s);
	}
	
	/***
	 * Compile the code into an object
	 */
	public void compile() {
		log("Start Compile");
		obj.compile();
		log("Compile completed");
		obj.dump();
	}
	
	 /***
	  * Parse the input file into discrete chunks
	  */
	public void parse() {
		log("Start Parse");
		byte section = 0;
		for (int i=0; i<rawLines.size(); i++) {
			String line = rawLines.get(i);
			if (line.length() < 1 || line.charAt(0) == '#') continue;
			line = removeComments(line);
			if (line.length() < 1 || line.charAt(0) == '#') continue;
			String[] lines = line.split("\\s+");
			switch(lines[0]) {
				case ".data": 
					section = 1;
					break;
				case ".text":
					section = 2;
					break;
				case ".import":
					section = 3;
					break;
				case ".export":
					section = 4;
					break;
				default:
					switch(section) {
						case 0:
							Util.error("Assembler", "No section declared", i+1);
							break;
						case 1:
							parseDataLine(line, i+1);
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							break;
						default:
							Util.error("Assembler", "Unknown error", i+1);
							break;
					}
					break;
			}
		}
		log("Parse completed");
	}
	
	/***
	 * Removes comments from the input string
	 * Comments are declared by prefixing them with '#'
	 * @param s The string to be cleared of comments
	 * @return  The input with comments removed
	 */
	private String removeComments(String s) {
		int index = s.indexOf('#');
		if (index != 0) {
			while (index != -1) {
				char c = s.charAt(index-1);
				if (c == '%') { //Allow escaping of # by prefixing it with % (%#)
					s = s.substring(0,index-1) + s.substring(index); //get rid of escaping %
					index = s.indexOf('#', index);
				} else break; //Found comment start
			}
		}
		if (index == 0) return "";
		if (index == -1) return s;
		//System.out.println(s.substring(0,index));
		return s.substring(0,index);
	}
	
	/***
	 * Parses a line from the .data section
	 * @param line the line to be parsed
	 * @param ln the current line number
	 */
	private void parseDataLine(String line, int ln) {
		String[] parts = line.split("\\s+");
		if (parts.length < 3) Util.error("Assembler", "Invalid data declaration", ln);
		if (parts[0].charAt(parts[0].length()-1) != ':') Util.error("Assembler", "Labels must be postfixed by a colon (:)", ln);
		String key = parts[0].substring(0, parts[0].length()-1);
		if (parts[1].charAt(0) != '.') Util.error("Assembler", "Types must be prefixed by a period (.)", ln);
		String type = parts[1].substring(1);
		String ds;
		if (parts.length == 3) {
			ds = parts[2];
		} else {
			ds = "";
			for (int i = 2; i<parts.length; i++) {
				ds += parts[i] + " ";
			}
			ds = ds.substring(0, ds.length()-1);
		}
		if (type.equals("const")) {
			obj.addConstant(parts[1].substring(0, parts[1].length()-1), DataParser.parseConst(parts[2], false, ln), ln); //Signed constant
		} else if (type.equals("_const")) {
			obj.addConstant(parts[1].substring(0, parts[1].length()-1), DataParser.parseConst(parts[2], true, ln), ln); //Unsigned constant
		} else {
			byte[] data = DataParser.translateData(ds, type, ln);
			ObjectChunk dataChunk = new ObjectChunk(key, data);
			obj.addChunk(dataChunk);
			//System.out.println(key);
		}
	}
	
	private void parseTextLine(String line, int ln) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
