
package ai;

import creatures.Creature;
import level.Area;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles behavior of creatures.
 */
public abstract class AITemplate implements Fileable{
    
    public EnType type; //The type of AI
    public int intelligence = 3; //The intelligence
    public MagicHexagon magic = new MagicHexagon(); //The AI's inate magic abilities.
    public int destinationx = -1, destinationy = -1; //The destination coords of the AI.
    public AIBaseActions BASEACTIONS = new AIBaseActions(); //The basic actions that the ai can do.

    /**
     * Sets the AI's destination coords.
     * @param x
     * @param y
     */
    protected void setDestination(int x, int y){
        destinationx = x;
        destinationy = y;
    }
    
    /**
     * The type of AI.
     */
    public enum EnType{
        PREDEFINED, HANDICAPPED, NORMAL, RANGED
    };
    
    
    @Override
    public String toFileString(){
        return type.toString() + "</ty>" + intelligence + magic.toFileString() + 
                destinationx + ":" + destinationy;
    }    
    
    /**
     * Decides what to do and does it.
     * @param c The creature who owns this AI.
     * @param area The area that the creature is in.
     */
    public void turn(Creature c, Area area){
        throw new UnsupportedOperationException("Unfinished AITemplate.turn()");
    }
    
}
