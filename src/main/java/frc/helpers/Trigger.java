package frc.helpers;

public class Trigger {
    private boolean triggered;
    private boolean on;
    public Trigger(){
        triggered = false;
        on = false;
    }
    public boolean trigger(boolean bool){
        if(bool) {
            if(!triggered){
                triggered = true;
                on = !on;
                return true;
            }
        } else {
            triggered = false;
        }
        return false;
    }

    public boolean on(){
        return on;
    }

    public void set(boolean set){
        on = set;
    }
}
