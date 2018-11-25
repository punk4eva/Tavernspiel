
package blob;

import animation.GasAnimator;
import blob.particles.FireParticle;
import buffs.BuffBuilder;
import creatureLogic.Description;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import level.Location;
import logic.ConstantFields;
import logic.Distribution;
import logic.GameSettings;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
@Unfinished("Need to override turn() and create burn method.")
public class Fire extends Blob{
    
    private final int depth;
    private transient Location location;
    
    public Fire(GasAnimator a, Location loc, int x, int y, int d){
        super("fire", new Description("naturals", "A fire is raging here"), BuffBuilder.fire(d, loc), 
                new GasAnimator(GameSettings.FIRE_SETTING.get(ConstantFields.fireColor, 
                        ConstantFields.fireTrailColor)), Distribution.getRandomInt(2, 5),x,y);
        depth = d;
        location = loc;
    }
    
    @Override
    protected void spread(){
        if(spreadNumber==0){
            area.removeObject(this);
            area.burn(x, y);
            return;
        }
        if(area.map[y-1][x].flammable) area.addObject(new Fire((GasAnimator)animator, location, x, y-1, depth));
        if(area.map[y+1][x].flammable) area.addObject(new Fire((GasAnimator)animator, location, x, y+1, depth));
        if(area.map[y][x-1].flammable) area.addObject(new Fire((GasAnimator)animator, location, x-1, y, depth));
        if(area.map[y][x+1].flammable) area.addObject(new Fire((GasAnimator)animator, location, x+1, y, depth));
        spreadNumber--;
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeObject(location.name);
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        location = Location.locationMap.get((String) in.readObject());
    }
    
}
