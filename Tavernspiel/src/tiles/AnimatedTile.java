
package tiles;

import animation.Animation;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class AnimatedTile extends Tile implements ActionListener{
    
    public Animation animation;
    
    public AnimatedTile(String tile, Location loc, Animation an){
        super(tile, loc);
        animation = an;
        setIcon(animation.frames[0]);
    }
    
    public AnimatedTile(String tile, Location loc){
        super(tile, ImageHandler.getFrames(tile, 0)[0]);
        animation = new Animation(ImageHandler.getFrames(tile, 0));
    }
    
    public AnimatedTile(String tile, Location loc, int x){
        super(tile, ImageHandler.getFrames(tile, x)[0]);
        animation = new Animation(ImageHandler.getFrames(tile, x));
    }
    
    public void addShaders(String shaderString, Location loc){
        animation.addShaders(shaderString, loc);
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
