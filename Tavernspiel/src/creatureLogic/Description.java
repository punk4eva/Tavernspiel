
package creatureLogic;

import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 * 
 * A layered description that is accessible to different levels based on expertise.
 */
public class Description implements Serializable{
    
    public String type;
    public final String[] layers;
    
    /**
     * Creates a new instance
     * @param t The type of the thing to be described.
     * @param l The layers of description.
     */
    public Description(String t, String... l){
        type = t;
        layers = l;
    }
    
    /**
     * Returns a String description stored in this Object.
     * @param e The Expertise to reference.
     * @return What the hero sees upon investigation.
     */
    public String getDescription(Expertise e){
        String ret = "";
        int level = 0;
        switch(type){
            case "armour": level = e.armour; break;
            case "weapons": level = e.weapons; break;
            case "potions": level = e.potions; break;
            case "scrolls": level = e.scrolls; break;
            case "creatures": level = e.creatures; break;
            case "amulets": level = e.amulets; break;
            case "wands": level = e.wands; break;
        }
        for(int n=0;n<=level&&n<layers.length;n++) ret += layers[n] + " ";
        return ret.substring(0, ret.length()-1);
    }
    
    /**
     * Creates a Description object from the given Strings.
     * @param type The type of the Description.
     * @param str A packaged form of the content of the description, layers 
     * separated with '|'.
     * @return
     * @deprecated
     * @see Description.new()
     */
    public static Description parseDescription(String type, String str){
        return new Description(type, str.split("|"));
    }
    
}
