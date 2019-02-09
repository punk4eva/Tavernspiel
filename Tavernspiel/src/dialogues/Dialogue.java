
package dialogues;

import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.utils.CButton;
import gui.utils.CComponent;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import logic.ConstantFields;
import logic.ImageUtils;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 * 
 * An Option Dialogue.
 */
public abstract class Dialogue extends DialogueBase{
    
    final CComponent[] options;
    private final int heightOfQuestion;
    
    /**
     * Creates a new Dialogue with the given options.
     * @param quest The question.
     * @param click Sets whether the user can click away.
     * @param opt The options.
     */
    public Dialogue(String quest, boolean click, String[] opt){
        super(Utils.lineFormat(quest), click);
        heightOfQuestion = ImageUtils.getStringHeight()*Utils.lineCount(question);
        height = 2*PADDING + heightOfQuestion + (36+PADDING)*opt.length;
        options = getButtons(opt);
        convertCComponents(options);
    }
    
    @Override
    public void render(Graphics2D g, int x, int y){
        g.setFont(ConstantFields.textFont);
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(x, y, width, height, false);
        g.setColor(ConstantFields.frontColor);
        for(CComponent option : options) option.paint(g);
        g.setColor(ConstantFields.textColor);
        ImageUtils.drawString(g, question, 2*PADDING+x, y+2*PADDING);
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
    
    private void convertCComponents(CComponent[] ary){
        LinkedList<Screen> lst = new LinkedList<>();
        setRectAndScreens(Main.WIDTH/3, height, lst);
        for(CComponent cc : ary) lst.add((Screen)cc);
        lst.add(new Screen("blank click", width, (Main.HEIGHT-height)/2, width, height, this));
        lst.add(new Screen("/exit", 0, 0, Main.WIDTH, Main.HEIGHT, this));
    }

    @Override
    public void keyTyped(KeyEvent ke){}
    @Override
    public void keyPressed(KeyEvent ke){}
    @Override
    public void keyReleased(KeyEvent ke){}
    
}
