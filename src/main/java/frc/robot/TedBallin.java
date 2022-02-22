package frc.robot;

import frc.helpers.CCSparkMax;
import frc.helpers.Timer;
// import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.Solenoid;
import frc.parent.RobotMap;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.Timer;


public class TedBallin implements RobotMap{

//template:public static CCSparkMax fLeft = new CCSparkMax("Front Left", "FL", RobotMap.FORWARD_LEFT, MotorType.kBrushless, IdleMode.kBrake, RobotMap.FORWARD_LEFT_REVERSE);



    //declares Motor Controllers
    public static CCSparkMax shooter = new CCSparkMax("Shooter", "S", RobotMap.SHOOTER,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SHOOTER_REVERSE, false);
        public static CCSparkMax shooter2 = new CCSparkMax("Shooter", "S", RobotMap.SHOOTER2,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.SHOOTER_REVERSE, false);
    public static CCSparkMax loader = new CCSparkMax("Loader", "L", RobotMap.LOADER,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.LOADER_REVERSE, false);


    public static void setShoot(double set){
        shooter.set(set);
        shooter2.set(set);
        loader.set(-set);
    }

    public static Integer trigger = null;
    public static Timer timer = new Timer(69);
    public static void setShoot(double set, int trig, double time){
        if(trigger != trig){
            shooter.set(set);
            shooter2.set(set);
            timer.set(time);
            trigger = trig;
            return;
        }
        if(!timer.triggered()) return;
        loader.set(-set);
    }

    public static void shootSlow(){
        shooter.set(.3);
        shooter2.set(.3);
        loader.set(-.6);
    }

    public static void shootStop(){
        shooter.set(0);
        shooter2.set(0);
        loader.set(0);
    }
    
    /** 
        *@param triggerOne what triggers shooting with positive speed (takes precedence over triggerTwo). Suggest passing in a button or axis input
        *@param triggerTwo what triggers shooting with negative speed. Suggest passing in a button or axis input
        *@param hardStop will set speed to 0 (takes precedence over triggers one and two)
        *@param speed the shoot speed
        *@param time how long it will take for the shooter to start after the indexer
    */
    
    public static void shoot(boolean triggerOne, boolean triggerTwo, boolean hardStop, double speed, double time){
        if(hardStop){
            setShoot(0);
            trigger = null;
            return;
        }
        if(triggerOne){
            setShoot(speed, 0, time);
            return;
        }
        if(triggerTwo){
            setShoot(-speed, 1, time);
            return;
        }
        setShoot(0);
        trigger = null;
    }

}
