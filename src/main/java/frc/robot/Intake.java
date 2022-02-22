package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.parent.RobotMap;


public class Intake implements RobotMap {
    public static CCSparkMax sucky = new CCSparkMax("sucky", "suck", RobotMap.SUCKY, MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, 69);
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

    public static void intakeArms(boolean set){
        intakeSols.set(set);
    }

    public static void toggleIntake(){
        intakeSols.toggle();
    }
}