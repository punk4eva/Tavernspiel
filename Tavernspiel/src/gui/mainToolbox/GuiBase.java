
package gui.mainToolbox;

import containers.HeroInventory;
import creatures.Hero;
import dialogues.Dialogue;
import gui.HUD;
import gui.Viewable;
import items.Item;
import java.awt.Color;
import java.awt.Graphics;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import logic.ConstantFields;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class GuiBase{

    private boolean viewingInventory = false;
    protected List<Screen> screens = new LinkedList<>();
    protected final List<Screen> draggables = new LinkedList<>();
    protected Screen lastDragged;
    public HUD hud;
    public Hero hero;
    public volatile Dialogue dialogue = null;
    public volatile Viewable viewable;
    protected ArrayDeque<SimpleEntry<Color, String>> queue = new ArrayDeque<>();
    
    /**
     * Creates a new instance.
     * The HUD and Hero need to be injected manually.
     */
    public GuiBase(){
        for(int i=0;i<4;i++) queue.add(new SimpleEntry(ConstantFields.plainColor, ""));
    }
    
    @Unfinished("Remove debug")
    public void paintScreens(Graphics g){
        g.setColor(Color.RED);
        screens.forEach((sc) -> {
            g.fill3DRect(sc.tlx, sc.tly, sc.brx-sc.tlx, sc.bry-sc.tly, true);
        });
    }
    
    /**
     * Removes the current Viewable.
     */
    public void removeViewable(){
        viewable = null;
        resetScreens();
    }
    
    /**
     * Updates the list of active screens.
     */
    protected void resetScreens(){
        if(viewable!=null) screens = viewable.getScreens();
        else if(dialogue!=null) screens = dialogue.getScreens();
        else if(viewingInventory) screens = hero.getInventoryScreens();
        else screens = hud.getScreens();
    }
    
    /**
     * Changes the look and feel of the HUD.
     * @param st The new layout strategy.
     */
    public void changeHUDLookAndFeel(HUDStrategy st){
        hud.setStrategy(st);
        resetScreens();
    }
    
    /**
     * Checks whether or not the HUD buttons are clear to be clicked on.
     * @return
     */
    public boolean hudClear(){
        return viewable==null&&dialogue==null&&!viewingInventory;
    }

    /**
     * Sets the current Viewable
     * @param v
     */
    public void setViewable(Viewable v){
        viewable = v;
        screens = viewable.getScreens();
    }

    /**
     * Adds a Draggable screen to this GUI.
     * @param sc
     */
    public void addDraggable(Screen sc){
        if(draggables.isEmpty()) lastDragged = sc;
        draggables.add(sc);
    }

    /**
     * Checks whether the GUI has an active Viewable.
     * @return
     */
    public boolean viewableActive(){
        return viewable!=null;
    }

    /**
     * Paints the GUI.
     * @param g The Graphics to paint on.
     */
    public void paint(Graphics g){
        g.setColor(ConstantFields.textColor);
        g.setFont(ConstantFields.smallTextFont);
        int height = Main.HEIGHT*6/7;
        Iterator<SimpleEntry<Color, String>> iter = queue.descendingIterator();
        while(iter.hasNext()){
            SimpleEntry se = iter.next();
            g.setColor((Color)se.getKey());
            g.drawString((String)se.getValue(), 48, height);
            height -= 16;
        }
        hud.paint(g);
        if(viewingInventory) hero.paintInventory(g);
        if(dialogue!=null) dialogue.paint(g);
        if(viewable!=null) viewable.paint(g);
    }

    /**
     * Sets the Dialogue.
     * @param d
     */
    public void setDialogue(Dialogue d){
        dialogue = d;
        resetScreens();
    }
    
    /**
     * Adds a message to the queue.
     * @param message The message.
     */
    public void addMessage(String message){
        if(message!=null){
            queue.add(new SimpleEntry(ConstantFields.plainColor, message));
            queue.pop();
        }
    }
    
    /**
     * Adds a message to the queue in the given color.
     * @param color The color.
     * @param message The message.
     */
    public void addMessage(Color color, String message){
        if(message!=null){
            queue.add(new SimpleEntry(color, message));
            queue.pop();
        }
    }
    
    /**
     * Sets the state of the inventory.
     * @param inv True if the inventory should be painted.
     */
    public void setInventoryActive(boolean inv){
        ((HeroInventory)hero.inventory).manager.predicate = 
                ConstantFields.truthPredicate;
        viewingInventory = inv;
        resetScreens();
    }
    
    /**
     * Sets the state of the inventory.
     * @param inv True if the inventory should be painted.
     * @param pred Whether the Item should be selectable.
     */
    public void setInventoryActive(boolean inv, Predicate<Item> pred){
        ((HeroInventory)hero.inventory).manager.predicate = pred;
        viewingInventory = inv;
        resetScreens();
    }
    
    /**
     * Toggles the whether or not to display the inventory.
     */
    @Unfinished("Could be redundant")
    public void toggleInventory(){
        viewingInventory = !viewingInventory;
        resetScreens();
    }

}
