package gui;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Charlie Hands
 */
public class Window extends Canvas{
    
    public static MainClass main;
    protected JFrame frame;
    public static float SFXVolume = 0;
    public static float MusicVolume = 0;
    
    public Window(int width, int height, String title, MainClass m){
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(m);
        frame.setVisible(true);
        main = m;
        main.start();
    }
    
}
