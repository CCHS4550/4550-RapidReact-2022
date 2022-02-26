package frc.diagnostics;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.helpers.*;

import static frc.diagnostics.MotorDataType.*;
/**
 * Diagnostics2 performs the same functions as Diagnostics, but coded without Streams and lambdas,
 * hopefully its more readable for novice java programmers.
 */
public class DiagnosticsNoLayout implements DiagnosticsIF {

    private MotorDataType[] displayedData = {FAULTS, STICKY_FAULTS, TEMP, INVERTED_STATE, POSITION, VELOCITY};
    enum PowerDataType {VOLTAGE, TEMP, CURRENT, ENERGY };
    private final ShuffleboardTab summaryTab = Shuffleboard.getTab("Summary");
    private final ShuffleboardTab motorTab = Shuffleboard.getTab("Motors");
    private NetworkTableEntry faultEntry;
    
    private CCSparkMax[] motors;

    // key -> motor name, value -> map (key -> DataType, value -> NetworkTableEntry)
    private Map<String, Map<MotorDataType, NetworkTableEntry>> motorEntryMap = new HashMap<>();

    private MotorUpdate motorUpdate;

    public DiagnosticsNoLayout(CCSparkMax... motors) {
        this.motors = motors;
    }

    @Override
    public void init() {
        
        faultEntry = summaryTab
          .add("Fault Indicator", false)
          .withWidget(BuiltInWidgets.kBooleanBox)
          .getEntry();

        motorUpdate = new MotorUpdate(motorEntryMap, motors, faultEntry, displayedData );

        int row = 0;
        
        // for each motor: Faults, Sticky Faults, Temp, Inverted state, position, velocity
        for(CCSparkMax m : motors) {
            int col = 0;
            Map<MotorDataType, NetworkTableEntry> entryMap = new EnumMap<>(MotorDataType.class);

            // initialize motorEntryMap
            motorEntryMap.put(m.getName(), entryMap);

            final String shortName = m.getShortName();

            for (MotorDataType md : displayedData) {
                int width = md.getWidth();
                entryMap.put(md, motorTab.add(shortName + " " + md.getLabel(), md.getDefaultValue())
                .withWidget(md.getWidgetType())
                .withPosition(col, row) 
                .withSize(width, 1)
                .withProperties(md.getProperties())
                .getEntry() ); 
                col += width;   
            }
            row++;
        }

        row = 0;
        
       
        // motorTab.add("spark", spark);
        Shuffleboard.selectTab("Motors");
    }
  
    @Override
    public void updateStatus() {
        motorUpdate.updateStatus();
    }
}