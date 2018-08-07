
package animation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Location;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class RandomAnimation extends FramedAnimation{
    
    private final static long serialVersionUID = 1749456789;
    
    private final Distribution skipChance;
    private final Supplier<ImageIcon[]> loader;

    public RandomAnimation(String[] f, Location loc, double d, Distribution s){
        super(null, d, null);
        loader = (Serializable & Supplier<ImageIcon[]>)() -> {
            ImageIcon[] ret = new ImageIcon[f.length];
            for(int n=0;n<f.length;n++)
                ret[n] = loc.getImage(f[n]);
            return ret;
        };
        frames = loader.get();
        skipChance = s;
    }
    
    public RandomAnimation(LoadableAnimation i, double d, Distribution s){
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
    
    @Override
    public RandomAnimation mirror(){
        return new RandomAnimation(new LoadableAnimation(loader, 0, null).mirror(), maxTicks, skipChance);
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        frames = loader.get();
    }
    
}
