package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.helpers.CCSparkMax;
import frc.helpers.OI;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;


public class Arms extends SubsystemBase {
    private final PneumaticsSystem solenoids = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_ONE, RobotMap.ARM_SOLENOID_TWO);
    private final CCSparkMax climber = new CCSparkMax("Climber", "C", RobotMap.CLIMBER, MotorType.kBrushless,
            IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, true) {
        public void set(double speed) {
            
        }
    };
    int bottom_port; //add num
    private final DigitalInput bottom = new DigitalInput(bottom_port);
    double topEncoder, bottomEncoder;
    private PIDController climberPid = new PIDController(0.5, 0, 0.05);
    private double encoderPos;
    private boolean positioning = true;


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


    public void setSpeed(double speed) {
        if (bottom.get() && speed < 0) {
            climber.set(0);
            climber.reset();
            encoderPos = 0;
        }else if(OI.normalize(climber.getPosition() * climber.getPositionConversionFactor(), 0, 1) >= 1 && speed > 0){
            climber.set(0);
        } else {
            climber.set(speed);
        }
    }

    //make sure climber is at top positon before calling 
    public void calibrate() {
        topEncoder = climber.getPosition();
        if (!bottom.get()) {
            climber.set(-.5); // change to positive if needed for reverse
        } else {
            climber.set(0);
            bottomEncoder = climber.getPosition();
            climber.reset();
            encoderPos = 0;
        }
        // climber.setPosition(bottomEncoder);
        climber.setPositionConversionFactor(1 / (topEncoder - bottomEncoder));
        System.out.println(climber.getPositionConversionFactor());
    }
    
    // public void setSpeed(double speed) {
    //     positioning = false;
    //     climber.set(speed);
    // }

    public void goToPosition() {
        if (positioning)
            climber.set(OI.normalize(climberPid.calculate(climber.getPosition(), encoderPos), -1, 1));
    }

    public void setPos() {
        encoderPos = climber.getPosition();
    }

    public void setPos(double pos) {
        encoderPos = pos;
    }

    public void changePos(double val) {
        positioning = true;
        encoderPos += val;
    }
}
