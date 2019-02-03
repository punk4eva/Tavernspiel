
package tiles.assets;

import animation.assets.GrassAnimation;
import creatures.Creature;
import level.Location;
import listeners.StepListener;
import tiles.AnimatedTile;
import tiles.TileDescriptionBuilder;

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
        super(loc, t ? "highgrass" : "lowgrass", null, true, true, !t);
        tall = t;
        animation = tall ? loc.highGrass : loc.lowGrass;
        lowGrass = loc.lowGrass;
        description = TileDescriptionBuilder.getDescription(name, loc);
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
