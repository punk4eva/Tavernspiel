
package buffs;

import creatureLogic.Description;
import creatures.Creature;
import java.util.Iterator;
import level.Location;
import static logic.Distribution.R;
import static logic.Distribution.getGaussianAboveZero;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds buffs.
 */
public final class BuffBuilder{
    
    private BuffBuilder(){}    
    
    private final static String[] FIRE_MESSAGES = {"You burnt to a crisp!",
            "You have been roasted!", "You turned to ash..."},
            BLEEDING_MESSAGES = {"You bled to death...", 
                "You should have worn a bandage...", "You bled out..."},
            POISON_MESSAGES = {"You succumbed to poison...", 
                "You choked on your own vomit..."}, 
            TOXICGAS_MESSAGES = {"You suffocated...", "You choked to death!",
                "The gas melted your insides..."};
    
    
    public static Buff fire(double duration, Location loc){
        return new Buff("fire", new Description("buffs", "You are on fire!"), duration){
            double ev;
            @Override
            public void start(Creature c){
                c.attributes.health.trauma.mean -= 0.33;
                ev = getGaussianAboveZero(0.3, 0.4);
                c.attributes.health.evasion.mean -= ev;
            }
            @Override
            public void turn(Creature c, Iterator iter){
                if(c.area.map[c.y][c.x].name.contains("water")) iter.remove();
                else{
                    c.attributes.health.cauterizeCheck();
                    c.attributes.health.addMinorTrauma(1 + 4*R.nextDouble(), name);
                }
            }
            @Override
            public void end(Creature c){
                c.attributes.health.trauma.mean += 0.33;
                c.attributes.health.evasion.mean += ev;
            }
        };
    }
    
    public static Buff superFire(double duration){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Buff confusion(double duration){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /*public static Buff enraged(){
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
            public void turn(Creature c, Iterator iter){
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
    }*/
    
    public static Buff exhausted(double duration){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Buff slowness(double spLoss, double duration){
        return new Buff("slowness", new Description("buffs", "Your muscles are slow to respond!"), duration){
            @Override
            public void start(Creature c){
                c.attributes.health.walkSpeed += spLoss;
                c.attributes.health.miscSpeed += spLoss;
                c.attributes.health.attackSpeed += spLoss;
                c.attributes.health.regen += 0.05;
            }
            @Override
            public void turn(Creature c, Iterator iter){}
            @Override
            public void end(Creature c){
                c.attributes.health.walkSpeed -= spLoss;
                c.attributes.health.miscSpeed -= spLoss;
                c.attributes.health.attackSpeed -= spLoss;
                c.attributes.health.regen -= 0.05;
            }
        };
    }
    
    /*public static Buff beserk(){
        return new Buff("beserk", new Description("buffs", "You have gone insane!")){
            @Override
            public void start(Creature c){
                c.attributes.attackMult *= 2;
                c.attributes.attackSpeed *= 2;
            }
            @Override
            public void turn(Creature c, Iterator iter){}
            @Override
            public void end(Creature c){
                c.attributes.attackMult /= 2;
                c.attributes.attackSpeed /= 2;
            }
        };
    }*/
    
    @Unfinished("Too close to PD")
    public static Buff shadowmelded(){
        return new Buff("shadowmelded", new Description("buffs", "You are "
                + "shrouded in a cleansing mist of dew")){
            @Override
            public void start(Creature c){
                c.attributes.health.regen *= 1.1;
                c.attributes.invisible = true;
            }
            @Override
            public void turn(Creature c, Iterator iter){}
            @Override
            public void end(Creature c){
                c.attributes.health.regen /= 1.1;
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
            public void turn(Creature c, Iterator iter){}
            @Override
            public void end(Creature c){}
            
        };
    }
    
    public static Buff bleeding(double damage){
        return new Buff("bleeding", new Description("buffs", "You are losing blood.")){
            double currentDamage = damage, att;
            @Override
            public void start(Creature c){
                c.attributes.health.trauma.mean -= 0.2;
                att = getGaussianAboveZero(0.3, 0.4);
                c.attributes.health.attack.mean -= att;
            }
            @Override
            public void turn(Creature c, Iterator iter){
                c.buffs.forEach(p -> {
                    if(p.name.contains("poison")) p.duration--;
                });
                c.attributes.health.addMinorTrauma(currentDamage, name);
                currentDamage *= R.nextDouble();
                if(currentDamage<1) iter.remove();
            }
            @Override
            public void end(Creature c){
                c.attributes.health.trauma.mean += 0.2;
                c.attributes.health.attack.mean += att;
            }
        };
    }
    
    public static Buff blindness(double duration){
        return new Buff("blindness", new Description("buffs", "You can't see!"), duration){
            int v;
            double acc;
            @Override
            public void start(Creature c){
                v = c.FOV.range;
                c.FOV.range = 1;
                acc = getGaussianAboveZero(0.3, 0.4);
                c.attributes.health.attack.mean -= acc;
            }
            @Override
            public void turn(Creature c, Iterator iter){}
            @Override
            public void end(Creature c){
                c.FOV.range = v;
                c.attributes.health.attack.mean += acc;
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
            public void turn(Creature c, Iterator iter){}
            @Override
            public void end(Creature c){
                c.attributes.ai.restrained = false;
            }
        };
    }
    
    public static Buff poison(double _damage){
        return new Buff("poison", new Description("buffs", "You feel very ill."), _damage){
            double atsp;
            @Override
            public void start(Creature c){
                c.attributes.health.trauma.mean -= 0.4;
                atsp = getGaussianAboveZero(0.1, 0.05);
                c.attributes.health.attackSpeed -= atsp;
                c.attributes.health.injurySavingThrow.mean += 1;
            }
            @Override
            public void turn(Creature c, Iterator iter){
                c.attributes.health.addMinorTrauma(duration/3, name);
            }
            @Override
            public void end(Creature c){
                c.attributes.health.trauma.mean += 0.4;
                c.attributes.health.attackSpeed += atsp;
                c.attributes.health.injurySavingThrow.mean -= 1;
            }
        };
    }
    
    public static Buff toxicGas(Location loc){
        return new Buff("toxic gas", new Description("buffs", "You are choking!")){
            double sp;
            @Override
            public void start(Creature c){
                c.attributes.health.trauma.mean -= 0.3;
                sp = getGaussianAboveZero(0.1, 0.05);
                c.attributes.health.walkSpeed -= sp;
            }
            @Override
            public void turn(Creature c, Iterator iter){
                c.attributes.health.addMinorTrauma((R.nextDouble()*3+1)*loc.feeling.difficulty, name);
            }
            @Override
            public void end(Creature c){
                c.attributes.health.trauma.mean += 0.3;
                c.attributes.health.walkSpeed += sp;
            }
        };
    }
    
}
