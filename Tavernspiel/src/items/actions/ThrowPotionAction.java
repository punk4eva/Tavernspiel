/*
 * Copyright 2019 Adam.
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

package items.actions;

import creatures.Creature;
import items.Item;
import items.consumables.Potion;

/**
 *
 * @author Adam Whittaker
 */
public class ThrowPotionAction extends LocationItemAction{
    
    protected ThrowPotionAction(){
        super("THROW", "Select a tile to throw at.", null);
    }

    @Override
    public void act(Item i, Creature c, int x, int y, int slot, Object... data){
        c.attributes.ai.BASEACTIONS.throwPotion(c, (Potion) i, x, y);
    }

}
