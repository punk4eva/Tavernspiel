
package gui.mainToolbox;

import ai.PlayerAI;
import gui.Window;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This Thread handles turns and queued events.
 */
public class TurnThread extends Thread{
        
    public static double gameTurns = 0;

    private volatile double turnsPassed = 0;
    
    //private volatile boolean screensActive;
    
    @Unfinished("Remove debugging")
    private boolean sleeping;

    TurnThread(){
        super("Turn Thread");
    }

    @Override
    public synchronized void run(){
        while(Window.main.running){
            sleeping = true;
            System.out.println("THREAD SLEEPING");
            try{
                while(turnsPassed==0/*&&!screensActive*/) this.wait();
            }catch(InterruptedException e){}
            System.out.println("THREAD AWOKEN");
            sleeping = false;
            do{
                System.out.println("TURNING");
                turn();
            }while(((PlayerAI)Window.main.player.attributes.ai).unfinished);
        }
    }

    /**
     * Runs the game for the given amount of turns.
     * @param delta The amount of turns to run.
     */
    private void turn(){
        gameTurns += turnsPassed;
        for(;turnsPassed>=Window.main.player.attributes.speed;
                turnsPassed-=Window.main.player.attributes.speed) 
            Window.main.currentArea.turn(Window.main.player.attributes.speed);
        if(turnsPassed!=0) Window.main.currentArea.turn(turnsPassed);
    }
        
    /**
     * Wakes up the turn thread to run for a set amount of turns.
     * @param t The amount of turns expended.
     * @deprecated DANGEROUS: Only to be called by the key receiving AWT-thread.
     * @unfinished unnecessary catch.
     */
    public synchronized void setTurnsPassed(double t){
        if(sleeping||((PlayerAI)Window.main.player.attributes.ai).unfinished){
            turnsPassed = t;
            this.notify();
        }else throw new IllegalStateException("Mistimed wakeup");
    }
    
    /**
     * Adds the turns that have passed to the turn counter
     * @param t The number of more turns to tick.
     */
    public synchronized void addTurnsPassed(double t){
        gameTurns += t;
        turnsPassed += t;
    }
    
    /*public synchronized void setScreensActive(boolean sc){
        if(screensActive&&!sc){
            screensActive = false;
            notify();
        }else if(!screensActive&&sc){
            screensActive = true;
        }
    }*/
        
}
