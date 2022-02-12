package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.parent.RobotMap;
//import edu.wpi.first.wpilibj.Solenoid;

public class Shoot {
    public static CCSparkMax intake = new CCSparkMax("Intake", "in", RobotMap.INTAKE, 
        MotorType.kBrushed, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, 420.69f);
    // public static Solenoid armsOne = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_ONE);
    // public static Solenoid armsTwo = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_TWO);
    public static CCSparkMax indexer = new CCSparkMax("Indexer", "index", RobotMap.INDEXER, 
    MotorType.kBrushed, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, 420.69f);
    public static void setIntake(float spd){
        intake.set(spd);
    }
    public static void setIndexer(float spd){
        indexer.set(spd);
    }
}
