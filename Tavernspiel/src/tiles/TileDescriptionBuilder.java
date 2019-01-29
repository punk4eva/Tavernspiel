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

package tiles;

import items.builders.DescriptionBuilder;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class procedurally generates the descriptions of tiles.
 */
public class TileDescriptionBuilder extends DescriptionBuilder{
    
    private static final String[] WATER = {"REFLECTION_PLACEHOLDER", "There seems to be something swimming in it.", 
        "Little shadows of blood are mixed in with it.", "Froth bubbles around the contours of the water.", 
        "Seems a little clear for where you found it.", "Almost looks good enough to drink from. Almost.", 
        "Especially murky.", "Looks like there's some reflective oil floating on top."};
    
    public static String waterDesc(){
        int r = Distribution.r.nextInt(WATER.length);
        if(r==0) return "You can almost see your " + word(appearance) + " reflection from over here!";
        return WATER[r];
    }
    
}
