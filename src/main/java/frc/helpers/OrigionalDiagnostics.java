package frc.robot;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.FaultID;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Diagnostics2 performs the same functions as Diagnostics, but coded without Streams and lambdas,
 * hopefully its more readable for novice java programmers.
 */
public class OrigionalDiagnostics {

    enum DataType {FAULTS, STICKY_FAULTS, TEMP, VOLTAGE, PDP, VELOCITY};

    private final ShuffleboardTab summaryTab = Shuffleboard.getTab("Summary");
    private final ShuffleboardTab motorTab = Shuffleboard.getTab("Motors");
    private NetworkTableEntry faultEntry;
    private CCSparkMax[] motors;
    
    private Map<String, Map<DataType, NetworkTableEntry>> motorEntryMap = new HashMap<>();
  
    public OrigionalDiagnostics(CCSparkMax... motors) {
        this.motors = motors;
    }

    public void init() {
        
        faultEntry = summaryTab
          .add("Fault Indicator", false)
          .withWidget(BuiltInWidgets.kBooleanBox)
          .getEntry();

        int row = 0;
        final int faultsWidth = 2;
        // for each motor: Faults, Sticky Faults, Temp, Inverted state, position, velocity
        for(CCSparkMax m : motors) {
            int col = 0;
            Map<DataType, NetworkTableEntry> entryMap = new EnumMap<>(DataType.class);

            // initialize motorEntryMap
            motorEntryMap.put(m.getName(), entryMap);

            final String name = m.getName();
            final String shortName = m.getShortName();

            // FAULTS
            entryMap.put(DataType.FAULTS, motorTab.add(name + " faults", "")
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(col, row) 
            .withSize(faultsWidth, 1)
            .getEntry() );
            col += faultsWidth;
            
            // STICKY_FAULTS
            entryMap.put(DataType.STICKY_FAULTS, motorTab.add(shortName + " sticky faults", "")
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(col, row) 
            .withSize(faultsWidth, 1)
            .getEntry() );
            col += faultsWidth;
            
            // INVERTED_STATE
            entryMap.put(DataType.VOLTAGE, motorTab.add(shortName + "Voltage", 0)
            .withWidget(BuiltInWidgets.kDial)
            .withPosition(col++, row) 
            .withSize(1, 1)
            .getEntry() );

            // POSITION
            entryMap.put(DataType.PDP, motorTab.add(shortName + "  position", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(col++, row) 
            .withSize(1, 1)
            .getEntry() );

            // TEMP
            entryMap.put(DataType.TEMP, motorTab.add(shortName + " temp", 0)
            .withWidget(BuiltInWidgets.kDial)
            .withPosition(col++, row) 
            .withSize(1, 1)
            .withProperties(Map.of("Min", 10, "Max", 100))  //celsius
            .getEntry() );
            
            // VELOCITY
            entryMap.put(DataType.VELOCITY, motorTab.add(shortName + "  velocity", 0)
            .withWidget(BuiltInWidgets.kDial)
            .withPosition(col++, row) 
            .withSize(1, 1)
            .getEntry() );
            
            row++;
        }

        Shuffleboard.selectTab("Motors");
    }
    
    private void updateFaultStatus(NetworkTableEntry entry, CCSparkMax motor) {
        int fault = motor.getFaults();
        String faultMsg = "No fault";
        if (fault != 0) {
             StringJoiner sj = new StringJoiner(",");
            for(CANSparkMax.FaultID faultId : FaultID.values()) {
                if (motor.getFault(faultId)) {
                    sj.add(faultId.name());
                }
            }
            faultMsg = sj.toString();
        }
        entry.setString(faultMsg);
    }

    private void updateStickyFaultStatus(NetworkTableEntry entry, CCSparkMax motor) {
        int fault = motor.getStickyFaults();
        String faultMsg = "No fault";
        if (fault != 0) {
            StringJoiner sj = new StringJoiner(",");
            for(CANSparkMax.FaultID faultId : FaultID.values()) {
                if (motor.getStickyFault(faultId)) {
                    sj.add(faultId.name());
                }
            }
            faultMsg = sj.toString();
        }
        entry.setString(faultMsg);
    }
    private NetworkTableEntry getEntry(CCSparkMax motor, DataType type) {
        return motorEntryMap.get(motor.getName()).get(type);
        
    }

    private void updateFaultStatus(CCSparkMax motor, DataType type) {
        if (type.equals(DataType.FAULTS)) {
            updateFaultStatus(getEntry(motor,type), motor);
        } else {
            updateStickyFaultStatus(getEntry(motor,type), motor);
        }
    }

    private void updateDoubleStatus(CCSparkMax motor, DataType type) {
        
        switch(type) {
            case TEMP:
                getEntry(motor,type).setDouble(motor.getMotorTemperature());
                break;
            case PDP:
                getEntry(motor,type).setString(Double.toString(motor.getEncoder().getPosition()));
                break;
            case VELOCITY:
                getEntry(motor,type).setDouble(motor.getEncoder().getVelocity());
                break;
            case VOLTAGE:
                System.out.println(motor.getAppliedOutput());
                getEntry(motor,type).setDouble(motor.getAppliedOutput());
            default:
                break;
        }
    }

    private void updateStatus(CCSparkMax motor, DataType type) {

        switch(type) {
            case FAULTS:
            case STICKY_FAULTS:
                updateFaultStatus(motor, type);
                break;
            case TEMP:
            case PDP:
            case VELOCITY:
                updateDoubleStatus(motor,type);
                break;
            case VOLTAGE: {
                double vol = motor.getAppliedOutput();
                getEntry(motor, type).setDouble(vol);
            }
            break;
        }
    }

    public void updateStatus() {
       
        int allFaults = 0;
        for (CCSparkMax motor : motors) {
            allFaults += motor.getFaults();
        }

        // boolean status
        faultEntry.setBoolean(allFaults == 0);

        // update status on SparkMax controllers
        for (CCSparkMax motor : motors) {
            for(DataType type : DataType.values()) {
                updateStatus(motor, type);
            }
        }
        
    }
}
