/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import listeners.ScreenListener;

/**
 *
 * @author Charlie Hands
 */
public class PauseMenu implements Viewable, ScreenListener{
    List<Screen> screens = getScreens();

    @Override
    public List<Screen> getScreens(){
       LinkedList<Screen> ret = new LinkedList<>();
       ret.add(new Screen("Background", 0,0,Window.main.getWidth(),Window.main.getHeight(),this));
       ret.add(new Screen("BackButton",100,100,20,20,this));
       
       return ret;
    }

    @Override
    public List<Screen> getScreenList(){
       return screens;
    }

    @Override
    public void paint(Graphics g){
        Font titlefnt = new Font("Arial", 10, 50);
        Font btnfont = new Font("Arial", 10, 20);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Window.main.getWidth(),Window.main.getHeight());
        g.setColor(Color.WHITE);
        g.setFont(titlefnt);
        g.drawString("Paused", 20,20);
        g.drawRect(100,100,20,20);
        g.setFont(btnfont);
        g.drawString("Back", 100,100);
    }

    @Override
    public void screenClicked(Screen.ScreenEvent e){
        
    }
    
}
