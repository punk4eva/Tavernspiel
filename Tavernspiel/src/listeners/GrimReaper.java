
package listeners;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 */
public class GrimReaper{
    
    private List<DeathListener> listenersInTheShadows = new ArrayList<>();
    
    /**
     * Adds a DeathListener to the notification list.
     * @param dl The DeathListener to add.
     */
    public void addDeathListener(DeathListener dl){
        listenersInTheShadows.add(dl);
    }
    
    public void notify(DeathEvent de){
        listenersInTheShadows.stream().forEach(arealistener -> {
            arealistener.lifeTaken(de);
        });
    }
}
