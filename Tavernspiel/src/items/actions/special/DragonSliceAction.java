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
package items.actions.special;

import creatures.Creature;
import items.Item;
import items.actions.LocationSelectAction;

/**
 *
 * @author Adam Whittaker
 */
public class DragonSliceAction extends LocationSelectAction{

    public DragonSliceAction(){
        super("SLICE", 1.2, "Select a creature to slice.", (c, x, y) -> {
            return Math.abs(c.x-x)+Math.abs(c.y-y)<4 && c.area.creaturePresent(x, y);
        });
    }

    @Override
    public void act(Item i, Creature c, int x, int y, int slot, Object... data){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
