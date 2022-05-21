package net.alcosmos.calc.drawer;

import net.alcosmos.calc.CalcApplication;
import net.alcosmos.calc.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public abstract class Drawer {
	FXMLLoader fxmlLoader;
	
	public Parent draw(String fxml) {
		try {
			fxmlLoader = new FXMLLoader(CalcApplication.class.getResource(fxml));
			
			return fxmlLoader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public Controller getController() {
		return fxmlLoader.getController();
	}
}
