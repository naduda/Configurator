package ua.pr.conf;

import java.net.URL;

import ua.pr.common.ToolsPrLib;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test_FXML extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(new URL("file:/" + ToolsPrLib.getFullPath("../Spinner.xml")));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
