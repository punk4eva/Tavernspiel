
package buffs;

import creatureLogic.Attack;
import creatureLogic.Attack.AttackType;
import creatureLogic.Description;
import creatures.Creature;
import java.util.HashMap;
import javax.swing.ImageIcon;
import level.Location;
import static logic.Distribution.r;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds buffs.
 */
public final class BuffBuilder{
    
    private BuffBuilder(){}
    
    @Unfinished("Add icons for Buffs")
    protected final static HashMap<String, ImageIcon> buffMap = new HashMap<>();
    static{
        buffMap.put("poison", new ImageIcon("graphics/gui/buffIcons/poison.png"));
        buffMap.put("poisonSmall", new ImageIcon("graphics/gui/buffIcons/poisonSmall.png"));
    }
    
    
    private final static String[] FIRE_MESSAGES = {"You burnt to a crisp!",
            "You have been roasted!", "You turned to ash..."},
            BLEEDING_MESSAGES = {"You bled to death...", 
                "You should have worn a bandage...", "You bled out..."},
            POISON_MESSAGES = {"You succumbed to poison...", 
                "You choked on your own vomit..."}, 
            TOXICGAS_MESSAGES = {"You suffocated...", "You choked to death!",
                "The gas melted your insides..."};
    
    
    @Unfinished("Redo damage")
    public static Buff fire(double duration, Location loc){
        return new Buff("fire", new Description("buffs", "You are on fire!"), duration){
            @Override
            public void start(Creature c){}
            @Override
            public void turn(Creature c){
                c.takeDamage(new Attack(
                        (r.nextInt(4)+1)*loc.feeling.difficulty,
                        FIRE_MESSAGES[r.nextInt(FIRE_MESSAGES.length)],
                        AttackType.FIRE));
            }
            @Override
            public void end(Creature c){}
        };
    }
    
    public static Buff superFire(double duration){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Buff confusion(double duration){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Buff enraged(){
        return new Buff("enraged", new Description("buffs", "You are angry!")){
            
            double mult, oSpeed, oAttack;
            
            @Override
            public void start(Creature c){
                mult = 1.0 + (1.0/c.attributes.hp);
                oSpeed = c.attributes.attackSpeed;
                oAttack = c.attributes.attackMult;
                c.attributes.attackSpeed = oSpeed * mult;
                c.attributes.attackMult = oAttack * mult;
            }
            @Override
            public void turn(Creature c){
                mult = 1.0 + (1.0/c.attributes.hp);
                c.attributes.attackSpeed = oSpeed * mult;
                c.attributes.attackMult = oAttack * mult;
            }
            @Override
            public void end(Creature c){
                c.attributes.attackSpeed = oSpeed;
                c.attributes.attackMult = oAttack;
            }
        
        };
    }
    
    public static Buff exhausted(double duration){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Buff slowness(double duration){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Buff beserk(){
        return new Buff("beserk", new Description("buffs", "You have gone insane!")){
            @Override
            public void start(Creature c){
                c.attributes.attackMult *= 2;
                c.attributes.attackSpeed *= 2;
            }
            @Override
            public void turn(Creature c){}
            @Override
            public void end(Creature c){
                c.attributes.attackMult /= 2;
                c.attributes.attackSpeed /= 2;
            }
        };
    }
    
    public static Buff shadowmelded(){
        return new Buff("shadowmelded", new Description("buffs", "You are "
                + "shrouded in a cleansing mist of dew")){
            @Override
            public void start(Creature c){
                c.attributes.regen *= 1.1;
                c.attributes.invisible = true;
            }
            @Override
            public void turn(Creature c){}
            @Override
            public void end(Creature c){
                c.attributes.regen /= 1.1;
            }
        };
    }
    
    public static Buff paralyzed(double duration){
        return new Buff("paralyzed", new Description("buffs", "You can't move!"), duration){
            @Override
            public void start(Creature c){
                c.attributes.ai.paralyze(duration);
            }
            @Override
            public void turn(Creature c){}
            @Override
            public void end(Creature c){}
            
        };
    }
    
    public static Buff bleeding(double damage){
        return new Buff("bleeding", new Description("buffs", "You are losing blood.")){
            double currentDamage = damage;
            @Override
            public void start(Creature c){}
            @Override
            public void turn(Creature c){
                c.takeDamage(new Attack(currentDamage, 
                        BLEEDING_MESSAGES[r.nextInt(BLEEDING_MESSAGES.length)], 
                        AttackType.BLEEDING));
                currentDamage *= r.nextDouble();
                if(currentDamage<1) c.removeBuff(this);
            }
            @Override
            public void end(Creature c){}
        };
    }
    
    public static Buff blindness(double duration){
        return new Buff("blind", new Description("buffs", "You can't see!"), duration){
            int v;
            @Override
            public void start(Creature c){
                v = c.FOV.range;
                c.FOV.range = 1;
            }
            @Override
            public void turn(Creature c){}
            @Override
            public void end(Creature c){
                c.FOV.range = v;
            }
        };
    }
    
    public static Buff restrained(double duration){
        return new Buff("restrained", new Description("buffs", "You struggle to escape your bonds!"), duration){
            @Override
            public void start(Creature c){
                c.attributes.ai.restrained = true;
            }
            @Override
            public void turn(Creature c){}
            @Override
            public void end(Creature c){
                c.attributes.ai.restrained = true;
            }
        };
    }
    
    public static Buff poison(double _damage){
        return new Buff("poison", new Description("buffs", "You feel very ill.")){
            double damage = _damage;
            int turns = 2;
            @Override
            public void start(Creature c){}
            @Override
            public void turn(Creature c){
                c.takeDamage(new Attack(damage, 
                        POISON_MESSAGES[r.nextInt(POISON_MESSAGES.length)], 
                        AttackType.POISON));
                if(turns==0){
                    turns = 2;
                    damage--;
                }else turns--;
                if(damage==0) c.removeBuff(this);
            }
            @Override
            public void end(Creature c){}
        };
    }
    
    @Unfinished("Redo damage")
    public static Buff toxicGas(Location loc){
        return new Buff("toxic gas", new Description("buffs", "You are choking!")){
            @Override
            public void start(Creature c){}
            @Override
            public void turn(Creature c){
                c.takeDamage(new Attack(
                        (r.nextInt(4)+1)*loc.feeling.difficulty,
                        TOXICGAS_MESSAGES[r.nextInt(TOXICGAS_MESSAGES.length)],
                        AttackType.POISON));
            }
            @Override
            public void end(Creature c){}
        };
    }
    
    @Unfinished
    public static Buff getTrapBuff(String trap){
        switch(trap){
            default: return null;
        }
    }
    
}
