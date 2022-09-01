package frc.robot;

import frc.helpers.*;
import frc.parent.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
public class Limelight {

    //network table gets all the fun vision info stuff from limelight
    public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    public static double x, y, area;
    public static boolean target;
    //for the maths, obv not right values
    public static double mountAngle = 0.0;
    public static double targetHeight = 0.0;
    public static double limelightHeight = 0.0; 

    //sets all variables based on info from limelight's network table, call periodically
    public static void look() {
        x = table.getEntry("tx").getDouble(0);
        y = table.getEntry("ty").getDouble(0);
        area = table.getEntry("ta").getDouble(0);
        target = table.getEntry("tv").getDouble(0) == 1;
    }

    //useful to set the pipeline to a certain number, like blue ballin vs red ballin
    public static void setPipeline(int pipeline) {
        table.getEntry("pipeline").setNumber(pipeline);
    }

    public static void ledOn() {
        table.getEntry("ledMode").setNumber(3);
    }

    public static void ledOff(){
        table.getEntry("ledMode").setNumber(1);
    }

    public static void ledBlink(){
        table.getEntry("ledMode").setNumber(2);
    }

    //crops image if there's outside nasty stuff, each value is -1 to 1
    public static void crop(int xMin, int yMin, int xMax, int yMax){
        table.getEntry("crop[0]").setNumber(xMin);
        table.getEntry("crop[1]").setNumber(xMax);
        table.getEntry("crop[2]").setNumber(yMin);
        table.getEntry("crop[3]").setNumber(yMax);
    }

    //returns distance from the thing
    public static double distance(){
        double targetAngle = (mountAngle + y) * Math.PI / 180; 
        return (targetHeight - limelightHeight) / Math.tan(targetAngle);
    }

}
