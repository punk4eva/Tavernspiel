
package logic;

import gui.mainToolbox.Main;
import items.Item;
import items.ItemBuilder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.function.Predicate;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public interface ConstantFields{
    
    public static final ImageIcon chestIcon = new ImageIcon(""); //@unfinished
    public static final ImageIcon crystalChestIcon = new ImageIcon(""); //@unfinished
    public static final ImageIcon lockedChestIcon = new ImageIcon(""); //@unfinished
    public static final ImageIcon skeletalRemainsIcon = new ImageIcon(""); //@unfinished
    
    public static final Color unidentifiedColour = new Color(129, 35, 160, 80);
    public static final Color cursedColour = new Color(210, 0, 0, 40);
    
    public static final Color textColor = Color.YELLOW;
    public static final Color backColor = Color.GRAY;
    public static final Color frontColor = Color.MAGENTA;
    public static final Color exploredColor = new Color(80, 80, 80, 150);
    public static final Color fadeColor = new Color(90, 90, 90);
    public static final Font textFont = new Font(Font.SERIF, Font.PLAIN, 18);
    public static final Font smallTextFont = new Font(Font.SERIF, Font.PLAIN, 15);
    public static final Color badColor = Color.RED;
    public static final Color interestColor = new Color(232, 121, 25);
    public static final Color plainColor = Color.YELLOW;
    
    public static final Image helmetOutline = new ImageIcon("graphics/outlines/helmetOutline.png").getImage();
    public static final Image chestplateOutline = new ImageIcon("graphics/outlines/chestplateOutline.png").getImage();
    public static final Image leggingsOutline = new ImageIcon("graphics/outlines/leggingsOutline.png").getImage();
    public static final Image bootsOutline = new ImageIcon("graphics/outlines/bootsOutline.png").getImage();
    public static final Image weaponOutline = new ImageIcon("").getImage(); //@unfinished
    public static final Image goldOutline = new ImageIcon("graphics/outlines/goldOutline.png").getImage();
    public static final Image amuletOutline = new ImageIcon("").getImage(); //@unfinished
    public static final Image gold = ItemBuilder.getIcon(96, 16).getImage();
    
    public static final Image eyeButtonImg = new ImageIcon("graphics/gui/eye1.png").getImage();
    public static final Image waitButtonImg = new ImageIcon("graphics/gui/WaitButton1.png").getImage();
    public static final Image inventoryButtonImage = new ImageIcon("graphics/gui/inventoryButtonImage.png").getImage();
    
    public final static int padding = 8,
        beginWidth = Main.WIDTH/9,
        beginHeight = Main.HEIGHT/9,
        sqwidth = (int)(((double)Main.WIDTH*(7.0/9.0)-7*padding)/6.0),
        sqheight = (int)(((double)Main.HEIGHT*(7.0/9.0)-6*padding)/5.0);
    
    public static final Predicate<Item> truthPredicate = t -> true;
    
}
