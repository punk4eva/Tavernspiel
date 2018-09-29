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
public class CounterHammer extends MeleeWeapon{
    
    public CounterHammer(){
        super(1.5,"Counter Hammer", 16, 80, new Description("weapons","A sturdy staff with a steel weight at each end, one for attack and one to counterbalance."), 160, 13, 2, 9, 1, 0.95, 1, 0);
    }
    
}
