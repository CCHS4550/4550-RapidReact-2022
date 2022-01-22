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
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.parent.ControMap;
import frc.parent.RobotMap;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot implements ControMap{
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private static final String kResetPIDs = "Reset PIDs";
  private String m_autoSelected;
  //private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Compressor c = new Compressor(PneumaticsModuleType.REVPH);


  int alliance;
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
    // Chassis.reset();

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
    System.out.println("Auto selected: " + m_autoSelected);
    
    double dist = SmartDashboard.getNumber("Distance", 0);
    double angl = SmartDashboard.getNumber("Angle", 0);
    switch (m_autoSelected) {
      case kCustomAuto:
        break;
      case kDefaultAuto:
        Chassis.driveDist(dist, 0.05, 0.04, 0.25, false);
        Chassis.turnToAngle(angl, 0.005, 0.5, 0.25, false);
        break;
      case kResetPIDs:
        break;
      default:
        break;
    }

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // System.out.println("method teleopPeriodic() entry");
    Chassis.axisDrive(OI.axis(ControMap.L_JOYSTICK_VERTICAL),
                      OI.axis(ControMap.R_JOYSTICK_HORIZONTAL), 0.5);
if(Arms.climberCont){
      if (OI.axis(LT) > 0){
        Arms.climberLeftDown();
      }
      else if (OI.button(LB_BUTTON)){
        Arms.climberLeftUp();
      }
      if(OI.axis(RT) > 0){
        Arms.climberRightDown();
      } else if(OI.button(RB_BUTTON)){
        Arms.climberRightUp();
      } 
    }
    
    if(OI.button(B_BUTTON)){
      Arms.toggleCont();
      Arms.climbMonkeyBars();
    }

    if(OI.button(A_BUTTON))
      BallDumpy.dumpy.set(true);
    else
      BallDumpy.dumpy.set(false);





   /* //shoot slow with A
    if(OI.button(ControMap.A_BUTTON)){
      Chassis.setFastMode(true);
      Chassis.setFactor(0.048);
    }
    //shoot fast with B
    if (OI.button(ControMap.B_BUTTON)){  
      Chassis.setFastMode(false);
      Chassis.setFactor(0.109);
    }
    //climb with DPad
*/
   

    

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
