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
    
    public double traumaLevel, evLoss, accLoss, attLoss;
    public int level;
    public EnBodyPart bodyPart;
    public final String baseName;
    
    public final static Distribution BODY_DISTRIBUTION = new Distribution(new int[]{5,3,3,7,7,1,1});
    
    /**
     * This Enum represents a part of the body.
     */
    public static enum EnBodyPart{
        NON_DOMINANT_ARM, DOMINANT_ARM, LEG, STOMACH, CHEST, HEAD, FOOT
    } 

    /**
     * Construct an instance.
     * @param n The name of the Injury.
     * @param desc The Description.
     * @param p The parameters of the Injury: traumaMean, traumaSdev, evasionMean, evasionSdev, accuracyMean, accuracySdev, attackMean, attackSdev.
     */
    public Injury(String n, Description desc, double[] p){
        super(n, desc);
        baseName = n;
        traumaLevel = Distribution.getGaussian(p[0], p[1]);
        evLoss = Distribution.getGaussian(p[2], p[3]);
        accLoss = Distribution.getGaussian(p[4], p[5]);
        attLoss = Distribution.getGaussian(p[6], p[7]);
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
        
        public abstract HealingInjury downgradeInjury();
        
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

}
