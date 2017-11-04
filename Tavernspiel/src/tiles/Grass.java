
package tiles;

import creatures.Creature;
import javax.swing.ImageIcon;
import level.Location;
import listeners.StepListener;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Grass extends Tile implements StepListener{

    private boolean tall;
    private ImageIcon shortImage;
    
    public Grass(Location loc, boolean t){
        super(t ? "highgrass" : "lowgrass", loc, true, true, !t);
        tall = t;
        if(tall) shortImage = ImageHandler.getImage("lowgrass", loc);
    }

    @Override
    public void steppedOn(Creature c){
        if(tall){
            tall = false;
            transparent = true;
            image = shortImage;
        }
    }
    
}
