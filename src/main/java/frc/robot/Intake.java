package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.helpers.CCSparkMax;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;


public class Intake implements RobotMap {
    public static CCSparkMax sucky = new CCSparkMax("sucky", "suck", RobotMap.SUCKY, MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true);
    public static PneumaticsSystem intakeSols = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.INTAKE_SOLENOID_ONE, RobotMap.INTAKE_SOLENOID_TWO);
    public static void suck() {
        sucky.set(0.6);
    }

    public static void vomit() {
        sucky.set(-0.6);
    }

    public static void stop() {
        sucky.set(0);
    }

    /** 
     * Will run the intake based on what triggers are true or false
     *@param triggerOne what triggers intake with positive speed (takes precedence over triggerTwo). Suggest passing in a button or axis input
     *@param triggerTwo what triggers intake with negative speed. Suggest passing in a button or axis input
     *@param hardStop will set speed to 0 (takes precedence over triggers one and two)
     *@param speed the intake speed
    */
    public static void run(boolean triggerOne, boolean triggerTwo, boolean hardStop, double speed){
        if(hardStop){
            sucky.set(0);
            return;
        }
        if(triggerOne){
            sucky.set(speed);
            return;
        }
        if(triggerTwo){
            sucky.set(speed);
            return;
        }
        sucky.set(0);
    }

    public static void intakeArms(boolean set){
        intakeSols.set(set);
    }

    /** 
     * Toggles the intake arms.
    */
    public static void toggleIntake(){
        intakeSols.toggle();
    }

    /** 
     * Toggles the intake arms. Will only trigger again after trigger is false
     *@param trigger what will trigger the toggle. Suggest passing in a button or axis input.
    */
    public static void toggleIntake(boolean trigger){
        intakeSols.triggerSystem(trigger);
    }
}