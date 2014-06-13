package ttt;

import javafx.scene.control.TextField;

public class MyCustomControl extends TextField {
	
	public MyCustomControl() {
		getStyleClass().add("custom-control");
	}
	
	@Override
	protected String getUserAgentStylesheet() {
		return MyCustomControl.class.getResource("customcontrol.css").toExternalForm();
	}
}