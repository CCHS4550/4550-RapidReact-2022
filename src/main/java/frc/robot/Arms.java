package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.helpers.CCSparkMax;
import frc.helpers.OI;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;

import frc.helpers.*;

//add something to check encoder rate of change
//if it slows down for over 1 second, stop or warn with rumble
public class Arms implements RobotMap {
 
    public static CCSparkMax climber = new CCSparkMax("Climber", "C", RobotMap.CLIMBER, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, true);

    public static PneumaticsSystem armSols = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_ONE, RobotMap.ARM_SOLENOID_TWO);
    public static double position;

    public static void nothing(){
        climber.setPositionConversionFactor(1 / ARM_ENCODER_HIGH);
        // position = climber.getPosition();
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
     *@param upTrigger what triggers the elevator with positive speed (takes precedence over triggerTwo). Suggest passing in a button or axis input.
     *@param downTrigger what triggers the elevator with negative speed. Suggest passing in a button or axis input.
     *@param hardStop will set speed to 0 (takes precedence over triggers one and two)
     *@param speed the elevator speed
    */
    public static void runElevator(boolean upTrigger, boolean downTrigger, boolean hardStop, double speed){
        if(!calibrated) return;
        // if a trigger is set, set pos to the right encoder to stop the elevator from going
        // otherwise move in the direction of the set pos
        if(hardStop){
            climber.set(0);
            return;
        }
        if(upTrigger){
            // if(calibrated) position = climber.getPosition();
            Robot.setPos = false;
            climber.set(speed);
            return;
        }
        if(downTrigger){
            Robot.setPos = false;
            if(climber.getPosition() <= -1.05 && calibrated) {
                return;
            }
            // if(calibrated) position = climber.getPosition();
            climber.set(-speed);
            return;
        }
        if(Robot.setPos) {
            Arms.setPosition(-0.1764);
            Arms.moveToPos();
        } else {
            double set = 0;
            //if(Math.abs(climber.getPosition() - position) > 0.02 && calibrated) set = -Math.abs(climber.getPosition() - position) / (climber.getPosition() - position);
            climber.set(OI.normalize(set, -speed, speed));
        }
    }

    /** 
     * Will run the elevator based on what triggers are true or false
     *@param upTrigger what triggers the elevator with positive speed (takes precedence over triggerTwo). Suggest passing in a button or axis input.
     *@param downTrigger what triggers the elevator with negative speed. Suggest passing in a button or axis input.
     *@param hardStop will set speed to 0 (takes precedence over triggers one and two)
     *@param speed the elevator speed
     *@param controller which joystick will vibrate when the elevator hits the max or min
    */
    public static Timer timer = new Timer(0.2);
    public static boolean down = false;
    public static boolean up = false;
    public static DigitalInput limit = new DigitalInput(RobotMap.ELEVATOR_SWITCH);
    
    public static void runElevator(boolean upTrigger, boolean downTrigger, boolean hardStop, double speed, Joystick controller, boolean override){
        //if(!calibrated) return;
        // if a trigger is set, set pos to the right encoder to stop the elevator from going
        // otherwise move in the direction of the set pos
        if(hardStop){
            climber.set(0);
            return;
        }
        if(upTrigger){
            Robot.setPos = false;
            if(limit.get() && !override){
                calibrated = true;
                Robot.calibrated.set(true);
                position = 0;
                climber.reset();
                if(!down){
                    down = true;
                    controller.setRumble(RumbleType.kRightRumble, 1);
                    controller.setRumble(RumbleType.kLeftRumble, 1);
                    timer.reset();
                    timer.start();
                }
                climber.set(0);
                return;
            } else {
                down = false;
            }
            //if(calibrated) position = climber.getPosition();
            climber.set(speed);
            return;
        }
        if(downTrigger){
            Robot.setPos = false;
            if(climber.getPosition() <= -1.05 && calibrated & !override) {
                if(!up){
                    timer.reset();
                    timer.start();
                    controller.setRumble(RumbleType.kRightRumble, 1);
                    controller.setRumble(RumbleType.kLeftRumble, 1);
                    up = true;
                }
                climber.set(0);
                return;
            } else {
                up = false;
            }
            // if(calibrated) position = climber.getPosition();
            climber.set(-speed);
            return;
        }
        if(timer.triggered()){
            controller.setRumble(RumbleType.kRightRumble, 0);
            controller.setRumble(RumbleType.kLeftRumble, 0);
        }
        if(Robot.setPos) {
            Arms.setPosition(-0.1764);
            Arms.moveToPos();
        } else {
            double set = 0;
            //if(Math.abs(climber.getPosition() - position) > 0.02 && calibrated) set = -Math.abs(climber.getPosition() - position) / (climber.getPosition() - position);
            climber.set(OI.normalize(set, -speed, speed));
        }
    }

    /** 
     * Toggles the climbing arms. Will only trigger again after trigger is false
     *@param trigger what will trigger the toggle. Suggest passing in a button or axis input.
    */
    public static void toggleArms(boolean trigger){
        armSols.triggerSystem(trigger);
    }

    public static final double ARM_ENCODER_HIGH = 1;
    //public static DigitalInput limit = new DigitalInput(RobotMap.ELEVATOR_SWITCH);
    public static boolean calibrated = false;
    public static boolean calibrate(){
        if(calibrated) return true;
        if(!calibrated){
            climber.set(.5);
        }
        if(limit.get() && !calibrated){
            climber.set(0);
            calibrated = true;
            //position = 0;
            climber.reset();
            return true;
        }
        return false;
    }

    /**
     * 
     * @param position a value from 0-1, with 0 being all the way down, and 1 being all the way up
     */
    // public static void setPosition(double position){
    //     if(calibrated);
    //     Arms.position = OI.normalize(position, -1, 0);
    // }

    public static void setPosition(double pos){
        if(calibrated)
            position = pos;
    }

    public static void moveToPos(){
        if(!calibrated) return;
        if(position >= -50){
            double set = 0;
            if(Math.abs(climber.getPosition() - position) > 0.02 && calibrated) set = -Math.abs(climber.getPosition() - position) / (climber.getPosition() - position);
            climber.set(OI.normalize(set, -1, 1));
        } else if(!limit.get()) {
            climber.set(1);
        }
    }

    public static boolean atPos(){
        if(position >= -50) return !(Math.abs(climber.getPosition() - position) > 0.02 && calibrated);
        return limit.get();
    }

    public static void autoSetPos(double set){
        if(!calibrated) return;
        setPosition(set);
        do {
            if(atPos()) return;
            if(set < -50){
                if(!limit.get()){
                    climber.set(.5);
                } else {
                    break;
                }
            } else {
                moveToPos();
            }
        } while(!Arms.atPos() && DriverStation.isAutonomous() && calibrated);
    }
    
}
