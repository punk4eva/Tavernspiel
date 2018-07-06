
package ai;

import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import gui.Window;
import items.Item;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import level.Area;
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
    
    public PlayerAI(Hero h){
        hero = h;
        BASEACTIONS = new AIPlayerActions();
    }
    
    public final void updateDestination(Integer... ary){
        hero.attributes.ai.destinationx = hero.x + ary[0];
        hero.attributes.ai.destinationy = hero.y + ary[1];
    }
    
    @Override
    public void keyTyped(KeyEvent ke){
        Integer[] m;
        switch(ke.getKeyChar()){
            case 'w': m = new Integer[]{0, -1};
                break;
            case 'a': m = new Integer[]{-1, 0};
                break;
            case 's': m = new Integer[]{0, 1};
                break;
            case 'd': m = new Integer[]{1, 0};
                break;
            case 'f': m = new Integer[]{0, 0};
                break;
            case 'e': Window.main.toggleInventory();
                return;
            default: return;
        }
        if(BASEACTIONS.canMove(hero, m)){
            Window.main.turnThread.click(hero.x+m[0], hero.y+m[1]);
        }
    }

    @Override
    @Unfinished("Escape key needs rewriting")
    public void keyPressed(KeyEvent ke){
        switch(ke.getKeyCode()){
            case KeyEvent.VK_ESCAPE: Window.main.removeViewable();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke){}

    @Override
    public void turn(Creature c, Area area){
        if(skipping>0){
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
    public void decideAndMove(Creature c){
        if(currentPath==null){
            currentPath = c.area.graph.searcher.findExpressRoute(new Point(c.x, c.y), new Point(destinationx, destinationy)).iterator();
            c.changeAnimation("move");
            unfinished = true;
            currentPath.next();
        }
        Point next = currentPath.next();
        if(c.animatingMotion()){
            c.area.objectLock.unlock();
            try{
                c.motionLatch.await();
            }catch(InterruptedException e){}
            c.area.objectLock.lock();
        }
        BASEACTIONS.smootheRaw(hero, next.x, next.y);
        if(!currentPath.hasNext()){
            currentPath = null;
            unfinished = false;
            c.changeAnimation("stand");
            if(c.area.getReceptacle(next.x, next.y)!=null){
                Item i = c.area.pickUp(next.x, next.y);
                if(!c.inventory.add(i)){
                    c.area.plop(i, next.x, next.y);
                    Main.addMessage("red", "Your pack is too full for the " +
                            i.toString(3));
                }
            }
        }
    }
    
    public void expendTurns(double turns){
        skipping += turns;
        unfinished = true;
        Window.main.addEvent(() -> {});
    }
    
    @Override
    public void paralyze(double turns){
        expendTurns(turns);
    }
    
}
