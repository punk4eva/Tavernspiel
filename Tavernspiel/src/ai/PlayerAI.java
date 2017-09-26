
package ai;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Adam Whittaker
 * 
 * The AI of the Hero.
 */
public class PlayerAI extends AITemplate implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e){
        switch(e.getKeyChar()){
            case 'w':
            case 'a':
            case 's':
            case 'd':
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
