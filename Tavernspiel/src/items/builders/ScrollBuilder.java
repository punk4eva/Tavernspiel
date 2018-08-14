
package items.builders;

import creatures.Creature;
import items.consumables.Scroll;
import items.consumables.ScrollProfile;
import items.consumables.scrolls.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds Scrolls.
 */
public class ScrollBuilder implements Serializable{
    
    private static final long serialVersionUID = 37819421333333L;
    
    protected HashMap<String, ScrollProfile> scrollMap = new HashMap<>();
    private final LinkedList<String> runes = new LinkedList<>();
    //@Unfinished("Remember to make custom scrolls part of natural item gen.")
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
    }
    
    /**
     * Identifies the Scroll with the given rune.
     * @param rune The rune.
     */
    public void identify(String rune){
        scrollMap.get(rune).identified = true;
    }
    
    public boolean isIdentified(String scroll){
        ScrollProfile sp = scrollMap.get(scroll);
        return sp!=null&&sp.identified;
    }
    
    /**
     * Gets the ScrollProfile for the Scroll name.
     * @param name The name of the Scroll (e.g: "Scroll of...").
     * @return The profile.
     */
    private ScrollProfile getProfile(String name){
        ScrollProfile sp = scrollMap.get(name);
        if(sp==null){
            sp = new ScrollProfile(
                    runes.remove(Distribution.r.nextInt(runes.size())));
            scrollMap.put(name, sp);
        }
        return sp;
    }

    public WonderScroll wonder(){
        return new WonderScroll(getProfile("Scroll of Wonder")){
            private final static long serialVersionUID = 58843248340249L;
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
        return new CountercurseScroll(getProfile("Scroll of Countercurse"));
    }
    
    public UpgradeScroll upgrade(){
        return new UpgradeScroll(getProfile("Scroll of Upgrade"));
    }
    
    public EnchantmentScroll enchantment(){
        return new EnchantmentScroll(getProfile("Scroll of Enchantment"));
    }
    
    public InfiniteHorseScroll infiniteHorse(){
        return new InfiniteHorseScroll(getProfile("Scroll of Infinite Horse"));
    }
    
    public IdentityScroll identity(){
        return new IdentityScroll(getProfile("Scroll of Identity"));
    }
    
    public EarthwallScroll earthwall(){
        return new EarthwallScroll(getProfile("Scroll of Earthwall"));
    }
    
    public JuxtapositionScroll juxtaposition(){
        return new JuxtapositionScroll(getProfile("Scroll of Juxtaposition"));
    }
    
    public KnowledgeScroll knowledge(){
        return new KnowledgeScroll(getProfile("Scroll of Knowledge"));
    }
    
    public RechargingScroll recharging(){
        return new RechargingScroll(getProfile("Scroll of Recharging"));
    }
    
    public SmiteScroll smite(){
        return new SmiteScroll(getProfile("Scroll of Smite"));
    }
    
    public CurseScroll curse(){
        return new CurseScroll(getProfile("Scroll of Curse"));
    }
    
    public SkeletonScroll skeleton(){
        return new SkeletonScroll(getProfile("Scroll of Skeleton"));
    }
    
    public EarthquakeScroll earthquake(){
        return new EarthquakeScroll(getProfile("Scroll of Earthquake"));
    }
    
    public AnimationScroll animation(){
        return new AnimationScroll(getProfile("Scroll of Animation"));
    }
    
    public BurialScroll burial(){
        return new BurialScroll(getProfile("Scroll of Burial"));
    }
    
    public GrimScroll grim(){
        return new GrimScroll(getProfile("Scroll of the Grim"));
    }
    
    public CustomScroll custom(){
        return new CustomScroll(ScrollProfile.getCustomProfile());
    }
    
    public Scroll getRandomScroll(Distribution scrollDist){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
}
