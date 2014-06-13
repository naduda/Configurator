package ttt;

import com.sun.javafx.scene.control.skin.SkinBase;

public class MyCustomControlSkin extends SkinBase<MyCustomControl, MyCustomControlBehavior> {
	
	public MyCustomControlSkin(MyCustomControl control) {		
		super(control, new MyCustomControlBehavior(control));
		getStylesheets().add(control.getUserAgentStylesheet());
	}
}
