package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.helpers.CCSparkMax;
import frc.helpers.PneumaticsSystem;
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

    public static void toggleIntake(){
        intakeSols.toggle();
    }

    public static void toggleIntake(boolean trigger){
        intakeSols.triggerSystem(trigger);
    }
}