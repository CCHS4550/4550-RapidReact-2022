package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import frc.helpers.CCSparkMax;
import frc.parent.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.helpers.*;


public class Intake extends SubsystemBase implements RobotMap {
    public static void nothing(){
        // if(!Robot.set){
        //     intake.reset();
        //     Robot.set = true;
        //     position = 0;
        //     in = true;
        // }
    }
    public  CCSparkMax sucky = new CCSparkMax("sucky", "suck", RobotMap.SUCKY,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true);

    public  CCSparkMax intake = new CCSparkMax("intake", "in", RobotMap.INTAKE_IN,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true);

    public  double position = 0;
    public  boolean in = true;

    public  double speed = 1;


    /** 
     * Will run the intake based on what triggers are true or false
     *@param triggerOne what triggers intake with positive speed (takes precedence over triggerTwo). Suggest passing in a button or axis input
     *@param triggerTwo what triggers intake with negative speed. Suggest passing in a button or axis input
     *@param hardStop will set speed to 0 (takes precedence over triggers one and two)
     *@param speed the intake speed
    */
    public  void run(boolean triggerOne, boolean triggerTwo, boolean hardStop, double speed){
        if(hardStop){
            sucky.set(0);
            return;
        }
        if(triggerOne){
            sucky.set(speed);
            return;
        }
        if(triggerTwo){
            sucky.set(-0.5 * speed);
            return;
        }
        sucky.set(0);
    }

    public  void suck(double speed){
        sucky.set(speed);
    }

    

    public void setIn(boolean set){
        in = set;
        position = in ? 0 : -1.1;
    }

    /** 
     * Toggles the intake arms.
    */
    public void toggleIntake(double speed){
        in = !in;
        position = in ? 0 : -0.95;
        this.speed = speed;
    }

    // public static void autoSetIntake(boolean set){
    //     in = set;
    //     position = in ? 0 : -1.14;
    //     while(DriverStation.isAutonomous()){
    //         intake.set(OI.normalize(spd.calculate(intake.getPosition(), position), -1, 1));
    //         intake.setPositionConversionFactor(0.10686979799148262178959147156001);
    //         if(intake.getPosition() - position < 0.03) break;
    //     }
    // }

    /** 
     * Toggles the intake arms. Will only trigger again after trigger is false
     *@param trigger what will trigger the toggle. Suggest passing in a button or axis input.
    */
    // public static Trigger t = new Trigger();
    // public static PIDController spd = new PIDController(0.5, 0, 0.01);
    // public static void toggleIntake(boolean trigger){
    //     if(t.trigger(trigger)){
    //         in = !in;
    //         position = in ? 0 : -1.05;
    //     }
    //     intake.set(OI.normalize(spd.calculate(intake.getPosition(), position), -1, 1));
    //     intake.setPositionConversionFactor(0.10686979799148262178959147156001);
    // }
}