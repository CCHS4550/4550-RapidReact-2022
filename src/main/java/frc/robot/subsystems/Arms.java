package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.helpers.CCSparkMax;
import frc.helpers.OI;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;


public class Arms extends SubsystemBase {
    private final PneumaticsSystem solenoids = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_ONE, RobotMap.ARM_SOLENOID_TWO);
    private final CCSparkMax climber = new CCSparkMax("Climber", "C", RobotMap.CLIMBER, MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, true);
    int bottom_port; //add num
    private final DigitalInput bottom = new DigitalInput(bottom_port);
    double topEncoder, bottomEncoder;

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
    public void extend() {
        if(OI.normalize(climber.getPosition() * climber.getPositionConversionFactor(), 0, 1) >= 1){
            climber.set(0);
        }else{
            climber.set(0.2); //can change to negative depending on reverse of motor. 
        }
    }

    public void retract() {
        if (OI.normalize(climber.getPosition() * climber.getPositionConversionFactor(), 0, 1) <= 0) {
            climber.set(0);
        } else {
            climber.set(-0.2);
        }
    }

    //make sure climber is at top positon before calling 
    public void calibrate() {
        topEncoder = climber.getPosition();
        if(!bottom.get()) {
            climber.set(-.5); // change to positive if needed for reverse
        } else {
            climber.set(0);
            bottomEncoder = climber.getPosition();
            climber.reset();
        }
        // climber.setPosition(bottomEncoder);
        climber.setPositionConversionFactor(1/(topEncoder-bottomEncoder));
    }
}
