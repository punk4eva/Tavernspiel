
package buffs;

import listeners.BuffEvent;
import creatureLogic.AttributeModifier;
import gui.MainClass;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Buff{
    
    public String name;
    public double duration = 1000000;
    public Distribution damageDistribution = null; //null if deals no damage.
    public AttributeModifier atribMod = null; //null if no attributes modified.
    public boolean visible = false;
    private BuffEvent event = null; //null if no event.
    
    public Buff(String n){
        name = n;
    }
    
    public Buff(String n, int d){
        name = n;
        duration = d;
    }
    
    public Buff(String n, AttributeModifier am){
        name = n;
        atribMod = am;
    }
    
    public Buff(String n, int d, AttributeModifier am){
        name = n;
        duration = d;
        atribMod = am;
    }
    
    public void end(){
        if(event!=null){
            MainClass.buffinitiator.notify(event);
            event = null;
        }
    }
    
}
