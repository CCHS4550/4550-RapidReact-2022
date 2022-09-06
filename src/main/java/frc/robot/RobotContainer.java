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

    Joystick[] controllers = OI.joystickArray;

    public RobotContainer(){
        configureButtons();

        driveTrain.setDefaultCommand(
            new RunCommand(() -> 
                driveTrain.drive(controllers[0], ControlMap.L_JOYSTICK_VERTICAL, ControlMap.R_JOYSTICK_HORIZONTAL), 
            driveTrain)
        );
    }

    private void configureButtons(){
        new JoystickButton(controllers[0], ControlMap.A_BUTTON)
            .whenPressed(() -> {
                driveTrain.toggleTorque();
                System.out.println("test");
            });

        new Trigger(){
            @Override
            public boolean get(){
                return OI.axis(1, ControlMap.RT) > 0.5;
            }
        }.whileActiveOnce(new RunCommand(() -> System.out.println("test")));
    }
}
