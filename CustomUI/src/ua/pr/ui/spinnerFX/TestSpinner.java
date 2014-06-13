package ua.pr.ui.spinnerFX;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class TestSpinner extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JavaFX Spinner Demo");
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        
        final NumberSpinner defaultSpinner = new NumberSpinner();
        defaultSpinner.setMinValue(100);
        defaultSpinner.setMaxValue(200);
        defaultSpinner.setStepWidth(15);
        defaultSpinner.setNumberStringConverter(new NumberStringConverter("0.00 A"));
        defaultSpinner.setPrefWidth(100);
//        defaultSpinner.setHAlignment(HPos.CENTER);
//        NumberSpinnerSkin nss = new NumberSpinnerSkin(defaultSpinner);

//        root.addRow(1, new Label("default"), nss);
        root.addRow(1, new Label("default"), defaultSpinner);
        root.addRow(2, new Label("default"), new TextField());

        Scene scene = new Scene(root);
//        String path = getClass().getResource("NumberSpinner.css").toExternalForm();
//        System.out.println("path=" + path);
//        scene.getStylesheets().add(path);
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
	}

}
