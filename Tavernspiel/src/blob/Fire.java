
package blob;

import animation.GasAnimator;
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
    
    private final int depth;
    
    public Fire(GasAnimator a, int x, int y, int d){
        super("fire", new Description("naturals", "A fire is raging here"), BuffBuilder.fire(d), a, Distribution.getRandomInt(2, 5),x,y);
        depth = d;
    }
    
    @Override
    protected void spread(){
        if(spreadNumber==0){
            area.removeObject(this);
            area.burn(x, y);
            return;
        }
        if(area.map[y-1][x].flammable) area.addObject(new Fire((GasAnimator)animator, x, y-1, depth));
        if(area.map[y+1][x].flammable) area.addObject(new Fire((GasAnimator)animator, x, y+1, depth));
        if(area.map[y][x-1].flammable) area.addObject(new Fire((GasAnimator)animator, x-1, y, depth));
        if(area.map[y][x+1].flammable) area.addObject(new Fire((GasAnimator)animator, x+1, y, depth));
        spreadNumber--;
    }
    
}
