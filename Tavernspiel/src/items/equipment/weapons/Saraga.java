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
public class Saraga extends MeleeWeapon{
    
    public Saraga(){
        super(2.5, "Saraga", 48, 80, new Description("weapons","A curved blade favored by low ranking military officers as it is designed to balance both speed and strength.","This is a well balanced weapon."), 151, 15, 5, 16, 1, 1.1, 1.1, 0);
    }
    
}
