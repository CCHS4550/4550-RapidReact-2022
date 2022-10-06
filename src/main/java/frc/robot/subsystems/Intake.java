package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.diagnostics.DoubleEntry;
import frc.diagnostics.DoubleSlider;
import frc.helpers.CCSparkMax;
import frc.helpers.OI;
import frc.parent.ControlMap;
import frc.parent.RobotMap;
import frc.robot.Robot;

public class Intake extends SubsystemBase {
    private final CCSparkMax sucky = new CCSparkMax("sucky", "suck", RobotMap.SUCKY,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true);

    private final CCSparkMax intake = new CCSparkMax("intake1", "in1", RobotMap.INTAKE_IN,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true){
            public void set(double value){
                super.set(OI.normalize(value, -0.5, 0.5));
            }
        };
    private final CCSparkMax intake2 = new CCSparkMax("intake", "in", RobotMap.INTAKE_IN_2,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SUCKY_REVERSE, true){
            public void set(double value){
                // top is 0, out is 1
                super.set(OI.normalize(value, -0.5, 0.5));
            }
        };

    private double position = 0;
    private boolean in = true;
    //ips stands for intake positioning power
    //I couldn't be bothered to write it out every time
    private DoubleSlider ips = new DoubleSlider("Intake Pos Power", 0.7, 0, 1); 

    public Intake(){
        intake.setPositionConversionFactor(0.95456);
        intake2.setPositionConversionFactor(0.85719);
        intake.reset();
        intake2.reset();
    }

    public void toggleIntake(){
        in = !in;
        position = in ? 0 : 1 ;
    }
    
    private PIDController poser1 = new PIDController(.1, 0, 0.01);
    private PIDController poser2 = new PIDController(.1, 0, 0.01);
    public void positionIntake(){
        intake.set(OI.normalize(poser1.calculate(intake.getPosition(), position), -ips.value(), ips.value()));
        intake2.set(OI.normalize(poser2.calculate(intake2.getPosition(), position), -ips.value(), ips.value()));
    }

    public void setSuck(double power){
        sucky.set(power);
    }

    public void setIntPower(double power){
        intake.set(power);
        intake2.set(-power);
    }

    DoubleEntry pos = new DoubleEntry("pos", 0.04);
    DoubleEntry pos2 = new DoubleEntry("pos2", 0.04);
    DoubleEntry conv = new DoubleEntry("conversion", 0.95456);
    PIDController p = new PIDController(0.5, 0, 0.0);
    PIDController p2 = new PIDController(0.5, 0, 0.0);
    DoubleEntry k = new DoubleEntry("prop", 0.1);
    boolean inn = false;
    boolean on = false;
    public void test(){
        System.out.println(intake.getPosition() + " " + intake2.getPosition());
        
        // if(OI.button(1, ControlMap.RB_BUTTON)){
        //     intake2.set(pos2.value());
        // } else if(OI.axis(1, ControlMap.RT) > 0.5) {
        //     intake2.set(-pos2.value());
        // } else {
        //     intake2.set(0);
        // }
        // }
        // if(OI.button(0, ControlMap.X_BUTTON)){
        //     intake.set(p.calculate(intake.getPosition(), pos.value()));
        // }
        // if(OI.button(1, ControlMap.X_BUTTON)){
        //     intake2.set(p2.calculate(intake2.getPosition(), pos2.value())); 
        // }
        // if(OI.button(0, ControlMap.Y_BUTTON)){
        //     intake.setPositionConversionFactor(conv.value());
        //     intake.reset();
        //     p.setP(k.value());
        // }
        // if(OI.button(1, ControlMap.Y_BUTTON)){
        //     intake2.setPositionConversionFactor(conv.value());
        //     intake2.reset();
        //     p2.setP(k.value());
        // }
        
        // System.out.println(intake.getPosition() + " " + intake2.getPosition());
        // if(OI.button(0, ControlMap.X_BUTTON)){
        //     intake.reset();
        //     intake2.reset();
        // }
        // if(OI.button(0, ControlMap.Y_BUTTON)){
        //     if(!on){
        //         inn = !inn;
        //         on = true;
        //     }
        // } else {
        //     on = false;
        // }
        // intake.set(p.calculate(intake.getPosition(), inn ? 0 : -1));
        // intake2.set(p2.calculate(intake2.getPosition(), inn ? 0 : 1));
    }
}
