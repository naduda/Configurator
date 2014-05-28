package ua.pr.conf.ui;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ua.pr.common.ToolsPrLib;
import ua.pr.conf.Main;

public class FindStage extends Stage {

	public FindStage(String pathXML) {
		try {
			Parent root = FXMLLoader.load(new URL("file:/" + ToolsPrLib.getFullPath(pathXML)));
			Scene scene = new Scene(root);      
			this.setTitle("Параметри пошуку");
			this.setScene(scene);
			this.initModality(Modality.WINDOW_MODAL);
			this.initOwner(Main.mainStage);
			this.initStyle(StageStyle.UTILITY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
