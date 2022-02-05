
package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.parent.RobotMap;



public class Arms implements RobotMap {
 
<<<<<<< Updated upstream
    public static CCSparkMax climberLeft = new CCSparkMax("Climber Left", "CL",RobotMap.CLIMBER_LEFT, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE);
    public static CCSparkMax climberRight = new CCSparkMax("Climber Right", "CR",RobotMap.CLIMBER_RIGHT, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_RIGHT_REVERSE);
    public static boolean climberCont = true;

    public static void climberLeftDown(){
        climberLeft.set(-1);
=======
    public static CCSparkMax climber = new CCSparkMax("Climber", "C",RobotMap.CLIMBER, 
        MotorType.kBrushed, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, 420.69f);
    public static Solenoid armsOne = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_ONE);
    public static Solenoid armsTwo = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_TWO);
    public static boolean climberCont = true;

    public static void climberDown(){
        climber.set(0.1);
    }
    public static void climberUp(){
        climber.set(-0.1);
>>>>>>> Stashed changes
    }
    public static void climberRightDown(){
        climberRight.set(-1);
    }
<<<<<<< Updated upstream

    public static void climberLeftUp(){
        climberLeft.set(1);
    }

    public static void climberRightUp(){
        climberRight.set(1);
=======
    /*
    public static void armsOut(){
        arms.set(true);
    }
    public static void armsIn(){
        arms.set(false);
    }*/
    public static void setArms(boolean on){
        armsOne.set(on);
        armsTwo.set(!on);
>>>>>>> Stashed changes
    }
    public static void climbMonkeyBars(){
        if(!climberCont){

        }
    }
    public static void toggleCont(){
        climberCont = !climberCont;
    }
}


