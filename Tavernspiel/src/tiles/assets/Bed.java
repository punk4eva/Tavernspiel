
package tiles.assets;

import creatures.Creature;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import level.Location;
import listeners.StepListener;
import logic.ImageUtils;
import tiles.CustomTile;

/**
 *
 * @author Adam Whittaker
 */
public class Bed extends CustomTile implements StepListener{

    public Bed(String na, Location loca, int num, int rot){
        super(loca, loc -> {
            Bed t = new Bed(na, loc, num);
            t.rotate(rot);
            return t;
        });
    }
    
    private Bed(String na, Location loc, int num){
        name = na+num;
        treadable = true;
        flammable = true;
        transparent = true;
        image = loc.getImage(name);
    }
    
    private void rotate(int rotation){
        BufferedImage ret = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) ret.getGraphics();
        AffineTransform t = AffineTransform.getRotateInstance(Math.PI/2*rotation);
        g.drawRenderedImage(ImageUtils.addImageBuffer(image), t);
        image = new ImageIcon(ret);
    }

    @Override
    public void steppedOn(Creature c){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
}
