
package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.parent.RobotMap;



public class Arms implements RobotMap {
 
    public static CCSparkMax climber = new CCSparkMax("Climber", "C",RobotMap.CLIMBER, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE);
    public static Solenoid arms = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.ARM_SOLENOID);
    public static boolean climberCont = true;

    public static void climberDown(){
        climber.set(-1);
    }
    public static void climberUp(){
        climber.set(1);
    }
    public static void climberStop(){
        climber.set(0);
    }
    public static void armsOut(){
        arms.set(true);
    }
    public static void armsIn(){
        arms.set(false);
    }
    public static void climbMonkeyBars(){
        if(!climberCont){

        }
    }
    public static void toggleCont(){
        climberCont = !climberCont;
    }
}


