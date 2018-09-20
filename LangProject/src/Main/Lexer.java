package Main;

public class Lexer {
	
	private String commentStart = "/*";
	private String commentEnd = "*/";
	
	private String in;
	private int line;
	private int pos;
	
	public Lexer(String in) {
		this.in = in;
		this.line = 1;
		this.pos = 0;
	}
	
	public String nextToken() {
		boolean isComment = false;
		String token = "";
		char c;
		while (pos < in.length()) {
			c = in.charAt(pos);
			pos++;
			if (c == '\n') line++; //Increment line count
			if ((Character.isWhitespace(c) || pos == in.length()) && !isComment) {
				if (pos == in.length() && !Character.isWhitespace(c)) token += c;
				if (token.isEmpty()) continue;
				return token;
			} else {
				token += c;
				if (isComment && token.length() > 2) token = token.substring(1);
			}
			if (token.equals(commentStart)) {
				isComment = true;
				token = "";
			}
			if (token.equals(commentEnd)) {
				isComment = false;
				token = "";
			}
			//System.out.println("Token: " + token);
		}
		return null;
	}
	
	public String getTokenType(String token) {
		if (token.matches("\\w*\\((\\w*)\\)")) {
			return "routineparams";
		} else if (token.equals("{")) {
			return "blockopen";
		} else if (token.equals("}")) {
			return "blockclose";
		} else if (token.matches("")) {
			return "expression";
		}
		return "keyword";
	}
	
	
	
}
