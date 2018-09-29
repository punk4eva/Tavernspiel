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
public class Triaxe extends MeleeWeapon{
    
    public Triaxe(){
        super(3.2, "Triaxe", 64, 80, new Description("weapons","A pole with three serated axe blades capable of inflicting multiple injuries in one strike.","This weapon blocks damage, is far reaching and is slightly accurate but slightly slow."), 165, 17, 8, 22, 2, 1.1, 0.9, 3);
    }
    
}
