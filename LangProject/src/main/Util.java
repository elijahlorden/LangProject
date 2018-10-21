package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {
	
	public static ArrayList<String> readFile(String path) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String data;
		FileInputStream in = new FileInputStream(new File(path));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while ((data = reader.readLine()) != null) {
			lines.add(data);
		}
		reader.close();
		return lines;
	}
	
	public static void error(String s1, String s2) {
		System.out.println("[" + s1 + "] " + s2);
		Scanner s = new Scanner(System.in);
		s.nextByte();
		s.close();
		System.exit(0);
	}
	
	public static void error(String s1,String s2, int line) {
		System.out.println("[" + s1 + "] (line "+ line + ") " + s2);
		Scanner s = new Scanner(System.in);
		s.nextByte();
		s.close();
		System.exit(0);
	}
	
	public static void printArray(Object[] array) {
		for (int i=0;i<array.length;i++) {
			System.out.println(array[i]);
		}
	}
	
	
}
