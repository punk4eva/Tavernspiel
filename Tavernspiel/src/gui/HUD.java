package gui;

import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import java.awt.Graphics;
import java.util.List;
import listeners.ScreenListener;
import creatureLogic.QuickSlot;
import dialogues.BuffDialogue;
import gui.mainToolbox.HUDStrategy;
import gui.mainToolbox.hudLayout.DefaultHUDLookAndFeel;

/**
 *
 * @author Adam Whittaker and Charlie Hands
 * 
 * This class represents the Heads-Up Display of the GUI.
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
                return;
            case "Wait": Window.main.player.attributes.ai.paralyze(1.0);
                return;
        }
        if(name.getName().startsWith("buff: "))
            new BuffDialogue(quickslot.hero.getBuff(name.getName().substring(6))).next();
    }    
    
}
