
package creatureLogic;

import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 */
public class Expertise implements Serializable{
    
    public int creatures = 0;
    public int potions = 0;
    public int armour = 0;
    public int scrolls = 0;
    public int weapons = 0;
    public int wands = 0;
    
    public Expertise(){}
    
    public Expertise(int cr, int po, int sc, int ar, int we, int wa){
        creatures = cr;
        potions = po;
        armour = ar;
        scrolls = sc;
        weapons = we;
        wands = wa;
    }
    
    public void merge(Expertise e){
        creatures += e.creatures;
        potions += e.potions;
        scrolls += e.scrolls;
        weapons += e.weapons;
        wands += e.wands;
        armour += e.armour;
    }
    
}
