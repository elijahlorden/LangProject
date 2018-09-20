package Main;

public class Main {
	public static void main(String[] args) {
		Lexer test = new Lexer("routine main(2) { }");
		while (true) {
			String next = test.nextToken();
			if (next == null) break;
			System.out.println(next);
		}
		
	}
}
