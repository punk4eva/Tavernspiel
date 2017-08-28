
package items.consumables;

import items.consumables.scrolls.EnchantmentScroll;
import items.consumables.scrolls.JuxtapositionScroll;
import items.consumables.scrolls.IdentityScroll;
import items.consumables.scrolls.EarthwallScroll;
import items.consumables.scrolls.InfiniteHorseScroll;
import items.consumables.scrolls.KnowledgeScroll;
import items.consumables.scrolls.RechargingScroll;
import items.consumables.scrolls.UpgradeScroll;
import items.consumables.scrolls.WonderScroll;
import items.consumables.scrolls.CountercurseScroll;
import creatures.Creature;
import creatures.Hero;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import level.Area;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class ScrollBuilder{
    
    private Hero hero;
    protected HashMap<String, String> nameAndRune = new HashMap<>(); //Works both ways.
    protected HashMap<String, Dimension> scrollMap = new HashMap<>();
    private static BufferedImage items = getItemsImage();
    
    public ScrollBuilder(Hero h){
        hero = h;
        initMap();
        randomlyAssign();
    }
    
    public void identify(String rune){
        scrollMap.put(nameAndRune.get(rune), scrollMap.get(rune));
    }
    
    private void initMap(){
        scrollMap.put("GICH", new Dimension(0, 80));
        scrollMap.put("META", new Dimension(16, 80));
        scrollMap.put("ALIKI", new Dimension(32, 80));
        scrollMap.put("SIMA", new Dimension(48, 80));
        scrollMap.put("IRIGI", new Dimension(64, 80));
        scrollMap.put("TILACHA", new Dimension(80, 80));
        scrollMap.put("MIDI", new Dimension(96, 80));
        scrollMap.put("MOTI", new Dimension(112, 80));
        scrollMap.put("SMUDGE1", new Dimension(0, 96));
        scrollMap.put("SMUDGE2", new Dimension(16, 96));
        scrollMap.put("INEMA", new Dimension(32, 96));
        scrollMap.put("KORO", new Dimension(48, 96));
        scrollMap.put("BERI", new Dimension(64, 96));
        scrollMap.put("REDO", new Dimension(80, 96));
        scrollMap.put("SEMA", new Dimension(96, 96));
        scrollMap.put("BLANK", new Dimension(112, 96));
        scrollMap.put("FERESI", new Dimension(64, 160));
        scrollMap.put("MOKATI", new Dimension(80, 160));
        scrollMap.put("MAZORI", new Dimension(96, 160));
        scrollMap.put("HULUMI", new Dimension(112, 160));
    }
    
    private boolean isIdd(String scroll){
        return scrollMap.get("Scroll of " + scroll)!=null;
    }
    
    private static BufferedImage getItemsImage(){
        ImageIcon ic = new ImageIcon("graphics/items.png");
        BufferedImage ret = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = ret.getGraphics();
        g.drawImage(ic.getImage(), 0, 0, null);
        g.dispose();
        return ret;
    }
    
    @Unfinished("Remember to make custom scrolls part of natural terrain gen.")
    private void randomlyAssign(){
        LinkedList<String> scrolls = new LinkedList<>(), runes = new LinkedList<>();
        runes.add("GICH");
        runes.add("META");
        runes.add("ALIKI");
        runes.add("SIMA"); 
        runes.add("IRIGI");
        runes.add("TILACHA");
        runes.add("MIDI"); 
        runes.add("MOTI");
        runes.add("INEMA");
        runes.add("KORO"); 
        runes.add("BERI"); 
        runes.add("REDO"); 
        runes.add("SEMA");
        runes.add("FERESI");
        runes.add("MOKATI");
        runes.add("MAZORI");
        runes.add("HULUMI");
        scrolls.add("Scroll of Curse");
        scrolls.add("Scroll of Countercurse");
        scrolls.add("Scroll of Smite");
        scrolls.add("Scroll of Upgrade");
        scrolls.add("Scroll of Enchantment");
        scrolls.add("Scroll of Skeleton");
        scrolls.add("Scroll of Earthquake");
        scrolls.add("Scroll of Animation");
        scrolls.add("Scroll of Burial");
        scrolls.add("Custom Scroll");
        scrolls.add("Scroll of Identity");
        scrolls.add("Scroll of the Grim");
        scrolls.add("Scroll of Infinite Horse");
        scrolls.add("Scroll of Recharging");
        scrolls.add("Scroll of Earthwall");
        scrolls.add("Scroll of Juxtaposition");
        scrolls.add("Scroll of Knowledge");
        Collections.shuffle(scrolls);
        Collections.shuffle(runes);
        for(int n=0;n<scrolls.size();n++){
            nameAndRune.put(scrolls.get(n), runes.get(n));
            nameAndRune.put(runes.get(n), scrolls.get(n));
        }
        nameAndRune.put("Scroll of Magical Storage", "BLANK");
        nameAndRune.put("BLANK", "Scroll of Magical Storage");
        if(Distribution.chance(1, 2)){
            nameAndRune.put("SMUDGE1", "Scroll of Wonder");
            nameAndRune.put("Scroll of Wonder", "SMUDGE1");
            nameAndRune.put("SMUDGE2", "Scroll of Sadness");
            nameAndRune.put("Scroll of Sadness", "SMUDGE2");
        }else{
            nameAndRune.put("SMUDGE2", "Scroll of Wonder");
            nameAndRune.put("Scroll of Wonder", "SMUDGE2");
            nameAndRune.put("SMUDGE1", "Scroll of Sadness");
            nameAndRune.put("Scroll of Sadness", "SMUDGE1");
        }
    }
    
    protected Image getImage(String name){
        Dimension dim = scrollMap.get(name);
        if(dim==null) dim = scrollMap.get(nameAndRune.get(name));
        return items.getSubimage(dim.width, dim.height, 16, 16);
    }
    
    
    public WonderScroll wonder(){
        return new WonderScroll(getImage("Scroll of Wonder"), isIdd("Wonder")){
            @Override
            public void use(Creature c){
                Scroll s;
                Distribution distrib = new Distribution(new double[]{0,1,2,3,4,5,6,7,8}, new int[]{18,3,1,12,18,18,12,15,15});
                switch((int)distrib.next()){
                    case 0: s = countercurse(); break;
                    case 1: s = upgrade(); break;
                    case 2: s = enchantment(); break;
                    case 3: s = infiniteHorse(); break;
                    case 4: s = identity(); break;
                    case 5: s = earthwall(); break;
                    case 6: s = juxtaposition(); break;
                    case 7: s = knowledge(); break;
                    default: s = recharging();
                }
                s.use(c);
            }
        };
    }
    
    public CountercurseScroll countercurse(){
        return new CountercurseScroll(getImage("Scroll of Countercurse"), isIdd("Countercurse"));
    }
    
    public UpgradeScroll upgrade(){
        return new UpgradeScroll(getImage("Scroll of Upgrade"), isIdd("Upgrade"));
    }
    
    public EnchantmentScroll enchantment(){
        return new EnchantmentScroll(getImage("Scroll of Enchantment"), isIdd("Enchantment"));
    }
    
    public InfiniteHorseScroll infiniteHorse(){
        return new InfiniteHorseScroll(getImage("Scroll of Infinite Horse"), isIdd("Infinite Horse"));
    }
    
    public IdentityScroll identity(){
        return new IdentityScroll(getImage("Scroll of Identity"), isIdd("Identity"));
    }
    
    public EarthwallScroll earthwall(){
        return new EarthwallScroll(getImage("Scroll of Earthwall"), isIdd("Earthwall"));
    }
    
    public JuxtapositionScroll juxtaposition(){
        return new JuxtapositionScroll(getImage("Scroll of Juxtaposition"), isIdd("Juxtaposition"));
    }
    
    public KnowledgeScroll knowledge(){
        return new KnowledgeScroll(getImage("Scroll of Knowledge"), isIdd("Knowledge"));
    }
    
    public RechargingScroll recharging(){
        return new RechargingScroll(getImage("Scroll of Recharging"), isIdd("Recharging"));
    }
    
}
