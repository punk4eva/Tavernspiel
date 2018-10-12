/*
 * Copyright 2018 Adam Whittaker.
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

import containers.Equipment;
import creatureLogic.Description;
import items.actions.ItemAction;
import listeners.ScreenItem;
import listeners.XPListener;

/**
 *
 * @author Adam Whittaker
 */
public abstract class MythicalWeapon extends MeleeWeapon implements ScreenItem, XPListener{
    
    protected int uses = 0;
    
    public MythicalWeapon(String n, int _x, int _y, Description desc, int st, int lo, int up, int re, double ac, double sp, double bl){
        super(6, n, _x, _y, desc, -1, st, lo, up, re, ac, sp, bl);
        actions = ItemAction.getArray(4);
        actions[2] = ItemAction.EQUIP;
        usesTillIdentify = 0;
    }
    
    @Override
    public void upgrade(){
        level++;
        damageDistrib.add(quality);
        testEnchantment();
    }
    
    @Override
    public void use(){
        uses++;
    }
    
    @Override
    public void setToUnequipped(){
        equipment.heroOwner.attributes.xpListener = null;
        super.setToUnequipped();
    }
    
    @Override
    public void setToEquipped(Equipment eq){
        super.setToEquipped(eq);
        equipment.heroOwner.attributes.xpListener = this;
    }
    
}
