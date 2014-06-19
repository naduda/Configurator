package ua.pr.conf.controller;

import ua.pr.conf.ui.FindStage;
import ua.pr.conf.ui.TableDevice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MainController {
	@FXML 
	private TableView<TableDevice> tableView;
	
	@FXML 
	protected void handleSubmitButtonAction(ActionEvent event) {
		FindStage findStage = new FindStage("../ui/Find.xml");
		findStage.show();
	}
	
	@FXML 
	protected void exitButtonAction(ActionEvent event) {
		System.out.println("exit");
		System.exit(0);
	}

}
