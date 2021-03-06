
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
import listeners.RotatableTile;
import listeners.StepListener;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class Bed extends Tile implements StepListener, RotatableTile, Serializable{
    
    private final int rotation;
    private final String locName;

    public Bed(String na, Location loc, int num, int rot){
        super(na+num, loc, true, true, true);
        locName = loc.name;
        rotation = rot;
        rotateImage(rotation);
    }
    
    @Override
    public final void rotateImage(int q){
        BufferedImage ret = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) ret.getGraphics();
        AffineTransform t = AffineTransform.getRotateInstance(-q*Math.PI/4d, 8, 8);
        g.transform(t);
        g.drawImage(image.getImage(), t, null);
        image = new ImageIcon(ret);
    }

    @Override
    public void steppedOn(Creature c){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        image = Location.LOCATION_MAP.get(locName).getImage(name);
        rotateImage(rotation);
    }
    
}
