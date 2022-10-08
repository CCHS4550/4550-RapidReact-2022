package frc.robot.subsystems;

import java.util.Set;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.helpers.CCSparkMax;
import frc.helpers.OI;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Chassis extends SubsystemBase{
    private final CCSparkMax fLeft = new CCSparkMax("Front Left", "FL", RobotMap.FORWARD_LEFT, 
        MotorType.kBrushless, IdleMode.kCoast, RobotMap.FORWARD_LEFT_REVERSE, true);

    private final CCSparkMax fRight = new CCSparkMax("Front Right", "FR", RobotMap.FORWARD_RIGHT, 
        MotorType.kBrushless, IdleMode.kCoast, RobotMap.FORWARD_RIGHT_REVERSE, true);

    private final CCSparkMax bLeft = new CCSparkMax("Back Left", "BL",RobotMap.BACK_LEFT, 
        MotorType.kBrushless, IdleMode.kCoast, RobotMap.BACK_LEFT_REVERSE, true);

    private final CCSparkMax bRight = new CCSparkMax("Back Right", "BR", RobotMap.BACK_RIGHT, 
        MotorType.kBrushless, IdleMode.kCoast, RobotMap.BACK_RIGHT_REVERSE, true);

    private final PneumaticsSystem shift = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.SHIFT_SOLENOID_ONE, RobotMap.SHIFT_SOLENOID_TWO);

    //AHRS gyro measures the angle of the bot
    private final AHRS gyro = new AHRS(SPI.Port.kMXP);

    MotorControllerGroup left = new MotorControllerGroup(fLeft, bLeft);
    MotorControllerGroup right = new MotorControllerGroup(fRight, bRight);

    private final DifferentialDrive driver = new DifferentialDrive(left, right);

    public Chassis() {
        //0.048 slow, 0.109 fast
        double factor = 0.048;
        fLeft.setPositionConversionFactor(factor);
        fRight.setPositionConversionFactor(factor);
        bLeft.setPositionConversionFactor(factor);
        bRight.setPositionConversionFactor(factor);
    }
    
    public void drive(double fwd, double bkd){
        driver.arcadeDrive(fwd, bkd);
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

    public void reset(){
        fLeft.reset();
        fRight.reset();
        bLeft.reset();
        bRight.reset();
    }

    public double[] getEncoders(){
        double[] res = {fLeft.getPosition(), fRight.getPosition(), bLeft.getPosition(), bRight.getPosition()};
        return res;
    }

    public static Command driveDist(double dist, Chassis driver){
        driver.reset();
        PIDController pid = new PIDController(0.25, 0, 0);
        return new RunCommand(() -> driver.drive(OI.normalize(pid.calculate(driver.getEncoders()[0], dist), -1, 1), 0), driver){
            @Override
            public boolean isFinished(){
                if(Math.abs(driver.getEncoders()[0] - dist) < 0.5) pid.close();
                return Math.abs(driver.getEncoders()[0] - dist) < 0.5;
            }
        };
    }
}
