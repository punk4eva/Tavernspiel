
package blob.assets;

import animation.GasAnimator;
import blob.Blob;
import buffs.BuffBuilder;
import containers.FloorCrate;
import containers.PhysicalCrate;
import creatureLogic.Description;
import logic.ConstantFields;
import logic.Distribution;
import logic.GameSettings;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents fire.
 */
public class Fire extends Blob{
    
    private final int depth;
    
    public Fire(int x, int y, int d){
        super("fire", new Description("naturals", "A fire is raging here"), BuffBuilder.fire(d), 
                new GasAnimator(GameSettings.FIRE_SETTING.get(ConstantFields.fireColor,
                        ConstantFields.fireTrailColor)), Distribution.getRandomInt(2, 5),x,y);
        depth = d;
    }
    
    @Override
    protected void spread(){
        if(spreadNumber==0){
            dead = true;
            if(area.map[y][x].flammable) area.burn(x, y);
            return;
        }
        if(area.map[y-1][x].flammable&&!area.gameObjectPresent(x, y-1, name)) area.addObject(new Fire(x, y-1, depth));
        if(area.map[y+1][x].flammable&&!area.gameObjectPresent(x, y+1, name)) area.addObject(new Fire(x, y+1, depth));
        if(area.map[y][x-1].flammable&&!area.gameObjectPresent(x-1, y, name)) area.addObject(new Fire(x-1, y, depth));
        if(area.map[y][x+1].flammable&&!area.gameObjectPresent(x+1, y, name)) area.addObject(new Fire(x+1, y, depth));
        spreadNumber--;
    }
    
    @Override
    public void turn(double delta){
        for(double d=delta+turndelta;d>=1;d--){
            if(area.map[y][x].interactable instanceof FloorCrate
                    && ((FloorCrate) area.map[y][x].interactable).burn())
                area.removeReceptacle(x, y);
            spread();
        }
        turndelta = (delta+turndelta)%1.0;
    }
    
}
