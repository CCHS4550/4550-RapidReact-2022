
package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.parent.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;


public class Arms implements RobotMap {
 
    public static CCSparkMax climber = new CCSparkMax("Climber", "C", RobotMap.CLIMBER, 
        MotorType.kBrushed, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, 420.69f);
    public static Solenoid armsOne = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_ONE);
    public static Solenoid armsTwo = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_TWO);
    public static boolean climberCont = true;

    public static void climberDown(){
        climber.set(0.1);
    }
    public static void climberUp(){
        climber.set(-0.1);
    }
    public static void climberStop(){
        climber.set(0);
    }

    public static void climberSet(double spd){
        climber.set(spd);
    }
    /*
    public static void armsOut(){
        armsOne.set(true);
    }
    public static void armsIn(){
        arms.set(false);
    }*/
    public static void setArms(boolean on){
        armsOne.set(on);
        armsTwo.set(!on);
    }
    public static void climbMonkeyBars(){
        if(!climberCont){

        }
    }
    public static void toggleCont(){
        climberCont = !climberCont;
    }
}


