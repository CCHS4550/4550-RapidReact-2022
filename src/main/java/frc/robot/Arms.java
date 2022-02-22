package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.helpers.CCSparkMax;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;


public class Arms implements RobotMap {
 
    public static CCSparkMax climber = new CCSparkMax("Climber", "C", RobotMap.CLIMBER, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, false);
    public static PneumaticsSystem armSols = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_ONE, RobotMap.ARM_SOLENOID_TWO);
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

    public static void setArms(boolean on){
        armSols.set(on);
    }

    /** 
     * Toggles the climbing arms.
    */
    public static void toggleArms(){
        armSols.toggle();
    }

    /** 
     * Will run the elevator based on what triggers are true or false
     *@param triggerOne what triggers the elevator with positive speed (takes precedence over triggerTwo). Suggest passing in a button or axis input.
     *@param triggerTwo what triggers the elevator with negative speed. Suggest passing in a button or axis input.
     *@param hardStop will set speed to 0 (takes precedence over triggers one and two)
     *@param speed the elevator speed
    */
    public static void runElevator(boolean upTrigger, boolean downTrigger, boolean hardStop, double speed){
        if(hardStop){
            climber.set(0);
            return;
        }
        if(upTrigger){
            climber.set(speed);
            return;
        }
        if(downTrigger){
            climber.set(-speed);
            return;
        }
        climber.set(0);
    }

    /** 
     * Toggles the climbing arms. Will only trigger again after trigger is false
     *@param trigger what will trigger the toggle
    */
    public static void toggleArms(boolean trigger){
        armSols.triggerSystem(trigger);
    }
    
}
