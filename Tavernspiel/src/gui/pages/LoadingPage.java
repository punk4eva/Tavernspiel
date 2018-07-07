
package gui.pages;

import gui.mainToolbox.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 */
public class LoadingPage implements Page{
    private int i;
    private final String[] tips;
    private final int rand;

    public LoadingPage(){
        this.i = 0;
        this.tips = new String[]{"Random tip","Another random tip"};
        this.rand = new Random().nextInt(tips.length);
    }
    @Override
    public void paint(Graphics2D g){
        i+=5;
        i%=360;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 48));
        g.drawString("Loading", Main.WIDTH/2-100, Main.HEIGHT/2-70);
        g.fillArc(345, 250, 50, 50, i, 70);
        g.fillArc(345, 250, 50, 50, i-180, 70);
        g.setColor(Color.black);
        g.fillOval(350,255,40,40);
        
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(5, Main.HEIGHT - 100, Main.WIDTH - 15, 65, true);
        
        g.setColor(Color.BLACK);
        g.setFont(ConstantFields.textFont);
        g.drawString("Tip: " + tips[rand], 7, Main.HEIGHT - 80);
    }
    
}
