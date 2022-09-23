package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.diagnostics.DoubleEntry;
import frc.helpers.CCSparkMax;
import frc.parent.RobotMap;

public class TedBallin extends SubsystemBase{
    private final CCSparkMax shooter = new CCSparkMax("Shooter", "S", RobotMap.SHOOTER,
        MotorType.kBrushless, IdleMode.kCoast, RobotMap.SHOOTER_REVERSE, true);

    private final DoubleEntry power = new DoubleEntry("Shooter Power", 0.75);

    // private final CCSparkMax shooter2 = new CCSparkMax("Shooter2", "S2", RobotMap.SHOOTER2,
    //     MotorType.kBrushless, IdleMode.kCoast, RobotMap.SHOOTER_REVERSE, true);

    private final CCSparkMax loader = new CCSparkMax("Loader", "L", RobotMap.LOADER,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.LOADER_REVERSE, true);

    public void setShoot(double speed){
        shooter.set(speed);
    }

    public void setLoader(double speed){
        loader.set(speed);
    }

    public DoubleEntry pow() {return power;}
}
