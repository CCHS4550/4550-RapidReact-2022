package frc.robot;

// import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.Solenoid;
import frc.parent.RobotMap;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.Timer;
//this is line 9

public class TedBallin implements RobotMap{

//template:public static CCSparkMax fLeft = new CCSparkMax("Front Left", "FL", RobotMap.FORWARD_LEFT, MotorType.kBrushless, IdleMode.kBrake, RobotMap.FORWARD_LEFT_REVERSE);



    //declares Motor Controllers
    public static CCSparkMax shooter = new CCSparkMax("Shooter", "S", RobotMap.SHOOTER,
        MotorType.kBrushed, IdleMode.kCoast, RobotMap.SHOOTER_REVERSE, 69);
    public static CCSparkMax loader = new CCSparkMax("Loader", "L", RobotMap.LOADER,
        MotorType.kBrushed, IdleMode.kCoast, RobotMap.LOADER_REVERSE, 69);



    public static void setShoot(double set){
        shooter.set(set);
        loader.set(-set);
    }

    public static void shootSlow(){
        shooter.set(.3);
        loader.set(-.6);
    }

    public static void shootStop(){
        shooter.set(0);
        loader.set(0);
    }

}
