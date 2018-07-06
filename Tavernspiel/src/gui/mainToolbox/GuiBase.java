
package gui.mainToolbox;

import creatures.Hero;
import dialogues.Dialogue;
import gui.HUD;
import gui.Viewable;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
    protected ArrayDeque<String> queue = new ArrayDeque<>();
    
    public GuiBase(){
        for(int i=0;i<4;i++) queue.add("");
    }
    
    @Unfinished("Remove debug")
    public void paintScreens(Graphics g){
        g.setColor(Color.RED);
        screens.forEach((sc) -> {
            g.fill3DRect(sc.tlx, sc.tly, sc.brx-sc.tlx, sc.bry-sc.tly, true);
        });
    }
    
    public void removeViewable(){
        viewable = null;
        resetScreens();
    }
    
    protected void resetScreens(){
        if(viewable!=null) screens = viewable.getScreens();
        else if(dialogue!=null) screens = dialogue.getScreens();
        else if(viewingInventory) screens = hero.getInventoryScreens();
        else screens = hud.getScreens();
    }
    
    public void changeHUDLookAndFeel(HUDStrategy st){
        hud.setStrategy(st);
        resetScreens();
    }
    
    public boolean hudClear(){
        return viewable==null&&dialogue==null&&!viewingInventory;
    }

    public void setViewable(Viewable v){
        viewable = v;
        screens = viewable.getScreens();
    }

    public void addDraggable(Screen lst){
        if(draggables.isEmpty()) lastDragged = lst;
        draggables.add(lst);
    }

    public boolean viewableActive(){
        return viewable!=null;
    }

    public void paint(Graphics g){
        g.setColor(ConstantFields.textColor);
        g.setFont(ConstantFields.smallTextFont);
        int height = Main.HEIGHT*6/7;
        Iterator<String> iter = queue.descendingIterator();
        while(iter.hasNext()){
            g.drawString(iter.next(), 48, height);
            height -= 16;
        }
        hud.paint(g);
        if(viewingInventory) hero.paintInventory(g);
        if(dialogue!=null) dialogue.paint(g);
        if(viewable!=null) viewable.paint(g);
    }

    public void setDialogue(Dialogue d){
        dialogue = d;
        resetScreens();
    }
    
    /**
     * Adds a message to the queue.
     * @param message The message.
     */
    public void addMessage(String message){
        queue.add(message);
        queue.pop();
    }
    
    /**
     * Adds a message to the queue in the given color.
     * @param colour The color.
     * @param message The message.
     */
    public void addMessage(String colour, String message){
        if(colour.startsWith("#")){
            queue.add("<html><font bgcolor=\""+colour+"\">"+message+"</font>");
        }else{
            queue.add("<html><font color=\""+colour+"\">"+message+"</font>");
        }
        queue.pop();
    }
    
    public void setInventoryActive(boolean inv){
        viewingInventory = inv;
        resetScreens();
    }
    
    public void toggleInventory(){
        viewingInventory = !viewingInventory;
        resetScreens();
    }

}
