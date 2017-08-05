
package ai;

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
     */
    public void turn(){
        throw new UnsupportedOperationException("Unfinished AITemplate.turn()");
    }
    
}
