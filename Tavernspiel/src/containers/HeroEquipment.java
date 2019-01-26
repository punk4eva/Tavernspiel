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

package containers;

import creatures.Hero;
import gui.mainToolbox.Screen;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import logic.ConstantFields;
import static logic.ConstantFields.beginHeight;
import static logic.ConstantFields.beginWidth;
import static logic.ConstantFields.padding;
import static logic.ConstantFields.sqheight;
import static logic.ConstantFields.sqwidth;
import static logic.ConstantFields.truthPredicate;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 */
public class HeroEquipment extends Equipment{
    
    public List<Screen> screens;
    public final Hero owner;

    /**
     * Creates a new instance
     * @param hero The owner.
     */
    public HeroEquipment(Hero hero){
        owner = hero;
    }
    
    /**
     * Paints this object onto the given graphics.
     * @param g The graphics to draw on.
     * @param beginWidth The width to begin drawing at.
     * @param beginHeight The height to begin drawing at.
     * @param sqwidth The width of item squares.
     * @param sqheight The height of item squares.
     * @param padding The length of padding.
     */
    public void paint(Graphics g, int beginWidth, int beginHeight, int sqwidth, int sqheight, int padding){
        if(weapon!=null) ImageUtils.paintItemSquare(g, beginWidth+padding, beginHeight+padding, sqwidth, sqheight, weapon, owner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+padding, beginHeight+padding, sqwidth, sqheight, ConstantFields.weaponOutline);
        if(helmet!=null) ImageUtils.paintItemSquare(g, beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, helmet, owner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, ImageUtils.scaledHelmetOutline);
        if(chestplate!=null) ImageUtils.paintItemSquare(g, beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, chestplate, owner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, ImageUtils.scaledChestplateOutline);
        if(leggings!=null) ImageUtils.paintItemSquare(g, beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, leggings, owner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, ImageUtils.scaledLeggingsOutline);
        if(boots!=null) ImageUtils.paintItemSquare(g, beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, boots, owner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, ImageUtils.scaledBootsOutline);
        
        if(amulet1!=null) ImageUtils.paintItemSquare(g, beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, amulet1, owner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, ConstantFields.amuletOutline);
        if(amulet2!=null) ImageUtils.paintItemSquare(g, beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, amulet2, owner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, ConstantFields.amuletOutline);
    }
    
    /**
     *
     * @param inv
     * @return
     */
    protected final List<Screen> getScreens(HeroInventory inv){
        if(screens!=null) return screens;
        List<Screen> ret = new LinkedList<>();
        ret.add(new Screen("e0", beginWidth+padding, beginHeight+padding, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e3", beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e4", beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e5", beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e6", beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, inv.manager));

        ret.add(new Screen("e1", beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e2", beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, inv.manager));
        return ret;
    }

}
