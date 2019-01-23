
package ai;

import containers.PhysicalReceptacle;
import creatureLogic.Action;
import creatureLogic.Action.MoveAction;
import creatureLogic.VisibilityOverlay;
import creatures.Creature;
import creatures.Hero;
import gui.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import level.Area;
import logic.KeyMapping;
import logic.Utils.Unfinished;
import pathfinding.Point;

/**
 *
 * @author Adam Whittaker
 * 
 * The AI of the Hero.
 */
public final class PlayerAI extends AITemplate implements KeyListener{    

    private final Hero hero;
    public volatile boolean unfinished = false;
    private final boolean keysDown[] = new boolean[KeyEvent.VK_Z + 1];
    
    public Action nextAction = null;
    private boolean waiting;
    
    /**
     * Creates a new instance.
     * @param h The Hero owner.
     */
    public PlayerAI(Hero h){
        hero = h;
        BASEACTIONS = new AIPlayerActions();
    }

    @Override
    public void turn(Creature c, Area area){
        if(nextAction!=null){
            nextAction.run();
            if(nextAction.turns>=hero.attributes.speed){
                nextAction.turns -= hero.attributes.speed;
                if(nextAction.turns<=hero.turndelta){
                    hero.turndelta -= nextAction.turns;
                    unfinished = false;
                }else{
                    skipping += nextAction.turns - hero.turndelta;
                    hero.turndelta = 0;
                }
            }else{
                hero.turndelta += hero.attributes.speed - nextAction.turns;
                unfinished = false;
            }
            nextAction = null;
        }else if(skipping>0){
            skipping-=hero.attributes.speed;
            if(skipping<=0){
                unfinished = false;
                skipping = 0;
            }
        }else if(hero.x!=hero.attributes.ai.destinationx||
                hero.y!=hero.attributes.ai.destinationy){
            decideAndMove(hero);
        }
    }
    
    @Override
    public synchronized void decideAndMove(Creature c){
        if(currentPath==null){
            currentPath = c.area.graph.searcher.findPlayerRoute(new Point(c.x, c.y), new Point(destinationx, destinationy), (VisibilityOverlay)hero.FOV).iterator();
            c.changeAnimation("move");
            unfinished = true;
            currentPath.next();
        }
        Point next = currentPath.next();
        
        animateMotion(next.x, next.y);
        
        if(!currentPath.hasNext()){
            currentPath = null;
            unfinished = false;
            c.changeAnimation("stand");
            PhysicalReceptacle r = c.area.getReceptacle(next.x, next.y); 
            if(r!=null) r.interact(c, c.area);
        }else Window.main.addTurnsPassed(hero.attributes.speed);
    }
    
    public synchronized void animateMotion(int x, int y){
        BASEACTIONS.smootheRaw(hero, x, y);
        waiting = true;
        //hero.area.objectLock.unlock();
        try{
            while(waiting) wait();
            //hero.area.objectLock.lock();
        }catch(InterruptedException e){}
        System.out.println("DONE WAITING");
        BASEACTIONS.moveRaw(hero, x, y);
    }
    
    /**
     * Waits the given amount of turns.
     * @param turns
     * @deprecated DANGEROUS AWT-only
     */
    public void skipTurns(double turns){
        skipping += turns;
        unfinished = true;
        Window.main.setTurnsPassed(turns);
    }
    
    @Override
    public void paralyze(double turns){
        skipTurns(turns);
    }
    
    /**
     * Wakes up the TurnThread.
     */
    public synchronized void release(){
        waiting = false;
        notify();
    }
    
    /**
     * Notifies the turn thread that there is an action.
     * @deprecated AWT-only
     */
    public void alertAction(){
        Window.main.setTurnsPassed(nextAction.turns);
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent ke){
        System.out.println(ke.getKeyChar() + ", " + ke.getKeyCode());
        Integer[] m;
        char k = ke.getKeyChar();
        if(k==KeyMapping.GO_UP) m = new Integer[]{0, -1};
        else if(k==KeyMapping.GO_LEFT) m = new Integer[]{-1, 0};
        else if(k==KeyMapping.GO_DOWN) m = new Integer[]{0, 1};
        else if(k==KeyMapping.GO_RIGHT) m = new Integer[]{1, 0};
        else if(k==KeyMapping.INTERACT){
            BASEACTIONS.interact(hero, hero.area, hero.x, hero.y);
            return;
        }else if(k==KeyMapping.TOGGLE_INVENTORY){
            Window.main.toggleInventory();
            return;
        }else return;
        if(BASEACTIONS.canMove(hero, m)&&!hero.animatingMotion()){
            nextAction = new MoveAction(hero, m);
            unfinished = true;
            Window.main.setTurnsPassed(nextAction.turns);
        }
    }

    @Override
    @Unfinished("Escape key needs rewriting")
    public void keyPressed(KeyEvent ke){
        System.err.println(ke.getKeyChar() + ", " + ke.getKeyCode());
        try{
            keysDown[ke.getKeyCode()] = true;
        }catch(ArrayIndexOutOfBoundsException e){
            //Foreign key pressed and should be ignored.
        }
        if(keysDown[KeyMapping.ESCAPE]) Window.main.setInventoryActive(false);
        if(keysDown[KeyMapping.UP]&&!keysDown[KeyMapping.DOWN]);
        else if(keysDown[KeyMapping.DOWN]&&!keysDown[KeyMapping.UP]);
        if(keysDown[KeyMapping.LEFT]&&!keysDown[KeyMapping.RIGHT]);
        else if(keysDown[KeyMapping.RIGHT]&&!keysDown[KeyMapping.LEFT]);
    }
    
    @Override
    public void keyReleased(KeyEvent ke){
        try{
            keysDown[ke.getKeyCode()] = false;
        }catch(ArrayIndexOutOfBoundsException e){
            //Foreign key pressed and should be ignored.
        }
    }
    
}
