package gui;

import creatureLogic.QuickSlot;
import dialogues.BuffDialogue;
import dialogues.TileDescriptionDialogue;
import static gui.LocationViewable.LOCATION_SELECT;
import gui.mainToolbox.HUDStrategy;
import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import gui.mainToolbox.hudLayout.*;
import java.awt.Graphics;
import java.util.List;
import listeners.ScreenListener;

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
    public void screenClicked(ScreenEvent screen){
        LOCATION_SELECT.setData((sc) -> {
            Window.main.removeViewable();
            new TileDescriptionDialogue(quickslot.hero.area.map[sc.y][sc.x], quickslot.hero).next();
        }, "Investigate", null);
        switch(screen.getName()){
            case "Inventory": 
                Window.main.toggleInventory();
                return;
            case "Wait": Window.main.player.attributes.ai.paralyze(1.0);
                return;
            case "Search": Window.main.setViewable(LOCATION_SELECT);
                return;
        }
        if(screen.getName().startsWith("buff: "))
            new BuffDialogue(quickslot.hero.getBuff(screen.getName().substring(6))).next();
    }    
    
}
