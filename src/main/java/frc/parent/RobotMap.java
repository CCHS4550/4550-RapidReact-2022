package frc.parent;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.CCSparkMax;

/*
    RobotMap holds all the ports involved in the robot.
    This ranges from talon ports, all the way to the ports
    on the controllers. This also contains the polarity for the wheels
    and the various ports assoiated with sensors

    If you wish to create your own port, here is the syntax:
        public static final returnType name = value;
    Notes on creating ports:
        1. Ports must be integers or booleans
        2. they MUST be public static final;
        3. If the port is not plugged in, make int values -1, and boolean values false


*/
public interface RobotMap {
   

    // Wheel Talons
    public static final int FORWARD_LEFT = 3;
    public static final int FORWARD_RIGHT = 1;
    public static final int BACK_LEFT = 4;
    public static final int BACK_RIGHT = 2;
    public static final int CLIMBER_LEFT = 5;
    public static final int CLIMBER_RIGHT = 6;


    //Wheel Polarities
    public static final boolean FORWARD_LEFT_REVERSE = true;
    public static final boolean FORWARD_RIGHT_REVERSE = false;
    public static final boolean BACK_LEFT_REVERSE = true;
    public static final boolean BACK_RIGHT_REVERSE = false;
    public static final boolean CLIMBER_LEFT_REVERSE = false;
    public static final boolean CLIMBER_RIGHT_REVERSE = false;
    
    //Pneumatic Stuff 
    public static final boolean COMPRESSOR_ENABLE = true;
    public static final int SHIFT_SOLENOID_ONE = 0;
    public static final int SHIFT_SOLENOID_TWO = 7;

    //Other Constancts 
    public static final int ROBOT_Y_DIR_SIGN = 1;
    public static final int ROBOT_X_DIR_SIGN = 1;
}