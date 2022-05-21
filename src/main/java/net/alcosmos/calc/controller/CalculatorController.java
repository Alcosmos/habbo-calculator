package net.alcosmos.calc.controller;

import net.alcosmos.calc.CalcApplication;
import net.alcosmos.calc.drawer.AboutDrawer;
import net.alcosmos.calc.drawer.Frame;
import net.alcosmos.calc.drawer.HistoryDrawer;
import net.alcosmos.calc.processor.InputType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CalculatorController implements Controller {
	@FXML
	private Text boxTextTop;
	
	@FXML
	private Text boxTextBottom;
	
	@FXML
	protected void onButtonOpenClick() {
		CalcApplication.getProcessor().addInput(InputType.OPEN);
	}
	
	@FXML
	protected void onButtonCloseClick() {
		CalcApplication.getProcessor().addInput(InputType.CLOSE);
	}
	
	@FXML
	protected void onButtonNumClick(ActionEvent event) {
		Object source = event.getSource();
		
		if (source instanceof Button) {
			CalcApplication.getProcessor().addInput(InputType.valueOf("NUM" + ((Button)source).getText()));
		}
	}
	
	@FXML
	protected void onButtonCommaClick() {
		CalcApplication.getProcessor().addInput(InputType.COMMA);
	}
	
	@FXML
	protected void onButtonEqualsClick() {
		CalcApplication.getProcessor().addInput(InputType.EQUALS);
	}
	
	/*
	 * Side buttons
	 */
	@FXML
	protected void onButtonDeleteClick() {
		CalcApplication.getProcessor().delete();
	}
	
	@FXML
	protected void onButtonClearClick() {
		CalcApplication.getProcessor().clear();
	}
	
	@FXML
	protected void onButtonPlusClick() {
		CalcApplication.getProcessor().addInput(InputType.PLUS);
	}
	
	@FXML
	protected void onButtonMinusClick() {
		CalcApplication.getProcessor().addInput(InputType.MINUS);
	}
	
	@FXML
	protected void onButtonMultClick() {
		CalcApplication.getProcessor().addInput(InputType.MULT);
	}
	
	@FXML
	protected void onButtonDivClick() {
		CalcApplication.getProcessor().addInput(InputType.DIV);
	}
	
	@FXML
	protected void onButtonRootClick() {
		CalcApplication.getProcessor().addInput(InputType.ROOT);
	}
	
	@FXML
	protected void onButtonPowerClick() {
		CalcApplication.getProcessor().addInput(InputType.POWER);
	}
	
	@FXML
	protected void copyResult() {
		CalcApplication.getProcessor().copyResult();
	}
	
	@FXML
	protected void openHistory() {
		HistoryDrawer historyDrawer = new HistoryDrawer();
		
		Frame frame = new Frame(new Stage());
		frame.setTitle("History");
		frame.setContent(historyDrawer.draw());
		frame.addStyle("calc-style.css");
		frame.addStyle("extra-style.css");
		
		HistoryController controller = (HistoryController)historyDrawer.getController();
		controller.setHistory();
	}
	
	@FXML
	protected void openAbout() {
		AboutDrawer aboutDrawer = new AboutDrawer();
		
		Frame frame = new Frame(new Stage());
		frame.setTitle("About");
		frame.setContent(aboutDrawer.draw());
		frame.addStyle("calc-style.css");
		frame.addStyle("extra-style.css");
	}
	
	public void setTopText(String text) {
		boxTextTop.setText(text);
	}
	
	public void setBottomText(String text) {
		boxTextBottom.setText(text);
	}
}
