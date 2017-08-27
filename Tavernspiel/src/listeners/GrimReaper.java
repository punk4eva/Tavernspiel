
package listeners;

import gui.Handler;

/**
 *
 * @author Adam Whittaker
 */
public class GrimReaper{
    
    private final Handler handler;
    
    public GrimReaper(Handler h){
        handler = h;
    }
    
    public void notify(DeathEvent de){
        handler.removeObject(de.getCreature());
    }
}
