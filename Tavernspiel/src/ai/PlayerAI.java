
package ai;

import creatures.Creature;
import creatures.Hero;
import gui.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import level.Area;

/**
 *
 * @author Adam Whittaker
 * 
 * The AI of the Hero.
 */
public final class PlayerAI extends AITemplate implements KeyListener{    

    private final Hero hero;
    
    public PlayerAI(Hero h){
        hero = h;
    }
    
    @Override
    public void keyTyped(KeyEvent ke){
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
        BASEACTIONS.move(hero, m);
        Window.main.setFocus(hero.x, hero.y);
    }

    @Override
    public void keyPressed(KeyEvent ke){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent ke){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void turn(Creature c, Area area){}
    
}
