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
public class Kanabo extends MeleeWeapon{
    
    public Kanabo(){
        super(3.1, "Kanabo", 64, 64, new Description("weapons","This powerful instrument of war can only serve those powerful enough to wield it.","This weapon is extremely durable but slow and inaccurate. It blocks a tremendous amount of damage."), 210, 17, 10, 25, 1, 0.8, 0.9, 7);
    }
    
}
