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
public class Robot extends TimedRobot implements ControlMap {
  public static Timer count = new Timer(9);
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

  public boolean calibrate = true;
  public static boolean set = false;

  //private DiagnosticsIF[] diagnostics;
  public static ArrayList<CCSparkMax> motors = new ArrayList<CCSparkMax>();


  public static ArrayList<Delay> deez = new ArrayList<Delay>();
  public boolean delay(double time, Lambda action){
    Delay d = null;
    for(Delay c : deez){
      if(c.action().equals(action)){
        d = c;
      } 
    }
    if(d == null){
      deez.add(new Delay(time, action));
      return false;
    }
    if(d.triggered()) d.run();
    return d.triggered();
  }

  public boolean delay(boolean cond, Lambda action){
    if(!cond) return false;
    Delay d = null;
    for(Delay c : deez){
      if(c.action().equals(action)){
        d = c;
      } 
    }
    if(d == null){
      Delay amongus = new Delay(0, action);
      deez.add(amongus);
      d = amongus;
    }
    if(d.triggered()) d.run();
    return d.triggered();
  }

  DoubleSlider slider;
  DoubleEntry entry;
  BooleanSwitch swit;

  public static BooleanDisplay calibrated = new BooleanDisplay("Calibrated", false);

  public Timer calibration = new Timer(3);
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    calibration.reset();
    calibration.start();
    slider = new DoubleSlider("test", 0, -5, 5);
    entry = new DoubleEntry("Entry Test", 69);
    swit = new BooleanSwitch("Switch Test", true);
    Chassis.shift.set(true);
    
    Arms.nothing();
    Chassis.nothing();
    Intake.nothing();
    TedBallin.nothing();
    // diagnostics = new DiagnosticsIF[] {
    //   new DiagnosticsNoLayout(motors),
    //   new PowerStatus()
    // };
    // for(DiagnosticsIF d : diagnostics) {
    //   d.init();
    // }

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

  // private long periodicCount;
  // private double updateTime = 2;

  

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
    Arms.nothing();
    // if (periodicCount++ % Timer.secondsToTicks(updateTime) == 0) {
    //   for(DiagnosticsIF d : diagnostics) {
    //     d.updateStatus();
    //   }
    // }

    if(RobotMap.COMPRESSOR_ENABLE)
      c.enableDigital();
    else
      c.disable();

    Timer.tick();

    //if(calibrate && !calibration.triggered()) Arms.calibrate();
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
    Intake.nothing();
    double tempTimer = System.currentTimeMillis();
    while(calibrate){
      Arms.calibrate();
      if(!DriverStation.isAutonomous() || Arms.calibrate() || System.currentTimeMillis() - tempTimer > 3000){
        break;
      }
    }

    Timer.delay(0.5);
    //timer.start();
    Chassis.reset();
    TedBallin.setShoot(0.7);
    Timer.delay(2);
    TedBallin.loader.set(-0.7);
    Timer.delay(1);
    // TedBallin.loader.set(-.35);
    //remove when 2 ball auto 
    TedBallin.setShoot(0);
    TedBallin.loader.set(0);
    // Intake.autoSetIntake(false);
    // Intake.suck(-1);
    Timer.delay(.5);
    // Arms.autoSetPos(-.176);
    Chassis.driveDist(-2, 0.1, 0.3, 0.25, false);
    // Chassis.reset();
    // Chassis.driveDist(2, 0.1, 0.3, 0.25, false);
    // Arms.autoSetPos(0.01);
    // TedBallin.loader.set(0);
    // Timer.delay(.5);
    // TedBallin.setShoot(.75);
    // Timer.delay(2);
    // TedBallin.loader.set(-1);
    // Timer.delay(2);
    // TedBallin.setShoot(0);
    // TedBallin.loader.set(0);
    


    // Timer.delay(1);
    // Chassis.driveDist(5, 0.1, 0.3, 0.5, false);
    // TedBallin.setShoot(0.4);
    // Timer.delay(1);
    // TedBallin.loader.set(-1);

    // Timer.delay(2);
    // //timer.start();
    // Chassis.reset();
    // TedBallin.setShoot(0.5);
    // Timer.delay(2);
    // TedBallin.loader.set(-1);
    // Timer.delay(1);
    // Chassis.driveDist(-3.5, 0.1, 0.3, 0.5, true);
    // TedBallin.setShoot(0);
    // TedBallin.loader.set(0);


    // shoot.start();
  }
  /**
   * This function is called periodically during autonomous.
   */
  // add timer.start so it's not as much of a pain :)
  // add auto timer class, add timer based auto functions (run at some point in time with what step)
  Timer shoot = new Timer(1);
  Timer driveStart = new Timer(0.5);
  public static int autoStep = 0;
  @Override
  public void autonomousPeriodic() {
    //if(!timer.triggered()) return;
    /*
    shoot first
    180 turn
    pop intake6
    run intake
    move straight out + grab ted ballin
    180
    stop intake
    pull intake in
    go straight;
    ted ballin the ball in
    
    tate does magic stuff here WOOOO
    */
    // if(!driveStart.started()){
    //   if(!shoot.started()) TedBallin.setShoot(1);
    //   if(shoot.triggered()){
    //     TedBallin.loader.set(.85);
    //     driveStart.start();
    //   } else return;
    // }
    // if(driveStart.triggered()){
    //   TedBallin.setShoot(0);
    //   TedBallin.loader.set(0);
    // } else return;
    // if(Chassis.driveDistPeriodic(3.5, 0.1, 0.5, 0.1, 0)) return;;
    
    // if(!Chassis.turnToAnglePeriodic(180, 0.1, 0.5, 3, 1)) return;
    // Intake.toggleIntake(true);
    // Intake.suck(1);
    // if(!Chassis.driveDistPeriodic(3.5, 0.1, 0.5, 0.5, 2)) return;
    // if(!Chassis.turnToAnglePeriodic(180, 0.1, 0.5, 0.1, 3)) return;
    // if(!Chassis.driveDistPeriodic(3.5, 0.1, 0.5, 0.1, 4)) return;

    //among us in real life
    //when the impostor is sussy!!!!
    //:)
    //:O
    //:D
    //D:
    //uwu
  }

  @Override
  public void teleopInit() {
    Intake.nothing();
    Face.angry();
    //timer.start();
  }
  /**
   * This function is called periodically during operator control.
   */
  public double decelTime = .2;
  public double decelTimeFast = .2;
  public double decelTimeSlow = 0;

  public double velocity = 0;

  public double deltaTime = 0.02;

  public boolean aimPressed = false;
  public double lastAim = 0;
  public double lastYaw = 500;

  public boolean autoClimb = false;
  public Trigger autoClimbTrigger = new Trigger();

  double kP = 0.5;
  double kI = 0.0;
  double kD = 0.0;
  PIDController accel = new PIDController(kP, kI, kD);

  DoubleSlider pos1Test = new DoubleSlider("Pos 1", -0.25, -1, 0);
  DoubleSlider pos2Test = new DoubleSlider("Pos 2", -1, -1, 0);

  DoubleEntry shootSpeed = new DoubleEntry("Shoot Speed", 0.7);

  int autoClimbCount = 0;

  public static boolean setPos = false;
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
    Chassis.toggleFast(OI.button(0, Y_BUTTON));
    // //driving with accel
    double joystick = -OI.axis(0, ControlMap.L_JOYSTICK_VERTICAL);
     if(Chassis.fast) {
       joystick *= 0.35;
     } else {
       joystick *= 0.8;
     }
    double rJoystick = OI.axis(0, ControlMap.R_JOYSTICK_HORIZONTAL);
    // if(Chassis.fast) rJoystick *= .5;
    rJoystick *= 0.5;
    
    //setting decel
    decelTime = OI.button(0, ControlMap.LB_BUTTON) ? decelTimeFast : decelTimeSlow;

    //Emergency Brake
    if(OI.button(0, ControlMap.LB_BUTTON)) {
      joystick = 0;
      rJoystick = 0;
    }
    //accelerate towards joystick
    if(joystick - velocity != 0 && decelTime != 0) velocity += (joystick - velocity) / Math.abs(joystick - velocity) * deltaTime / decelTime;
    if(decelTime == 0) velocity = joystick;
    if(Math.abs(velocity) < 0.05 && Math.abs(joystick) <= 0.05) velocity = 0;

    //velocity = accel.calculate(velocity, joystick);
    Chassis.axisDrive(velocity, rJoystick, 1);
    //Chassis.arcadeDrive(joystick, OI.axis(0, ControlMap.R_JOYSTICK_HORIZONTAL) * 0.5);

    double armSpeed = 1;
    if(OI.dPad(1, DPAD_DOWN_RIGHT) || OI.dPad(1, DPAD_DOWN_LEFT) || OI.dPad(1, DPAD_UP_RIGHT) || OI.dPad(1, DPAD_UP_LEFT)) armSpeed *= 0.5;
    // //dpad up or down to control elevator;;;
    Arms.runElevator((OI.dPad(1, DPAD_DOWN) || OI.dPad(1, DPAD_DOWN_LEFT) || OI.dPad(1, DPAD_DOWN_RIGHT)) || (OI.axis(1, RT) > 0.5 && Arms.calibrated),
                     OI.dPad(1, DPAD_UP) || OI.dPad(1, DPAD_UP_LEFT) || OI.dPad(1, DPAD_UP_RIGHT), false, armSpeed, OI.joystickArray[1], OI.button(1, A_BUTTON));
    if(OI.button(1, A_BUTTON)){
      calibrate = false;
      Arms.calibrated = false;
      autoClimb = false;
      autoClimbCount = 0;
    } 

    //LB to index, LT to unindex
    TedBallin.runIndexer(OI.button(1, LB_BUTTON), OI.axis(1, LT) >= 0.1, false, 0.5);

    //RB for fast shoot, RT for reverse
    TedBallin.runShooter(OI.axis(1, RT) >= 0.1, OI.button(1, RB_BUTTON), false, shootSpeed.value(), 4);

    //A for in, B for out
    Intake.run(OI.axis(1, LT) >= 0.1, OI.button(1, LB_BUTTON), false, -1);

    //Climbing Arms Toggle (Y)
    Arms.toggleArms(OI.button(1, Y_BUTTON));

    //Intake Arms Toggle (X)
    Intake.toggleIntake(OI.button(1, X_BUTTON));

    //Fast Mode Toggle (A)
    Chassis.toggleFastMode(OI.button(0, A_BUTTON), OI.joystickArray[0]);

    //Fast Toggle (Y)
    Chassis.toggleFast(OI.button(0, Y_BUTTON));

    if(OI.button(1, L_JOYSTICK_BUTTON)) setPos = true;

    if(autoClimbTrigger.trigger(OI.button(1, B_BUTTON))){
       autoClimb = !autoClimb;
       autoClimbCount = 0;
       Intake.setIn(true);
    }
    if(autoClimb) autoClimb();

    

  }

  double pos1 = -1.05;
  double pos2 = -70;
  
  Timer quarter = new Timer(0.2);
  Timer half = new Timer(0.75);


  Trigger toggle = new Trigger();
  void autoClimb(){
    if(Arms.calibrated){
      if(autoClimbCount == 0){
        //moves elevator to middle, bringing robot to mid bar
        Arms.setPosition(pos2);
        Arms.moveToPos();
        if(Arms.atPos()) {
          autoClimbCount = 1;
          Arms.setPosition(pos1);
          quarter.reset();
          quarter.start();
        }
        //robot is on mid bar
      } else if(autoClimbCount == 1){
        //start extending elevator up
        //after a .25 second delay, extend climber hooks
        Arms.toggleArms(toggle.trigger(quarter.triggered()));
        Arms.moveToPos();
        if(Arms.atPos() && quarter.triggered()){
          //once elevator is fully extended, start another .25 second timer
          autoClimbCount = 2;
          half.reset();
          half.start();
          toggle = new Trigger();
        }
        //robot is tilted on the mid bar, with arms fully extended on mid bar
      } else if(autoClimbCount == 2){
        //retract arms
        Arms.toggleArms(toggle.trigger(true));
        if(half.triggered()){
          //after a .25 second delay, continue to next step
          Arms.setPosition(pos2);
          autoClimbCount = 3;
        }
        //robot is now tilted and hugging the high bar
      } else if(autoClimbCount == 3){
        //begin lowering elevator
        Arms.moveToPos();
        if(Arms.atPos()){
          //once elevator is lowered, the robot will be swinging on the high bar
          //continue to next step
          autoClimbCount = 4;
          Arms.setPosition(pos1);
          quarter.reset();
          quarter.start();
        }
        //robot is now on high bar
      } else if(autoClimbCount == 4){
        //start extending elevator up
        //after a .25 second delay, extend climber hooks
        Arms.toggleArms(toggle.trigger(quarter.triggered()));
        Arms.moveToPos();
        if(Arms.atPos() && quarter.triggered()){
          //once elevator is fully extended, start another .25 second timer
          autoClimbCount = 5;
          half.reset();
          half.start();
          toggle = new Trigger();
        }
        //robot is tilted on the high bar, with arms fully extended
      } else if(autoClimbCount == 5){
        //retract arms
        Arms.toggleArms(toggle.trigger(true));
        if(half.triggered()){
          //after a .25 second delay, continue to next step
          Arms.setPosition(pos2);
          autoClimbCount = 6;
          quarter.reset();
          quarter.start();
          toggle = new Trigger();
        }
        //robot is now tilted and hugging the traversal bar
      } else if(autoClimbCount == 6){
        //begin lowering elevator
        if(quarter.triggered()){
          if(toggle.trigger(true)) Arms.setPosition(pos2);
          Arms.moveToPos();
          if(Arms.atPos()) autoClimb = false;
        }
        //once elevator is lowered, the robot will be swinging on the traversal bar
        //robot is now on traversal bar
      }
      // if(!delay(0.25, () -> Arms.toggleArms())) return;

      // if(!delay(Arms.atPos(), () -> Arms.toggleArms())) return;
      // if(!delay(0.5, () -> Arms.setPosition(pos1))) return;
    }
  }

  /**
   * This function is called right after disabling
   */
  @Override
  public void disabledInit() {
    set = false;

    for(Timer t : Timer.timers){
      t.stop();
      t.reset();
    }

    Arms.calibrated = false;
    autoClimb = false;
    autoClimbCount = 0;

    OI.joystickArray[0].setRumble(RumbleType.kLeftRumble, 0);
    OI.joystickArray[0].setRumble(RumbleType.kRightRumble, 0);
    OI.joystickArray[1].setRumble(RumbleType.kLeftRumble, 0);
    OI.joystickArray[1].setRumble(RumbleType.kRightRumble, 0);
  }

  /**
   * This function is called periodically during test mode.
   */
  DoubleEntry conversion = new DoubleEntry("conversion factor", 1);
  @Override
  public void testPeriodic() {
    double armSpeed = 1;
    if(OI.dPad(1, DPAD_DOWN_RIGHT) || OI.dPad(1, DPAD_DOWN_LEFT) || OI.dPad(1, DPAD_UP_RIGHT) || OI.dPad(1, DPAD_UP_LEFT)) armSpeed *= 0.5;
    // //dpad up or down to control elevator;;;
    Arms.runElevator((OI.dPad(1, DPAD_DOWN) || OI.dPad(1, DPAD_DOWN_LEFT) || OI.dPad(1, DPAD_DOWN_RIGHT)),
                     OI.dPad(1, DPAD_UP) || OI.dPad(1, DPAD_UP_LEFT) || OI.dPad(1, DPAD_UP_RIGHT), false, armSpeed, OI.joystickArray[1], OI.button(1, L_JOYSTICK_BUTTON));
    if(OI.button(1, L_JOYSTICK_BUTTON)){
      calibrate = false;
      Arms.calibrated = false;
      autoClimb = false;
    } 

    if(autoClimbTrigger.trigger(OI.button(1, R_JOYSTICK_BUTTON))) autoClimb = !autoClimb;
    if(autoClimb) autoClimb();
  }

}
