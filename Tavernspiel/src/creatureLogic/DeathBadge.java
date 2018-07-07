
package creatureLogic;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class DeathBadge extends Badge{
    
    @Unfinished("static part")
    public static final List<DeathBadge> allDeathBadges = new LinkedList<>();
    static{
    
    }
    public int[] overridesLivingBadges;
    private Predicate<PlayData> obtainCheck;
    
    /**
     * Creates an instance.
     * @param n The name.
     * @param req The requirements.
     * @param i The id.
     * @param l The level.
     */
    public DeathBadge(String n, String req, int i, int l){
        super(n, req, i, l);
    }
    
    /**
     * Creates an instance.
     * @param n The name.
     * @param req The requirements.
     * @param i The id.
     * @param l The level.
     * @param overrides The id of living badges that it overrides
     */
    public DeathBadge(String n, String req, int i, int l, int... overrides){
        super(n, req, i, l);
        overridesLivingBadges = overrides;
    }
    
    /**
     * Creates an instance.
     * @param n The name.
     * @param req The requirements.
     * @param i The id.
     * @param l The level.
     * @param sB Whether it is a super badge.
     * @param overrides The id of living badges that it overrides.
     */
    public DeathBadge(String n, String req, int i, int l, boolean sB, int... overrides){
        super(n, req, i, l, sB);
        overridesLivingBadges = overrides;
    }
    
    /**
     * Creates an instance.
     * @param n The name.
     * @param req The requirements.
     * @param i The id.
     * @param l The level.
     * @param ob A check to see whether the badge has been obtained.
     */
    public DeathBadge(String n, String req, int i, int l, Predicate<PlayData> ob){
        super(n, req, i, l);
        obtainCheck = ob;
    }
    
    /**
     * Creates an instance.
     * @param n The name.
     * @param req The requirements.
     * @param i The id.
     * @param l The level.
     * @param ob A check to see whether the badge has been obtained.
     * @param overrides The id of living badges that it overrides.
     */
    public DeathBadge(String n, String req, int i, int l, Predicate<PlayData> ob, int... overrides){
        super(n, req, i, l);
        overridesLivingBadges = overrides;
        obtainCheck = ob;
    }
    
    /**
     * Checks whether the badge has been obtained.
     * @param data The data to compare against.
     * @return true if it has, 
     */
    public boolean check(PlayData data){
        return obtainCheck.test(data);
    }
    
    /**
     * Returns a list off all Death badges that have been collected.
     * @param data The play data to reference.
     * @return The list.
     */
    public static List<DeathBadge> checkAll(PlayData data){
        return allDeathBadges.stream()
                .filter((b) -> (b.check(data)))
                .collect(Collectors.toList());
    }
    
}
