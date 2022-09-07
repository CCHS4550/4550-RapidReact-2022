package frc.robot;

import java.util.ResourceBundle.Control;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.helpers.OI;
import frc.parent.ControlMap;
import frc.robot.subsystems.*;

public class RobotContainer {
    private final Arms arms = new Arms();
    private final Chassis driveTrain = new Chassis();
    private final TedBallin shooter = new TedBallin();

    Joystick[] controllers = OI.joystickArray;

    public RobotContainer(){
        //calls the button configs, you don't need to assign buttons or triggers to vars unless you need to refer to them later
        //however, they will be called whether or not they have a var attached
        configureButtons();

        //set default command for subsystem
        //when no other commands are overriding, this is what the subsystem does
        //need to test to see how teleop configs work with self working commands
        driveTrain.setDefaultCommand(
            new RunCommand(() -> 
                driveTrain.drive(controllers[0], ControlMap.L_JOYSTICK_VERTICAL, ControlMap.R_JOYSTICK_HORIZONTAL), 
            driveTrain)
        );
    } 

    private void configureButtons(){
        //basic button mapping, joystick button takes a controller (use controllers[index], 0 for drive, 1 for mechanisms)
        //and a button ID, use controlMap for xbox
        //check https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/Button.html for full list of
        //actions that can be done
        new JoystickButton(controllers[0], ControlMap.A_BUTTON)
            .whenPressed(() -> {
                driveTrain.toggleTorque();
                System.out.println("test");
            });

        //somewhat complex series of triggers (for shooting)
        //first one is basic trigger, must construct a trigger and override the get method with the boolean you want checked
        //full list of trigger actions here https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/Trigger.html        
        Trigger shootFwd = new Trigger(){
            public boolean get(){
                return OI.axis(1, ControlMap.RT) > 0.5;
            }
        }.whenActive(() -> shooter.setShoot(0.5));

        //second one is a reverse of the first one, starts with a joystick button for simple checking, but b/c and returns a trigger
        //whole thing counts as a trigger
        Trigger shootBkd = new JoystickButton(controllers[1], ControlMap.RB_BUTTON)
            //& check to make sure this will only go if fwd not being pressed
            //still uses whenActive
            .and(shootFwd.negate())
            .whenActive(() -> {
                shooter.setShoot(-0.5);
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
    }
}
