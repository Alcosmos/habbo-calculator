package net.alcosmos.calc.processor;

import net.alcosmos.calc.CalcApplication;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Processor {
	public static final String TEXT_CUT = "[...]";
	
	private List<InputType> inputList;
	private List<String> historyList;
	private String oldNumber;
	private String currentNumber;
	
	private DecimalFormat df;
	
	public Processor() {
		inputList = new ArrayList<>();
		
		df = new DecimalFormat("#");
	}
	
	public void addInput(InputType input) {
		// First we check if its a valid input right now, and how to add it
		if (inputList.size() == 0) {
			if ((input.isNumber() && input != InputType.CLOSE) || input == InputType.MINUS) {
				inputList.add(input);
			}
		} else {
			if (!getLast().isNumber()) {
				if (input == InputType.ROOT) {
					inputList.add(input);
				} else if (!input.isNumber()) {
					if (input != InputType.MINUS) {
						setLast(input);
					} else if (getLast() != InputType.MINUS) {
						inputList.add(input);
					}
				} else if (getLast() == InputType.EQUALS) {
					setLast(InputType.MULT);
					inputList.add(input);
				} else {
					inputList.add(input);
				}
			} else if (input == InputType.OPEN) {
				// Adding 'x' before parenthesis if last is a number
				inputList.add(InputType.MULT);
				inputList.add(input);
			} else if (input != InputType.ROOT) {
				if (getLast() == InputType.ROOT) {
					if (inputList.size() >= 1) {
						if (inputList.get(0).isNumber()) {
							inputList.add(input);
						}
					}
				} else if (input == InputType.CLOSE) {
					if (getDepth() > 0) {
						inputList.add(input);
					}
				} else {
					inputList.add(input);
				}
			}
		}
		
		process(preProcess());
	}
	
	public void delete() {
		if (inputList.size() != 0) {
			inputList.remove(inputList.size() - 1);
			process(preProcess());
		}
		
//		if (inputList.size() > 0) {
//			inputList.remove(inputList.size() - 1);
//		}
//		
//		if (currentNumber != null) {
//			if (currentNumber.length() > 0) {
//				currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
//			}
//			
//			setTopText(oldNumber);
//			setBottomText(currentNumber);
//		}
	}
	
	public void clear() {
		inputList = new ArrayList<>();
		historyList = new ArrayList<>();
		
		oldNumber = "";
		currentNumber = "";
		
		setTopText("");
		setBottomText("");
	}
	
	public List<String> getHistory() {
		return historyList;
	}
	
	public void copyResult() {
		if (oldNumber != null) {
			if (!oldNumber.isBlank()) {
				ClipboardContent content = new ClipboardContent();
				content.putString(oldNumber);
				
				Clipboard clipboard = Clipboard.getSystemClipboard();
				clipboard.setContent(content);
			}
		}
	}
	
	private InputType getLast() {
		return inputList.get(inputList.size()-1);
	}
	
	private void setLast(InputType input) {
		inputList.set(inputList.size() - 1, input);
	}
	
	private int getDepth() {
		int depth = 0;
		
		for (InputType input : inputList) {
			if (input == InputType.OPEN) {
				depth++;
			} else if (input == InputType.CLOSE) {
				depth--;
			}
		}
		
		return depth;
	}
	
	/**
	 * Does the parenthesis first
	 * @return
	 */
	private List<InputType> preProcess() {
		int depth = 0;
		
		int startPos = -1;
		int endPos = -1;
		
		for (int i = 0; i < Math.exp(inputList.size()); i++) {
			startPos = -1;
			endPos = -1;
			
			// Processing content between deepest parenthesis
			for (int j = 0; j < inputList.size(); j++) {
				if (inputList.get(j) == InputType.OPEN) {
					startPos = j;
					depth++;
				}
				
				if (inputList.get(j) == InputType.CLOSE) {
					endPos = j;
					depth--;
				}
				
				// Did we get a closed flat parenthesis
				if (startPos != -1 && endPos != -1) {
					if (getNextOpen(startPos) > endPos) {
						List<InputType> startList = cutList(inputList, 0, startPos);
						List<InputType> endList = cutList(inputList, endPos, inputList.size() - 1);
						
						List<InputType> processingList = cutList(inputList, startPos + 1, endPos);
						processingList.add(InputType.PLUS);
						
						// Parenthesis closed right after opening it? Lets delete it
						if (endPos != startPos + 1) {
							double result = process(processingList);
							
							inputList = joinLists(startList, doubleToInput(result), endList);
						} else {
							inputList = startList;
						}
					}
				}
			}
			
			// No parenthesis
			if (startPos == -1 && endPos == -1) {
				return inputList;
			}
			
			// Uneven parenthesis count
			if (depth != 0) {
				return inputList;
			}
		}
		
		return inputList;
	}
	
	/**
	 * Returns next open parenthesis, or Integer.MAX_VALUE
	 * @param pos
	 * @return
	 */
	private int getNextOpen(int pos) {
		for (int i = pos + 1; i < inputList.size(); i++) {
			if (inputList.get(i) == InputType.OPEN) {
				return i;
			}
		}
		
		return Integer.MAX_VALUE;
	}
	
	/**
	 * Cuts a list
	 * @param originList
	 * @param startPos
	 * @param endPos
	 * @return
	 */
	private List<InputType> cutList(List<InputType> originList, int startPos, int endPos) {
		List<InputType> newList = new ArrayList<>();
		
		for (int i = startPos; i < endPos; i++) {
			newList.add(originList.get(i));
		}
		
		return newList;
	}
	
	/**
	 * Joins multiple lists
	 * @param originList
	 * @return
	 */
	private List<InputType> joinLists(List<InputType> ...originList) {
		List<InputType> newList = new ArrayList<>();
		
		for (List<InputType> thisList : originList) {
			for (InputType input : thisList) {
				newList.add(input);
			}
		}
		
		return newList;
	}
	
	/**
	 * Makes an input list with a double
	 * @param thisDouble
	 * @return
	 */
	private List<InputType> doubleToInput(double thisDouble) {
		List<InputType> inputList = new ArrayList<>();
		
		for (char number : String.valueOf(thisDouble).toCharArray()) {
			inputList.add(switch (number) {
				case '-' -> InputType.MINUS;
				case '.' -> InputType.COMMA;
				case '1' -> InputType.NUM1;
				case '2' -> InputType.NUM2;
				case '3' -> InputType.NUM3;
				case '4' -> InputType.NUM4;
				case '5' -> InputType.NUM5;
				case '6' -> InputType.NUM6;
				case '7' -> InputType.NUM7;
				case '8' -> InputType.NUM8;
				case '9' -> InputType.NUM9;
				default -> InputType.NUM0;
			});
		}
		
		return inputList;
	}
	
	/**
	 * Makes a double with an input list
	 * @param inputList
	 * @return
	 * @throws Exception
	 */
	private double inputToDouble(List<InputType> inputList) throws Exception {
		String output = "";
		
		for (InputType input : inputList) {
			output += input;
		}
		
		try {
			return Double.parseDouble(output);
		} catch (Exception ex) {
			try {
				return Double.parseDouble(output.substring(0, output.length() - 1));
			} catch (Exception ex2) {
				throw ex2;
			}
		}
	}
	
	/**
	 * Operates over a list if it doesnt find parenthesis, in which case it goes into read-only mode and just prints the result
	 * @param inputList
	 * @return Result of operations
	 */
	private double process(List<InputType> inputList) {
		try {
			historyList = new ArrayList<>();
			
			oldNumber = "";
			currentNumber = "";
			
			InputType oldOp = null;
			
			double result = 0;
			boolean readOnly = false;
			
			for (InputType input : inputList) {
				if (readOnly) {
					oldNumber += input;
				} else if (input.isParenthesis()) {
					readOnly = true;
					
					if (oldOp != null) {
						oldNumber += oldOp;
					}
					
					oldNumber += input;
				} else if (input.isNumber()) {
					currentNumber += input;
				} else if (input == InputType.MINUS && currentNumber.length() == 0) {
					currentNumber += input;
				} else {
					if (!oldNumber.isBlank()) {
						double oldNumberDouble = processRoot(oldNumber);
						
						double currentNumberDouble = processRoot(currentNumber);
						
						result = switch (oldOp) {
							case PLUS -> oldNumberDouble + currentNumberDouble;
							case MINUS -> oldNumberDouble - currentNumberDouble;
							case MULT -> oldNumberDouble * currentNumberDouble;
							case DIV -> oldNumberDouble / currentNumberDouble;
							case POWER -> Math.pow(oldNumberDouble, currentNumberDouble);
							default -> 0;
						};
						
						// Adding to history
						historyList.add(oldNumber + " " + oldOp + " " + currentNumber
								+ " " + InputType.EQUALS_SYMBOL + " " + result);
						
						// Operate over result now
						oldNumber = String.valueOf(result);
					} else if (!currentNumber.isBlank()) {
						if (currentNumber.charAt(0) == InputType.ROOT_SYMBOL) {
							
							oldNumber = String.valueOf(processRoot(currentNumber));
						} else {
							oldNumber = currentNumber;
						}
					} else {
						// First operation will always have oldNumber empty
						oldNumber = currentNumber;
					}
					
					currentNumber = "";
					oldOp = input;
				}
			}
			
			// Removing trailing zeros
			try {
				oldNumber = BigDecimal.valueOf(Double.valueOf(oldNumber)).stripTrailingZeros().toString();
			} catch (Exception ignored) {
				// Exception? Not just a number then, so nothing to do
			}
			
			if (oldOp != null) {
				if (!readOnly) {
					if (oldOp == InputType.EQUALS) {
						setTopText(oldOp + oldNumber);
					} else {
						setTopText(oldNumber + oldOp);
					}
				} else {
					setTopText(oldNumber);
				}
			} else {
				setTopText(oldNumber);
			}
			
			setBottomText(currentNumber);
			
			try {
				return inputToDouble(inputList);
			} catch (Exception ignored) {
				return result;
			}
		} catch (Exception ignored) {
			// No exceptions found, but with coding you never know for sure
			setTopText("ERROR");
			setBottomText("");
			
			return 0;
		}
	}
	
	/**
	 * Checks if its a root number and calculates it if it is
	 * @param number
	 * @return Result
	 */
	private double processRoot(String number) {
		if (!number.isEmpty()) {
			if (number.charAt(0) == InputType.ROOT_SYMBOL) {
				String subString = number.substring(1);
				double result;
				
				if (subString.isEmpty() && currentNumber.charAt(0) != '√') {
					result = Math.sqrt(Double.parseDouble(currentNumber));
				} else if (subString.isEmpty()) {
					result = Math.sqrt(Double.parseDouble(oldNumber));
				} else {
					result = Math.sqrt(Double.parseDouble(subString));
				}
				
				historyList.add(number + " " + InputType.EQUALS + " " + result);
				
				return result;
			} else {
				return Double.valueOf(number);
			}
		} else {
			return 0;
		}
	}
	
	private void setTopText(String topText) {
		if (topText.length() > 25) {
			topText = TEXT_CUT + topText.substring(topText.length() - 23, topText.length() - 1);
			//topText = topText.substring(0, 22) + "[...]" + topText.charAt(topText.length() - 1);
		}
		
		CalcApplication.getCalculatorController().setTopText(topText);
	}
	
	private void setBottomText(String bottomText) {
		if (bottomText.length() > 25) {
			bottomText = TEXT_CUT + bottomText.substring(bottomText.length() - 23, bottomText.length() - 1);
			
//			if (!getLast().isNumber()) {
//				bottomText += getLast();
//			}
		}
		
		CalcApplication.getCalculatorController().setBottomText(bottomText);
	}
}
