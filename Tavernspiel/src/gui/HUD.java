package gui;

import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import listeners.ScreenListener;
import logic.ConstantFields;
import logic.ImageUtils;
import creatureLogic.QuickSlot;
import gui.mainToolbox.HUDStrategy;
import gui.mainToolbox.hudLayout.DefaultHUDLookAndFeel;

/**
 *
 * @author Charlie Hands
 */
public class HUD implements Viewable, ScreenListener{
    
    public final QuickSlot quickslot;
    private HUDStrategy strategy;
    
    public HUD(QuickSlot q){
        quickslot = q;
        strategy = new DefaultHUDLookAndFeel(this);
    }
    
    public void setStrategy(HUDStrategy st){
        strategy = st;
    }

    @Override
    public List<Screen> getScreens(){
        return strategy.getScreens();
    }

    @Override
    public void paint(Graphics g){
        strategy.paint(g);
    }

    @Override
    public void screenClicked(ScreenEvent name){
        switch(name.getName()){
            case "Inventory": 
                Window.main.toggleInventory();
                break;
            case "Wait": Window.main.player.attributes.ai.paralyze(1.0);
                break;
        }
    }    
    
}
