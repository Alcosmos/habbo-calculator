package net.alcosmos.calc.controller;

import net.alcosmos.calc.CalcApplication;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class HistoryController implements Controller {
	@FXML
	private VBox history;
	
	protected void setHistory() {
		List<String> historyList = CalcApplication.getProcessor().getHistory();
		
		if (historyList == null) {
			history.getChildren().add(new Text("No entries"));
		} else if (historyList.size() == 0) {
			history.getChildren().add(new Text("No entries"));
		} else {
			for (String text : historyList) {
				if (text.length() > 26) {
					text = text.substring(0, 25) + "[...]";
				}
				
				history.getChildren().add(new Text(text));
			}
		}
	}
}
