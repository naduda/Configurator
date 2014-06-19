package ua.pr.conf.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ua.pr.common.ToolsPrLib;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class FindController implements Initializable {

	@FXML
	private ComboBox<String> cbTypeDevice;
	@FXML
	private ComboBox<String> cbStartSpeed;
	@FXML
	private ComboBox<String> cbEndSpeed;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File folder = new File(ToolsPrLib.getFullPath("../devices"));
		System.out.println(folder);
		FilenameFilter filter = new FilenameFilter() {
	        public boolean accept(File directory, String fileName) {
	        	if (fileName.toLowerCase().endsWith(".xml")) {
					return true;
				} else {
					return false;
				}
	        }};
		File[] listOfFiles = folder.listFiles(filter);

		List<String> list = new ArrayList<String>();
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	    		list.add(new String(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf("."))));
	    	}
	    }
	    
        ObservableList<String> obList = FXCollections.observableList(list);
        cbTypeDevice.getItems().clear();
        cbTypeDevice.setItems(obList);
        cbTypeDevice.setValue(list.get(0));
//        -----------------------------------------------------------------------------------------------------------
        
	}
	
	@FXML 
	protected void cancelButtonAction(ActionEvent event) {
		((Button)event.getSource()).getScene().getWindow().hide();;
	}
	
	

}
