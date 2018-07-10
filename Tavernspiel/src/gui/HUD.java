package gui;

import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import java.awt.Graphics;
import java.util.List;
import listeners.ScreenListener;
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
    
    /**
     * Creates a new instance.
     * @param q The QuickSlot
     */
    public HUD(QuickSlot q){
        quickslot = q;
        strategy = new DefaultHUDLookAndFeel(this);
    }
    /**
     * Creates a new instance
     * @param q The QuickSlot
     * @param st The HUDStrategy
     */
    public HUD(QuickSlot q, HUDStrategy st){
        quickslot = q;
        strategy = st;
    
    }
    
    /**
     * Resets the HUD layout strategy.
     * @param st The new strategy
     */
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
