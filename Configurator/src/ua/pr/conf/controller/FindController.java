package ua.pr.conf.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FindController {

	@FXML 
	protected void cancelButtonAction(ActionEvent event) {
		((Button)event.getSource()).getScene().getWindow().hide();;
	}
}
