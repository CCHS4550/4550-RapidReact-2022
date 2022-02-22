/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//testing
package frc.robot;
//import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.Compressor;
// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.helpers.OI;
import frc.helpers.Timer;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.parent.ControlMap;
import frc.parent.RobotMap;
//import frc.raspi.Vision;
//import frc.raspi.Vision;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Solenoid;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

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
  //Vision vision = new Vision("Camera 1");

  NetworkTableEntry switchEntry;
  NetworkTableInstance inst;
  NetworkTable table;

  public int alliance;
  double spdmlt = 1;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    inst = NetworkTableInstance.getDefault();
    table = inst.getTable("switch");

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
    //Vision.setPipeline(alliance);


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
    //System.out.println("Auto selected: " + m_autoSelected);

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
    //if(!Chassis.driveDistPeriodic(5, 0.1, 0.5, 0.5, 0)) return;
  }

  @Override
  public void teleopInit() {

  }
  /**
   * This function is called periodically during operator control.
   */
  public double decelTime = 4;
  public double decelTimeFast = 0.5;
  public double decelTimeSlow = 4;

  public double velocity = 0;
  public double deltaTime = 0.02;

  public boolean aimPressed = false;
  public double lastAim = 0;

  public Solenoid sol0 = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
  public Solenoid sol1 = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
  public Solenoid sol2 = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
  public Solenoid sol3 = new Solenoid(PneumaticsModuleType.CTREPCM, 3);

  @Override
  public void teleopPeriodic() {
    Timer.tick();
    boolean switchPressed = table.getEntry("switch").getBoolean(false);

    // if(OI.button(0, ControlMap.Y_BUTTON)){
    //   if(aimPressed && lastAim <= 0.05) return;
    //   if(Vision.aim() == null){
    //     if(!aimPressed) return;
    //     Chassis.axisDrive(0, lastAim, 0.25);
    //     return;
    //   }
    //   aimPressed = true;
    //   lastAim = Vision.aim();
    //   Chassis.axisDrive(0, Vision.aim(), 0.25);
    // } else {
    //   aimPressed = false;
    // }

    //driving with accel
    double joystick = OI.axis(0, ControlMap.L_JOYSTICK_VERTICAL);
    //Emergency Brake
    decelTime = OI.button(0, ControlMap.LB_BUTTON) ? decelTimeFast : decelTimeSlow;
    if(OI.button(0, ControlMap.LB_BUTTON)) joystick = 0;
    //accelerate towards joystick
    if(joystick - velocity != 0) velocity += (joystick - velocity) / Math.abs(joystick - velocity) * deltaTime / decelTime;
    Chassis.axisDrive(velocity, OI.axis(0, ControlMap.R_JOYSTICK_HORIZONTAL) * 0.25, 1);

    //dpad up or down to control elevator
    Arms.runElevator(OI.dPad(1, DPAD_DOWN) || OI.dPad(1, DPAD_DOWN_LEFT) || OI.dPad(1, DPAD_DOWN_RIGHT), 
                     OI.dPad(1, DPAD_UP) || OI.dPad(1, DPAD_UP_LEFT) || OI.dPad(1, DPAD_UP_RIGHT), false, 0.1);
    
    //LB to suck, LT to vom
    Intake.run(OI.button(1, LB_BUTTON), OI.axis(1, LT) >= 0.1, false, 0.6);

    // //RB for fast shoot, RT for slow shoot
    TedBallin.shoot(OI.button(1, RB_BUTTON), OI.axis(1, RT) >= 0.1, false, 0.6);

    //Climbing Arms Toggle (Y)
    Arms.toggleArms(OI.button(1, Y_BUTTON));

    //Intake Arms Toggle (X)
    Intake.toggleIntake(OI.button(1, X_BUTTON));

    //Fast Mode Toggle (A)
    Chassis.toggleFastMode(OI.button(1, A_BUTTON));

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
