package ua.pr.conf.ui;

import java.io.IOException;
import java.net.URL;

import ua.pr.common.ToolsPrLib;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Stage {

	public MainStage(String pathXML) {
		try {
			Parent root = FXMLLoader.load(new URL("file:/" + ToolsPrLib.getFullPath(pathXML)));
			Scene scene = new Scene(root);      
			this.setTitle("Modbus device's configurator");
			this.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
