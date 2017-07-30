
package gui;

import java.util.ArrayDeque;

/**
 *
 * @author Adam Whittaker
 */
public class MessageQueue{
    
    protected ArrayDeque<String> queue = new ArrayDeque<>();
    
    public MessageQueue(){
        for(int i : new int[4]) queue.add("");
    }
    
    public void add(String message){
        queue.add(message);
        queue.pop();
    }
    
}
