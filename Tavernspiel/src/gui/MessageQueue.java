
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
    
    public void add(String colour, String message){
        if(colour.startsWith("#")){
            queue.add("<html><font bgcolor=\""+colour+"\">"+message+"</font>");
        }else{
            queue.add("<html><font color=\""+colour+"\">"+message+"</font>");
        }
        queue.pop();
    }
    
}
