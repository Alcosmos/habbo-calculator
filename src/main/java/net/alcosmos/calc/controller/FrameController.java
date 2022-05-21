package net.alcosmos.calc.controller;

import net.alcosmos.calc.drawer.Frame;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class FrameController implements Controller {
	@FXML
	private GridPane frameGrid;
	
	@FXML
	private Text topTextContent;
	
	private Frame frame;
	
	public void setFrame(Frame frame) {
		this.frame = frame;
	}
	
	public void setContent(Parent content) {
		frameGrid.add(content, 1, 2);
	}
	
	public void setTitle(String title) {
		topTextContent.setText(title);
	}
	
	@FXML
	protected void setMouseCoords(MouseEvent event) {
		frame.setMouseCoords(event);
	}
	
	@FXML
	protected void moveWindow(MouseEvent event) {
		frame.moveWindow(event);
	}
	
	@FXML
	private void closeWindow() {
		frame.closeWindow();
	}
}
