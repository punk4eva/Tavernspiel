
package ai;

import logic.Distribution;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 * 
 * The innate spiritual magic possessed by a creature.
 */
public class MagicHexagon implements Fileable{
    
    //The 6 poles of the Hexagon.
    protected int offensive = 0;
    protected int focus = 0;
    protected int healing = 0;
    protected int defensive = 0;
    protected int mind = 0;
    protected int sacrificial = 0;
    protected int total = 0;
    
    /**
     * Creates a MagicHexagon with predefined fields.
     * @param o
     * @param d
     * @param f
     * @param m
     * @param h
     * @param s
     */
    public MagicHexagon(int o, int d, int f, int m, int h, int s){
        offensive = o;
        defensive = d;
        focus = f;
        mind = m;
        healing = h;
        sacrificial = s;
        total = o+d+f+m+h+s;
    }
    
    /**
     * Creates a new MagicHexagon.
     */
    public MagicHexagon(){}
    
    /**
     * Upgrades a pole of the MagicHexagon.
     */
    protected void upgradeOffensive(){
        offensive++;
        if(defensive + offensive > 15) defensive--;
        else if(total<30) total++;
        else{
            switch(Distribution.getRandomInclusiveInt(1, 8)){
                case 1: focus--;
                case 2: healing--;
                case 3: mind--;
                case 4: sacrificial--;
                default: defensive--;
            }
        }
    }
    
    /**
     * Upgrades a pole of the MagicHexagon.
     */
    protected void upgradeFocus(){
        focus++;
        if(mind + focus > 15) mind--;
        else if(total<30) total++;
        else{
            switch(Distribution.getRandomInclusiveInt(1, 8)){
                case 1: defensive--;
                case 2: healing--;
                case 3: offensive--;
                case 4: sacrificial--;
                default: mind--;
            }
        }
    }
    
    /**
     * Upgrades a pole of the MagicHexagon.
     */
    protected void upgradeDefensive(){
        defensive++;
        if(defensive + offensive > 15) offensive--;
        else if(total<30) total++;
        else{
            switch(Distribution.getRandomInclusiveInt(1, 8)){
                case 1: focus--;
                case 2: healing--;
                case 3: mind--;
                case 4: sacrificial--;
                default: offensive--;
            }
        }
    }
    
    /**
     * Upgrades a pole of the MagicHexagon.
     */
    protected void upgradeMind(){
        mind++;
        if(mind + focus > 15) focus--;
        else if(total<30) total++;
        else{
            switch(Distribution.getRandomInclusiveInt(1, 8)){
                case 1: defensive--;
                case 2: healing--;
                case 3: offensive--;
                case 4: sacrificial--;
                default: focus--;
            }
        }
    }
    
    /**
     * Upgrades a pole of the MagicHexagon.
     */
    protected void upgradeHealing(){
        healing++;
        if(healing + sacrificial > 15) sacrificial--;
        else if(total<30) total++;
        else{
            switch(Distribution.getRandomInclusiveInt(1, 8)){
                case 1: focus--;
                case 2: defensive--;
                case 3: mind--;
                case 4: offensive--;
                default: sacrificial--;
            }
        }
    }
    
    /**
     * Upgrades a pole of the MagicHexagon.
     */
    protected void upgradeSacrificial(){
        sacrificial++;
        if(sacrificial + healing > 15) healing--;
        else if(total<30) total++;
        else{
            switch(Distribution.getRandomInclusiveInt(1, 8)){
                case 1: focus--;
                case 2: defensive--;
                case 3: mind--;
                case 4: offensive--;
                default: healing--;
            }
        }
    }

    @Override
    public String toFileString(){
        return "<hex>" + offensive + "," + defensive + "," + focus + "," + 
                mind + "," + healing + "," + sacrificial + "</hex>";
    }

    public static MagicHexagon getFromFileString(String filestring){
        String[] profile = filestring.replace("<hex>", "").replace("</hex>", "")
                .split(",");
        return new MagicHexagon(Integer.parseInt(profile[0]), Integer.parseInt(profile[1]),
            Integer.parseInt(profile[2]), Integer.parseInt(profile[3]), 
            Integer.parseInt(profile[4]), Integer.parseInt(profile[5]));
    }
    
}
