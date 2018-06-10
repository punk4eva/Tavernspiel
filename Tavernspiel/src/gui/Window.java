package gui;

import gui.mainToolbox.Main;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Charlie Hands and Adam Whittaker
 */
public class Window extends Canvas{
    
    public static Main main;
    protected JFrame frame;
    public static float SFXVolume = 0, musicVolume = 0;
    
    /**
     * Creates a new instance.
     * @param width The width of the frame.
     * @param height The height of the frame.
     * @param title The title of the frame.
     * @param m The MainClass peer.
     */
    public Window(int width, int height, String title, Main m){
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
    }
    
}
