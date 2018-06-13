
package gui.pages;

import gui.mainToolbox.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class LoadingPage implements Page{

    @Override
    public void paint(Graphics2D g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 48));
        g.drawString("Loading", Main.WIDTH/2-100, Main.HEIGHT/2-70);
    }
    
}
