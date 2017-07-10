
package listeners;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 */
public class BuffEventInitiator{
    
    private List<BuffListener> listeners = new ArrayList<>();
    
    /**
     * Adds a buff listener to the notification list.
     * @param bl The BuffListener to add.
     */
    public void addBuffListener(BuffListener bl){
        listeners.add(bl);
    }
    
    public void notify(BuffEvent be){
        listeners.stream().forEach(bufflistener -> {
            bufflistener.buffTriggered(be);
        });
    }
}
