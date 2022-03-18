package frc.helpers;

public class Delay extends Timer {
    private Lambda action;
    private Trigger trigger;
    public Delay(double time, Lambda action){
        super(time);
        super.start();
        trigger = new Trigger();
        this.action = action;
    }
    
    public void run(){
        if(trigger.trigger(true)) action.run();
    }

    public Lambda action(){
        return action;
    }
}
