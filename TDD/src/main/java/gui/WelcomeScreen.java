// MIT License, check LICENSE.txt in the src folder for full text
// Das ist der Begrüßungsbildschirm, der dann fadet.

package main.java.gui;

import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class WelcomeScreen extends Pane {
	WelcomeScreen() {
		this.setMinWidth(GUI.WIDTH);
		this.setMinHeight(GUI.HEIGHT);
		this.setId("welcome_screen");
		
		FadeTransition ftOUT = new FadeTransition(Duration.millis(1500), this);
		ftOUT.setFromValue(1.0);
		ftOUT.setToValue(0.0);
		ftOUT.setDelay(Duration.seconds(1.5));
		ftOUT.play();
		
		ftOUT.setOnFinished(e -> {this.setVisible(false);});
	}
}
