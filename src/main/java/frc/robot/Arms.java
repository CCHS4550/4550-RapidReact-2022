package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.parent.RobotMap;


public class Arms implements RobotMap {
 
    public static CCSparkMax climber = new CCSparkMax("Climber", "C", RobotMap.CLIMBER, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE);
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

    public static void toggleArms(){
        armSols.toggle();
    }

    
}
