/*
 * Copyright 2019 Adam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dialogues;

import static dialogues.DialogueBase.PADDING;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import logic.ConstantFields;
import logic.ImageUtils;
import static logic.ImageUtils.scale;
import logic.KeyMapping;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public abstract class TextDialogue extends DialogueBase{

    protected final Image image;
    protected final String title, text;
    protected final int heightOfQuestion;
    
    public TextDialogue(ImageIcon img, String ttl, String txt){
        super(true);
        image = img == null ? null : img.getImage();
        title = ttl;
        text = Utils.lineFormat(txt);
        heightOfQuestion = PADDING+ImageUtils.getStringHeight()*(Utils.lineCount(text)+2);
        LinkedList<Screen> lst = new LinkedList<>();
        lst.add(new Screen("blank click", width, (Main.HEIGHT-height)/2, width, height, this));
        setRectAndScreens(Main.WIDTH/3, 2*PADDING + heightOfQuestion, lst);
    }
    
    protected TextDialogue(boolean click, ImageIcon img, String ttl, String txt){
        super(click);
        image = img == null ? null : img.getImage();
        title = ttl;
        text = Utils.lineFormat(txt);
        heightOfQuestion = PADDING+ImageUtils.getStringHeight()*(Utils.lineCount(text)+2);
    }

    @Override
    public void render(Graphics2D g, int x, int y){
        g.setFont(ConstantFields.textFont);
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(x, y, width, height, false);
        g.setColor(ConstantFields.textColor);
        
        BufferedImage buffer = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bg = (Graphics2D) buffer.getGraphics();
        bg.drawImage(image, 0, 0, null);
        g.drawImage(scale(buffer, 3), x+2*PADDING, y+2*PADDING, null);
        
        ImageUtils.drawString(g, title, 9*PADDING+x, y+4*PADDING);
        ImageUtils.drawString(g, text, 2*PADDING+x, y+8*PADDING+ImageUtils.getStringHeight());
    }

    @Override
    public void screenClicked(Screen.ScreenEvent name){}
    
    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent ke){
        if(clickOffable&&ke.getKeyChar()==KeyMapping.ESCAPE) deactivate();
    }
    @Override
    public void keyReleased(KeyEvent e){}

}
