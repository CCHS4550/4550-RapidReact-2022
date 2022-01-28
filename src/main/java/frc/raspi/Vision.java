package frc.raspi;

import java.util.ArrayList;
import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Transform2d;

public class Vision{
    PhotonTrackedTarget bestTarget;
    PhotonCamera camera;
    public Vision(String camName){
        camera = new PhotonCamera(camName);
        PhotonPipelineResult result = camera.getLatestResult();
        List<PhotonTrackedTarget> targets = result.getTargets();
        bestTarget = result.getBestTarget();
    }
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
    public double getYaw(){
        return bestTarget.getYaw();
    }
    public void aim(){
        var result = camera.getLatestResult();
        double rotationSpeed;
        double forwardSpeed;
        

        
       /* if (result.hasTargets()) {
            // Calculate angular turn power
            // -1.0 required to ensure positive PID controller effort _increases_ yaw
            rotationSpeed = -1 * turn nController.calculate(result.getBestTarget().getYaw(), 0);
        } else {
            // If we have no targets, stay still.
            rotationSpeed = 0;
        }
    } else {
        // Manual Driver Mode
        rotationSpeed = xboxController.getLeftX();
    }

    // Use our forward/turn speeds to control the drivetrain
    drive.arcadeDrive(forwardSpeed, rotationSpeed);
    */
    }
    
}