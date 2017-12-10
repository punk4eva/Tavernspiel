
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
            default: return;
        }
        if(BASEACTIONS.canMove(hero, m)){
            Window.main.turnThread.click(hero.x+m[0], hero.y+m[1]);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke){
        switch(ke.getKeyCode()){
            case KeyEvent.VK_ESCAPE: if(Window.main.viewablesSize()!=1){
                Window.main.removeTopViewable();
            }
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke){}

    @Override
    public synchronized void turn(Creature c, Area area){
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
    public synchronized void decideAndMove(Creature c){
        if(currentPath==null){
            currentPath = c.area.graph.searcher.findExpressRoute(new Point(c.x, c.y), new Point(destinationx, destinationy)).iterator();
            c.changeAnimation("move");
            unfinished = true;
            currentPath.next();
        }
        Point next = currentPath.next();
        BASEACTIONS.smootheRaw(hero, next.x, next.y);
        if(!currentPath.hasNext()){
            currentPath = null;
            unfinished = false;
            c.changeAnimation("stand");
            if(c.area.getReceptacle(c.x, c.y)!=null) BASEACTIONS.pickUp(c);
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
