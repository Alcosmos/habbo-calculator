package net.alcosmos.calc.controller;

import net.alcosmos.calc.CalcApplication;
import net.alcosmos.calc.processor.InputType;
import net.alcosmos.calc.processor.Processor;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class KeyboardPress {
	private Scene scene;
	private Processor processor;
	
	public KeyboardPress(Scene scene) {
		this.scene = scene;
		
		processor = CalcApplication.getProcessor();
	}
	
	public void start() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			switch (event.getCode()) {
				case DIGIT0, NUMPAD0 -> processor.addInput(InputType.NUM0);
				case DIGIT1, NUMPAD1 -> processor.addInput(InputType.NUM1);
				case DIGIT2, NUMPAD2 -> processor.addInput(InputType.NUM2);
				case DIGIT3, NUMPAD3 -> processor.addInput(InputType.NUM3);
				case DIGIT4, NUMPAD4 -> processor.addInput(InputType.NUM4);
				case DIGIT5, NUMPAD5 -> processor.addInput(InputType.NUM5);
				// Powers in the English keyboard way because Spanish one is poorly supported by JavaFX and that key says 'undefined'
				case DIGIT6, NUMPAD6 -> { if (event.isShiftDown()) { processor.addInput(InputType.POWER); } else { processor.addInput(InputType.NUM6); } }
				case DIGIT7, NUMPAD7 -> { if (event.isShiftDown()) { processor.addInput(InputType.DIV); } else { processor.addInput(InputType.NUM7); } }
				// If shift is down, parenthesis. If not, numbers
				case DIGIT8, NUMPAD8 -> { if (event.isShiftDown()) { processor.addInput(InputType.OPEN); } else { processor.addInput(InputType.NUM8); } }
				case DIGIT9, NUMPAD9 -> { if (event.isShiftDown()) { processor.addInput(InputType.CLOSE); } else { processor.addInput(InputType.NUM9); } }
				case PERIOD, COMMA, SEPARATOR -> processor.addInput(InputType.COMMA);
				
				case LEFT_PARENTHESIS -> processor.addInput(InputType.OPEN);
				case RIGHT_PARENTHESIS -> processor.addInput(InputType.CLOSE);
				
				case EQUALS, ENTER -> processor.addInput(InputType.EQUALS);
				case PLUS, ADD -> processor.addInput(InputType.PLUS);
				case MINUS, SUBTRACT -> processor.addInput(InputType.MINUS);
				case X, ASTERISK, MULTIPLY -> processor.addInput(InputType.MULT);
				case SLASH, DIVIDE -> processor.addInput(InputType.DIV);
				case DEAD_CIRCUMFLEX -> processor.addInput(InputType.POWER);
				
				case BACK_SPACE -> processor.delete();
				case DELETE -> processor.clear();
			}
		});
	}
}
