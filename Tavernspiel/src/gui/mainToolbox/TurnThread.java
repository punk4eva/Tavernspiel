
package gui.mainToolbox;

import ai.PlayerAI;
import dialogues.StatisticsDialogue;
import gui.Window;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Adam Whittaker
 */
public class TurnThread extends Thread{
        
        public static double gameTurns = 0;
        
        public final LinkedBlockingQueue<Runnable> queuedEvents = new LinkedBlockingQueue<>();
        
        TurnThread(){
            super("Turn Thread");
        }
        
        @Override
        public synchronized void run(){
            while(Window.main.running){
                try{
                    queuedEvents.take().run();
                }catch(InterruptedException e){}
                while(((PlayerAI)Window.main.player.attributes.ai).unfinished){
                    turn(Window.main.player.attributes.speed);
                }
            }
        }
        
        public void click(int x, int y){
            if(Window.main.currentArea.overlay.isUnexplored(x, y)) return;
            if(Window.main.player.x!=x||Window.main.player.y!=y) queuedEvents.add(() -> {
                ((PlayerAI)Window.main.player.attributes.ai).unfinished = true;
                Window.main.player.attributes.ai.setDestination(x, y);
            });
            else queuedEvents.add(() -> {
                try{
                    Window.main.player.attributes.ai.BASEACTIONS.pickUp(Window.main.player);
                }catch(NullPointerException e){
                    new StatisticsDialogue(Window.main.player).next();
                }
            });
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
        
    }
