/*
 * Copyright 2018 steelr.
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
package items.equipment.weapons;

import creatureLogic.Attack;
import creatureLogic.Description;
import creatures.Creature;
import items.equipment.MythicalWeapon;

/**
 *
 * @author Adam Whittaker
 */
public class ElementalSkull extends MythicalWeapon{

    public ElementalSkull(){
        super("Elemental Skull Chain", 112, 144, new Description("weapons", "Somehow, someone managed to bind a rare chaotic fire-frost elemental to a chain as a pet."), 23, 19, 56, 1, 2.2, 1.5, 0);
    }

    @Override
    public boolean onOpeningDialogue(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void xpChange(int xp, int level){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateModification(int lev){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldActivate(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onHit(Creature victim, Attack attack){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Attack.AttackType getAttackType(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
