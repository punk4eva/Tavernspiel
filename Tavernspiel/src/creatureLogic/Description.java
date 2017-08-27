
package creatureLogic;

import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 */
public class Description implements Serializable{
    
    public final String type;
    public final String[] layers;
    
    public Description(String t, String... l){
        type = t;
        layers = l;
    }
    
    public String getDescription(Expertise e){
        String ret = "";
        int level = 0;
        switch(type){
            case "armour": level = e.armour; break;
            case "weapons": level = e.weapons; break;
            case "potions": level = e.potions; break;
            case "scrolls": level = e.scrolls; break;
            case "creatures": level = e.creatures; break;
            case "wands": level = e.wands; break;
        }
        for(int n=0;n<=level&&n<layers.length;n++) ret += layers[n] + " ";
        return ret.substring(0, ret.length()-1);
    }
    
}
