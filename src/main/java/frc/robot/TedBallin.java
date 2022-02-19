package frc.robot;

// import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.Solenoid;
import frc.parent.RobotMap;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;


public class TedBallin implements RobotMap{
    
//template:public static CCSparkMax fLeft = new CCSparkMax("Front Left", "FL", RobotMap.FORWARD_LEFT, MotorType.kBrushless, IdleMode.kBrake, RobotMap.FORWARD_LEFT_REVERSE);



    //declares Motor Controllers
    public static CCSparkMax shooter = new CCSparkMax("Shooter", "S", RobotMap.SHOOTER, 
       MotorType.kBrushless, IdleMode.kCoast, RobotMap.SHOOTER_REVERSE);
    public static CCSparkMax shooter2 = new CCSparkMax("Shooter2", "S2", RobotMap.SHOOTER2, 
        MotorType.kBrushless, IdleMode.kCoast, RobotMap.SHOOTER_REVERSE);
    public static CCSparkMax spin = new CCSparkMax("Shooter3", "S3", RobotMap.SPINDEXER, 
       MotorType.kBrushless, IdleMode.kBrake, RobotMap.SHOOTER_REVERSE);
    
     public static CCSparkMax loader = new CCSparkMax("Loader", "L", RobotMap.LOADER, 
        MotorType.kBrushless, IdleMode.kCoast, RobotMap.LOADER_REVERSE);
    
    public static void nothing(){

    }

    public static void shootFast(){
        shooter.set(.5);
        shooter2.set(.5);
        //Timer.delay(3000);
        loader.set(-.6);
    }

    public static void shootSlow(){
        shooter.set(.5);
        shooter2.set(.5);
        //Timer.delay(3000);
        loader.set(-.6);
    }

    public static void shootStop(){
        shooter.set(0);
        shooter2.set(0);
        loader.set(0);
    }

    public static void setSpin(double set){
        spin.set(set);
    }
    
}