
package items.consumables;

import creatures.Creature;
import creatures.Hero;
import items.consumables.scrolls.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds Scrolls.
 */
public class ScrollBuilder implements Serializable{
    
    public transient Hero hero;
    protected HashMap<String, String> nameAndRune = new HashMap<>(); //Works both ways.
    protected HashMap<String, Dimension> scrollMap = new HashMap<>();
    @Unfinished("Remember to make custom scrolls part of natural terrain gen.")
    private final static BufferedImage items;
    static{
        ImageIcon ic = new ImageIcon("graphics/items.png");
        items = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = items.getGraphics();
        g.drawImage(ic.getImage(), 0, 0, null);
        g.dispose();
    }
    {
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
        scrollMap.put("FERESI", new Dimension(64, 176));
        scrollMap.put("MOKATI", new Dimension(80, 176));
        scrollMap.put("MAZORI", new Dimension(96, 176));
        scrollMap.put("HULUMI", new Dimension(112, 176));
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
            String scr = scrolls.get(n), rune = runes.get(n);
            nameAndRune.put(scr, rune);
            nameAndRune.put(rune, scr);
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
    
    /**
     * Creates a new instance.
     * @param h The hero.
     */
    public ScrollBuilder(Hero h){
        hero = h;
    }
    
    /**
     * Identifies the Scroll with the given rune.
     * @param rune The rune.
     */
    public void identify(String rune){
        scrollMap.put(nameAndRune.get(rune), scrollMap.get(rune));
    }
    
    private boolean isIdd(String scroll){
        return scrollMap.get("Scroll of " + scroll)!=null;
    }
    
    private boolean isCustomIdd(){
        return scrollMap.get("Custom Scroll")!=null;
    }
    
    /**
     * Gets the image of the Scroll name.
     * @param name The name of the Scroll.
     * @return The image.
     */
    protected Image getImage(String name){
        Dimension dim = scrollMap.get(name);
        if(dim==null) dim = scrollMap.get(nameAndRune.get(name));
        return items.getSubimage(dim.width, dim.height, 16, 16);
    }

    public WonderScroll wonder(){
        return new WonderScroll(getImage("Scroll of Wonder"), isIdd("Wonder")){
            @Override
            public boolean use(Creature c){
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
                return s.use(c);
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
    
    public SmiteScroll smite(){
        return new SmiteScroll(getImage("Scroll of Smite"), isIdd("Smite"));
    }
    
    public CurseScroll curse(){
        return new CurseScroll(getImage("Scroll of Curse"), isIdd("Curse"));
    }
    
    public SkeletonScroll skeleton(){
        return new SkeletonScroll(getImage("Scroll of Skeleton"), isIdd("Skeleton"));
    }
    
    public EarthquakeScroll earthquake(){
        return new EarthquakeScroll(getImage("Scroll of Earthquake"), isIdd("Earthquake"));
    }
    
    public AnimationScroll animation(){
        return new AnimationScroll(getImage("Scroll of Animation"), isIdd("Animation"));
    }
    
    public BurialScroll burial(){
        return new BurialScroll(getImage("Scroll of Burial"), isIdd("Burial"));
    }
    
    public GrimScroll grim(){
        return new GrimScroll(getImage("Scroll of the Grim"), isIdd("Grim"));
    }
    
    public CustomScroll custom(){
        return new CustomScroll(getImage("Custom Scroll"), isCustomIdd());
    }
    
    /**
     * Returns a random ImageIcon of a smudge.
     * @return
     */
    public ImageIcon getRandomSmudge(){
        if(Distribution.chance(1, 2)) return new ImageIcon(getImage("SMUDGE1"));
        else return new ImageIcon(getImage("SMUDGE2"));
    }
    
    /**
     * Sets the Hero after Serialization.
     * @param h
     */
    public void setHero(Hero h){
        hero = h;
    }
    
}
