
package buffs;

import creatureLogic.Description;
import creatures.Creature;
import java.util.HashMap;
import javax.swing.ImageIcon;
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
        
    }
    
    @Unfinished("Redo Attack")
    public static Buff fire(double duration){
        return new Buff("fire", new Description("You are on fire!"), duration){
            @Override
            public void start(Creature c){}
            @Override
            public void turn(Creature c){
                //c.getAttacked(new Attack(null, ));
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
        return new Buff("enraged", new Description("You are angry!")){
            
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
        return new Buff("beserk", new Description("You have gone insane!")){
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
        return new Buff("shadowmelded", new Description("You are shrouded in a"
                + "cleansing mist of dew")){
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
        return new Buff("paralyzed", new Description("You can't move!"), duration){
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Buff blindness(double duration){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Unfinished("Redo turn")
    public static Buff toxicGas(){
        return new Buff("toxic gas", new Description("You are choking!")){
            @Override
            public void start(Creature c){}
            @Override
            public void turn(Creature c){
                //c.takeDamage(new Attack());
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
