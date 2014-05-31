package ua.pr.conf.ui.nspinner;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import javafx.util.Duration;

public class ClickRepeater {

    private final PauseTransition initialPause = new PauseTransition(Duration.millis(500));
    private final PauseTransition pauseTransition = new PauseTransition();
    private final SequentialTransition sequentialTransition = new SequentialTransition(initialPause, pauseTransition);
    private final ChangeListener<Boolean> changeListener;

    private ClickRepeater(final ButtonBase buttonBase, final Duration interval) {
        initialPause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Fire the button the first time after the initial pause.
                buttonBase.fire();
            }
        });
 
        pauseTransition.setDuration(interval);
        pauseTransition.setCycleCount(Animation.INDEFINITE);
        pauseTransition.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration duration2) {
                // Every time a new cycle starts, fire the button.
                if (duration.greaterThan(duration2)) {
                    buttonBase.fire();
                }
            }
        });
        changeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                if (aBoolean2) {
                    // If the button gets armed, start the animation.
                    sequentialTransition.playFromStart();
                } else {
                    // Stop the animation, if the button is no longer armed.
                    sequentialTransition.stop();
                }
            }
        };
        buttonBase.armedProperty().addListener(changeListener);
    }

    public static void install(ButtonBase buttonBase) {
        install(buttonBase, Duration.millis(80));
    }
 
    public static void install(ButtonBase buttonBase, Duration interval) {
        // Uninstall any previous behavior.
        uninstall(buttonBase);
 
        // Initializes a new ClickRepeater
        if (!buttonBase.getProperties().containsKey(ClickRepeater.class)) {
            // Store the ClickRepeater in the button's properties.
            // If the button will get GCed, so will its ClickRepeater.
            buttonBase.getProperties().put(ClickRepeater.class, new ClickRepeater(buttonBase, interval));
        }
    }

    public static void uninstall(ButtonBase buttonBase) {
        if (buttonBase.getProperties().containsKey(ClickRepeater.class) && buttonBase.getProperties().get(ClickRepeater.class) instanceof ClickRepeater) {
            ClickRepeater clickRepeater = (ClickRepeater) buttonBase.getProperties().remove(ClickRepeater.class);
            buttonBase.armedProperty().removeListener(clickRepeater.changeListener);
        }
    }
}
