//Das ist der Wrapper, der das ganze GUI initialisiert

package gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Timer;
import vk.core.api.CompilationUnit;
import vk.core.api.JavaStringCompiler;

public class GUI extends Application{

	private Stage mainMenu;
	private int exc_auswahl = -1;        //Falls keine Wahl getroffen wurde -1
	private CompilationUnit compileClass;
	private CompilationUnit compileTest;
	private JavaStringCompiler compiler;
	private Timer timer;

	private String css = this.getClass().getResource("/layout/style.css").toExternalForm();
	private Image icon = new Image(getClass().getResourceAsStream("/images/TDDicon.png"));

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage mainStage){
		
		mainStage.getIcons().add(icon);
		
		Pane root = new Pane();
		root.getChildren().add(new Menu(mainStage));
		root.getChildren().add(new WelcomeScreen());

		Scene sc_menu = new Scene(root);
		sc_menu.getStylesheets().addAll(css);
		mainStage.setScene(sc_menu);
		mainStage.setTitle("Menü");
		mainStage.show();
		mainMenu = mainStage;

		//closes the running thread 
		mainStage.setOnCloseRequest( e -> {
			Platform.exit();
			System.exit(0);
		});
	}
}
