package net.alcosmos.calc.drawer;

import javafx.scene.Parent;
import javafx.scene.text.Text;

public class CalculatorDrawer extends Drawer {
	private Text textPrevious;
	private Text textCurrent;
	
	public Parent draw() {
		return super.draw("calc-view.fxml");
	}
}
