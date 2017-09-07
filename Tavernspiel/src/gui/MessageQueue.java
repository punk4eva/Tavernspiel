
package gui;

import java.util.ArrayDeque;

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
        for(int i : new int[4]) queue.add("");
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
    
}
