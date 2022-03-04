package frc.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

import java.awt.Point;

public class BooleanSwitch{
    private Point pos = ShuffleManager.pos;

    private SimpleWidget widget;
    private NetworkTableEntry entry;

    /**
     * 
     * @param tab
     * @param title
     * @param defaultValue
     * @param min
     * @param max
     */
    public BooleanSwitch(String tab, String title, Boolean defaultValue){
        widget =
        Shuffleboard.getTab(tab)
            .add(title, defaultValue)
            .withWidget("Toggle Switch")
            .withPosition(pos.x, pos.y)
            .withSize(2, 1);
        entry = widget.getEntry();
        pos.move(2, 0);
        if(pos.x >= 7) pos.setLocation(0, pos.y + 1);
    }

    /**
     * 
     * @param title
     * @param defaultValue
     * @param min
     * @param max
     */
    public BooleanSwitch(String title, Boolean defaultValue){
        widget =
        Shuffleboard.getTab("Config")
            .add(title, defaultValue)
            .withWidget("Toggle Switch")
            .withPosition(pos.x, pos.y)
            .withSize(2, 1);
        entry = widget.getEntry();
        pos.move(2, 0);
        if(pos.x >= 7) pos.setLocation(1, pos.y + 2);
    }

    public double value(){
        return entry.getDouble(0);
    }
}