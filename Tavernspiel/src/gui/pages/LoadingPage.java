
package gui.pages;

import gui.mainToolbox.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import logic.ConstantFields;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker and Charlie Hands
 */
public class LoadingPage implements Page{
    
    private int i = 0;
    private final int rand;
    private static final String[] TIPS = new String[]{"Do you even read these?", "Another random tip!",
            "Just because a weapon deals more damage than another, does not mean it is better. All weapons are nuanced in speed, accuracy and durability.",
            "Some weapons have secrets waiting to be discovered by a skilled enough wielder.", 
            "To get more information about the speed of a weapon, find someone willing to teach you the art of weaponry.",
            "The harder you look, the more powerful the items that you find will be.",
            "There are rumours of the existance of an axe made of the wings of a dragon."};

    public LoadingPage(){
        rand = Distribution.r.nextInt(TIPS.length);
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
        g.drawString(TIPS[rand], 7, Main.HEIGHT - 80);
    }
    
}
