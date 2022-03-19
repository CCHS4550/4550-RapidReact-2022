package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class Face {
    public static Servo eyebrow1 = new Servo(1); //left
    public static Servo eyebrow2 = new Servo(2); //right

    public static void neutral() {
        setServos(90, 90);
    }
 
    public static void angry(){
        setServos(45, 135);
    }
 
    public static void sad(){
        setServos(135, 45);
    }
 
    public static void theRock(){
        setServos(90, 40);
    }

    public static void setServos(double angle1, double angle2){
        if(angle1 < 0) angle1 += 180;
        if(angle1 > 180) angle1 %= 180;
        eyebrow1.setAngle(angle1);
        if(angle2 < 0) angle2 += 180;
        if(angle2 > 180) angle2 %= 180;
        eyebrow2.setAngle(angle2);
    }

}
