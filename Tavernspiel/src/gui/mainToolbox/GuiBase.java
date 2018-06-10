
package gui.mainToolbox;

import dialogues.Dialogue;
import gui.Viewable;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 */
public class GuiBase{

    protected final List<Viewable> viewables = new LinkedList<>();
    protected final List<Screen> screens = new LinkedList<>();
    protected final List<Screen> draggables = new LinkedList<>();
    protected Screen lastDragged;
    public volatile Dialogue dialogue = null;
    protected ArrayDeque<String> queue = new ArrayDeque<>();
    
    public GuiBase(){
        for(int i=0;i<4;i++) queue.add("");
    }

    public void removeTopViewable(){
        Viewable top = viewables.remove(viewables.size()-1);
        screens.removeAll(top.getScreens());
        screens.addAll(viewables.get(viewables.size()-1).getScreens());
    }

    public void addViewable(Viewable viewable){
        if(!viewables.isEmpty())screens.removeAll(viewables.get(viewables.size()-1).getScreens());
        viewables.add(viewable);
        screens.addAll(viewable.getScreens());
    }

    public void addDraggable(Screen lst){
        if(draggables.isEmpty()) lastDragged = lst;
        draggables.add(lst);
    }

    public boolean viewablesEmpty(){
        return viewables.isEmpty();
    }

    public boolean screensEmpty(){
        return screens.isEmpty();
    }

    public Stream<Viewable> streamViewables(){
        return viewables.stream();
    }

    public Stream<Screen> streamScreens(){
        return screens.stream();
    }

    public int viewablesSize(){
        return viewables.size();
    }

    public int screensSize(){
        return screens.size();
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
        
        Iterator<Viewable> v = viewables.iterator();
        while(v.hasNext()) v.next().paint(g);
        if(dialogue!=null) dialogue.paint(g);
    }
    
    /**
     * Adds a screen to the top of the Viewable.
     * @param sc
     */
    public void addScreen(Screen sc){
        screens.add(sc);
    }
    
    public void addScreenFirst(Screen sc){
        screens.add(0, sc);
    }
    
    /**
     * Removes the array of Screens from the active list.
     * @param scs The regex.
     */
    public void removeScreens(Screen[] scs){
        for(Screen sc : scs){
            screens.remove(sc);
        }
    }

    public void changeDialogue(Dialogue d){
        dialogue = d;
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

}
