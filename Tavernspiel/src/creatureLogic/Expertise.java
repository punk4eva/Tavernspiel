
package creatureLogic;

import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 * 
 * Represents the knowledge of the Hero.
 */
public class Expertise implements Serializable{
    
    private final static long serialVersionUID = 2140086769;
    
    public int creatures = 0;
    public int potions = 0;
    public int armour = 0;
    public int scrolls = 0;
    public int weapons = 0;
    public int wands = 0;
    public int amulets = 0;
    
    /**
     * Creates an instance with no knowledge.
     */
    public Expertise(){}
    
    /**
     * Creates an instance with knowledge about given subjects.
     * @param cr Creature knowledge.
     * @param po Potion knowledge.
     * @param sc Scroll knowledge.
     * @param ar Armour knowledge.
     * @param we Weapon knowledge.
     * @param wa Wand  knowledge.
     * @param am Amulet knowledge.
     */
    public Expertise(int cr, int po, int sc, int ar, int we, int wa, int am){
        creatures = cr;
        amulets = am;
        potions = po;
        armour = ar;
        scrolls = sc;
        weapons = we;
        wands = wa;
    }
    
    /**
     * Combines this experience object with another.
     * @param e The concatenation.
     */
    public void merge(Expertise e){
        creatures += e.creatures;
        potions += e.potions;
        scrolls += e.scrolls;
        weapons += e.weapons;
        wands += e.wands;
        amulets += e.amulets;
        armour += e.armour;
    }
    
}
