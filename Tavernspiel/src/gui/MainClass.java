
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logic.SoundHandler;

/**
 *
 * @author Adam Whittaker
 */
public class MainClass implements ActionListener{
    
    public MainClass(){
    
    }
    
    
    public static void main(String[] args){
        SoundHandler.addSong("Journey Through The Woods Part 1.wav");
        SoundHandler.addSong("Journey Through The Woods Part 2.wav");
        SoundHandler.addSong("Journey Through The Woods Part 3.wav");
        SoundHandler.playAbruptQueue(0);
    }
    

    @Override
    public void actionPerformed(ActionEvent e){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
