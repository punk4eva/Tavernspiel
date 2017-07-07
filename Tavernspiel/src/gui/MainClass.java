
package gui;

import items.equipment.MeleeWeapon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.IDHandler;
import logic.SoundHandler;

/**
 *
 * @author Adam Whittaker
 */
public class MainClass implements ActionListener{
    
    public static final IDHandler idhandler = new IDHandler();
    
    public MainClass(){
    
    }
    
    
    public static void main(String[] args){
        //System.err.println(new MeleeWeapon("", new ImageIcon(""), 1, new Distribution(0, 1),1).getClass().getGenericSuperclass());
    }
    

    @Override
    public void actionPerformed(ActionEvent e){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
