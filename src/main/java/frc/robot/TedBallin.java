package frc.robot;

import frc.helpers.AutoTimer;
import frc.helpers.CCSparkMax;
import frc.helpers.Timer;
// import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.Solenoid;
import frc.parent.RobotMap;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.Timer;

/**
 * Praise be Mr. Ballin' himself
 * @param RawPower - infinity
 * @param Flaws - zero
 * @param Potential - also infinity
 */
public class TedBallin implements RobotMap{

//template:public static CCSparkMax fLeft = new CCSparkMax("Front Left", "FL", RobotMap.FORWARD_LEFT, MotorType.kBrushless, IdleMode.kBrake, RobotMap.FORWARD_LEFT_REVERSE);

    //declares Motor Controllers
    public static CCSparkMax shooter = new CCSparkMax("Shooter", "S", RobotMap.SHOOTER,
        MotorType.kBrushless, IdleMode.kCoast, RobotMap.SHOOTER_REVERSE, true);

    public static CCSparkMax shooter2 = new CCSparkMax("Shooter2", "S2", RobotMap.SHOOTER2,
        MotorType.kBrushless, IdleMode.kCoast, RobotMap.SHOOTER_REVERSE, true);

    public static CCSparkMax loader = new CCSparkMax("Loader", "L", RobotMap.LOADER,
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.LOADER_REVERSE, true);

    public static void nothing(){};

    /**
     * a shoot function to be used in autonomous, will set the shoot then move onto the next step after time is up
     * @param power speed of shooting
     * @param time how long before autonomous proceeds
     * @param step what step of auto it is
     */
    public static boolean shootTriggered = false;
    public static AutoTimer shootTimer = new AutoTimer(0, 0);
    public static boolean shoot(double power, double time, int step){
        if(step != Robot.autoStep){
            return true;
        }
        if(!shootTriggered){
            shootTriggered = true;
            shootTimer = new AutoTimer(time, step);
            shootTimer.start(step);
        }
        setShoot(power);
        if(shootTimer.triggered()){
            Robot.autoStep++;
            shootTriggered = false;
            return true;
        }
        return false;
    }

    public static boolean setShoot(double set, int step){
        if(step != Robot.autoStep) return true;
        setShoot(set);
        Robot.autoStep++;
        return true;
    }

    /**
     * a shoot function to be used in autonomous, will set the indexer then move onto the next step after time is up
     * @param power speed of shooting
     * @param time how long before autonomous proceeds
     * @param step what step of auto it is
     */
    public static boolean indexTriggered = false;
    public static AutoTimer indTimer = new AutoTimer(0, 0);
    public static boolean index(double power, double time, int step){
        if(step != Robot.autoStep){
            return true;
        }
        if(!indexTriggered){
            indexTriggered = true;
            indTimer = new AutoTimer(time, step);
            indTimer.start(step);
        }
        setShoot(power);
        if(indTimer.triggered()){
            Robot.autoStep++;
            indexTriggered = false;
            return true;
        }
        return false;
    }

    public static boolean setIndexer(double set, int step){
        if(step != Robot.autoStep) return true;
        loader.set(set);
        Robot.autoStep++;
        return true;
    }

    public static void setShoot(double set){
        shooter.set(set);
        shooter2.set(-set);
    }
    
    /** 
        *@param triggerOne what triggers shooting with positive speed (takes precedence over triggerTwo). Suggest passing in a button or axis input
        *@param triggerTwo what triggers shooting with negative speed. Suggest passing in a button or axis input
        *@param hardStop will set speed to 0 (takes precedence over triggers one and two)
        *@param speed the shoot speed
        *@param time how long it will take for the shooter to start after the indexer
    */
    public static double velocity = 0;
    public static void runShooter(boolean triggerOne, boolean triggerTwo, boolean hardStop, double speed, double decel){
        if(hardStop){
            decel = 0.5;
            triggerOne = false;
            triggerTwo = false;
        }
        if(triggerOne){
            setShoot(speed);
            velocity = speed;
            Face.sad();
            return;
        }
        if(triggerTwo){
            setShoot(-0.15);
            velocity = -0.15;
            Face.sad();
            return;
        }
        velocity -= Timer.deltaTime / decel * Math.signum(velocity);
        if(Math.abs(velocity) <= 0.1) velocity = 0;
        setShoot(velocity);
    }

    /** 
        *@param triggerOne what triggers shooting with positive speed (takes precedence over triggerTwo). Suggest passing in a button or axis input
        *@param triggerTwo what triggers shooting with negative speed. Suggest passing in a button or axis input
        *@param hardStop will set speed to 0 (takes precedence over triggers one and two)
        *@param speed the shoot speed
        *@param time how long it will take for the shooter to start after the indexer
    */
    public static void runIndexer(boolean triggerOne, boolean triggerTwo, boolean hardStop, double speed){
        if(hardStop){
            triggerOne = false;
            triggerTwo = false;
        }
        if(triggerOne){
            loader.set(0.5 * speed);
            return;
        }
        if(triggerTwo){
            loader.set(-speed);
            return;
        }
        loader.set(0);
    }

}
