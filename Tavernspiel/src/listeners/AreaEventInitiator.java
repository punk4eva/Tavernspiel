
package listeners;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 */
public class AreaEventInitiator{
    
    private List<AreaListener> listeners = new ArrayList<>();
    
    /**
     * Adds an area listener to the notification list.
     * @param al The AreaListener to add.
     */
    public void addAreaListener(AreaListener al){
        listeners.add(al);
    }
    
    public void notify(AreaEvent ae){
        listeners.stream().forEach(arealistener -> {
            arealistener.areaActedUpon(ae);
        });
    }
    
}
