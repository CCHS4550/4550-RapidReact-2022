package frc.robot;

import java.util.ArrayList;
@SuppressWarnings("rawtypes")
public class Timer {
    public final static double deltaTime = 0.02; //seconds per tick

    public static ArrayList<Timer> timers = new ArrayList<Timer>();

    private double ticksPassed;
    private double ticksTotal;
    private boolean triggered;
    private boolean started;
    private LambdaRunner[] triggers;

    /** 
     * A timer that will run in periodic methods. The trigger will be true after the specified time has passed.
     *@param time how much time will pass before the timer is triggered
    */
    public Timer(double time){
        triggers = new LambdaRunner[0];
        triggers = null;
        ticksTotal = secondsToTicks(time);
        ticksPassed = 0;
        triggered = false;
        timers.add(this);
    }

    /** 
     * A timer that will run in periodic methods. The trigger will be true after the specified time has passed.
     *@param time how much time will pass before the timer is triggered
    */
    public Timer(double time, LambdaRunner... ms){
        triggers = ms;
        ticksTotal = secondsToTicks(time);
        ticksPassed = 0;
        triggered = false;
        timers.add(this);
    }

    /** 
     * A timer that will run in periodic methods. The trigger will be true after the specified time has passed.
     *@param time how much time will pass before the timer is triggered
     *@param start what time you're starting the timer at
    */
    public Timer(double time, double start){
        triggers = new LambdaRunner[0];
        ticksTotal = secondsToTicks(time);
        ticksPassed = secondsToTicks(start);
        triggered = false;
        timers.add(this);
    }

    /** 
     * Increments every timer. Must be included in the periodic function for timers to work.
    */
    public static void tick(){
        for(int i = 0; i < timers.size(); i++){
            Timer t = timers.get(i);
            if(!t.started) continue;
            t.ticksPassed++;
            if(t.ticksPassed >= t.ticksTotal){
                for(LambdaRunner tr : t.triggers){
                    if(!t.triggered()) tr.run();
                }
                t.setTriggered(true);
            }
        }
    }

    /** 
     * Resets a timer's time to 0 without changing the target time.
    */
    public void reset(){
        ticksPassed = 0;
        triggered = false;
    }

    /** 
     * Resets a timer's time to a specified time without changing the target time.
     * @param start the time that the timer will restart to
    */
    public void reset(double start){
        ticksPassed = secondsToTicks(start);
        triggered = false;
    }

    /** 
     * Restarts a timer to 0 and sets the time to a specified value.
     * @param time how long it will take for the timer to trigger
    */
    public void set(double time){
        ticksPassed = 0;
        ticksTotal = secondsToTicks(time);
        triggered = false;
        started = false;
    }

    public void set(double time, LambdaRunner... ls){
        ticksPassed = 0;
        ticksTotal = secondsToTicks(time);
        triggered = false;
        started = false;
        triggers = ls;
    }

    /** 
     * Restarts a timer to a specified value and sets the time to a specified value.
     * @param time how long it will take for the timer to trigger
     * @param start the time that the timer will restart to
    */
    public void set(double time, double start){
        ticksPassed = secondsToTicks(start);
        ticksTotal = secondsToTicks(time);
        triggered = false;
    }

    /** 
     * @return if the timer has run past it's specified time
    */
    public boolean triggered(){
        return triggered;
    }

    /** 
     * @return how long in seconds the timer has been running for
    */
    public double elapsed(){
        return ticksToSeconds(ticksPassed);
    }

    public static double ticksToSeconds(double ticks){
        return ticks * deltaTime;
    }

    public static double secondsToTicks(double seconds){
        return seconds / deltaTime;
    }

    public void start(){
        started = true;
    }
    
    public void stop(){
        started = false;
    }

    public boolean started(){
        return started;
    }

    public double total(){
        return ticksToSeconds(ticksTotal);
    }

    private void setTriggered(boolean set){
        triggered = set;
    }

    public String toString(){
        return elapsed() + "/" + total() + ": " + triggers.length + " actions";
    }
}
