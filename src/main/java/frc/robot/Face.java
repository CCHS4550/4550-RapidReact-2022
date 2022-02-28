package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class Face {
    public static Servo eyebrow1 = new Servo(1);
    public static Servo eyebrow2 = new Servo(2);

    public static void neutral() {
        eyebrow1.setAngle(0);
        eyebrow2.setAngle(0);
    }
 
    public static void angry(){
        eyebrow1.setAngle(45);
        eyebrow2.setAngle(-45);
    }
 
    public static void sad(){
        eyebrow1.setAngle(-45);
        eyebrow2.setAngle(45);
    }
 
    public static void theRock(){
        eyebrow1.setAngle(0);
        eyebrow2.setAngle(45);
    }
 

}
