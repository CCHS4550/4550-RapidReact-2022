package frc.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

public class BooleanSwitch extends ShuffleManager{

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
        // ShuffleboardTab t = Shuffleboard.getTab(title);
        // for(ShuffleboardComponent c : t.getComponents()){
        //     if(c.getTitle().equals(title)){

        //     }
        // }
        widget =
        Shuffleboard.getTab(tab)
            .add(title, defaultValue)
            .withWidget("Toggle Switch")
            .withPosition(pos.x, pos.y)
            .withSize(2, 1);
        entry = widget.getEntry();
        pos.translate(2, 0);
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
        System.out.print(pos);
        widget =
        Shuffleboard.getTab("Config")
            .add(title, defaultValue)
            .withWidget("Toggle Switch")
            .withPosition(pos.x, pos.y)
            .withSize(2, 1);
        entry = widget.getEntry();
        pos.translate(2, 0);
        if(pos.x >= 7) pos.setLocation(1, pos.y + 2);
    }

    public boolean value(){
        return entry.getBoolean(true);
    }
}