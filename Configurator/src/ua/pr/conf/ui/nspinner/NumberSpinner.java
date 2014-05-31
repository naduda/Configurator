package ua.pr.conf.ui.nspinner;

import java.math.BigDecimal;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

public class NumberSpinner extends StackPane implements Skin<NumberTextField> {
	private static final String TOP_LEFT = "top-left";	 
    private static final String BOTTOM_LEFT = "bottom-left"; 
    private static final String LEFT = "left"; 
    private static final String RIGHT = "right"; 
    private static final String BOTTOM_RIGHT = "bottom-right"; 
    private static final String TOP_RIGHT = "top-right";
    private static final String CENTER = "center";
    private final String[] cssClasses = {TOP_LEFT, TOP_RIGHT, LEFT, CENTER, RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT};

	private final NumberTextField numberTextField;
	private final TextField textField;
	private final ChangeListener<IndexRange> changeListenerSelection;
	private final ChangeListener<Number> changeListenerCaretPosition;
	private final ChangeListener<Number> changeListenerValue;
	private final ChangeListener<HPos> changeListenerHAlignment;
	private final Button btnIncrement;
	private final Button btnDecrement;
	private final Region arrowIncrement;
	private final Region arrowDecrement;
	
	public NumberSpinner() {
        numberTextField = new NumberTextField(); 
        minHeightProperty().bind(numberTextField.minHeightProperty());
 
        // The TextField
        textField = new TextField();
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean1) {
                if (textField.isEditable() && aBoolean1) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            textField.selectAll();
                        }
                    });
                }
 
                // setStyle explicitly is a workaround for this JavaFX 2.2 bug:
                // https://javafx-jira.kenai.com/browse/RT-23085
                String javafxVersion = System.getProperty("javafx.runtime.version");
                if (textField.isFocused()) {
                    getStyleClass().add("number-spinner-focused");
                    if (javafxVersion.startsWith("2.2")) {
                        setStyle("-fx-background-color: -fx-focus-color, -fx-text-box-border, -fx-control-inner-background;\n" +
                                "    -fx-background-insets: -0.4, 1, 2;\n" +
                                "    -fx-background-radius: 3.4, 2, 2");
                    }
                } else {
                    getStyleClass().remove("number-spinner-focused");
                    if (javafxVersion.startsWith("2.2")) {
                        setStyle("-fx-background-color: null;\n" +
                                "    -fx-background-insets: null;\n" +
                                "    -fx-background-radius: null");
                    }
                    parseText();
                    setText();
                }
            }
        });
 
        // Mimic bidirectional binding: Whenever the selection changes of either the control or the text field, propagate it to the other.
        // This ensures that the selectionProperty of both are in sync.
        changeListenerSelection = new ChangeListener<IndexRange>() {
            @Override
            public void changed(ObservableValue<? extends IndexRange> observableValue, IndexRange indexRange, IndexRange indexRange2) {
                textField.selectRange(indexRange2.getStart(), indexRange2.getEnd());
            }
        };
        numberTextField.selectionProperty().addListener(changeListenerSelection);
 
        textField.selectionProperty().addListener(new ChangeListener<IndexRange>() {
            @Override
            public void changed(ObservableValue<? extends IndexRange> observableValue, IndexRange indexRange, IndexRange indexRange1) {
            	numberTextField.selectRange(indexRange1.getStart(), indexRange1.getEnd());
            }
        });
 
        // Mimic bidirectional binding: Whenever the caret position changes in either the control or the text field, propagate it to the other.
        // This ensures that both caretPositions are in sync.
        changeListenerCaretPosition = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number1) {
                textField.positionCaret(number1.intValue());
            }
        };
        numberTextField.caretPositionProperty().addListener(changeListenerCaretPosition);
 
        textField.caretPositionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number1) {
            	numberTextField.positionCaret(number1.intValue());
            }
        });
 
        // Bind the control's properties to the text field.
        textField.minHeightProperty().bind(numberTextField.minHeightProperty());
        textField.maxHeightProperty().bind(numberTextField.maxHeightProperty());
        textField.textProperty().bindBidirectional(numberTextField.textProperty());
        textField.alignmentProperty().bind(numberTextField.alignmentProperty());
        textField.editableProperty().bind(numberTextField.editableProperty());
        textField.prefColumnCountProperty().bind(numberTextField.prefColumnCountProperty());
        textField.promptTextProperty().bind(numberTextField.promptTextProperty());
        textField.onActionProperty().bind(numberTextField.onActionProperty());
        textField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!keyEvent.isConsumed()) {
                    if (keyEvent.getCode().equals(KeyCode.UP)) {
                        btnIncrement.fire();
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                        btnDecrement.fire();
                        keyEvent.consume();
                    }
                }
            }
        });
        setText();
 
        changeListenerValue = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                setText();
            }
        };
        numberTextField.valueProperty().addListener(changeListenerValue);
        changeListenerHAlignment = new ChangeListener<HPos>() {
            @Override
            public void changed(ObservableValue<? extends HPos> observableValue, HPos hPos, HPos hPos1) {
                align(numberTextField.hAlignmentProperty().get());
            }
        };
        numberTextField.hAlignmentProperty().addListener(changeListenerHAlignment);
 
 
        // The increment button.
        btnIncrement = new Button();
        btnIncrement.setFocusTraversable(false);
        btnIncrement.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(numberTextField.valueProperty(), numberTextField.maxValueProperty());
            }
 
            @Override
            protected boolean computeValue() {
 
                return numberTextField.valueProperty().get() != null && numberTextField.maxValueProperty().get() != null && numberTextField.valueProperty().get().doubleValue() >= numberTextField.maxValueProperty().get().doubleValue();
            }
        });
        btnIncrement.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                parseText();
                numberTextField.increment();
            }
        });
        arrowIncrement = createArrow();
        btnIncrement.setGraphic(arrowIncrement);
 
        btnIncrement.setMinHeight(0);
        ClickRepeater.install(btnIncrement);
 
 
        // The decrement button
        btnDecrement = new Button();
        btnDecrement.setFocusTraversable(false);
        btnDecrement.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(numberTextField.valueProperty(), numberTextField.minValueProperty());
            }
 
            @Override
            protected boolean computeValue() {
                return numberTextField.valueProperty().get() != null && numberTextField.minValueProperty().get() != null && numberTextField.valueProperty().get().doubleValue() <= numberTextField.minValueProperty().get().doubleValue();
            }
        });
        btnDecrement.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                parseText();
                numberTextField.decrement();
            }
        });
        arrowDecrement = createArrow();
        btnDecrement.setGraphic(arrowDecrement);
        btnDecrement.setMinHeight(0);
        ClickRepeater.install(btnDecrement);
 
        // Allow the buttons to grow vertically.
        VBox.setVgrow(btnIncrement, Priority.ALWAYS);
        VBox.setVgrow(btnDecrement, Priority.ALWAYS);
 
        // Allow the text field to allow horizontally.
        HBox.setHgrow(textField, Priority.ALWAYS);
        align(numberTextField.hAlignmentProperty().get());
    }
	
	private Region createArrow() {
        Region arrow = new Region();
        arrow.setMaxSize(8, 8);
        arrow.getStyleClass().add("arrow");
        return arrow;
    }

    private void align(HPos hPos) {
        getChildren().clear();
        clearStyles();
        btnIncrement.maxHeightProperty().unbind();
        btnDecrement.maxHeightProperty().unbind();
        switch (hPos) {
            case LEFT:
            case RIGHT:
                alignLeftOrRight(hPos);
                break;
            case CENTER:
                alignCenter();
                break;
        }
    }

    private void alignCenter() {
        btnIncrement.getStyleClass().add(RIGHT);
        btnDecrement.getStyleClass().add(LEFT);
        textField.getStyleClass().add(CENTER);
 
        btnIncrement.maxHeightProperty().setValue(Double.MAX_VALUE);
        btnDecrement.maxHeightProperty().setValue(Double.MAX_VALUE);
 
        arrowIncrement.setRotate(-90);
        arrowDecrement.setRotate(90);
 
        getChildren().add(HBoxBuilder.create().children(btnDecrement, textField, btnIncrement).build());
    }

    private void alignLeftOrRight(HPos hPos) {
        // The box which aligns the two buttons vertically.
        final VBox buttonBox = new VBox();
        HBox hBox = new HBox();
        switch (hPos) {
            case RIGHT:
                btnIncrement.getStyleClass().add(TOP_LEFT);
                btnDecrement.getStyleClass().add(BOTTOM_LEFT);
                textField.getStyleClass().add(RIGHT);
                hBox.getChildren().addAll(buttonBox, textField);
                break;
            case LEFT:
                btnIncrement.getStyleClass().add(TOP_RIGHT);
                btnDecrement.getStyleClass().add(BOTTOM_RIGHT);
                textField.getStyleClass().add(LEFT);
                hBox.getChildren().addAll(textField, buttonBox);
                break;
            case CENTER:
                break;
        }
 
        btnIncrement.maxHeightProperty().bind(textField.heightProperty().divide(2.0));
        // Subtract 0.5 to ensure it looks fine if height is odd.
        btnDecrement.maxHeightProperty().bind(textField.heightProperty().divide(2.0).subtract(0.5));
        arrowIncrement.setRotate(180);
        arrowDecrement.setRotate(0);
 
        buttonBox.getChildren().addAll(btnIncrement, btnDecrement);
        getChildren().add(hBox);
    }

    private void clearStyles() {
        btnIncrement.getStyleClass().removeAll(cssClasses);
        btnDecrement.getStyleClass().removeAll(cssClasses);
        textField.getStyleClass().removeAll(cssClasses);
    }

    private void parseText() {
        if (textField.getText() != null) {
            try {
                numberTextField.valueProperty().set(BigDecimal.valueOf(numberTextField.numberStringConverterProperty().get().fromString(textField.getText()).doubleValue()));
            } catch (Exception e) {
            	numberTextField.valueProperty().set(null);
            }
 
        } else {
        	numberTextField.valueProperty().set(null);
        }
    }
 
    private void setText() {
        if (numberTextField.valueProperty().get() != null && 
        		!Double.isInfinite((numberTextField.valueProperty().get().doubleValue())) && 
        		!Double.isNaN(numberTextField.valueProperty().get().doubleValue())) {
            textField.setText(numberTextField.numberStringConverterProperty().get().toString(numberTextField.valueProperty().get()));
        } else {
            textField.setText(null);
        }
    }
 
    @Override
    public NumberTextField getSkinnable() {
        return numberTextField;
    }
 
    @Override
    public Node getNode() {
        return this;
    }
 
    @Override
    public void dispose() {
 
        // Unbind everything and remove listeners, in order to avoid memory leaks.
        minHeightProperty().unbind();
 
        textField.minHeightProperty().unbind();
        textField.maxHeightProperty().unbind();
        textField.textProperty().unbindBidirectional(numberTextField.textProperty());
        textField.alignmentProperty().unbind();
        textField.editableProperty().unbind();
        textField.prefColumnCountProperty().unbind();
        textField.promptTextProperty().unbind();
        textField.onActionProperty().unbind();
 
        numberTextField.selectionProperty().removeListener(changeListenerSelection);
        numberTextField.caretPositionProperty().removeListener(changeListenerCaretPosition);
        numberTextField.valueProperty().removeListener(changeListenerValue);
        numberTextField.hAlignmentProperty().removeListener(changeListenerHAlignment);
        btnIncrement.disableProperty().unbind();
        btnDecrement.disableProperty().unbind();
 
    }

    public final Number getMinValue() {
        return numberTextField.minValueProperty().get();
    }
    
    public final void setMinValue(final Number minValue) {
        numberTextField.minValueProperty().set(minValue);
    }
    
    public final Number getMaxValue() {
        return numberTextField.maxValueProperty().get();
    }
    
    public final void setMaxValue(final Number maxValue) {
        numberTextField.maxValueProperty().set(maxValue);
    }
    
    public final Number getValue() {
        return numberTextField.valueProperty().get();
    }
    
    public final void setValue(final Number value) {
        numberTextField.valueProperty().set(value);
    }
    
    public final Number getStep() {
        return numberTextField.stepWidthProperty().get();
    }
    
    public final void setStep(final Number stepWidth) {
    	numberTextField.stepWidthProperty().setValue(stepWidth);
    }
    
    public final NumberStringConverter getNumberStringConverter() {
        return numberTextField.numberStringConverterProperty().get();
    }
    
    public final void setNumberStringConverter(final NumberStringConverter numberStringConverter) {
    	numberTextField.numberStringConverterProperty().set(numberStringConverter);
    }
    
    public final String getFormat() {
    	return numberTextField.numberStringConverterProperty().get().toString();
    }
    
    public final void setFormat(String format) {
    	numberTextField.numberStringConverterProperty().set(new NumberStringConverter(format));
    }
    
    public String getHAlignment() {
        return numberTextField.hAlignmentProperty().get().toString();
    }
    
    public void setHAlignment(final String hAlignment) {
    	switch (hAlignment.toLowerCase()) {
		case "left":
			numberTextField.hAlignmentProperty().set(HPos.LEFT);
			break;
		case "right":
			numberTextField.hAlignmentProperty().set(HPos.RIGHT);
			break;
		case "center":
			numberTextField.hAlignmentProperty().set(HPos.CENTER);
			break;
		}
    	
    }
}
