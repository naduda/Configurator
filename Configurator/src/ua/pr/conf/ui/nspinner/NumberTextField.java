package ua.pr.conf.ui.nspinner;

import java.math.BigDecimal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class NumberTextField extends TextField {

	private final ObjectProperty<Number> maxValue = new SimpleObjectProperty<Number>(this, "maxValue") {
        @Override
        protected void invalidated() {
            if (maxValue.get() != null) {
                if (minValue.get() != null && maxValue.get().doubleValue() < minValue.get().doubleValue()) {
                    throw new IllegalArgumentException("maxValue must not be greater than minValue");
                }
                if (value.get() != null && value.get().doubleValue() > maxValue.get().doubleValue()) {
                    value.set(maxValue.get());
                }
            }
        }
    };
    
    private final ObjectProperty<Number> minValue = new SimpleObjectProperty<Number>(this, "minValue") {
        @Override
        protected void invalidated() {
            if (minValue.get() != null) {
                if (maxValue.get() != null && maxValue.get().doubleValue() < minValue.get().doubleValue()) {
                    throw new IllegalArgumentException("minValue must not be smaller than maxValue");
                }
                if (value.get() != null && value.get().doubleValue() < minValue.get().doubleValue()) {
                    value.set(minValue.get());
                }
            }
        }
    };
    
	private final ObjectProperty<Number> value = new SimpleObjectProperty<Number>(this, "value") {
        @Override
        protected void invalidated() {
            if (!isBound() && value.get() != null) {
                if (maxValue.get() != null && value.get().doubleValue() > maxValue.get().doubleValue()) {
                    set(maxValue.get());
                }
                if (minValue.get() != null && value.get().doubleValue() < minValue.get().doubleValue()) {
                    set(minValue.get());
                }
            }
        }
    };
    
    private final ObjectProperty<Number> stepWidth = new SimpleObjectProperty<Number>(this, "stepWidth", 1);
    
    private final ObjectProperty<NumberStringConverter> numberStringConverter = new SimpleObjectProperty<>(this, "numberFormatter", new NumberStringConverter());
    
    private ObjectProperty<HPos> hAlignment = new SimpleObjectProperty<>(this, "hAlignment", HPos.LEFT);
    
    public NumberTextField() {
    	
    }

	public final ObjectProperty<Number> valueProperty() {
		return value;
	}
    
    public final ObjectProperty<Number> maxValueProperty() {
        return maxValue;
    }

    public final ObjectProperty<Number> minValueProperty() {
        return minValue;
    }

    public final ObjectProperty<Number> stepWidthProperty() {
        return stepWidth;
    }

    public final ObjectProperty<NumberStringConverter> numberStringConverterProperty() {
        return numberStringConverter;
    }

    public ObjectProperty<HPos> hAlignmentProperty() {
        return hAlignment;
    }
    
    public void increment() {
        if (stepWidth.get() != null && isFinite(stepWidth.get().doubleValue())) {
            if (value.get() != null && isFinite(value.get().doubleValue())) {
                value.set(BigDecimal.valueOf(value.get().doubleValue()).add(BigDecimal.valueOf(stepWidth.get().doubleValue())));
            } else {
                if (minValue.get() != null && isFinite(minValue.get().doubleValue())) {
                	value.set(BigDecimal.valueOf(minValue.get().doubleValue()).add(BigDecimal.valueOf(stepWidth.get().doubleValue())));
                } else {
                	value.set(BigDecimal.valueOf(stepWidth.get().doubleValue()));
                }
            }
        }
    }
    
    public void decrement() {
        if (stepWidth.get() != null && isFinite(stepWidth.get().doubleValue())) {
            if (value.get() != null && isFinite(value.get().doubleValue())) {
            	value.set(BigDecimal.valueOf(value.get().doubleValue()).subtract(BigDecimal.valueOf(stepWidth.get().doubleValue())));
            } else {
                if (maxValue.get() != null && isFinite(maxValue.get().doubleValue())) {
                	value.set(BigDecimal.valueOf(maxValue.get().doubleValue()).subtract(BigDecimal.valueOf(stepWidth.get().doubleValue())));
                } else {
                	value.set(BigDecimal.valueOf(stepWidth.get().doubleValue()).multiply(new BigDecimal(-1)));
                }
            }
        }
    }

    private boolean isFinite(double value) {
        return !Double.isInfinite(value) && !Double.isNaN(value);
    }
    
    @Override
    protected String getUserAgentStylesheet() {
        return getClass().getResource("NumberSpinner.css").toExternalForm();
    }
}
