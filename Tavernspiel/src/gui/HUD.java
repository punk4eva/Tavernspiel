package gui;

import gui.Screen.ScreenEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import listeners.ScreenListener;
import logic.ConstantFields;
import logic.ImageUtils;
import containers.Receptacle;

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
        for(int i = 0; i < 5; i++) screens.add(new Screen("QuickSlot" + (i + 1),Game.WIDTH - (350 + i * 45), Game.HEIGHT - 73, 40, 40, this));
    }
    private boolean viewingInventory = false;
    
    public HUD(){
        Window.main.addViewable(this);
    }
    
    private QuickSlot quickslot = new QuickSlot();
    
    public class QuickSlot extends Receptacle{
        public QuickSlot(){
            super(null,5,"Quickslots for Player", -1,-1);
        }
    
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
        
        for(int i = 0; i < 5; i++){
            g.fill3DRect(Game.WIDTH - (350 + i * 45), Game.HEIGHT - 73, 40, 40, true);
            try{
                ImageUtils.paintItemSquare(g, Game.WIDTH - (350 + i * 45), Game.HEIGHT - 73, 40, 40, quickslot.getElse(i), Window.main.player);
            }catch(NullPointerException e){}
        }
        
        g.fill3DRect(Game.WIDTH - 50, Game.HEIGHT - 73, 40, 40, true);
        g.drawImage(ConstantFields.eyeButtonImg, Game.WIDTH - 50, Game.HEIGHT - 70, null);
        g.fill3DRect(Game.WIDTH - 95, Game.HEIGHT - 73, 40, 40, true);
        g.drawImage(ConstantFields.waitButtonImg,Game.WIDTH - 95, Game.HEIGHT - 74,null);
        g.fill3DRect(Game.WIDTH - 140, Game.HEIGHT - 73, 40, 40, true);
        
        g.setColor(Color.red);
        g.fill3DRect(70, 5, (int) ((double)Window.main.player.attributes.hp/(double)(Window.main.player.attributes.maxhp) * 200), 10, true);
       

    }

    @Override
    public void screenClicked(ScreenEvent name){
        switch(name.getName()){
            case "Inventory": 
                viewingInventory = true;
                Window.main.addViewable(Window.main.player);
                break;
            case "Wait": Window.main.player.attributes.ai.paralyze(1.0);
                break;
        }
    }
    
    public void disableInventory(){
        if(viewingInventory) Window.main.removeTopViewable();
        viewingInventory = false;
    }
    
    
}
