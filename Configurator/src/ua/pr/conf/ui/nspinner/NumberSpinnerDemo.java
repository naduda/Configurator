package ua.pr.conf.ui.nspinner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class NumberSpinnerDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Spinner Demo");
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        
        final NumberSpinner defaultSpinner = new NumberSpinner();
        defaultSpinner.setMinValue(100);
        defaultSpinner.setMaxValue(200);
        defaultSpinner.setStep(15);
        defaultSpinner.setNumberStringConverter(new NumberStringConverter("0.00 %"));
        defaultSpinner.setHAlignment("left");
        
        root.addRow(1, new Label("default"), defaultSpinner);
       

        Scene scene = new Scene(root);
        String path = getClass().getResource("NumberSpinner.css").toExternalForm();
        System.out.println("path=" + path);
        defaultSpinner.getStylesheets().add(path);
        //defaultSpinner.setStyle(path);
//        scene.getStylesheets().add(path);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
