package frc.diagnostics;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import static edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets.*;

import java.util.Map;

public enum PowerDataType {
    VOLTAGE("Voltage", kVoltageView, 0, Map.of("Min", 0, "Max", 100), 1),
    TEMP("Temp", kNumberBar, 0, Map.of("Min", 0, "Max", 100), 1), 
    CURRENT("Current",kNumberBar, 0, Map.of("Min", 0, "Max", 10), 1), 
    ENERGY("Energy", kNumberBar, 0, Map.of("Min", 0, "Max", 10), 1);

    private final BuiltInWidgets widgetType;
    private final String label;
    private final Object defaultValue;
    private final Map<String,Object> properties;
    private final int width;

    private PowerDataType(String label, BuiltInWidgets widget, Object defaultValue, Map<String, Object> properties, int width) {
        this.label = label;
        this.widgetType = widget;
        this.defaultValue = defaultValue;
        this.properties = properties;
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
