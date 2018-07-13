
package gui.mainToolbox;

import ai.PlayerAI;
import gui.Window;

/**
 *
 * @author Adam Whittaker
 * 
 * This Thread handles turns and queued events.
 */
public class TurnThread extends Thread{
        
    public static double gameTurns = 0;

    private double turnsPassed = 0;

    TurnThread(){
        super("Turn Thread");
    }

    @Override
    public synchronized void run(){
        while(Window.main.running){
            try{
                while(turnsPassed==0) this.wait();
            }catch(InterruptedException e){}
            do{
                for(;turnsPassed>=Window.main.player.attributes.speed;
                        turnsPassed-=Window.main.player.attributes.speed)
                    turn(Window.main.player.attributes.speed);
            }while(((PlayerAI)Window.main.player.attributes.ai).unfinished);
        }
    }

    /**
     * Runs the game for the given amount of turns.
     * @param delta The amount of turns to run.
     */
    private void turn(double delta){
        gameTurns += delta;
        for(;delta>=1;delta--) Window.main.currentArea.turn(1.0);
        if(delta!=0) Window.main.currentArea.turn(delta);
    }
        
    /**
     * Wakes up the turn thread to run for a set amount of turns.
     * @param t The amount of turns expended.
     * @deprecated DANGEROUS: Only to be called by the key receiving AWT-thread.
     */
    public synchronized void setTurnsPassed(double t){
            turnsPassed = t;
            this.notify();
        }
        
}
