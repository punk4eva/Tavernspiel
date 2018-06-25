package gui;

import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import listeners.ScreenListener;
import logic.ConstantFields;
import logic.ImageUtils;
import creatureLogic.QuickSlot;

/**
 *
 * @author Charlie Hands
 */
public class HUD implements Viewable, ScreenListener{
    
    private final List<Screen> screens = new LinkedList<>();
    private boolean viewingInventory = false;
    private final QuickSlot quickslot;
    
    public HUD(QuickSlot q){
        quickslot = q;
        for(int i = 0; i < q.length(); i++) screens.add(new Screen("QuickSlot" + i,Game.WIDTH - (350 + i * 45), Game.HEIGHT - 73, 40, 40, quickslot));
        Window.main.addViewable(this);
        
        screens.add(new Screen("Player",5,5,60,60,this));
        screens.add(new Screen("Wait",Game.WIDTH - 50, Game.HEIGHT - 73, 40, 40,this));
        screens.add(new Screen("Search",Game.WIDTH - 95, Game.HEIGHT - 73, 40, 40,this));
        screens.add(new Screen("Inventory",Game.WIDTH - 140, Game.HEIGHT - 73, 40, 40,this));
        screens.add(new Screen("QuickAttack",Game.WIDTH - 70, Game.HEIGHT - 250, 70, 40,this));
        screens.add(new Screen("QuickPickup",Game.WIDTH - 70, Game.HEIGHT - 300, 70, 40,this));
        
        for(int i = 0; i < quickslot.length(); i++) screens.add(new Screen("QuickSlot:" + i,Game.WIDTH - (350 + i * 45), Game.HEIGHT - 73, 40, 40,this));
    }

    @Override
    public List<Screen> getScreens(){
        return screens;
    }

    @Override
    public void paint(Graphics g){
        g.setColor(ConstantFields.backColor);
        g.fillRect(5, 5, 60, 60);
        g.drawImage(Window.main.player.animator.active.getCurrentIcon().getImage().getScaledInstance(60, 60, 0),5,2,null);
        g.fillRect(70,5,200,10);
        
        for(int i = 0; i < quickslot.length(); i++){
            g.fill3DRect(Game.WIDTH - (350 + i * 45), Game.HEIGHT - 73, 40, 40, true);
            try{
                ImageUtils.paintItemSquare(g, Game.WIDTH - (350 + i * 45), Game.HEIGHT - 73, 40, 40, quickslot.getItem(i), Window.main.player);
            }catch(NullPointerException e){}
        }
        
        g.fill3DRect(Game.WIDTH - 50, Game.HEIGHT - 73, 40, 40, true);
        g.drawImage(ConstantFields.eyeButtonImg, Game.WIDTH - 50, Game.HEIGHT - 70, null);
        g.fill3DRect(Game.WIDTH - 95, Game.HEIGHT - 73, 40, 40, true);
        g.drawImage(ConstantFields.waitButtonImg,Game.WIDTH - 95, Game.HEIGHT - 74,null);
        g.fill3DRect(Game.WIDTH - 140, Game.HEIGHT - 73, 40, 40, true);
        
        g.setColor(ConstantFields.frontColor);
        g.fill3DRect(Game.WIDTH - 70, Game.HEIGHT - 250, 70, 40, true);
        g.fill3DRect(Game.WIDTH - 70, Game.HEIGHT - 300, 70, 40, true);
        
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
