
package tiles.assets;

import animation.assets.GrassAnimation;
import creatures.Creature;
import level.Location;
import listeners.StepListener;
import tiles.AnimatedTile;

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
        super(t ? "highgrass" : "lowgrass", t ? "The vegetation is so high you can't see past!" : "This is vegetation.", null, true, true, !t);
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
