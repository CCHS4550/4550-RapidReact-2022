package frc.robot;

import java.util.ResourceBundle.Control;
import java.util.Set;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.helpers.OI;
import frc.parent.ControlMap;
import frc.parent.DDRMap;
import frc.robot.subsystems.*;

public class AutoBallin extends SequentialCommandGroup{
    public AutoBallin(Chassis drive, Arms arms, Intake intake, TedBallin shooter){
        InstantCommand startShoot = new InstantCommand(() -> shooter.setShoot(.75), shooter);
        InstantCommand startLoad = new InstantCommand(() -> shooter.setLoader(-0.8), shooter);
        InstantCommand intakeDown = new InstantCommand(() -> intake.setIntPower(-.75));
        InstantCommand intakeStop = new InstantCommand(() -> intake.setIntPower(0));
        InstantCommand stopShoot = new InstantCommand(() -> shooter.setShoot(0), shooter);
        InstantCommand stopLoad = new InstantCommand(() -> shooter.setLoader(0), shooter);
        InstantCommand startSuck = new InstantCommand(() -> intake.setSuck(0.7), intake);
        InstantCommand stopSuck = new InstantCommand(() -> intake.setSuck(0), intake);
        super.addCommands(
            startShoot,
            delay(8.5),
            startLoad,
            delay(2),
            stopShoot,
            stopLoad,
            Chassis.driveDist(-3, drive)
        );
    }

    private Command delay(double time){
        return new WaitCommand(time);
    }
}