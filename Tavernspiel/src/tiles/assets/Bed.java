
package tiles.assets;

import creatures.Creature;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.swing.ImageIcon;
import level.Location;
import listeners.StepListener;
import logic.ImageUtils;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class Bed extends Tile implements StepListener, Serializable{
    
    private final int rotation;
    private final String locName;

    public Bed(String na, Location loc, int num, int rot){
        super(na+num, loc, true, true, true);
        locName = loc.name;
        rotation = rot;
        rotate();
    }
    
    private void rotate(){
        BufferedImage ret = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) ret.getGraphics();
        AffineTransform t = AffineTransform.getQuadrantRotateInstance(rotation);
        g.drawRenderedImage(ImageUtils.convertToBuffered(image), t);
        image = new ImageIcon(ret);
    }

    @Override
    public void steppedOn(Creature c){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        image = Location.locationMap.get(locName).getImage(name);
        rotate();
    }
    
}
