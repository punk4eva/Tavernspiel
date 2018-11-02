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
package items.equipment.weapons;

import creatureLogic.Description;
import items.actions.ItemAction;
import items.equipment.MythicalWeapon;

/**
 *
 * @author Adam Whittaker
 */
public class DragonWings extends MythicalWeapon{
    
    public int flameReload = 15;
    private int currentHeroLevel, lastHeroLevel;
    
    public DragonWings(){
        super("Wings of the Dragon", 80, 144, new Description("weapons", "This is tremendously rare weapon was forged by many craftsmen over 1000s of years using divine black magic."), 24, 26, 54, 2, 1.6, 1.1, 5);
        actions[3] = ItemAction.DRAGON_SLICE;
    }

    @Override
    public boolean onOpeningDialogue(){
        boolean flame = flameReload<=0, wrath = uses>99&&currentHeroLevel>lastHeroLevel;
        ItemAction equip = actions[2];
        if(flame&&wrath){
            actions = ItemAction.getArray(6);
            actions[2] = equip;
            actions[3] = ItemAction.DRAGON_SLICE;
            actions[4] = ItemAction.DRAGON_FLAME;
            actions[5] = ItemAction.DRAGON_WRATH;
        }else if(flame){
            actions = ItemAction.getArray(5);
            actions[2] = equip;
            actions[3] = ItemAction.DRAGON_SLICE;
            actions[4] = ItemAction.DRAGON_FLAME;
        }else if(wrath){
            actions = ItemAction.getArray(5);
            actions[2] = equip;
            actions[3] = ItemAction.DRAGON_SLICE;
            actions[4] = ItemAction.DRAGON_WRATH;
        }else{
            actions = ItemAction.getArray(4);
            actions[2] = equip;
            actions[3] = ItemAction.DRAGON_SLICE;
        }
        return true;
    }
    
    @Override
    public void use(){
        super.use();
        flameReload--;
    }

    @Override
    public void xpChange(int xp, int level){
        currentHeroLevel = level;
    }
    
}
