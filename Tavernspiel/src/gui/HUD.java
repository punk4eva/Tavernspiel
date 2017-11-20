package gui;

import gui.Screen.ScreenEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import listeners.ScreenListener;
import logic.ConstantFields;

/**
 *
 * @author Charlie Hands
 */
public class HUD implements Viewable, ScreenListener{
    
    private final List<Screen> screens = new LinkedList<>();
    {
        screens.add(new Screen("Player",5,5,60,60,this));
        screens.add(new Screen("Wait",Game.WIDTH - 50, Game.HEIGHT - 73, 40, 40,this));
        screens.add(new Screen("Search",Game.WIDTH - 95, Game.HEIGHT - 73, 40, 40,this));
        screens.add(new Screen("Inventory",Game.WIDTH - 140, Game.HEIGHT - 73, 40, 40,this));
    }
    private boolean viewingInventory = false;
    
    private ImageIcon eye = new ImageIcon("graphics/gui/eye1.png");
    private ImageIcon waitButton = new ImageIcon("graphics/gui/WaitButton1.png");
    
    public HUD(){
        Window.main.addViewable(this);
    }

    @Override
    public List<Screen> getScreens(){
        return screens;
    }

    @Override
    public void paint(Graphics g){
        g.setColor(ConstantFields.backColor);
        g.fillRect(5, 5, 60, 60);
        g.drawImage(Window.main.player.animator.active.frames[0].getImage().getScaledInstance(60, 60, 0),5,2,null);
        g.fillRect(70,5,200,10);
        
        g.fill3DRect(Game.WIDTH - 50, Game.HEIGHT - 73, 40, 40, true);
        g.drawImage(eye.getImage(), Game.WIDTH - 50, Game.HEIGHT - 70, null);
        g.fill3DRect(Game.WIDTH - 95, Game.HEIGHT - 73, 40, 40, true);
        g.drawImage(waitButton.getImage(),Game.WIDTH - 95, Game.HEIGHT - 74,null);
        g.fill3DRect(Game.WIDTH - 140, Game.HEIGHT - 73, 40, 40, true);
        
        g.setColor(Color.red);
        g.fill3DRect(70, 5, (int) ((double)Window.main.player.attributes.hp/(double)(Window.main.player.attributes.maxhp) * 200), 10, true);
       

    }

    @Override
    public void screenClicked(ScreenEvent name){
        switch(name.getName()){
            case "Inventory": if(!viewingInventory){
                viewingInventory = true;
                Window.main.addViewable(Window.main.player);
            }else Window.main.removeTopViewable();
                break;
            case "Wait": Window.main.player.attributes.ai.paralyze(1.0);
                break;
        }
    }
    
}
