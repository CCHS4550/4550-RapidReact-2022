package frc.robot;

import java.util.ArrayList;

public class Timer {
    public final static double deltaTime = 0.02; //seconds per tick

    private static ArrayList<Timer> timers = new ArrayList<Timer>();

    private double ticksPassed;
    private double ticksTotal;
    private boolean triggered;

    public Timer(double time){
        ticksTotal = secondsToTicks(time);
        ticksPassed = 0;
        triggered = false;
        timers.add(this);
    }

    public Timer(double time, double start){
        ticksTotal = secondsToTicks(time);
        ticksPassed = secondsToTicks(start);
        triggered = false;
        timers.add(this);
    }

    public static void tick(){
        for(Timer t : timers){
            t.ticksPassed++;
            if(t.ticksPassed >= t.ticksTotal) t.triggered = true;
        }
    }

    public void reset(){
        ticksPassed = 0;
        triggered = false;
    }

    public void reset(double start){
        ticksPassed = secondsToTicks(start);
        triggered = false;
    }

    public boolean triggered(){
        return triggered;
    }

    public double elapsed(){
        return ticksToSeconds(ticksPassed);
    }

    private static double ticksToSeconds(double ticks){
        return ticks * deltaTime;
    }

    private static double secondsToTicks(double seconds){
        return seconds / deltaTime;
    }
    
}
