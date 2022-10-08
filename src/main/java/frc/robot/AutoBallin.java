package frc.robot;

import java.util.ResourceBundle.Control;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.helpers.OI;
import frc.parent.ControlMap;
import frc.parent.DDRMap;
import frc.robot.subsystems.*;

public class AutoBallin extends SequentialCommandGroup{
    public AutoBallin(Chassis drive, Arms arms, Intake intake, TedBallin shooter){
        Command startShoot = new Command(){
            @Override
            public void execute(){
                shooter.setShoot(0.8);
            }
            public boolean isFinished(){
                return true;
            }
        };

        Command startLoad = new Command(){
            @override
            public void execute(){
                shooter.setLoader(0.8);
            }
            public boolean isFinished(){
                return true;
            }
        };

        Command intakeTog = new Command(){
            @override
            public void execute(){
                intake.toggleIntake();
            }
            public boolean isFinished(){
                return true;
            }
        };

        Command stopShoot = new Command(){
            @override
            public void execute(){
                shooter.setShoot(0);
            }
            public boolean isFinished(){
                return true;
            }
        };

        Command stopLoad = new Command(){
            @override
            public void execute(){
                shooter.setLoader(0);
            }
            public boolean isFinished(){
                return true;
            }
        };

        Command startSuck = new Command(){
            @override
            public void execute(){
                intake.setSuck(0.7);
            }
            public boolean isFinished(){
                return true;
            }
        };

        Command stopSuck = new Command(){
            @override
            public void execute(){
                intake.setSuck(0);
            }
            public boolean isFinished(){
                return true;
            }
        };

        addCommands(
            startShoot,
            getDelay(1),
            startLoad,
            intakeTog,
            stopShoot,
            stopLoad,
            startSuck,
            //drive back
            getDelay(3)
            stopSuck,
            getDelay(3),
            startShoot,
            getDelay(1),
            startLoad,
            startSuck
        )
    }

    private Command getDelay(int time){
        Command delay = new Command(){
            public void execute(){
                Timer.delay(time);
            }
            public boolean isFinished(){
                return true;
            }
        };

        return delay;
    }
}

    //             shooter.setShoot(0.8);
    //             Timer.delay(1);
    //             shooter.setLoader(0.8);
    //             intake.toggleIntake();
    //             shooter.setShoot(0);
    //             shooter.setLoader(0)
    //             intake.setSuck(0.7);
    //             // Drive back
    //             intake.setSuck(0);
    //             // Move forward
    //             shooter.setShoot(0.8);
    //             Timer.delay(1);
    //             shooter.setLoader(0.8);
    //             intake.setSuck(0.7);