
package ai;

import creatures.Creature;
import creatures.Hero;
import gui.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import level.Area;
import pathfinding.Point;

/**
 *
 * @author Adam Whittaker
 * 
 * The AI of the Hero.
 */
public final class PlayerAI extends AITemplate implements KeyListener{    

    private final Hero hero;
    public volatile boolean unfinished = false, skipping = false;
    
    public PlayerAI(Hero h){
        hero = h;
        BASEACTIONS = new AIPlayerActions();
    }
    
    public final void updateDestination(Integer... ary){
        hero.attributes.ai.destinationx = hero.x + ary[0];
        hero.attributes.ai.destinationy = hero.y + ary[1];
    }
    
    @Override
    public synchronized void keyTyped(KeyEvent ke){
        Integer[] m = null;
        switch(ke.getKeyChar()){
            case 'w': m = new Integer[]{0, -1};
                break;
            case 'a': m = new Integer[]{-1, 0};
                break;
            case 's': m = new Integer[]{0, 1};
                break;
            case 'd': m = new Integer[]{1, 0};
                break;
        }
        if(BASEACTIONS.canMove(hero, m)){
            unfinished = true;
            Window.main.turnThread.click(hero.x+m[0], hero.y+m[1]);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke){
        
    }

    @Override
    public void keyReleased(KeyEvent ke){
        
    }

    @Override
    public synchronized void turn(Creature c, Area area){
        if(hero.x!=hero.attributes.ai.destinationx||
                hero.y!=hero.attributes.ai.destinationy){
            decideAndMove(hero);
        }
    }
    
    @Override
    public synchronized void decideAndMove(Creature c){
        if(currentPath==null){
            currentPath = c.area.graph.searcher.findExpressRoute(new Point(c.x, c.y), new Point(destinationx, destinationy)).iterator();
            c.changeAnimation("move");
            unfinished = true;
            currentPath.next();
        }
        Point next = currentPath.next();
        BASEACTIONS.moveRaw(c, next.x, next.y);
        if(!currentPath.hasNext()){
            currentPath = null;
            unfinished = false;
            c.changeAnimation("stand");
        }
    }
    
}
