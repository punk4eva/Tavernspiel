
package ai.intelligence;

import ai.AITemplate;
import creatures.Creature;
import java.util.LinkedList;
import level.Area;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * An AI with intelligence 0. Capable of shambling around randomly.
 */
public class IntelligentAI0 extends AITemplate{
    
    protected LinkedList<Integer[]> directionsToMove = new LinkedList<>();
    
    public IntelligentAI0(){
        intelligence = 0;
        directionsToMove.add(new Integer[]{-1, -1});
        directionsToMove.add(new Integer[]{0, -1});
        directionsToMove.add(new Integer[]{1, -1});
        directionsToMove.add(new Integer[]{-1, 0});
        directionsToMove.add(new Integer[]{1, 0});
        directionsToMove.add(new Integer[]{1, 1});
        directionsToMove.add(new Integer[]{0, -1});
        directionsToMove.add(new Integer[]{-1, 1});
    }
    
    private void updateDirections(Creature c, Area area){
        directionsToMove.clear();
        if(area.tileFree(c.x-1, c.y-1))
            directionsToMove.add(new Integer[]{-1, -1});
        if(area.tileFree(c.x, c.y-1))
            directionsToMove.add(new Integer[]{0, -1});
        if(area.tileFree(c.x+1, c.y-1))
            directionsToMove.add(new Integer[]{1, -1});
        if(area.tileFree(c.x, c.y))
            directionsToMove.add(new Integer[]{-1, 0});
        if(area.tileFree(c.x+1, c.y))
            directionsToMove.add(new Integer[]{1, 0});
        if(area.tileFree(c.x-1, c.y+1))
            directionsToMove.add(new Integer[]{-1, 1});
        if(area.tileFree(c.x, c.y+1))
            directionsToMove.add(new Integer[]{0, 1});
        if(area.tileFree(c.x+1, c.y+1))
            directionsToMove.add(new Integer[]{1, 1});
    }
    
    @Override
    public void turn(Creature c, Area area){
        if(skipping>0){
            skipping-=c.attributes.speed;
            if(skipping<0){
                skipping = 0;
            }
        }else{
            updateDirections(c, area);
            BASEACTIONS.move(c, 
                directionsToMove.get(
                        Distribution.getRandomInt(0, directionsToMove.size()-1)));
        }
    }
    
}
