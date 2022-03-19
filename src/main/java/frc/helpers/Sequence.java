package frc.helpers;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class Sequence extends ArrayList <TimerEvent> {

    private Timer t = new Timer();
    private int currentTask = 0;

    public Sequence(){}

    public Sequence(ArrayList<TimerEvent> list){
        Consumer<TimerEvent> eachTimer = (event) -> {
            add(event);
        };
        list.forEach(eachTimer);
    }

    public boolean add(TimerEvent t){
        return super.add(t);
    }

    public void run(){
        if(currentTask < super.size()){
            if(!super.get(currentTask).getRunning()){
                super.get(currentTask).run();
                super.get(currentTask).setRunning();
                TimerTask task = new TimerTask(){
                    @Override
                    public void run(){
                        currentTask++;
                        Sequence.this.run();
                    }
                };
                t.schedule(task, super.get(currentTask).getTime());
            }
            
        }
        // super.forEach(method);
        
    }

}
