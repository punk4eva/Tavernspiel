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
package items.actions;

import creatures.Creature;
import creatures.Hero;
import gui.LocationViewable.ClickPredicate;
import static gui.LocationViewable.LOCATION_SELECT;
import gui.Window;
import gui.mainToolbox.Screen.ScreenEvent;
import items.Item;
import listeners.ScreenListener;

/**
 *
 * @author Adam Whittaker
 */
public abstract class LocationSelectAction extends ItemAction{
    
    private final String locationMessage;
    private final ClickPredicate predicate; 

    public LocationSelectAction(String str, double tM, String locMessage, ClickPredicate p){
        super(str, tM);
        locationMessage = locMessage;
        predicate = p;
    }
    
    public LocationSelectAction(String str, String locMessage, ClickPredicate p){
        super(str);
        locationMessage = locMessage;
        predicate = p;
    }

    @Override
    public void act(Item i, Creature c, int slot, Object... data){
        if(c instanceof Hero){
            LOCATION_SELECT.setData((ScreenEvent sc) -> {
                System.out.println(sc.getName());
                switch(sc.getName()){
                    case "backLocation": 
                        act(i, c, sc.x, sc.y, slot, data);
                    case "locationPopupX": Window.main.removeViewable();
                    break;
                }
            }, locationMessage, c, predicate);
            Window.main.setViewable(LOCATION_SELECT);
        }else act(i, c, (int)data[0], (int)data[1], slot, data);
    }
    
    public abstract void act(Item i, Creature c, int x, int y, int slot, Object... data);
    
}
