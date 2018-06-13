
package blob;

import animation.GameObjectAnimator;
import buffs.BuffBuilder;
import creatureLogic.Description;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
@Unfinished("Need to override turn() and create burn method.")
public class Fire extends Blob{
    
    /**
     * Creates an instance.
     * @param a The animator
     * @param x The x
     * @param y The y
     */
    public Fire(GameObjectAnimator a, int x, int y){
        super("fire", new Description("naturals", "A fire is raging here"), BuffBuilder.fire(), a, Distribution.getRandomInt(2, 5),x,y);
    }
    
    @Override
    protected void spread(){
        if(spreadNumber==0){
            area.removeObject(this);
            area.burn(x, y);
            return;
        }
        if(area.map[y-1][x].flammable) area.addObject(new Fire(animator, x, y-1));
        if(area.map[y+1][x].flammable) area.addObject(new Fire(animator, x, y+1));
        if(area.map[y][x-1].flammable) area.addObject(new Fire(animator, x-1, y));
        if(area.map[y][x+1].flammable) area.addObject(new Fire(animator, x+1, y));
        spreadNumber--;
    }
    
}
