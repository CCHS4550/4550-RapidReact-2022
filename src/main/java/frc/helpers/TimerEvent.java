package frc.helpers;

public abstract class TimerEvent {
    private int time;
    private boolean running = false;
    public abstract void run();

    public TimerEvent(int time){

    }
    
    public boolean getRunning(){
        return running;
    }
    public void setRunning(){
        running = true;
    }
    public int getTime(){
        return time;
    } 
}
