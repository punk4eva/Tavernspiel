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
package items.equipment;

import creatureLogic.Attack;
import creatureLogic.Attack.AttackType;
import creatureLogic.Description;
import creatures.Creature;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Melee Weapon that possesses specific non-enchantment 
 * traits.
 */
public abstract class ModifiedWeapon extends MeleeWeapon{

    public ModifiedWeapon(double q, String n, int _x, int _y, Description desc, int dur, int st, int lo, int up, int re, double ac, double sp, double bl){
        super(q, n, _x, _y, desc, dur, st, lo, up, re, ac, sp, bl);
    }
    public abstract void updateModification(int lev);
    
    public abstract boolean shouldActivate();
    
    public abstract void onHit(Creature victim, Attack attack);
    
    public abstract AttackType getAttackType();
}
