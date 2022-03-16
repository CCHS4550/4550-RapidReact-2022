package frc.helpers;

public class AutoTimer extends Timer {
    private int step;
    public AutoTimer(double time, int step){
        super(time);
        this.step = step;
    }

    public int step(){
        return step;
    }

    public void start(int step){
        if(this.step == step) super.start();
    }

    public void set(double time, int step){
        if(this.step == step) super.set(time);
    }

    public void reset(int step){
        if(this.step == step) super.reset();
    }
    
    public void stop(int step){
        if(this.step == step) super.stop();
    }
    
}
