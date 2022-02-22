package frc.helpers;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class PneumaticsSystem {
    private boolean on;

    private Solenoid solenoid1;
    private Solenoid solenoid2;

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

    public boolean on(){ return on; }

    private boolean triggered = false;
    public void triggerSystem(boolean trigger){
        if(!trigger){
            triggered = false;
            return;
        }
        if(triggered) return;
        triggered = true;
        toggle();
    }
}
