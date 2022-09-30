package frc.robot;

import java.util.ResourceBundle.Control;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.helpers.OI;
import frc.parent.ControlMap;
import frc.parent.DDRMap;
import frc.robot.subsystems.*;

public class RobotContainer {
    private final Arms arms = new Arms();
    private final Chassis driveTrain = new Chassis();
    private final Intake intake = new Intake();
    private final TedBallin shooter = new TedBallin();

    private ShuffleboardTab tab = Shuffleboard.getTab("Climb Speed");
    private NetworkTableEntry climbSpeed = tab.add("Climb Speed", 1).getEntry();
    // private double armSpeed = maxSpeed.getDouble(1.0); //default speed, can change

    

    Joystick[] controllers = OI.joystickArray;
    Joystick driveJoystick = controllers[0];

    public RobotContainer(){
        //calls the button configs, you don't need to assign buttons or triggers to vars unless you need to refer to them later
        //however, they will be called whether or not they have a var attached
        configureButtons();

        //set default command for subsystem
        //when no other commands are overriding, this is what the subsystem does
        //need to test to see how teleop configs work with self working commands
        driveTrain.setDefaultCommand(
            new RunCommand(() -> {
                double power = 0.85;
                double vert = driveJoystick.getRawAxis(ControlMap.L_JOYSTICK_VERTICAL) * power;
                double turn = driveJoystick.getRawAxis(ControlMap.R_JOYSTICK_HORIZONTAL) * power;
                driveTrain.drive(turn, -vert);
            }, 
            driveTrain)
        );
        
        arms.setDefaultCommand(new RunCommand(() -> {
            arms.goToPosition();
        }, arms));

        intake.setDefaultCommand(new RunCommand(() -> {
            intake.positionIntake();
        }, intake));
        
    } 

    private void configureButtons() {
        Trigger sucOn = new JoystickButton(controllers[1], ControlMap.RB_BUTTON).whenPressed(() ->{
            intake.setSuck(0.7);
        });

        Trigger sucBack = new Trigger(){
            public boolean get(){
                return OI.axis(1, ControlMap.RT) > 0.5;
            }
        }.and(sucOn.negate())
         .whenActive(() -> intake.setSuck(-0.7));

        new Trigger(){
            public boolean get(){return true;}
        }.and(sucOn.negate())
         .and(sucBack.negate())
         .whenActive(() -> intake.setSuck(0));


        new JoystickButton(controllers[1], ControlMap.Y_BUTTON) //Conflicts with arm sols
         .whenActive(() -> intake.toggleIntake());
        
        //basic button mapping, joystick button takes a controller (use controllers[index], 0 for drive, 1 for mechanisms)
        //and a button ID, use controlMap for xbox
        //check https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/Button.html for full list of
        //actions that can be done
        new JoystickButton(controllers[0], ControlMap.A_BUTTON)
            .whenPressed(() -> {
                driveTrain.toggleTorque();
            });

        new JoystickButton(controllers[1], ControlMap.Y_BUTTON)
            .whenPressed(() -> arms.toggleSols());


        Trigger elevatorUp = new Trigger(){
            public boolean get(){
                return OI.dPad(1, ControlMap.DPAD_UP);
            }
        }.whenActive(() -> arms.setSpeed(climbSpeed.getDouble(1)));
        //Still need to open shuffle board and test to make sure it takes the double eveytime we change it

        Trigger elevatorDown = new Trigger(){ //gets the dpad for down and sets speed to negative arm speed.s
            public boolean get(){
                return OI.dPad(1, ControlMap.DPAD_DOWN);
            }
        }.and(elevatorUp.negate()).whenActive(() -> arms.setSpeed(-climbSpeed.getDouble(1)));

        new Trigger(){
            public boolean get(){
                return true;
            }
            //activates when neither going up nor down with arms
        }.and(elevatorDown.negate())
        .and(elevatorUp.negate())
        .whenActive(() -> arms.setSpeed(0));

        //somewhat complex series of triggers (for shooting)
        //first one is basic trigger, must construct a trigger and override the get method with the boolean you want checked
        //full list of trigger actions here https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/Trigger.html        
        Trigger shootFwd = new Trigger(){
            public boolean get(){
                return OI.axis(1, ControlMap.RT) > 0.5;
            }
        }.whenActive(() -> shooter.setShoot(shooter.pow().value()));

        //second one is a reverse of the first one, starts with a joystick button for simple checking, but b/c and returns a trigger
        //whole thing counts as a trigger
        Trigger shootBkd = new JoystickButton(controllers[1], ControlMap.RB_BUTTON)
            //& check to make sure this will only go if fwd not being pressed
            //still uses whenActive
            .and(shootFwd.negate())
            .whenActive(() -> {
                shooter.setShoot(-0.25);
            });
        //3rd trigger to stop shooter, get func returns true b/c idk how they work and I want it to go without relying on any inputs
        new Trigger(){
            public boolean get(){
                return true;
            }
            //but also, must only activate when neither shooting fwd nor bkd
        }.and(shootFwd.negate())
        .and(shootBkd.negate())
        .whenActive(() -> shooter.setShoot(0));

        //sets both controllers to have the option to switch driver control to them
        for(Joystick ctrl : controllers){
            JoystickButton lJoy = new JoystickButton(ctrl, ControlMap.L_JOYSTICK_BUTTON);
            new JoystickButton(ctrl, ControlMap.R_JOYSTICK_BUTTON)
                .and(lJoy)
                .whenActive(() -> {
                    driveJoystick = ctrl;
                    for(Joystick ctr : controllers){
                        if(ctr != ctrl) ctrl.setRumble(RumbleType.kLeftRumble, .5); else ctrl.setRumble(RumbleType.kLeftRumble, 0);
                    }
                }); 
        }

    }
}
