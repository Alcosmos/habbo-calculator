module net.alcosmos.calc {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens net.alcosmos.calc to javafx.fxml;
	exports net.alcosmos.calc;
	exports net.alcosmos.calc.processor;
	opens net.alcosmos.calc.processor to javafx.fxml;
	exports net.alcosmos.calc.drawer;
	opens net.alcosmos.calc.drawer to javafx.fxml;
	exports net.alcosmos.calc.controller;
	opens net.alcosmos.calc.controller to javafx.fxml;
}
