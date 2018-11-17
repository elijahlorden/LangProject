package main;

import java.io.File;
import java.util.ArrayList;

import assembler.Assembler;
import assembler.containers.ASMObject;

public class ProjectBuilder {
	
	private static void log(String s) {
		System.out.println("[ProjectBuilder] " + s);
	}
	
	public static void build(String path) {
		File folder = new File(path);
		if (!folder.isDirectory()) Util.error("ProjectBuilder", "'" + path + "' is not a directory");
		ArrayList<ASMObject> objects = getObjects(folder);
		
		
		
		
	}
	
	private static ArrayList<ASMObject> getObjects(File folder) {
		log("Start Assemble");
		ArrayList<ASMObject> objects = new ArrayList<ASMObject>();
		for (File f : folder.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".asm")) {
				Assembler asm = new Assembler(f.getAbsolutePath());
				asm.parse();
				asm.compile();
				objects.add(asm.getObject());
			}
		}
		log("Assemble complete");
		return objects;
	}
	
	
}
