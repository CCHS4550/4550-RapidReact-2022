package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.helpers.CCSparkMax;
import frc.parent.RobotMap;

import frc.helpers.*;


public class Intake implements RobotMap {
    public static void nothing(){
        intake.reset();
    }
    public static CCSparkMax sucky = new CCSparkMax("sucky", "suck", RobotMap.SUCKY,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true);

    public static CCSparkMax intake = new CCSparkMax("intake", "in", RobotMap.INTAKE_IN,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true);

    public static double position = 0;
    public static boolean in = true;

    public static double speed = 1;


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
            sucky.set(-speed);
            return;
        }
        sucky.set(0);
    }

    public static void suck(double speed){
        sucky.set(speed);
    }

    

    public static void intakeArms(boolean set){
        in = set;
        position = in ? 0 : 1;
    }

    /** 
     * Toggles the intake arms.
    */
    public static void toggleIntake(double speed){
        in = !in;
        position = in ? 0 : 1;
        Intake.speed = speed;
    }

    /** 
     * Toggles the intake arms. Will only trigger again after trigger is false
     *@param trigger what will trigger the toggle. Suggest passing in a button or axis input.
    */
    public static Trigger t = new Trigger();
    public static void toggleIntake(boolean trigger, double speed){
        if(t.trigger(trigger)){
            in = !in;
            position = in ? 0 : 1;
            Intake.speed = speed;
        }
        double set = 0;
        if(Math.abs(intake.getPosition() - position) > 0.02) set = -Math.abs(intake.getPosition() - position) / (intake.getPosition() - position);
        intake.set(OI.normalize(set, -1, 1));
    }
}