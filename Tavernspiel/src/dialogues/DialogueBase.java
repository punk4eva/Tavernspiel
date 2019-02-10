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

package dialogues;

import gui.Window;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import gui.utils.CSlider;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.List;
import listeners.ScreenListener;

/**
 *
 * @author Adam Whittaker
 * 
 * The Base class for all dialogues.
 */
public abstract class DialogueBase implements ScreenListener, KeyListener{

    List<Screen> screens;
    final boolean clickOffable;
    public int width, height, sx, sy;
    
    public static final int PADDING = 8;
    public static final ScreenEvent OFF_CASE = new ScreenEvent("offCase");
    
    /**
     * Creates a new Dialogue.
     * @param click Sets whether the user can click away.
     */
    public DialogueBase(boolean click){
        clickOffable = click;
    }
    
    /**
     * Sets the width, height and screens of this dialogue at the leisure of the
     * subclass.
     * @param w
     * @param h
     * @param sc
     */
    protected final void setRectAndScreens(int w, int h, List<Screen> sc){
        width = w;
        height = h;
        sx = (Main.WIDTH-width)/2;
        sy = (Main.HEIGHT-height)/2;
        screens = sc;
    }
    
    /**
     * Gets the screens associated with this Dialogue.
     * @return The Screens.
     */
    public final List<Screen> getScreens(){
        return screens;
    }
    
    /**
     * Deactivates this Dialogue.
     */
    protected void deactivate(){
        Window.main.setDialogue(null);
    }
    
    /**
     * Activates this Dialogue.
     */
    public synchronized void next(){
        Window.main.setDialogue(this);
        screens.forEach((sc) -> {
            if(sc instanceof CSlider.CSliderHandle) Window.main.addDraggable(sc);
        });
    }
    
    /**
     * Notifies this Dialogue that the user clicked away.
     */
    public void clickedOff(){
        if(clickOffable) deactivate();
    }
    
    /**
     *
     * @param g
     */
    public void paint(Graphics2D g){
        render(g, sx, sy);
    }
    
    protected abstract void render(Graphics2D g, int x, int y);
    
}
