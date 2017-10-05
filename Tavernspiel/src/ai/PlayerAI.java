
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
        Window.main.addKeyListener(this);
    }
    
    private void updateDestination(Integer[] ary){
        hero.attributes.ai.destinationx = hero.x + ary[0];
        hero.attributes.ai.destinationy = hero.y + ary[1];
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
        if(BASEACTIONS.canMove(hero, m)){
            updateDestination(m);
            BASEACTIONS.move(hero, m);
        }
        Window.main.setFocus(hero.x, hero.y);
    }

    @Override
    public void keyPressed(KeyEvent ke){
        
    }

    @Override
    public void keyReleased(KeyEvent ke){
        
    }

    @Override
    public void turn(Creature c, Area area){}
    
}
