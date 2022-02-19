/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//testing
package frc.robot;
import edu.wpi.first.wpilibj.Compressor;
// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.parent.ControlMap;
import frc.parent.RobotMap;
import frc.raspi.Vision;
//import frc.raspi.Vision;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
//import edu.wpi.first.wpilibj.Solenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot implements ControlMap{
  // private static final String kDefaultAuto = "Default";
  // private static final String kCustomAuto = "My Auto";
  // private static final String kResetPIDs = "Reset PIDs";
  // private String m_autoSelected;
  //private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Compressor c = new Compressor(PneumaticsModuleType.CTREPCM);
  public Boolean armExtended = false;
  public Boolean armPressed = false;
  //Vision vision = new Vision("Camera 1");

  public int alliance;
  double spdmlt = 1;
 
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    
   // diagnostics = new Diagnostics2(Chassis.fLeft, Chassis.fRight, Chassis.bLeft, Chassis.bRight, Chassis.climberLeft, Chassis.climberRight);
    // m_chooser.addOption("My Auto", kCustomAuto);
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("Reset PID Values", kResetPIDs);
    // SmartDashboard.putNumber("Distance", 0.0);
    // SmartDashboard.putNumber("Angle", 0.0);
    // SmartDashboard.putData("Auto choices", m_chooser);
    Chassis.reset();

    switch(DriverStation.getAlliance()){
      case Blue:
        alliance = 1;
      break;

      case Red:
        alliance = 0; 
      break;
      
      case Invalid:
        alliance = -1;
      break;
    }
    Vision.setPipeline(alliance);
    

  }

  
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
    
    if(RobotMap.COMPRESSOR_ENABLE)
      c.enableDigital();
    else 
      c.disable();
  }
//stage deez
  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    Chassis.reset();
    // System.out.println("Auto selected: " + m_autoSelected);
    
    // double dist = SmartDashboard.getNumber("Distance", 0);
    // double angl = SmartDashboard.getNumber("Angle", 0);
    // switch (m_autoSelected) {
    //   case kCustomAuto:
    //     break;
    //   case kDefaultAuto:
    //     Chassis.driveDist(dist, 0.05, 0.04, 0.25, false);
    //     Chassis.turnToAngle(angl, 0.005, 0.5, 0.25, false);
    //     break;
    //   case kResetPIDs:
    //     break;
    //   default:
    //     break;
    // }

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Chassis.axisDrive(0.3, 0, 0.3);
  }

  @Override
  public void teleopInit() {
    
  }
  /**
   * This function is called periodically during operator control.
   */
  public double deltaTime = 0.02;
  public double decelTimeSlow = 4;
  public double decelTimeFast = 1;
  public double decelTime = decelTimeSlow;
  public double velocity = 0;
  public boolean aimPressed = false;
  public double aimAng = 0;
  public double camWidth = 50;
  public double lastYaw = 69;
  public boolean amongus = true;
  @Override
  public void teleopPeriodic() {

    if(OI.button(0, ControlMap.Y_BUTTON)){
      //if yaw isn't just angle this might work
      if(!amongus){
        //if there's no target, do nothing
        if(Vision.getYaw() == null) return;
        if(!aimPressed){
          aimPressed = true;
          lastYaw = Vision.getYaw();
          //if it's the first frame, initialize yaw
        } else if(Math.abs(Vision.getYaw()) <= lastYaw) {
          //only turn if the current yaw is closer to 0 than the previous (may have to update this every few frames instead)
          Chassis.axisDrive(0, Vision.getYaw() / Math.abs(Vision.getYaw()), 0.25);
          lastYaw = Math.abs(Vision.getYaw());
        }
      } else {
        //if yaw is just angle this should work I hope
        if(!aimPressed){
          //if there's no target, do nothing
          if(Vision.getYaw() == null) return;
          //if there's a target, reset the gyro and get the angle to turn to (depending on how negatives work, may have to add or subtract 360)
          Chassis.reset();
          aimAng = Vision.getYaw();
          if(aimAng < 0) aimAng += 360;
          aimPressed = true;
          System.out.println(Chassis.getAngle() + " " + aimAng);
        } else {
          //if we're not within 5 degrees, turn in the directon of the aimed angle
          if(Chassis.getAngle() - aimAng >= 1){
            Chassis.axisDrive(0, Math.abs(Chassis.getAngle() - aimAng) / (Chassis.getAngle() - aimAng), 0.25);
          }
        }
      }
    } else {
      aimPressed = false;
    }

    //Emergency brake
    decelTime = OI.button(0, ControlMap.LB_BUTTON) ? decelTimeFast : decelTimeSlow;

    //System.out.println("method teleopPeriodic() entry");
    double joystick = OI.axis(0, ControlMap.L_JOYSTICK_VERTICAL);
    if(Math.abs(joystick - velocity) < 0.05) velocity = joystick; 
    if(joystick - velocity != 0) velocity += (joystick - velocity) * deltaTime / Math.abs(joystick - velocity) / decelTime;
    //System.out.println(OI.axis(0, ControlMap.R_JOYSTICK_HORIZONTAL) + " " + velocity);
    Chassis.axisDrive(velocity, OI.axis(0, ControlMap.R_JOYSTICK_HORIZONTAL) * 0.25, 1);
    if(true /*Arms.climberCont*/){
      // if (OI.button(0, A_BUTTON)){
      //   System.out.println("Elevator down");
      //   Arms.climberDown();
      // }
      // else if (OI.button(0, B_BUTTON)){
      //   System.out.println("Elevator up");
      //   Arms.climberUp();
      // } else {
      //   Arms.climberStop();
      // }
      if(OI.button(1, Y_BUTTON)){
        // Button pressed for first time
        if (!armPressed) {
          armPressed = true;
          armExtended = !armExtended;
          Arms.setArms(armExtended);
          System.out.println("Arms up");
        }
      } else if (armPressed) {
        // Button released
        armPressed = false;
      }

      //shoot slow with A
      if(OI.button(1, ControlMap.A_BUTTON)){
        Chassis.setFastMode(true);
        Chassis.setFactor(0.048);
      }
      //shoot fast with B
      if (OI.button(1, ControlMap.B_BUTTON)){  
        Chassis.setFastMode(false);
        Chassis.setFactor(0.109);
      }
    }
    //climb with DPad

  }

  /**
   * This function is called right after disabling
   */
  @Override
  public void disabledInit() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

}
