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
public class Ulysses extends MeleeWeapon{
    
    public Ulysses(){
        super(2,"Ulysses", 32, 80, new Description("weapons","Three blades chained to a well-crafted handle. Whip-like weapons such as this are effective at digging into flesh.","This weapon is slightly lacking in durability but is fast."), 142, 12, 2, 7, 1, 0.97, 1.12, 0);
    }
    
}
