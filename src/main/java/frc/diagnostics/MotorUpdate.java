package frc.diagnostics;

import frc.helpers.*;

import java.util.Map;
import java.util.StringJoiner;
import java.util.ArrayList;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.FaultID;

import edu.wpi.first.networktables.NetworkTableEntry;

public class MotorUpdate {

    private Map<String, Map<MotorDataType, NetworkTableEntry>> motorEntryMap;
    private ArrayList<CCSparkMax> motors;
    private NetworkTableEntry faultEntry;
    private MotorDataType[] displayedData;

    public MotorUpdate(Map<String, Map<MotorDataType, NetworkTableEntry>> motorEntryMap,
        ArrayList<CCSparkMax> motors, NetworkTableEntry faultEntry, MotorDataType[] displayedData) {
        this.motorEntryMap = motorEntryMap;
        this.motors = motors;
        this.faultEntry = faultEntry;
        this.displayedData = displayedData;
    }

    public static void updateFaultStatus(NetworkTableEntry entry, CCSparkMax motor) {
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

    public void updateStickyFaultStatus(NetworkTableEntry entry, CCSparkMax motor) {
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

    private void updateFaultStatus(CCSparkMax motor, MotorDataType type) {
        if (type.equals(MotorDataType.FAULTS)) {
            updateFaultStatus(getEntry(motor,type), motor);
        } else {
            updateStickyFaultStatus(getEntry(motor,type), motor);
        }
    }

    public NetworkTableEntry getEntry(CCSparkMax motor, MotorDataType type) {
        return motorEntryMap.get(motor.getName()).get(type);
    }
    
    public void updateDoubleStatus(CCSparkMax motor, MotorDataType type) {
        switch(type) {
            case TEMP:
                getEntry(motor,type).setDouble(motor.getMotorTemperature());
                break;
            case POSITION:
                getEntry(motor,type).setString(Double.toString(motor.getEncoder().getPosition()));
                break;
            case VELOCITY:
                getEntry(motor,type).setDouble(motor.getEncoder().getVelocity());
                break;
            default:
                break;
        }
    }

    public void updateStatus(CCSparkMax motor, MotorDataType type) {

        switch(type) {
            case FAULTS:
            case STICKY_FAULTS:
                updateFaultStatus(motor, type);
                break;
            case TEMP:
            case POSITION:
            case VELOCITY:
                updateDoubleStatus(motor,type);
                break;
            case INVERTED_STATE: {
                String msg = motor.getInverted() ? "inverted" : "";
                getEntry(motor, type).setString(msg);
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
            for(MotorDataType type : displayedData) {
                updateStatus(motor, type);
            }
        }
        
    }
}
