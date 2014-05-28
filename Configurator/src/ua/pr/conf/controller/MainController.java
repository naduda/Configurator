package ua.pr.conf.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ua.pr.conf.ui.FindStage;
import ua.pr.conf.ui.TableDevice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class MainController implements Initializable {
	@FXML 
	private TableView<TableDevice> tableView;
	@FXML
	private ComboBox<String> cbTypeDevice;
	
	@FXML 
	protected void handleSubmitButtonAction(ActionEvent event) {
		FindStage findStage = new FindStage("../Find.xml");
		findStage.show();
	}
	
	@FXML 
	protected void exitButtonAction(ActionEvent event) {
		System.out.println("exit");
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<String> list = new ArrayList<String>();
        list.add("Item A");
        list.add("Item B");
        list.add("Item C");
        ObservableList<String> obList = FXCollections.observableList(list);
        cbTypeDevice.getItems().clear();
        cbTypeDevice.setItems(obList);
        cbTypeDevice.setValue(list.get(0));
	}
}
