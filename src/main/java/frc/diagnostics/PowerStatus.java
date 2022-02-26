package frc.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import static frc.diagnostics.PowerDataType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PowerStatus implements DiagnosticsIF {

    private final PowerDataType[] displayedPower = { VOLTAGE, TEMP, CURRENT, ENERGY };

    private final ShuffleboardTab powerTab = Shuffleboard.getTab("Power");
    private final static int NUM_POWER_CHANNELS = 8;

    // key -> PowerDataType, value -> NetworkTableEntry
    private Map<PowerDataType, NetworkTableEntry> powerEntryMap = new HashMap<>();
    private List<NetworkTableEntry> powerChannels = new ArrayList<>();

    /* the power distribution panel */
    private PowerDistribution pdp = new PowerDistribution(0, PowerDistribution.ModuleType.kCTRE);
    
    @Override
    public void init() {

        for (PowerDataType p : displayedPower) {
            powerEntryMap.put(p, powerTab.add(p.getLabel(), p.getDefaultValue())
            .withWidget(p.getWidgetType())
            // .withPosition(col++, row)
            .withSize(p.getWidth(), 1)
            .withProperties(p.getProperties())
            .getEntry());
        }

        ShuffleboardLayout layout = powerTab.getLayout("Channel Current",  BuiltInLayouts.kGrid)
        .withSize(8, 2)
        .withProperties(Map.of(
            "Label position", "BOTTOM",
            "Number of Columns", displayedPower.length, // defines how many widgets in a row
            "Number of Rows", 2));

        int col = 0;
        for (int i = 0; i < NUM_POWER_CHANNELS; i++) {
            powerChannels.add(layout.add("Channel " + i, 0)
                    .withWidget(BuiltInWidgets.kNumberBar)
                    // .withPosition(col++, row)
                    .withPosition(col, i / 4)
                    .withProperties(Map.of("Min", 0, "Max", 10))
                    .withSize(1, 1)
                    .getEntry());
            col = col < 3 ? col+1 : 0;
        }

        // powerTab.add("power", pdp)
        //     .withWidget(BuiltInWidgets.kPowerDistribution)
        //     .withSize(4, 4);        
    }

    @Override
    public void updateStatus() {
         // update status of Power Distribution Panel
        for (PowerDataType type : displayedPower) {
            updatePowerStatus(type);
        }

        // update current for individual channels
        for (int i = 0, size = powerChannels.size(); i < size; i++) {
            updateCurrentStatus(i);
        }

        // powerEntry.setValue(pdp);
       
    }

    private void updatePowerStatus(PowerDataType dataType) {

        double value = 0.0;
        switch (dataType) {
            case VOLTAGE:
                value = pdp.getVoltage();
                break;
            case TEMP:
                value = pdp.getTemperature();
                break;
            case CURRENT:
                value = pdp.getTotalCurrent();
                break;
            case ENERGY:
                value = pdp.getTotalEnergy();
                break;
            default:
                System.err.println("Unsupported PowerDataType : " + dataType);
                return;
        }
        powerEntryMap.get(dataType).setDouble(value);
    }

    private void updateCurrentStatus(int channel) {
        if (channel < powerChannels.size()) {
            powerChannels.get(channel).setDouble(pdp.getCurrent(channel));
        } else {
            System.err.println("Invalid channel: " + channel);
        }
    }
}
