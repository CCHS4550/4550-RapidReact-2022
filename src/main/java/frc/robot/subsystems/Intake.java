package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.diagnostics.DoubleSlider;
import frc.helpers.CCSparkMax;
import frc.helpers.OI;
import frc.parent.RobotMap;
import frc.robot.Robot;

public class Intake extends SubsystemBase {
    private final CCSparkMax sucky = new CCSparkMax("sucky", "suck", RobotMap.SUCKY,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true);

    private final CCSparkMax intake = new CCSparkMax("intake", "in", RobotMap.INTAKE_IN,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true);

    private double position = 0;
    private boolean in = true;
    //ips stands for intake positioning power
    //I couldn't be bothered to write it out every time
    private DoubleSlider ips = new DoubleSlider("Intake Pos Power", 0.7, 0, 1); 

    public Intake(){
        intake.setPositionConversionFactor(0.10686979799148262178959147156001);
    }

    public void toggleIntake(){
        in = !in;
        position = in ? 0 : 1 ;
    }
    
    private PIDController spd = new PIDController(0.5, 0, 0.01);
    public void positionIntake(){
        intake.set(OI.normalize(spd.calculate(intake.getPosition(), position), -ips.value(), ips.value()));
    }
}
