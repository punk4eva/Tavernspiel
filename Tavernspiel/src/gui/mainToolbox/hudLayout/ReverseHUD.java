package gui.mainToolbox.hudLayout;

import creatureLogic.QuickSlot;
import gui.Game;
import gui.HUD;
import gui.Window;
import gui.mainToolbox.HUDStrategy;
import gui.mainToolbox.Screen;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import logic.ConstantFields;
import logic.ImageUtils;

/**
 *
 * @author charl
 */
public class ReverseHUD implements HUDStrategy{
    private final List<Screen> screens = new LinkedList<>();
    private final QuickSlot quickslot;
    public ReverseHUD(HUD hud){
        quickslot = hud.quickslot;
        screens.add(new Screen("Player",5,5,60,60,hud));
        screens.add(new Screen("Wait",5, Game.HEIGHT - 73, 40, 40,hud));
        screens.add(new Screen("Search",50, Game.HEIGHT - 73, 40, 40,hud));
        screens.add(new Screen("Inventory",95, Game.HEIGHT - 73, 40, 40,hud));
        screens.add(new Screen("QuickAttack",0, Game.HEIGHT - 250, 70, 40,hud));
        screens.add(new Screen("QuickPickup",0, Game.HEIGHT - 300, 70, 40,hud));
        
        for(int i = 0; i < quickslot.length(); i++) screens.add(new Screen("QuickSlot:" + i,Game.WIDTH - (350 + i * 45), Game.HEIGHT - 73, 40, 40, quickslot));
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
                ImageUtils.paintItemSquare(g, Game.WIDTH - (350 + i * 45), Game.HEIGHT - 73, 40, 40, quickslot.getItem(i), Window.main.player, ConstantFields.truthPredicate);
            }catch(NullPointerException e){}
        }
        
        g.fill3DRect(5, Game.HEIGHT - 73, 40, 40, true);
        g.drawImage(ConstantFields.eyeButtonImg, 5, Game.HEIGHT - 70, null);
        g.fill3DRect(50, Game.HEIGHT - 73, 40, 40, true);
        g.drawImage(ConstantFields.waitButtonImg,50, Game.HEIGHT - 74,null);
        g.fill3DRect(95, Game.HEIGHT - 73, 40, 40, true);
        
        g.setColor(ConstantFields.frontColor);
        g.fill3DRect(0, Game.HEIGHT - 250, 70, 40, true);
        g.fill3DRect(0, Game.HEIGHT - 300, 70, 40, true);
        
        g.setColor(Color.red);
        g.fill3DRect(70, 5, (int) ((double)Window.main.player.attributes.hp/(double)(Window.main.player.attributes.maxhp) * 200), 10, true);    
    }
    
}