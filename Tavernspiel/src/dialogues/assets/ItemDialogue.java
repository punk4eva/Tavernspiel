
package dialogues.assets;

import ai.PlayerAI;
import creatureLogic.Action.ActionOnItem;
import creatures.Hero;
import dialogues.ButtonDialogue;
import static dialogues.DialogueBase.PADDING;
import gui.Window;
import gui.mainToolbox.Screen.ScreenEvent;
import gui.utils.CButton;
import items.Item;
import items.actions.ItemAction;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import listeners.ScreenItem;
import logic.ConstantFields;
import logic.ImageUtils;
import static logic.ImageUtils.scale;

/**
 *
 * @author Adam Whittaker
 * 
 * A Dialogue showing an Item Description.
 */
public class ItemDialogue extends ButtonDialogue{
    
    private final Item item;
    private final Hero hero;
    private final int slot;
    
    
    /**
     * Creates a new instance.
     * @param i The item to display.
     * @param h The Hero.
     * @param s The slot.
     * @Unfinished Bad coding! (Perhaps do a dialogue). Add ImageIcon.
     */
    public ItemDialogue(Item i, Hero h, int s){
        super(true, null, i.name, ((i instanceof ScreenItem && ((ScreenItem)i).onOpeningDialogue() ? "" : "") + i.description.getDescription(h.expertise)), 
                ItemAction.toStringArray(i.actions));
        item = i;
        hero = h;
        slot = s;
    }
    
    @Override
    public void render(Graphics2D g, int x, int y){
        g.setFont(ConstantFields.textFont);
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(x, y, width, height, false);
        g.setColor(ConstantFields.textColor);
        
        BufferedImage buffer = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bg = (Graphics2D) buffer.getGraphics();
        item.animation.animate(bg, 0, 0);
        g.drawImage(scale(buffer, 3), x+2*PADDING, y+2*PADDING, null);
        
        ImageUtils.drawString(g, title, 9*PADDING+x, y+5*PADDING);
        ImageUtils.drawString(g, text, 2*PADDING+x, y+8*PADDING+ImageUtils.getStringHeight());
        for(CButton option : options) option.paint(g);
    }

    @Override
    public void screenClicked(ScreenEvent sc){
        deactivate();
        for(ItemAction action : item.actions) if(action.name.equals(sc.getName())){
            Window.main.setInventoryActive(false);
            ((PlayerAI)hero.attributes.ai).nextAction = new ActionOnItem(action, item, hero, -1, -1, slot);
            ((PlayerAI)hero.attributes.ai).alertAction();
        }
    }
    
}
