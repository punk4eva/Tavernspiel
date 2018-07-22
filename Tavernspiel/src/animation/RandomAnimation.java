
package animation;

import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Location;
import logic.Distribution;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class RandomAnimation extends FramedAnimation{
    
    private final Distribution skipChance;
    private Supplier<ImageIcon[]> loader;

    public RandomAnimation(Dimension[] f, Location loc, int d, Distribution s){
        super(null, d, null);
        frames = new ImageIcon[f.length];
        for(int n=0;n<f.length;n++)
            frames[n] = ImageHandler.getImage(f[n], loc);
        skipChance = s;
    }
    
    public RandomAnimation(LoadableAnimation i, int d, Distribution s){
        super(i.frames, d, null);
        loader = i.loader;
        skipChance = s;
    }
    
    @Override
    protected void recalc(){
        currentTicks += ticksPerFrame;
        while(currentTicks>maxTicks){
            currentTicks -= maxTicks;
            if(!skipChance.chance()) currentFrame++;
            if(currentFrame>=frames.length){
                currentFrame = 0;
            }
        }
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        frames = loader.get();
    }
    
}
