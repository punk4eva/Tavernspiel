
package creatureLogic;


/**
 *
 * @author Adam Whittaker
 */
public enum EnClass{
    
    //AITemplate ait, double sp, double atksp, double dex, double acc, double reg, int mhp, int stg, int xp, Resistance... rst
    
    NoClass (new Expertise(), new Attributes()),
    Warrior (new Expertise(1,0,0,2,1,0,0), new Attributes(null,1,1.05,1.1,1,0.025,20,10,0), new EnSubclass[]{EnSubclass.Berserker, EnSubclass.Gladiator}),
    Mage (new Expertise(0,1,2,0,0,2,1), new Attributes(null,1,1,1,1.05,0.025,20,10,0), new EnSubclass[]{EnSubclass.Battlemage, EnSubclass.Warlock}),
    Rogue (new Expertise(1,1,1,1,1,0,2), new Attributes(null,1,1,1.05,1,0.025,20,10,0), new EnSubclass[]{EnSubclass.Freerunner, EnSubclass.Assassin}),
    Huntress (new Expertise(2,1,0,0,1,0,1), new Attributes(null,1,1.05,1,1,0.025,20,10,0), new EnSubclass[]{EnSubclass.Warden, EnSubclass.Sniper}),
    Summoner (new Expertise(2,1,1,0,0,1,0), new Attributes(null,1,1,1,1,0.025,20,10,0), new EnSubclass[]{EnSubclass.Corruptor, EnSubclass.Necromancer}),
    Paladin (new Expertise(1,0,0,2,2,0,0), new Attributes(null,1,1,1,1,0.035,20,10,0), new EnSubclass[]{EnSubclass.Knight, EnSubclass.Cleric});

    
    protected final EnSubclass[] possibleSubclasses;
    protected final Expertise expertiseGained;
    protected final Attributes attributes; 
    
    EnClass(Expertise e, Attributes atb, EnSubclass... subclasses){
        expertiseGained = e;
        possibleSubclasses = subclasses;
        attributes = atb;
    }
    
    
    /**
     * The hero's subclass.
     */
    public enum EnSubclass{
        Berserker (new Expertise(1,0,0,0,0,0,0), new Attributes(null,1,1.1,1,1,0.025,20,10,0), "Not finished"), 
        Gladiator (new Expertise(0,0,0,0,1,0,0), new Attributes(null,1,1.05,1,1,0.025,20,10,0), "Not finished"),
        Battlemage (new Expertise(0,0,0,1,1,0,0), new Attributes(null,1,1,1,1.1,0.025,20,10,0), "Not finished"), 
        Warlock (new Expertise(1,1,0,0,0,0,1), new Attributes(null,1,1,1,1.05,0.025,20,10,0), "Not finished"),
        Freerunner (new Expertise(0,0,1,1,0,0,0), new Attributes(null,1,1,1.05,1,0.025,20,10,0), "Not finished"), 
        Assassin (new Expertise(1,0,0,0,1,0,0), new Attributes(null,1,1,1.1,1,0.025,20,10,0), "Not finished"),
        Warden (new Expertise(0,1,1,0,0,0,0), new Attributes(null,1,1.05,1,1,0.025,20,10,0), "Not finished"), 
        Sniper (new Expertise(0,0,0,0,1,1,1), new Attributes(null,1,1.1,1,1,0.025,20,10,0), "Not finished"),
        Corruptor (new Expertise(0,1,1,0,0,1,0), new Attributes(null,1,1,1,1,0.025,20,10,0), "Not finished"), 
        Necromancer (new Expertise(0,1,1,0,0,0,1), new Attributes(null,1,1,1,1,0.025,20,10,0), "Not finished"),
        Cleric (new Expertise(0,0,0,0,0,1,1), new Attributes(null,1,1,1,1,0.05,20,10,0), "Not finished"), 
        Knight (new Expertise(1,1,0,0,0,0,0), new Attributes(null,1,1,1,1,0.035,20,10,0), "Not finished");
        
        protected final String description;
        protected final Expertise expertiseGained;
        protected final Attributes attributes;
        EnSubclass(Expertise e, Attributes atb, String desc){
            expertiseGained = e;
            description = desc;
            attributes = atb;
        }
    }
    
}
