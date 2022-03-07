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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.parent.ControlMap;
import frc.parent.RobotMap;
import frc.diagnostics.*;
//import frc.raspi.Vision;
//import frc.raspi.Vision;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.math.filter.SlewRateLimiter;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import java.util.ArrayList;
import frc.helpers.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
//API: https://first.wpi.edu/wpilib/allwpilib/docs/release/java/index.html
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

  private DiagnosticsIF[] diagnostics;
  public static ArrayList<CCSparkMax> motors = new ArrayList<CCSparkMax>();

  // public DoubleSlider slider; 
  // public DoubleEntry entry;
  // public BooleanSwitch swit;
  SlewRateLimiter limiter = new SlewRateLimiter(1);
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    Chassis.shift.set(true);
    // slider = new DoubleSlider("test", 0, -5, 5);
    // entry = new DoubleEntry("Entry Test", 69);
    // swit = new BooleanSwitch("Switch Test", true);
    Arms.nothing();
    Chassis.nothing();
    Intake.nothing();
    TedBallin.nothing();
    diagnostics = new DiagnosticsIF[] {
      new DiagnosticsNoLayout(motors),
      new PowerStatus()
    };
    for(DiagnosticsIF d : diagnostics) {
      d.init();
    }

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

  private long periodicCount;
  private double updateTime = 2;

  

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
    if (periodicCount++ % Timer.secondsToTicks(updateTime) == 0) {
      for(DiagnosticsIF d : diagnostics) {
        d.updateStatus();
      }
    }

    if(RobotMap.COMPRESSOR_ENABLE)
      c.enableDigital();
    else
      c.disable();

    Timer.tick();
    //Arms.calibrate();
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
  boolean started = false;
  //Timer timer = new Timer(5, new LambdaRunner<Object, Double>(new Object(), 0d, (t, v) -> TedBallin.setShoot(v)));

  @Override
  public void autonomousInit() {
    //timer.start();
    TedBallin.setShoot(1);
    Chassis.reset();

  }
  /**
   * This function is called periodically during autonomous.
   */
  // add timer.start so it's not as much of a pain :)
  
  @Override
  public void autonomousPeriodic() {
    //if(!timer.triggered()) return;
    if(!Chassis.driveDistPeriodic(3.5, 0.1, 0.5, 0.5, 0)) return;
  }

  @Override
  public void teleopInit() {
    
    //timer.start();
  }
  /**
   * This function is called periodically during operator control.
   */
  public double decelTime = .2;
  public double decelTimeFast = .2;
  public double decelTimeSlow = .5;

  public double decelTimeR = .2;
  public double decelTimeRFast = .1;
  public double decelTimeRSlow = .3;

  public double velocity = 0;
  public double rVelocity = 0;

  public double deltaTime = 0.02;

  public boolean aimPressed = false;
  public double lastAim = 0;
  public double lastYaw = 500;

  double kP = 0.5;
  double kI = 0.0;
  double kD = 0.0;
  PIDController accel = new PIDController(kP, kI, kD);

  @Override
  //@SuppressWarnings("unused")
  public void teleopPeriodic() {
    //System.out.println("Switch: " + limit.get());
    // // boolean switchPressed = table.getEntry("switch").getBoolean(false);
    // // System.out.println(switchPressed);
    // // if(OI.button(0, ControlMap.Y_BUTTON)){
    // //   if(aimPressed && lastAim <= 0.05) return;
    // //   if(Vision.aim() == null){
    // //     if(!aimPressed) return;
    // //     Chassis.axisDrive(0, lastAim, 0.25);
    // //     return;
    // //   }
    // //   if(!aimPressed) lastAim = 500;
    // //   aimPressed = true;
    // //   if(lastYaw <= Vision.getYaw()) lastAim = Vision.aim();
    // //   Chassis.axisDrive(0, lastAim, 0.25);
    // // } else {
    // //   aimPressed = false;
    // // }

    // //driving with accel
    double joystick = -OI.axis(0, ControlMap.L_JOYSTICK_VERTICAL);
     if(Chassis.fast) joystick *= 0.5;
    double rJoystick = OI.axis(0, ControlMap.R_JOYSTICK_HORIZONTAL);
    // if(Chassis.fast) rJoystick *= .5;
    rJoystick *= 0.5;
    
    // //setting decel
    // decelTime = OI.button(0, ControlMap.LB_BUTTON) ? decelTimeFast : decelTimeSlow;
    // decelTimeR = OI.button(0, ControlMap.LB_BUTTON) ? decelTimeRFast : decelTimeRSlow;

    // //Emergency Brake
    // if(OI.button(0, ControlMap.LB_BUTTON)) {
    //   joystick = 0;
    //   rJoystick = 0;
    // }
    // //accelerate towards joystick
    // if(joystick - velocity != 0) velocity += (joystick - velocity) / Math.abs(joystick - velocity) * deltaTime / decelTime;
    // if(Math.abs(velocity) < 0.05 && Math.abs(joystick) <= 0.05) velocity = 0;

    // if(rJoystick - rVelocity != 0) rVelocity += (rJoystick - rVelocity) / Math.abs(rJoystick - rVelocity) * deltaTime / decelTimeR;
    // if(Math.abs(rVelocity) < 0.05 && Math.abs(rJoystick) <= 0.05) rVelocity = 0;

    //velocity = accel.calculate(velocity, joystick);
    Chassis.axisDrive(limiter.calculate(joystick), rJoystick, 1);
    //Chassis.arcadeDrive(joystick, OI.axis(0, ControlMap.R_JOYSTICK_HORIZONTAL) * 0.5);

    double armSpeed = 1;
    if(OI.dPad(1, DPAD_DOWN_RIGHT) || OI.dPad(1, DPAD_DOWN_LEFT) || OI.dPad(1, DPAD_UP_RIGHT) || OI.dPad(1, DPAD_UP_LEFT)) armSpeed *= 0.5;
    // //dpad up or down to control elevator;;;
    Arms.runElevator((OI.dPad(1, DPAD_DOWN) || OI.dPad(1, DPAD_DOWN_LEFT) || OI.dPad(1, DPAD_DOWN_RIGHT)),
                     OI.dPad(1, DPAD_UP) || OI.dPad(1, DPAD_UP_LEFT) || OI.dPad(1, DPAD_UP_RIGHT), false, armSpeed, OI.joystickArray[1]);

    //LB to index, LT to unindex
    TedBallin.runIndexer(OI.button(1, LB_BUTTON), OI.axis(1, LT) >= 0.1, false, 0.5);

    //RB for fast shoot, RT for reverse
    TedBallin.runShooter(OI.button(1, RB_BUTTON), OI.axis(1, RT) >= 0.1, false, -.75, 4);

    //A for in, B for out
    Intake.run(OI.button(1, A_BUTTON), OI.button(1, B_BUTTON), false, -1);

    //Climbing Arms Toggle (Y)
    Arms.toggleArms(OI.button(1, Y_BUTTON));

    //Intake Arms Toggle (X)
    Intake.toggleIntake(OI.button(1, X_BUTTON));

    //Fast Mode Toggle (A)
    Chassis.toggleFastMode(OI.button(0, A_BUTTON), OI.joystickArray[0]);

    //Fast Toggle (Y)
    Chassis.toggleFast(OI.button(0, Y_BUTTON));

  }

  /**
   * This function is called right after disabling
   */
  @Override
  public void disabledInit() {
    for(Timer t : Timer.timers){
      t.stop();
      t.reset();
    }
    Arms.calibrated = false;
    OI.joystickArray[0].setRumble(RumbleType.kLeftRumble, 0);
    OI.joystickArray[0].setRumble(RumbleType.kRightRumble, 0);
    OI.joystickArray[1].setRumble(RumbleType.kLeftRumble, 0);
    OI.joystickArray[1].setRumble(RumbleType.kRightRumble, 0);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

}
