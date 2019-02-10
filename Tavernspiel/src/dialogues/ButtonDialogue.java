
package dialogues;

import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.utils.CButton;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import logic.KeyMapping;

/**
 *
 * @author Adam Whittaker
 * 
 * An Option Dialogue.
 */
public abstract class ButtonDialogue extends TextDialogue{
    
    protected final CButton[] options;
    private int selectedButton = -1;
    
    /**
     * Creates a new Dialogue with the given options.
     * @param click Sets whether the user can click away.
     * @param i The ImageIcon.
     * @param ttl The title.
     * @param txt The text.
     * @param opt The options.
     */
    public ButtonDialogue(boolean click, ImageIcon i, String ttl, String txt, String[] opt){
        super(click, i, ttl, txt);
        height = 2*PADDING + heightOfQuestion + (36+PADDING)*opt.length;
        options = getButtons(opt);
        convertCComponents(options);
    }
    
    @Override
    public void render(Graphics2D g, int x, int y){
        /*g.setFont(ConstantFields.textFont);
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(x, y, width, height, false);
        g.setColor(ConstantFields.frontColor);
        for(CButton option : options) option.paint(g);
        g.setColor(ConstantFields.textColor);
        ImageUtils.drawString(g, question, 2*PADDING+x, y+2*PADDING);*/
        super.render(g, x, y);
        for(CButton option : options) option.paint(g);
    }

    private CButton[] getButtons(String[] strs){
        CButton[] ary = new CButton[strs.length];
        int beginWidth = Main.WIDTH/3 + PADDING;
        int beginHeight = (Main.HEIGHT-height)/2;
        for(int n=0;n<ary.length;n++){
            ary[n] = new CButton(strs[n], beginWidth, 
                    2*PADDING+heightOfQuestion+(36+PADDING)*n+beginHeight, 
                    PADDING, this);
        }
        return ary;
    }
    
    private void convertCComponents(CButton[] ary){
        LinkedList<Screen> lst = new LinkedList<>();
        setRectAndScreens(Main.WIDTH/3, height, lst);
        for(CButton cc : ary) lst.add((Screen)cc);
        lst.add(new Screen("blank click", width, (Main.HEIGHT-height)/2, width, height, this));
    }

    @Override
    public void keyTyped(KeyEvent ke){
        char k = ke.getKeyChar();
        if(selectedButton==-1){
            if(k==KeyMapping.GO_UP) selectedButton = options.length-1;
            else if(k==KeyMapping.GO_DOWN) selectedButton = 0;
            options[selectedButton].setSelected(true);
        }else{
            if(k==KeyMapping.INTERACT){
                options[selectedButton].wasClicked();
                return;
            }
            options[selectedButton].setSelected(false);
            if(k==KeyMapping.GO_UP) selectedButton = (options.length+selectedButton-1)%options.length;
            else if(k==KeyMapping.GO_DOWN) selectedButton = (selectedButton+1)%options.length;
            options[selectedButton].setSelected(true);
        }
    }
    
}
