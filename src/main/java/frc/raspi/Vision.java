package frc.raspi;

import java.util.ArrayList;
import java.util.List;

//import com.revrobotics.CANSparkMax;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
// import frc.parent.ControMap;
// import frc.robot.CCSparkMax;
// import frc.robot.Chassis;
import frc.robot.OI;
//import frc.robot.Robot;

public class Vision extends OI{
    final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24);

    final double TARGET_HEIGHT_METERS = Units.feetToMeters(5);

    // Angle between horizontal and the camera.

    final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);


    // How far from the target we want to be

    final double GOAL_RANGE_METERS = Units.feetToMeters(3);


    // Change this to match the name of your camera

    public static PhotonCamera camera = new PhotonCamera("ballscam");


    // PID constants should be tuned per robot
    public static final double LINEAR_P = 0.1;
    public static final double LINEAR_D = 0;


    public static PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);
    public static final double ANGULAR_P = 0.1;
    public static final double ANGULAR_D = 0;



    public static PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);


    //XboxController xboxController = new XboxController(0);


    // Drive motors




    PhotonTrackedTarget bestTarget;

    // public Vision(String camName){
    //     camera = new PhotonCamera(camName);
    //     PhotonPipelineResult result = camera.getLatestResult();
    //     List<PhotonTrackedTarget> targets = result.getTargets();
    //     bestTarget = result.getBestTarget();
    // }
    //Returns array list with yaw , pitch, area and skew matters respecticely
    public ArrayList<Double> getData(){
        double yaw = bestTarget.getYaw();
        double pitch = bestTarget.getPitch();
        double area = bestTarget.getArea();
        double skew = bestTarget.getSkew();
        ArrayList<Double> data = new ArrayList<>();
        data.add(yaw);
        data.add(pitch);
        data.add(area);
        data.add(skew);
        return data;
    }
    public PhotonTrackedTarget updateTarget(){
        PhotonPipelineResult result = camera.getLatestResult();
        bestTarget = result.getBestTarget();
        return bestTarget;
    }
    //Returns x and y coordinates of the target
    public List<TargetCorner> getCorners(){
       List<TargetCorner> corners = bestTarget.getCorners();
       return corners;
    }
    public static Double getYaw(){
        var result = camera.getLatestResult();
        if(!result.hasTargets()) return null;
        return result.getBestTarget().getYaw();
    }
    public static double aim(){
        double rotationSpeed;
        // Vision-alignment mode

        // Query the latest result from PhotonVision

        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            // Calculate angular turn power

            // -1.0 required to ensure positive PID controller effort _increases_ yaw
            if(Math.abs(result.getBestTarget().getYaw()) <= 1) return 0;
            rotationSpeed = result.getBestTarget().getYaw() / Math.abs(result.getBestTarget().getYaw());
            // rotationSpeed = -turnController.calculate(result.getBestTarget().getYaw(), 0);

        } else {

            // If we have no targets, stay still.
            System.out.println("No Targets");
            rotationSpeed = 0;

        }
        return rotationSpeed;
    }

    public static void setPipeline(int p){
        camera.setPipelineIndex(p);
    }
//     public void aim(){
//         double forwardSpeed;
//         double rotationSpeed;

//         double linearP = 0.1;
//         double linearI = 0;
//         double linearD = 0;
//         PIDController linPID = new PIDController(linearP, linearI, linearD);

//         double angularP = 0.1;
//         double angularI = 0.0;
//         double angularD = 0.0;
//         PIDController angPID = new PIDController(angularP, angularI, angularD);
// //NOTE FOR DHRUV: CHK DOCU FOR AXIS CONT
//         forwardSpeed = -joystickArray[0].getAxisCount();

//         if (OI.button(1, ControMap.A_BUTTON)) {
//             // Vision-alignment mode
//             // Query the latest result from PhotonVision
//             var result = camera.getLatestResult();
    
//             if (result.hasTargets()) {
        
//                 rotationSpeed = linPID .calculate(result.getBestTarget().getYaw(), 0);
//             } else {
//                 // If we have no targets, stay still.
//                 rotationSpeed = 0;
//             }
//         } else {
//             // Manual Driver Mode
//             rotationSpeed = OI.joystickArray[0].getAxisCount();
//         }

//         // Use our forward/turn speeds to control the drivetrain
//        !!!!!DOUBLECHK LOGIC HERE
//        // Chassis.axisDrive(0, forwardSpeed, rotationSpeed);
//     }  

}