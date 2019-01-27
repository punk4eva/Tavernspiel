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

package animation.assets;

import animation.DrawnAnimation;
import items.Item;
import java.awt.Graphics2D;
import listeners.AnimationListener;

/**
 *
 * @author Adam Whittaker
 * 
 * This class handles the animation of throwing an Item.
 */
public class ThrowAnimation extends DrawnAnimation{
    
    private final Item item;
    private int x, y;
    private final int toX, toY, fX, fY;
    
    private static final int DURATION = 360;

    public ThrowAnimation(Item i, int fx, int fy, int tx, int ty, AnimationListener al){
        super(DURATION, al);
        item = i;
        x = fX = fx*16;
        y = fY = fy*16;
        toX = 16*tx-fX;
        toY = 16*ty-fY;
    }

    @Override
    public void animate(Graphics2D g, int focusx, int focusy){
        x = (int)(toX*(currentTicks/maxTicks))+fX;
        y = (int)(toY*(currentTicks/maxTicks))+fY;
        item.animation.animate(g, x+focusx, y+focusy);
        recalc();
    }

}
