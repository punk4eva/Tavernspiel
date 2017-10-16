
package dialogues;

import guiUtils.CComponent;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
@Unfinished("More CComponents are necessary to build this")
public class LocationBuilderDialogue extends Dialogue{
    
    public LocationBuilderDialogue(CComponent... opt){
        super("Settings for location", "offCase", false, opt);
    }
    
}
