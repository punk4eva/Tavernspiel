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
import items.equipment.MeleeWeapon;

/**
 *
 * @author Adam Whittaker
 */
public class FateScythe extends MeleeWeapon{
    
    private int usesTillMastery = 60; /*Damage scales to 12-27*/
    
    public FateScythe(){
        super(3, "Fate's Scythe", 112, 80, new Description("weapons", 
                "The unwieldly shape of this weapon means it needs a lot of skill to master. It is designed to quickly eliminate multiple opponents.","This weapon has insane durability, is well balanced and blocks some damage but takes a while to master."), 205, 19, 2, 7, 1, 1.08, 1.08, 2);
    }
    
    @Override
    public void use(){
        super.use();
        if(usesTillMastery>0){
            usesTillMastery--;
            if(usesTillMastery%6==0) damageDistrib.add(1, 2);
        }
    }
    
}
