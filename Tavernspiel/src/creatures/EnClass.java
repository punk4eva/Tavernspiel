
package creatures;

import creatureLogic.Expertise;

/**
 *
 * @author Adam Whittaker
 */
public enum EnClass{
    
    NoClass (new Expertise()),
    Warrior (new Expertise(1,0,0,2,1,0,0), new EnSubclass[]{EnSubclass.Berserker, EnSubclass.Gladiator}),
    Mage (new Expertise(0,1,2,0,0,2,1), new EnSubclass[]{EnSubclass.Battlemage, EnSubclass.Warlock}),
    Rogue (new Expertise(1,1,1,1,1,0,2), new EnSubclass[]{EnSubclass.Freerunner, EnSubclass.Assassin}),
    Huntress (new Expertise(2,1,0,0,1,0,1), new EnSubclass[]{EnSubclass.Warden, EnSubclass.Sniper}),
    Summoner (new Expertise(2,1,1,0,0,1,0), new EnSubclass[]{EnSubclass.Corruptor, EnSubclass.Necromancer});

    
    protected final EnSubclass[] possibleSubclasses;
    protected final Expertise expertiseGained;
    
    EnClass(Expertise e, EnSubclass... subclasses){
        expertiseGained = e;
        possibleSubclasses = subclasses;
    }
    
    
    /**
     * The hero's subclass.
     */
    public enum EnSubclass{
        Berserker (new Expertise(1,0,0,0,0,0,0), "Not finished"), Gladiator (new Expertise(0,0,0,0,1,0,0), "Not finished"),
        Battlemage (new Expertise(0,0,0,1,1,0,0), "Not finished"), Warlock (new Expertise(1,1,0,0,0,0,1), "Not finished"),
        Freerunner (new Expertise(0,0,1,1,0,0,0), "Not finished"), Assassin (new Expertise(1,0,0,0,1,0,0), "Not finished"),
        Warden (new Expertise(0,1,1,0,0,0,0), "Not finished"), Sniper (new Expertise(0,0,0,0,1,1,1), "Not finished"),
        Corruptor (new Expertise(0,1,1,0,0,1,0), "Not finished"), Necromancer (new Expertise(0,1,1,0,0,0,1), "Not finished");
        
        protected final String description;
        protected final Expertise expertiseGained;
        EnSubclass(Expertise e, String desc){
            expertiseGained = e;
            description = desc;
        }
    }
    
}
