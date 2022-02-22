package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class PneumaticsSystem {
    public boolean on;

    public Solenoid solenoid1;
    public Solenoid solenoid2;

    public PneumaticsSystem(PneumaticsModuleType type, int port1, int port2){
        solenoid1 = new Solenoid(type, port1);
        solenoid2 = new Solenoid(type, port2);
        on = false;
    }

    public void toggle(){
        on = !on;
        solenoid1.set(on);
        solenoid2.set(!on);
    }

    public void set(boolean set){
        on = set;
        solenoid1.set(set);
        solenoid2.set(!set);
    }
}
