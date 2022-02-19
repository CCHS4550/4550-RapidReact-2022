package frc.robot;

//import javax.naming.directory.DirContext;

import edu.wpi.first.wpilibj.Joystick;
import frc.parent.ControMap;

//This controllers are user input
//It also has the normalize method cuz IDK where else to put it
//To add another controller:
//  1. Copy the line that includes "Joystick contOne = new Joystick(0)"
//  2. Change "contOne" to "contTwo"
//  3. Change "Joystick(0)" to "Joystick(1)"
public class OI {
    public static Joystick[] joystickArray = {new Joystick(0), new Joystick(1)};

   

    //Returns whether or not the button is being pressed
    //The method takes in the RobotMap button
    //i.e "RobotMap.A_BUTTON"
    public static boolean button(int index, int button){
        if(button == ControMap.LT) return axis(index, ControMap.LT) > 0.1;
        return joystickArray[index].getRawButton(button);
    }

    //DPad
    //returns an integer representation of direction
    //starts with up at 0 and increases clockwise
    //right is 90
    //upper left is 315
    //-1 if nothing
    public static boolean dPad(int index, int direction){
        return joystickArray[index].getPOV() == direction;
    }

    public static int dPadAng(int index){
        return joystickArray[index].getPOV();
    }

    //Returns how much the axises is being pushed or pulled down
    //The method takes in RobotMap axis
    //i.e. "RobotMap.LT"
    public static double axis(int index, int axis){
        double axisVal = joystickArray[index].getRawAxis(axis);
        if(axisVal < ControMap.ZERO && axisVal > -ControMap.ZERO)
            return 0;
        else 
            return axisVal;
    }

    //Takes in a value and some bounds and forces it within those bounds
    //Used pretty much everywhere, so make sure you don't change anything to drastic.
    public static double normalize(double value, double min, double max){
        if(value > max)
            return max;
        else if(value < min)
            return min;
        else 
            return value;

       //return value > max ? max : value < min ? min : value;
    }


}
