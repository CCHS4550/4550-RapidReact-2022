package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.parent.RobotMap;

public class BallDumpy implements RobotMap{
    //declares solenoids
    public static Solenoid dumpy = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.SHIFT_SOLENOID_ONE);

    public static void setFastMode(boolean on){
        dumpy.set(!on);
    }

    public static void moveDumpy(boolean up){
        dumpy.set(up);
    }
    
    public static void throwItBack(boolean back){
        dumpy.set(back);
    }
}