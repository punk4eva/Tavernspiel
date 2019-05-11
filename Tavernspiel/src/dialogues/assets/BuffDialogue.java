
package dialogues.assets;

import buffs.Buff;
import creatures.Hero;
import static dialogues.DialogueBase.PADDING;
import dialogues.TextDialogue;
import gui.mainToolbox.Screen;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import logic.ConstantFields;
import logic.ImageUtils;
import static logic.ImageUtils.scale;

/**
 *
 * @author Adam Whittaker
 * 
 * This class controls the info pop-up screen that activates when the player
 * clicks on a Buff.
 */
public class BuffDialogue extends TextDialogue{
    
    private final Buff buff;
    
    public BuffDialogue(Buff b, Hero hero){
        super(b.icon, b.name, b.description.getDescription(hero.expertise));
        buff = b;
    }

    @Override
    public void screenClicked(Screen.ScreenEvent sc){
    }
    
    @Override
    public void render(Graphics2D g, int x, int y){
        g.setFont(ConstantFields.textFont);
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(x, y, width, height, false);
        g.setColor(ConstantFields.textColor);
        
        BufferedImage buffer = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bg = (Graphics2D) buffer.getGraphics();
        bg.drawImage(image, 0, 0, null);
        g.drawImage(scale(buffer, 1.5), x+2*PADDING, y+2*PADDING, null);
        
        ImageUtils.drawString(g, title, 9*PADDING+x, y+4*PADDING);
        ImageUtils.drawString(g, text, 2*PADDING+x, y+8*PADDING+ImageUtils.getStringHeight());
    }
    
}
