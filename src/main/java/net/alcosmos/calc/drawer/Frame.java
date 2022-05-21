package net.alcosmos.calc.drawer;

import net.alcosmos.calc.CalcApplication;
import net.alcosmos.calc.controller.FrameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Frame {
	private Stage stage;
	private Scene scene;
	private FrameController controller;
	
	private double mouseX;
	private double mouseY;
	
	public Frame(Stage stage) {
		this.stage = stage;
		
		stage.initStyle(StageStyle.UNDECORATED);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.getIcons().add(new Image("icon.png"));
		
		draw();
	}
	
	public void draw() {
		try {
			FXMLLoader fxmlFrame = new FXMLLoader(CalcApplication.class.getResource("frame-view.fxml"));
			Parent root = fxmlFrame.load();
			
			scene = new Scene(root, 240, 264);
			scene.setFill(Color.TRANSPARENT);
			
			stage.setScene(scene);
			
			controller = fxmlFrame.getController();
			controller.setFrame(this);
			
			stage.show();
			
			addStyle("frame-style.css");
			Font.loadFont(CalcApplication.class.getResourceAsStream("volter_goldfish.ttf"), 10);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void setTitle(String title) {
		stage.setTitle(title);
		
		controller.setTitle(title);
	}
	
	public void addStyle(String style) {
		scene.getStylesheets().add(getClass().getClassLoader().getResource(style).toExternalForm());
	}
	
	public void setContent(Parent content) {
		controller.setContent(content);
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void setMouseCoords(MouseEvent event) {
		this.mouseX = stage.getX() - event.getScreenX();
		this.mouseY = stage.getY() - event.getScreenY();
	}
	
	public void moveWindow(MouseEvent event) {
		stage.setX(event.getScreenX() + mouseX);
		stage.setY(event.getScreenY() + mouseY);
	}
	
	public void closeWindow() {
		stage.close();
	}
}
