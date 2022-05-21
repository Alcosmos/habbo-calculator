package net.alcosmos.calc;

import net.alcosmos.calc.controller.CalculatorController;
import net.alcosmos.calc.controller.KeyboardPress;
import net.alcosmos.calc.drawer.CalculatorDrawer;
import net.alcosmos.calc.drawer.Frame;
import net.alcosmos.calc.processor.Processor;
import javafx.application.Application;
import javafx.stage.*;

import java.io.IOException;

public class CalcApplication extends Application {
	private static CalculatorController calcController;
	private static Processor processor;
	
	@Override
	public void start(Stage stage) throws IOException {
		System.out.println(System.getProperty("user.dir"));
		
		CalculatorDrawer calcDrawer = new CalculatorDrawer();
		
		Frame frame = new Frame(stage);
		frame.setTitle("Calculator");
		frame.setContent(calcDrawer.draw());
		frame.addStyle("calc-style.css");
		
		KeyboardPress keyboardPress = new KeyboardPress(frame.getScene());
		keyboardPress.start();
		
		calcController = (CalculatorController)calcDrawer.getController();
	}
	
	public static void main(String[] args) {
		/*
		 * This might make fonts uglier in Windows,
		 * but that is a sacrifice I am willing to make
		 */
		System.setProperty("prism.lcdtext", "false");
		System.setProperty("prism.text", "t2k");
		
		processor = new Processor();
		
		launch();
	}
	
	public static CalculatorController getCalculatorController() {
		return calcController;
	}
	
	public static Processor getProcessor() {
		return processor;
	}
}
