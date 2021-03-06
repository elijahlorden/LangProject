package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;

public class Util {
	
	private final static String[] hexMapping = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	
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
	 * Prints a warning message (concatenation of s1 + s2)
	 * @param s1
	 * @param s2
	 */
	public static void warn(String s1, String s2) {
		System.out.println("[" + s1 + " WARNING] " + s2);
	}
	
	/**
	 * Prints an error message (concatenation of s1 + s2) and exits the program
	 * @param s1
	 * @param s2
	 */
	public static void error(String s1, String s2) {
		System.out.println("[" + s1 + " ERROR] " + s2);
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
		System.out.println("[" + s1 + " ERROR] (line "+ line + ") " + s2);
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
	
	public static void printArray(boolean[] array) {
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
	
	public static byte[] concatArrays(byte[] arr1, byte[] arr2, byte[] arr3) {
		return concatArrays(arr1, concatArrays(arr2, arr3));
	}
	
	/***
	 * Get the binary string representation of a single byte
	 * Uses little endian formatting
	 * @param b the byte to translate
	 * @return the byte translated into a binary string
	 */
	public static String byteToBinaryString(byte b) {
		String s = "";
		for (int i=0; i<8; i++) {
			s = s + ((((b >>> (7-i)) & 1) > 0) ? "1" : "0");
		}
		return s;
	}
	
	/***
	 * Get the binary string representation of a byte array
	 * Octets are delimited by a space
	 * Bytes are translated in little endian format
	 * @param arr byte array to be translated
	 * @return the byte array translated into a binary string
	 */
	public static String byteArrayToBinaryString(byte[] arr) {
		String s = "";
		for (int i=0; i<arr.length; i++) {
			s = s + byteToBinaryString(arr[i]) + " ";
		}
		return s;
	}
	
	/***
	 * Get the hexadecimal string representation of a single byte
	 * @param b the byte to translate
	 * @return the byte translated into a hexadecimal string
	 */
	public static String byteToHexString(byte b) {
		int q1 = (int) (b & 0x0F);
		int q2 = (int) ((b >>> 4) & 0x0F);
		return hexMapping[q2] + hexMapping[q1];
	}
	
	/***
	 * Get the hexadecimal string representation of a byte array
	 * @param arr the array to be translated
	 * @param columns the number of bytes per line
	 * @return the array translated into a hexadecimal string
	 */
	public static String byteArrayToHexString(byte[] arr, int columns) {
		columns = (columns >= 1) ? columns : 1;
		String s = "";
		for (int i=0; i<arr.length; i++) {
			s = s + byteToHexString(arr[i]);
			s = s + (((i+1) % columns == 0) ? "\n" : " ");
		}
		return s;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
