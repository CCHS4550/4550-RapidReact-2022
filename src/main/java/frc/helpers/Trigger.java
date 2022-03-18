package frc.helpers;

public class Trigger {
    private boolean triggered;
    public Trigger(){
        triggered = false;
    }
    public boolean trigger(boolean bool){
        if(bool) {
            if(!triggered){
                triggered = true;
                return true;
            }
        } else {
            triggered = false;
        }
        return false;
    }
}
