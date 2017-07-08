
package tiles;

import animation.Animation;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class AnimatedTile extends Tile implements ActionListener{
    
    public Animation animation;
    
    public AnimatedTile(String tile, Animation an){
        super(tile);
        animation = an;
    }
    
    public AnimatedTile(String tile){
        super(tile);
        animation = new Animation(ImageHandler.getFrames(tile, 0));
    }
    
    public AnimatedTile(String tile, int x){
        super(tile);
        animation = new Animation(ImageHandler.getFrames(tile, x));
    }
    
    public void addShaders(String shaderString){
        animation.addShaders(shaderString);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        animation.animate(this, g);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
    }
    
    public void startAnimation(){
        animation.start(this);
    }
    
    public void stopAnimation(){
        animation.stop();
    }

}
