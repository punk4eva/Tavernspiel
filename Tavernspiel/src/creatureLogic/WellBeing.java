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

package creatureLogic;

import buffs.Injury;
import buffs.Injury.*;
import creatureLogic.Attack.CreatureAttack;
import creatures.Creature;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import logic.Distribution.NormalProb;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the over all status of a Creature, i.e: its injuries
 * and the attributes that depend on them.
 */
public class WellBeing implements Serializable{
    
    private static final long serialVersionUID = 56734892217432L;
    
    public double regen = 0.075;
    public double miscSpeed = 1, walkSpeed = 1, attackSpeed = 1;
    public final NormalProb evasion, accuracy, attack, trauma, 
            injurySavingThrow = new NormalProb(5, 2.5);
    
    public InjuryManager injuries = new InjuryManager();
    
    /**
     * A default constructor with parameters for the Hero.
     */
    public WellBeing(){
        this(0.075,1,1,1,new NormalProb(6, 3),new NormalProb(6, 3),
                new NormalProb(6, 3),new NormalProb(7, 2));
    }
    
    /**
     * Creates an instance.
     * @param reg The regen speed.
     * @param msp The misc Item action speed.
     * @param wsp The walking speed.
     * @param asp The attack speed.
     * @param ev The evasion curve.
     * @param acc The accuracy curve.
     * @param att The attack curve.
     * @param tra The trauma curve.
     */
    public WellBeing(double reg, double msp, double wsp, double asp, NormalProb ev, NormalProb acc, NormalProb att, NormalProb tra){
        regen = reg;
        miscSpeed = msp;
        walkSpeed = wsp;
        attackSpeed = asp;
        evasion = ev;
        accuracy = acc;
        attack = att;
        trauma = tra;
    }
    
    /**
     * Rolls a saving throw to see if the Creature has died from its current 
     * trauma.
     * @return true if the Creature died.
     */
    @Unfinished
    public boolean checkTrauma(){
        if(!trauma.check()){
            System.out.println("U ded fam uwu");
            return true;
        }
        return false;
    }
    
    /**
     * Adds the given Injury to the InjuryManager.
     * @param inj
     * @param traumaCheck Whether to roll a trauma check.
     */
    public void addInjury(Injury inj, boolean traumaCheck){
        if(inj instanceof PermanentInjury) injuries.addInjury((PermanentInjury) inj);
        else injuries.addInjury((HealingInjury) inj);
        if(traumaCheck) checkTrauma();
    }
    
    /**
     * Removes the given Injury to the InjuryManager.
     * @param inj
     */
    public void removeInjury(Injury inj){
        if(inj instanceof HealingInjury) injuries.removeInjury((HealingInjury) inj);
        else injuries.removeInjury((PermanentInjury) inj);
    }
    
    /**
     * Takes a hit.
     * @param atk The attack.
     * @param owner The Creature being hit.
     */
    public void hit(CreatureAttack atk, Creature owner){
        double dam = atk.damage.next() - owner.inventory.equipment.getDefense(atk.injury.bodyPart).next();
        if(dam>0){
            injuries.minorTrauma += dam;
            dam -= injurySavingThrow.next();
            if(dam>0){
                atk.injury.decideLevel(dam);
                addInjury(atk.injury, true);
            }
        }
    }

    /**
     * This class manages all Injuries that the Creature has sustained.
     */
    public class InjuryManager implements Serializable{

        private static final long serialVersionUID = 56734892217432L;
        
        public double minorTrauma = 0; //Capped at 35
        public final List<PermanentInjury> permanent = new LinkedList<>();
        public final List<HealingInjury> healing = new LinkedList<>();
        
        /**
         * Registers the given Injury to the List of Injuries.
         * @param inj
         */
        public void addInjury(HealingInjury inj){
            if(checkHealingValidity(inj)){
                prepareInjuryAddition(inj);
                healing.add(inj);
            }
        }
        
        /**
         * Registers the given Injury to the List of Injuries.
         * @param inj
         */
        public void addInjury(PermanentInjury inj){
            ListIterator<PermanentInjury> iter = permanent.listIterator();
            PermanentInjury i;
            while(iter.hasNext()){
                i = iter.next();
                if(i.bodyPart.equals(inj.bodyPart)&&!i.injuryVito&&i.baseName.equals(inj.baseName)){
                    if(i.level<inj.level){
                        prepareInjuryRemoval(i);
                        prepareInjuryAddition(inj);
                        iter.set(inj);
                    }else if(i.level==inj.level){
                        inj.setLevel(inj.level+1);
                        prepareInjuryRemoval(i);
                        prepareInjuryAddition(inj);
                        iter.set(inj);
                    }else if(i.level>inj.level) return;
                }
            }
        }

        /**
         * De-registers the given Injury to the List of Injuries.
         * @param inj
         */
        @Unfinished
        public void removeInjury(PermanentInjury inj){
            
        }
        
        /**
         * De-registers the given Injury to the List of Injuries.
         * @param inj
         */
        public void removeInjury(HealingInjury inj){
            HealingInjury n = prepareInjuryRemoval(inj);
            if(n!=null) healing.add(n);
            healing.remove(inj);
        }
        
        private HealingInjury prepareInjuryRemoval(HealingInjury inj){
            inj.removeFrom(WellBeing.this);
            if(inj.level>1) return inj.downgradeInjury();
            return null;
        }
        
        private void prepareInjuryRemoval(PermanentInjury inj){
            inj.removeFrom(WellBeing.this);
        }
        
        private void prepareInjuryAddition(Injury inj){
            inj.addTo(WellBeing.this);
        }
        
        /**
         * Progresses the healing of all healable Injuries.
         * @param turnNum
         */
        public void decrementInjuryLevel(double turnNum){
            if(minorTrauma>0){
                minorTrauma -= turnNum * regen;
                if(minorTrauma<0) minorTrauma = 0;
            }
            ListIterator<HealingInjury> iter = healing.listIterator();
            HealingInjury inj;
            while(iter.hasNext()){
                inj = iter.next();
                inj.healingNum -= turnNum*regen;
                if(inj.healingNum<=0){
                    HealingInjury n = prepareInjuryRemoval(inj);
                    if(n!=null){
                        prepareInjuryAddition(n);
                        iter.set(n);
                    }
                    else iter.remove();
                }
            }
        }
        
        /**
         * Checks to see if the Injury is not overriden by a permanent.
         * @param inj
         * @return True if the Injury is valid.
         */
        public boolean checkHealingValidity(HealingInjury inj){
            return !permanent.stream().anyMatch(i -> i.bodyPart.equals(inj.bodyPart)
                    && ((PermanentInjury)i).injuryVito);
        }
        
    }
    
}
