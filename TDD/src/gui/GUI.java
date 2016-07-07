//Das ist der Wrapper, der das ganze GUI initialisiert

package gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application{

	private Stage mainMenu;

	private String css = this.getClass().getResource("/layout/style.css").toExternalForm();
	private Image icon = new Image(getClass().getResourceAsStream("/images/TDDicon.png"));

	public static int WIDTH = 700;
	public static int HEIGHT = 600;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage mainStage){
		
		mainStage.getIcons().add(icon);
		
		Pane root = new Pane();

		//Menu
		
		MenuBar menuBar = new MenuBar();
		VBox topContainer = new VBox();
//		ToolBar toolBar = new ToolBar();

		Menu about = new Menu("About");
		menuBar.getMenus().addAll(about);
		root.getChildren().add(topContainer);
		
		topContainer.getChildren().add(menuBar);
		
		
		root.getChildren().add(new MenuControls(mainStage));

		root.getChildren().add(new WelcomeScreen());

		Scene sc_menu = new Scene(root, WIDTH, HEIGHT);
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
