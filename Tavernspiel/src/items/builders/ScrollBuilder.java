
package items.builders;

import creatures.Creature;
import items.consumables.Scroll;
import items.consumables.scrolls.*;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Supplier;
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
    
    private static final long serialVersionUID = 37819421333433L;
    
    /*protected HashMap<String, ScrollProfile> scrollMap = new HashMap<>();
    private final LinkedList<String> runes = new LinkedList<>();
    {
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
    }*/
    private static final HashMap<String, Dimension> runeMap = new HashMap<>();
    static{
        runeMap.put("GICH", new Dimension(0, 176));
        runeMap.put("META", new Dimension(16, 176));
        runeMap.put("ALIKI", new Dimension(32, 176));
        runeMap.put("SIMA", new Dimension(48, 176));
        runeMap.put("IRIGI", new Dimension(64, 176));
        runeMap.put("TILACHA", new Dimension(80, 176));
        runeMap.put("MIDI", new Dimension(96, 176));
        runeMap.put("MOTI", new Dimension(112, 176));
        runeMap.put("SMUDGE1", new Dimension(0, 192));
        runeMap.put("SMUDGE2", new Dimension(16, 192));
        runeMap.put("INEMA", new Dimension(32, 192));
        runeMap.put("KORO", new Dimension(48, 192));
        runeMap.put("BERI", new Dimension(64, 192));
        runeMap.put("REDO", new Dimension(80, 192));
        runeMap.put("SEMA", new Dimension(96, 192));
        runeMap.put("BLANK", new Dimension(112, 192));
        runeMap.put("FERESI", new Dimension(64, 272));
        runeMap.put("MOKATI", new Dimension(80, 272));
        runeMap.put("MAZORI", new Dimension(96, 272));
        runeMap.put("HULUMI", new Dimension(112, 272));
    }
    private ScrollRecord[] records = new ScrollRecord[17];
    {
        records[0] = new ScrollRecord("Scroll of Wonder");
        records[1] = new ScrollRecord("Scroll of Countercurse");
        records[2] = new ScrollRecord("Scroll of Upgrade");
        records[3] = new ScrollRecord("Scroll of Enchantment");
        records[4] = new ScrollRecord("Scroll of Infinite Horse");
        records[5] = new ScrollRecord("Scroll of Identity");
        records[6] = new ScrollRecord("Scroll of Earthwall");
        records[7] = new ScrollRecord("Scroll of Juxtaposition");
        records[8] = new ScrollRecord("Scroll of Knowledge");
        records[9] = new ScrollRecord("Scroll of Recharging");
        records[10] = new ScrollRecord("Scroll of Smite");
        records[11] = new ScrollRecord("Scroll of Curse");
        records[12] = new ScrollRecord("Scroll of Skeleton");
        records[13] = new ScrollRecord("Scroll of Earthquake");
        records[14] = new ScrollRecord("Scroll of Animation");
        records[15] = new ScrollRecord("Scroll of Burial");
        records[16] = new ScrollRecord("Scroll of Grim");
        
        LinkedList<String> runes = new LinkedList<>();
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
        Collections.shuffle(runes);
        
        for(int n=0;n<records.length;n++) records[n].setRune(runes.get(n));
        
    }
    
    /**
     * Identifies the Scroll with the given rune.
     * @param scroll The name of the scroll e.g: "Scroll of ...".
     */
    public void identify(String scroll){
        for(ScrollRecord record : records) if(record.name.contains(scroll)){
            record.identified = true;
            return;
        }
        throw new IllegalArgumentException("Unknown scroll name: " + scroll);
    }
    
    /**
     * Checks whether the given scroll is identified.
     * @param scroll The name of the scroll e.g: "Scroll of ...".
     * @return
     */
    private boolean isIdentified(String scroll){
        for(ScrollRecord record : records) if(record.name.contains(scroll)) return record.identified;
        throw new IllegalArgumentException("Unknown scroll name: " + scroll);
    }
    
    /**
     * Gets the ScrollProfile for the Scroll name.
     * @param scroll The name of the Scroll (e.g: "Scroll of...").
     * @return The profile.
     */
    private ScrollRecord getRecord(String scroll){
        for(ScrollRecord record : records) if(record.name.contains(scroll))
            return record;
        throw new IllegalArgumentException("Unknown scroll name: " + scroll);
    }

    public WonderScroll wonder(Distribution scDist){
        return new WonderScroll(getRecord("Scroll of Wonder")){
            private final static long serialVersionUID = 58843248340249L;
            private final Scroll scroll = getRandomScroll(scDist);
            @Override
            public boolean use(Creature c){
                return scroll.use(c);
            }
        };
    }
    
    public CountercurseScroll countercurse(){
        return new CountercurseScroll(getRecord("Scroll of Countercurse"));
    }
    
    @Unfinished("Might separate upgrade mechanic from scrolls.")
    public UpgradeScroll upgrade(){
        return new UpgradeScroll(getRecord("Scroll of Upgrade"));
    }
    
    public EnchantmentScroll enchantment(){
        return new EnchantmentScroll(getRecord("Scroll of Enchantment"));
    }
    
    public InfiniteHorseScroll infiniteHorse(){
        return new InfiniteHorseScroll(getRecord("Scroll of Infinite Horse"));
    }
    
    public IdentityScroll identity(){
        return new IdentityScroll(getRecord("Scroll of Identity"));
    }
    
    public EarthwallScroll earthwall(){
        return new EarthwallScroll(getRecord("Scroll of Earthwall"));
    }
    
    public JuxtapositionScroll juxtaposition(){
        return new JuxtapositionScroll(getRecord("Scroll of Juxtaposition"));
    }
    
    public KnowledgeScroll knowledge(){
        return new KnowledgeScroll(getRecord("Scroll of Knowledge"));
    }
    
    public RechargingScroll recharging(){
        return new RechargingScroll(getRecord("Scroll of Recharging"));
    }
    
    public SmiteScroll smite(){
        return new SmiteScroll(getRecord("Scroll of Smite"));
    }
    
    public CurseScroll curse(){
        return new CurseScroll(getRecord("Scroll of Curse"));
    }
    
    public SkeletonScroll skeleton(){
        return new SkeletonScroll(getRecord("Scroll of Skeleton"));
    }
    
    public EarthquakeScroll earthquake(){
        return new EarthquakeScroll(getRecord("Scroll of Earthquake"));
    }
    
    public AnimationScroll animation(){
        return new AnimationScroll(getRecord("Scroll of Animation"));
    }
    
    public BurialScroll burial(){
        return new BurialScroll(getRecord("Scroll of Burial"));
    }
    
    public GrimScroll grim(){
        return new GrimScroll(getRecord("Scroll of the Grim"));
    }
    
    public CustomScroll custom(){
        return new CustomScroll(getBlankScrollRecord());
    }
    
    /**
     * Generates a random scroll.
     * @param scrollDist
     * @return
     */
    @Unfinished("Remove print check.")
    public Scroll getRandomScroll(Distribution scrollDist){
        int i = (int)scrollDist.next();
        System.out.println("Scroll: " + i);
        switch(i){
            case 0: return wonder(scrollDist);
            case 1: return countercurse();
            case 2: return enchantment();
            case 3: return infiniteHorse();
            case 4: return identity();
            case 5: return earthwall();
            case 6: return juxtaposition();
            case 7: return knowledge();
            case 8: return recharging();
            case 9: return smite();
            case 10: return curse();
            case 11: return skeleton();
            case 12: return earthquake();
            case 13: return animation();
            case 14: return burial();
            case 15: return grim();
            case 16: return custom();
        }
        throw new IllegalStateException("Scroll Distribution Error");
    }
    
    /**
     * Generates a random benevolent scroll.
     * @param scrollDist
     * @return
     */
    public Scroll getRandomPositiveScroll(Distribution scrollDist){
        switch((int)scrollDist.nextFromRange(0, 9)){
            case 0: return wonder(scrollDist);
            case 1: return countercurse();
            case 2: return enchantment(); 
            case 3: return infiniteHorse(); 
            case 4: return identity(); 
            case 5: return earthwall(); 
            case 6: return juxtaposition(); 
            case 7: return knowledge();
            default: return recharging();
        }
    }
    
    /**
     * Generates a random malevolent scroll.
     * @param scrollDist
     * @return
     */
    public Scroll getRandomNegativeScroll(Distribution scrollDist){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
    public static class ScrollRecord{
        
        public final String name;
        private String rune;
        private Supplier<ImageIcon> loader;
        private boolean identified = false;
        
        public ScrollRecord(String n){
            name = n;
        }
        
        public void setRune(String r){
            rune = r;
            loader = (Serializable & Supplier<ImageIcon>) () -> {
                Dimension dim = runeMap.get(r);
                return ItemBuilder.getIcon(dim.width, dim.height);
            };
        }
        
        public String getRune(){
            return rune;
        }

        public boolean isIdentified(){
            return identified;
        }

        public Supplier<ImageIcon> getLoader(){
            return loader;
        }
        
    }
    
    private static ScrollRecord getBlankScrollRecord(){
        ScrollRecord sp = new ScrollRecord("Blank Scroll");
        sp.setRune("BLANK");
        sp.identified = true;
        return sp;
    }
    
    /*public static ScrollRecord getSmudgedScrollProfile(){
        ScrollRecord sp = new ScrollRecord();
        sp.unknownName = "Smudged Scroll";
        sp.description = "";
        sp.loader = getSmudgeIconSupplier();
        return sp;
    }*/
    
    public static Supplier<ImageIcon> getSmudgeIconSupplier(){
        if(Distribution.chance(1, 2)) return (Serializable & Supplier<ImageIcon>) () -> ItemBuilder.getIcon(0, 96);
        else return (Serializable & Supplier<ImageIcon>) () -> ItemBuilder.getIcon(16, 96);
    }
    
}
