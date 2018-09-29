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
public class CandleStaff extends MeleeWeapon{
    
    public CandleStaff(){
        super(4, "Candle Staff", 80, 80, new Description("weapons", "This oriental varient of the trident has longer blades so can be used to chop as well as stab.","This weapon has a high reach and is slightly more durable."), 170, 18, 9, 23, 2, 1, 1, 0);
    }
    
}
