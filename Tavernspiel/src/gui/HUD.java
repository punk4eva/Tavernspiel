package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import listeners.ScreenListener;
import logic.ConstantFields;

/**
 *
 * @author Charlie Hands
 */
public class HUD implements Viewable, ScreenListener{
    List<Screen> screens = getScreens();
    MainClass main;
    public HUD(MainClass main){
        this.main= main;
    }
    @Override
    public List<Screen> getScreens(){
        List<Screen> toReturn = new LinkedList<Screen>();
        toReturn.add(new Screen("Player",5,5,60,60,this));
        toReturn.add(new Screen("Wait",0,Game.HEIGHT - 20,20,20,this));
        return toReturn;
    }

    @Override
    public List<Screen> getScreenList(){
        return screens;
    }

    @Override
    public void paint(Graphics g){
        g.setColor(ConstantFields.backColor);
        g.fillRect(5, 5, 60, 60);
        g.drawImage(main.player.animator.active.frames[0].getImage().getScaledInstance(60, 60, 0),5,0,null);
        g.fillRect(70,5,200,10);
        g.setColor(Color.red);
        g.fill3DRect(70, 5, (int) ((double)main.player.attributes.hp/(double)(main.player.attributes.maxhp+0.01) * 200), 10, true);
        g.fill3DRect(0, Game.HEIGHT - 20, 20, 20, true);
    }

    @Override
    public void screenClicked(Screen.ScreenEvent name){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
