package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.parent.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;


public class Intake implements RobotMap {
    //public static CCSparkMax sucky = new CCSparkMax("sucky", "suck", RobotMap.SUCKY, MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE);
    public static Solenoid intSol1 = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.INTAKE_SOLENOID_ONE);
    public static Solenoid intSol2 = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.INTAKE_SOLENOID_TWO);
    public static void suck() {
       // sucky.set(0.5);
    }

    public static void vomit() {
        //sucky.set(-0.5);
    }

    public static void stop() {
       // sucky.set(0);
    }

    public static void intakeArms(boolean set){
        intSol1.set(set);
        intSol2.set(!set);
    }
}