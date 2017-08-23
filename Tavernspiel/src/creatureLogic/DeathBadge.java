
package creatureLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class DeathBadge extends Badge{
    
    public int[] overridesLivingBadges;
    private Obtained obtainCheck;
    private interface Obtained{
        boolean check(PlayData data);
    }
    
    public DeathBadge(String n, String req, int i, int l){
        super(n, req, i, l);
    }
    
    public DeathBadge(String n, String req, int i, int l, int... overrides){
        super(n, req, i, l);
        overridesLivingBadges = overrides;
    }
    
    public DeathBadge(String n, String req, int i, int l, boolean sB, int... overrides){
        super(n, req, i, l, sB);
        overridesLivingBadges = overrides;
    }
    
    public DeathBadge(String n, String req, int i, int l, Obtained ob){
        super(n, req, i, l);
        obtainCheck = ob;
    }
    
    public DeathBadge(String n, String req, int i, int l, Obtained ob, int... overrides){
        super(n, req, i, l);
        overridesLivingBadges = overrides;
        obtainCheck = ob;
    }
    
    public boolean check(PlayData data){
        return obtainCheck.check(data);
    }
    
    public static List<DeathBadge> checkAll(PlayData data){
        return allDeathBadges().stream()
                .filter((b) -> (b.check(data)))
                .collect(Collectors.toList());
    }
    
    public static ArrayList<DeathBadge> allDeathBadges(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
