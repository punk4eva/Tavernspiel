/*
 * Copyright 2019 Adam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package buffs;

import static buffs.Injury.EnBodyPart.*;
import creatureLogic.Description;
import creatureLogic.WellBeing;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents an injury that a Creature sustained.
 */
public abstract class Injury extends StatusAbnormality{
    
    public double traumaLevel, evLoss, accLoss, attLoss, spLoss, atspLoss;
    public int level;
    public EnBodyPart bodyPart;
    public final String baseName;
    public String source;
    
    public final static Distribution BODY_DISTRIBUTION = new Distribution(new int[]{5,3,3,7,7,1,1});
    
    /**
     * This Enum represents a part of the body.
     */
    public static enum EnBodyPart{
        NON_DOMINANT_ARM("non-dominant arm"), DOMINANT_ARM("dominant arm"),
        LEG("leg"), STOMACH("stomach"), CHEST("chest"), HEAD("head"), FOOT("foot");
        
        public final String agnomen;
        
        private EnBodyPart(String n){
            agnomen = n;
        }
        
    } 

    /**
     * Construct an instance.
     * @param n The name of the Injury.
     * @param desc The Description.
     * @param p The parameters of the Injury: traumaMean, traumaSdev, evasionMean, 
     * evasionSdev, accuracyMean, accuracySdev, attackMean, attackSdev, speedMean,
     * speedSdev, atspMean, atspSdev.
     */
    public Injury(String n, Description desc, double[] p){
        super(n, desc);
        baseName = n;
        setAttributes(p);
    }
    
    /**
     * Constructs a pseudo-instance of an Injury.
     * @param n The name of the Injury.
     * @param desc The Description.
     */
    public Injury(String n, Description desc){
        super(n, desc);
        baseName = n;
    }
    
    /**
     * Sets the attributes of this Injury.
     * @param p The parameters of the Injury: traumaMean, traumaSdev, evasionMean, 
     * evasionSdev, accuracyMean, accuracySdev, attackMean, attackSdev, speedMean,
     * speedSdev, atspMean, atspSdev.
     */
    protected final void setAttributes(double[] p){
        traumaLevel = Distribution.getGaussian(p[0], p[1]);
        evLoss = Distribution.getGaussian(p[2], p[3]);
        accLoss = Distribution.getGaussian(p[4], p[5]);
        attLoss = Distribution.getGaussian(p[6], p[7]);
        spLoss = Distribution.getGaussian(p[8], p[9]);
        atspLoss = Distribution.getGaussianAboveZero(p[10], p[11]);
    }
    
    /**
     * This class represents an Injury that cannot be healed naturally.
     */
    public abstract static class PermanentInjury extends Injury{
        
        public boolean injuryVito = false;
        
        public PermanentInjury(String n, Description desc, double[] p){
            super(n, desc, p);
        }
        
    }
    
    /**
     * This class represents an Injury that heals naturally.
     */
    public abstract static class HealingInjury extends Injury{
        
        public double healingNum;
        
        public HealingInjury(String n, Description desc, double[] p){
            super(n, desc, p);
        }
        
        public HealingInjury(String n){
            super(n, new Description("Injury", ""));
        }
        
        public abstract HealingInjury downgradeInjury();
        
        /**
         * Sets the name of this Injury using its level and body part.
         */
        protected void resetName(){
            name = baseName + "of the " + bodyPart.agnomen;
            switch(level){
                case 1: name = "Shallow " + name; break;
                case 2: name = "Significant " + name; break;
                case 3: name = "Severe " + name; break;
            }
        }
        
        /**
         * Returns a String describing the severity of the Injury based on the 
         * given level.
         * @param level
         * @return
         */
        public static String injuryText(int level){
            switch(level){
                case 1: return "Although this injury is not as serious as could be, it is not to be taken lightly.";
                case 2: return "This injury is not life-threatening yet, but it could be if combined with other injuries.";
                default: return "A serious, life-threatening injury such as this one should be dealt with as soon as possible.";
            }
        }
        
    }
    
    /**
     * Generates a random body part based on the BODY_DISTRIBUTION.
     * @return
     */
    public static EnBodyPart getRandomBodyPart(){
        switch((int)BODY_DISTRIBUTION.next()){
            case 0: return NON_DOMINANT_ARM;
            case 1: return DOMINANT_ARM;
            case 2: return LEG;
            case 3: return STOMACH;
            case 4: return CHEST;
            default: return HEAD;
        }
    }
    
    /**
     * Adds the Injury to the given WellBeing.
     * @param w
     */
    public void addTo(WellBeing w){
        w.accuracy.mean -= accLoss;
        w.evasion.mean -= evLoss;
        w.attack.mean -= attLoss;
        w.trauma.mean -= traumaLevel;
        w.walkSpeed += spLoss;
        w.checkTrauma();
        addAction(w);
    }
    
    /**
     * Removes the Injury to the given WellBeing.
     * @param w
     */
    public void removeFrom(WellBeing w){
        w.accuracy.mean += accLoss;
        w.evasion.mean += evLoss;
        w.attack.mean += attLoss;
        w.walkSpeed -= spLoss;
        w.trauma.mean += traumaLevel;
        removeAction(w);
    }
    
    /**
     * The procedure to run when the Injury is added.
     * @param w
     */
    protected abstract void addAction(WellBeing w);
    
    /**
     * The procedure to run when the Injury is removed.
     * @param w
     */
    protected abstract void removeAction(WellBeing w);
    
    /**
     * Sets the level of this Injury.
     * @param lvl
     */
    public abstract void setLevel(int lvl);
    
    /**
     * Sets the body part of this Injury.
     * @param bP
     */
    public void setBodyPart(EnBodyPart bP){
        bodyPart = bP;
    }

    /**
     * Decides the level of this Injury based on the damage inflicted.
     * @param dam
     */
    public void decideLevel(double dam){
        setLevel((int)Math.floorDiv((int)dam, 3));
    }
    
    /**
     * Sets the source of the Injury.
     * @param src
     */
    public void setSource(String src){
        source = src;
    }

}
