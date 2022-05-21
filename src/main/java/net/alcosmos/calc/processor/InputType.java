package net.alcosmos.calc.processor;

public enum InputType {
	NUM0('0'),
	NUM1('1'),
	NUM2('2'),
	NUM3('3'),
	NUM4('4'),
	NUM5('5'),
	NUM6('6'),
	NUM7('7'),
	NUM8('8'),
	NUM9('9'),
	COMMA('.'),
	OPEN('('),
	CLOSE(')'),
	EQUALS('='),
	PLUS('+'),
	MINUS('-'),
	MULT('x'),
	DIV('/'),
	POWER('^'),
	ROOT('√');
	
	public static final char ROOT_SYMBOL = '√';
	public static final char EQUALS_SYMBOL = '=';
	
	private char visual;
	
	InputType(char visual) {
		this.visual = visual;
	}
	
	public char getVisual() {
		return visual;
	}
	
	@Override
	public String toString() {
		return String.valueOf(visual);
	}
	
	public boolean isNumber() {
		switch (this) {
			case NUM0:
			case NUM1:
			case NUM2:
			case NUM3:
			case NUM4:
			case NUM5:
			case NUM6:
			case NUM7:
			case NUM8:
			case NUM9:
			case COMMA:
			// Parenthesis are also numbers because we will preprocess the list
			case OPEN:
			case CLOSE:
			// So are roots because we dont operate with them
			case ROOT:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isParenthesis() {
		return this == OPEN || this == CLOSE;
	}
}
