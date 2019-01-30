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
    
    private static final Distribution DESCRIPTION_CHANCE = new Distribution(1,5);
    
    private static final String[] WATER = {"REFLECTION_PLACEHOLDER", "There seems to be something swimming in it.", 
        "Little shadows of blood are mixed in with it.", "Froth bubbles around the contours of the water.", 
        "Seems a little clear for where you found it.", "Almost looks good enough to drink from. Almost.", 
        "Especially murky.", "Looks like there's some reflective oil floating on top."};
    private static final String[] WALL = {"DRIPPING_PLACEHOLDER","OOZING_PLACEHOLDER","GAS_PLACEHOLDER","This particular section looks more damaged than the others.", 
        "There are barely visible claw marks on it.", "There is evidence that part of it collapsed and had to be reinforced.",
        "Pin-sized cavities riddle the wall, probably from acid.", "This wall has seen better days.",
        "It almost seems like it was recently repaired.", "You see fist-sized holes in the wall. Could be burrows."};
    private static final String[] FLOOR = {"RITUAL_PLACEHOLDER","STAIN_PLACEHOLDER","RUNE_PLACEHOLDER",
        "You had better tread carefully! This floor looks like it could collapse any moment!",
        "You think you see what looks like a hidden trap.", "There are a lot of bloodstains on this floor.",
        "A corpse of some small critter is lying here.", "Some insects are attempting to make a burrow here.",
        "There is some shattered glass here.", "You can see the muddy footprints of some creature.",
        "This floor could us some cleaning.", "This floor looks rather good for where you found it."};
    
    public static String waterDesc(){
        int r = Distribution.r.nextInt(WATER.length);
        if(r==0) return "You can almost see your " + word(appearance) + " reflection from over here!";
        return WATER[r];
    }
    
    public static String wallDesc(){
        int r = Distribution.r.nextInt(WALL.length);
        switch (r){
            case 0:
                return "There is a " + word(color) + " liquid dripping from " + word(appearance) + " pores in the wall.";
            case 1:
                return "There is a " + word(viscosity) + " fluid oozing from this section of the wall. It smells like " + smellWord() + ".";
            case 2:
                return "You can feel a" + word(temp) + " draft from natural gas coming from " + word(shapeMod) + " cracks in the wall.";
            default:
                return WALL[r];
        }
    }
    
    public static String floorDesc(){
        int r = Distribution.r.nextInt(FLOOR.length);
        switch (r){
            case 0:
                return "There are dim remains of " + word(shape) + " ritual markings on the floor.";
            case 1:
                return "A " + word(colorMod) + word(appearance) + " stain of blood here.";
            case 2:
                return "A " + word(rune) + " rune painted on the ground.";
            default:
                return FLOOR[r];
        }
    }
    
    public static String grassDesc(){
        throw new UnsupportedOperationException();
    }
    
    public static String depthDesc(){
        throw new UnsupportedOperationException();
    }
    
    public static void augmentDescription(Tile t){
        if(DESCRIPTION_CHANCE.chance()){
            t.description.layers[0] += "\n\n";
            if(t.name.contains("water")) t.description.layers[0] += waterDesc();
            else if(t.name.contains("floor")) t.description.layers[0] += floorDesc();
            else if(t.name.contains("wall")) t.description.layers[0] += wallDesc();
            else if(t.name.contains("grass")) t.description.layers[0] += grassDesc();
            else if(t.name.contains("depth")) t.description.layers[0] += depthDesc();
        }
    }
    
}
