package ua.pr.conf;

import ua.pr.conf.ui.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Stage mainStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage = new MainStage("../ui/Main.xml");
		mainStage = stage;
        stage.show();
	}

}
