package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Util {
	
	/***
	 * Attempts to read the given file into a list of lines
	 * @param path path of the file to be read
	 * @return list of lines read from the file
	 * @throws IOException
	 */
	public static ArrayList<String> readFile(String path) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String data;
		FileInputStream in = new FileInputStream(new File(path));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while ((data = reader.readLine()) != null) {
			lines.add(data);
		}
		in.close();
		reader.close();
		return lines;
	}
	
	/**
	 * Prints an error message (concatenation of s1 + s2) and exits the program
	 * @param s1
	 * @param s2
	 */
	public static void error(String s1, String s2) {
		System.out.println("[" + s1 + "] " + s2);
		Scanner s = new Scanner(System.in);
		s.nextByte();
		s.close();
		System.exit(0);
	}
	
	/**
	 * Prints an error message (concatenation of s1 + s2 + ln) and exits the program
	 * @param s1
	 * @param s2
	 */
	public static void error(String s1,String s2, int line) {
		System.out.println("[" + s1 + "] (line "+ line + ") " + s2);
		Scanner s = new Scanner(System.in);
		s.nextByte();
		s.close();
		System.exit(0);
	}
	
	/**
	 * Prints the contents of an array
	 * @param array
	 */
	public static void printArray(byte[] array) {
		for (int i=0;i<array.length;i++) {
			System.out.println(array[i]);
		}
	}
	
	/***
	 * Concatenates two arrays
	 * @param arr1
	 * @param arr2
	 * @return The concatenation of arr1 + arr2
	 */
	public static byte[] concatArrays(byte[] arr1, byte[] arr2) {
		byte[] res = Arrays.copyOf(arr1, arr1.length + arr2.length);
		System.arraycopy(arr2, 0, res, arr1.length, arr2.length);
		return res;
	}
	
	
	
	
	
	
	
}
