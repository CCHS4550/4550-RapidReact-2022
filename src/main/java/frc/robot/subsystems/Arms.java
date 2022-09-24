package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.helpers.CCSparkMax;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;

public class Arms extends SubsystemBase {
    private final PneumaticsSystem solenoids = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_ONE, RobotMap.ARM_SOLENOID_TWO);
    //private final CCSparkMax climber = new CCSparkMax("Climber", "C", RobotMap.CLIMBER, MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, true);

    public Arms(){
        //initialization (for stuff that has to do with declaration, the init method can handle action-y inits)
    }

    public void periodic(){
        //stuff that should run every frame, action stuff should be put in other methods
    }

    public void setSols(boolean on){
        solenoids.set(on);
    }

    public void toggleSols(){
        solenoids.toggle();
    }

    //need to add back elevator stuff & calibration stuff, will do when elevator is fixed and stuff can be tested
}
