package gui;

import buffs.Buff;
import creatures.Hero;
import gui.Game;
import gui.Viewable;
import gui.Window;
import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
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
public class BuffDisplay implements Viewable,ScreenListener{
    private final int x,y,width,height;
    private final Hero player;
    private List<Screen> screens = new LinkedList<>();
    public BuffDisplay(int x, int y, int width, int height, Hero player){
        this.x = x;
        this.y = y;
        this.player = player;
        this.width = width;
        this.height = height;
        
        screens.add(new Screen("Main",x,y,width,height,this));
        screens.add(new Screen("Outside",0,0,Game.WIDTH,Game.HEIGHT,this));
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(x,y,width,height,true);
        g.setColor(Color.blue);
        int i = 0;
        for(Buff buff : player.buffs){
            g.fillRect(x+5, y+i, 12, 12);
            //g.drawImage(buff.icon.getImage(), x + 5, y+i, null);
            i+=5;
        }
    }

    @Override
    public List<Screen> getScreens(){
        return screens;
    }

    @Override
    public void screenClicked(ScreenEvent name){
        if(!name.getName().equals("Main")) Window.main.removeViewable();
    }
}
