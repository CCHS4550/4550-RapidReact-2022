package frc.diagnostics;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import static edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets.*;

import java.util.Collections;
import java.util.Map;

public enum MotorDataType {
    FAULTS("Faults", kTextView ,"No fault", 2), 
    STICKY_FAULTS("Sticky Faults", kTextView, "No Fault", 2), 
    TEMP("Temp",kNumberBar, 0, Map.of("Min", 0, "Max", 100), 1), 
    INVERTED_STATE("Inv. State", kTextView, "", 1), 
    POSITION("Position", kTextView, "", 1), 
    VELOCITY("Velocity", kNumberBar, 0, Map.of("Min", 0, "Max", 100), 1);

    private final BuiltInWidgets widgetType;
    private final String label;
    private final Object defaultValue;
    private final Map<String,Object> properties;
    private final int width;

    private MotorDataType(String label, BuiltInWidgets widget, Object defaultValue, Map<String, Object> properties, int width) {
        this.label = label;
        this.widgetType = widget;
        this.defaultValue = defaultValue;
        this.properties = properties;
        this.width = width;
    }

    private MotorDataType(String label, BuiltInWidgets widget, Object defaultValue, int width) {
        this.label = label;
        this.widgetType = widget;
        this.defaultValue = defaultValue;
        this.properties = Collections.emptyMap();
        this.width = width;
    }

    public String getLabel() {
        return label;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public BuiltInWidgets getWidgetType() {
        return widgetType;
    }

    public Map<String,Object> getProperties() {
        return properties;
    }

    public int getWidth() {
        return width;
    }
}