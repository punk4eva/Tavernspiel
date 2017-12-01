
package gui;

import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Iterator;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 * 
 * A dialogue of game events.
 */
public class MessageQueue{
    
    protected ArrayDeque<String> queue = new ArrayDeque<>();
    
    /**
     * Creates a new instance.
     */
    public MessageQueue(){
        for(int i=0;i<4;i++) queue.add("");
    }
    
    /**
     * Adds a message to the queue.
     * @param message The message.
     */
    public void add(String message){
        queue.add(message);
        queue.pop();
    }
    
    /**
     * Adds a message to the queue in the given colour.
     * @param colour The colour.
     * @param message The message.
     */
    public void add(String colour, String message){
        if(colour.startsWith("#")){
            queue.add("<html><font bgcolor=\""+colour+"\">"+message+"</font>");
        }else{
            queue.add("<html><font color=\""+colour+"\">"+message+"</font>");
        }
        queue.pop();
    }
    
    public void paint(Graphics g){
        g.setColor(ConstantFields.textColor);
        g.setFont(ConstantFields.smallTextFont);
        int height = MainClass.HEIGHT*6/7;
        Iterator<String> iter = queue.descendingIterator();
        while(iter.hasNext()){
            g.drawString(iter.next(), 48, height);
            height -= 16;
        }
    }
    
}
