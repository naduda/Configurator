package ttt;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

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
        
//        MyCustomControlSkin cc = new MyCustomControlSkin(new MyCustomControl());
//        cc.setPrefWidth(100);
        MyCustomControl ccc = new MyCustomControl();
        ccc.setPrefWidth(100);
        root.addRow(1, new Label("default"), ccc);
//        root.addRow(1, new Label("default"), defaultSpinner);

        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
	}

}
