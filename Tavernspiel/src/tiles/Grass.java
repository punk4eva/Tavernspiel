
package tiles;

import animation.GrassAnimation;
import creatures.Creature;
import level.Location;
import listeners.StepListener;

/**
 *
 * @author Adam Whittaker
 */
public class Grass extends AnimatedTile implements StepListener{

    private boolean tall;
    private final GrassAnimation lowGrass;
    
    /**
     * Creates a new instance.
     * @param loc The Location
     * @param t Whether the Grass is tall or not.
     */
    public Grass(Location loc, boolean t){
        super(t ? "highgrass" : "lowgrass", null, true, true, !t);
        tall = t;
        animation = tall ? loc.highGrass : loc.lowGrass;
        lowGrass = loc.lowGrass;
    }
    
    @Override
    public void steppedOn(Creature c){
        if(tall){
            tall = false;
            transparent = true;
            lowGrass.startFrom((GrassAnimation)animation);
            animation = lowGrass;
        }
    }
    
}
