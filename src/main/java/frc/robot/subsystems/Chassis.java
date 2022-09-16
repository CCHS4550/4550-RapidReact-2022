package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.helpers.CCSparkMax;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Chassis extends SubsystemBase{
    private final CCSparkMax fLeft = new CCSparkMax("Front Left", "FL", RobotMap.FORWARD_LEFT, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.FORWARD_LEFT_REVERSE, true);

    private final CCSparkMax fRight = new CCSparkMax("Front Right", "FR", RobotMap.FORWARD_RIGHT, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.FORWARD_RIGHT_REVERSE, true);

    private final CCSparkMax bLeft = new CCSparkMax("Back Left", "BL",RobotMap.BACK_LEFT, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.BACK_LEFT_REVERSE, true);

    private final CCSparkMax bRight = new CCSparkMax("Back Right", "BR", RobotMap.BACK_RIGHT, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.BACK_RIGHT_REVERSE, true);

    private final PneumaticsSystem shift = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.SHIFT_SOLENOID_ONE, RobotMap.SHIFT_SOLENOID_TWO);

    //AHRS gyro measures the angle of the bot
    private final AHRS gyro = new AHRS(SPI.Port.kMXP);

    MotorControllerGroup left = new MotorControllerGroup(fLeft, bLeft);
    MotorControllerGroup right = new MotorControllerGroup(fRight, bRight);

    private final DifferentialDrive driver = new DifferentialDrive(left, right);

    public Chassis() {
        //probably need to do more initialization stuff, to be figured out with testing
    }
    
    public void drive(double x, double y){
        driver.arcadeDrive(y, x);
    }
    public void drive(Joystick controller, int drvPort, int turnPort){
        driver.arcadeDrive(controller.getRawAxis(drvPort), controller.getRawAxis(turnPort));
    }

    public void shiftTorque(boolean on){
        shift.set(on);
    }

    public void triggerTorque(boolean trigger){
        shift.triggerSystem(trigger);
    }

    public void toggleTorque(){
        shift.toggle();
    }
}
