package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.diagnostics.BooleanDisplay;
import frc.helpers.CCSparkMax;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;

public class Arms extends SubsystemBase {
    private final PneumaticsSystem solenoids = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.ARM_SOLENOID_ONE, RobotMap.ARM_SOLENOID_TWO);
    private final CCSparkMax climber = new CCSparkMax("Climber", "C", RobotMap.CLIMBER, MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE, true){
        public void set(double value){
            if(calibrated && !override && !canceled){
                if(value < 0 && climber.getPosition() > 1){
                    super.set(0);
                    return;
                }
                if(value > 0 && limit.get()){
                    super.set(0);
                    if(!override){
                        calibrated = true;
                        climber.reset();
                        pos = 0;
                    }
                    return;
                }
            }
            super.set(value);
        }
    };

    private boolean calibrated = false;
    private boolean override = false;
    private boolean calibrate = true;
    private boolean canceled = true;
    private double pos = 0;

    private DigitalInput limit = new DigitalInput(RobotMap.ELEVATOR_SWITCH);

    private BooleanDisplay limitDisp = new BooleanDisplay("Limit Switch", false);
    public Arms(){
        //initialization (for stuff that has to do with declaration, the init method can handle action-y inits)
    }

    public void periodic(){
        //stuff that should run every frame, action stuff should be put in other methods
        limitDisp.set(limit.get());
        System.out.println(climber.getPosition());
    }

    public void setSols(boolean on){
        solenoids.set(on);
    }

    public void toggleSols(){
        solenoids.toggle();
    }


    public void calibrate(){
        if(canceled || calibrated) return;
        if(!calibrate){
            if(limit.get()){
                climber.set(0);
                climber.reset();
                pos = 0;
                calibrated = true;
            }
        } else if(!limit.get()){
            climber.set(0.5);
        } else {
            climber.set(0);
            climber.reset();
            pos = 0;
            calibrated = true;
        }
    }

    public void setSpeed(double value){
        if(!calibrated) calibrate = false;
        climber.set(value);
    }
}
