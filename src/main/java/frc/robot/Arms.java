
package frc.robot;
//vicotr smell


import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.parent.RobotMap;



public class Arms implements RobotMap {
 
    public static CCSparkMax climberLeft = new CCSparkMax("Climber Left", "CL",RobotMap.CLIMBER_LEFT, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_LEFT_REVERSE);
    public static CCSparkMax climberRight = new CCSparkMax("Climber Right", "CR",RobotMap.CLIMBER_RIGHT, 
        MotorType.kBrushless, IdleMode.kBrake, RobotMap.CLIMBER_RIGHT_REVERSE);
    public static boolean climberCont = true;

    public static void climberLeftDown(){
        climberLeft.set(-1);
    }
    public static void climberRightDown(){
        climberRight.set(-1);
    }

    public static void climberLeftUp(){
        climberLeft.set(1);
    }

    public static void climberRightUp(){
        climberRight.set(1);
    }
    public static void climbMonkeyBars(){
        if(!climberCont){

        }
    }
    public static void toggleCont(){
        climberCont = !climberCont;
    }
}


