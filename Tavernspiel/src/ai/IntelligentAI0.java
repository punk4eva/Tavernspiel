
package ai;

import creatures.Creature;
import java.util.ArrayList;
import level.Area;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * An AI with intelligence 0. Capable of shambling around randomly.
 */
public class IntelligentAI0 extends AITemplate{
    
    protected ArrayList<Integer> directionsToMove = new ArrayList<>();
    
    public IntelligentAI0(){
        intelligence = 0;
        directionsToMove.add(1);
        directionsToMove.add(2);
        directionsToMove.add(3);
        directionsToMove.add(4);
        directionsToMove.add(5);
        directionsToMove.add(6);
        directionsToMove.add(7);
        directionsToMove.add(8);
    }

    public static IntelligentAI0 getFromFileString(String filestring){
        return new IntelligentAI0();
    }
    
    private void updateDirections(Creature c, Area area){
        directionsToMove.clear();
        if(area.withinBounds(c.x-1, c.y-1)&&area.map[c.y-1][c.x-1].treadable)
            directionsToMove.add(1);
        if(area.withinBounds(c.x, c.y-1)&&area.map[c.y-1][c.x].treadable)
            directionsToMove.add(2);
        if(area.withinBounds(c.x+1, c.y-1)&&area.map[c.y-1][c.x+1].treadable)
            directionsToMove.add(3);
        if(area.withinBounds(c.x-1, c.y)&&area.map[c.y][c.x-1].treadable)
            directionsToMove.add(4);
        if(area.withinBounds(c.x+1, c.y-1)&&area.map[c.y-1][c.x+1].treadable)
            directionsToMove.add(5);
        if(area.withinBounds(c.x-1, c.y+1)&&area.map[c.y+1][c.x-1].treadable)
            directionsToMove.add(6);
        if(area.withinBounds(c.x, c.y+1)&&area.map[c.y+1][c.x].treadable)
            directionsToMove.add(7);
        if(area.withinBounds(c.x+1, c.y+1)&&area.map[c.y+1][c.x+1].treadable)
            directionsToMove.add(8);
    }
    
    @Override
    public void turn(Creature c, Area area){
        updateDirections(c, area);
        BASEACTIONS.move(c, 
            directionsToMove.get(
                    Distribution.getRandomInclusiveInt(0, directionsToMove.size()-1)));
    }
    
}
