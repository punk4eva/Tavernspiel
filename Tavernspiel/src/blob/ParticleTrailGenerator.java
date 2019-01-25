
package blob;


import blob.ParticleAnimation.Particle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Adam
 * 
 * This class generates a trail for a particle.
 */
public class ParticleTrailGenerator implements Serializable{

    private static final long serialVersionUID = 463178L;

    private final int width, height, R, G, B;
    private int tick;
    public int intensity, capacity;
    public float fadespeed;

    private final ArrayList<Trail> trail = new ArrayList<>();

    public void paint(Graphics2D g, int x, int y){
        tick++;
        if(tick >= intensity){
            tick = 0;
            if(trail.size() < capacity){
                trail.add(new Trail(fadespeed, x, y, width, height, R, G, B));
            }
        }
        trail.removeIf(p -> p.expired);
        trail.stream().forEach(p -> p.paint(g));
    }

    public ParticleTrailGenerator(Color col, float f, int... v){
        intensity = v[0];
        capacity = v[1];
        width = v[2];
        height = v[3];
        R = col.getRed();
        G = col.getGreen();
        B = col.getBlue();
        fadespeed = f;
    }
    
    public ParticleTrailGenerator(Particle p, int i, int c, float f){
        width = p.shape.width;
        height = p.shape.height;
        R = p.color.getRed();
        G = p.color.getGreen();
        B = p.color.getBlue();
        intensity = i;
        capacity = c;
        fadespeed = f;
    }
    
    private ParticleTrailGenerator(ParticleTrailGenerator g){
        width = g.width;
        height = g.height;
        R = g.R;
        G = g.G;
        B = g.B;
        intensity = g.intensity;
        capacity = g.capacity;
        fadespeed = g.fadespeed;
    }
    
    @Override
    public ParticleTrailGenerator clone(){
        return new ParticleTrailGenerator(this);
    }

    private class Trail implements Serializable{

        private static final long serialVersionUID = 1001L;

        private final int x, y, width, height, fadespeed;
        private int R, G, B, alpha = 255;
        private final float fadedelta;
        private float delta;
        private boolean expired = false;

        Trail(float f, int... v){
            x = v[0];
            y = v[1];
            width = v[2];
            height = v[3];
            R = v[4];
            G = v[5];
            B = v[6];
            fadespeed = (int) Math.floor(f);
            fadedelta = f - fadespeed;
        }

        int decrementAlpha(){
            alpha-=fadespeed;
            delta+=fadedelta;
            if(delta>=1){
                alpha--;
                delta--;
            }
            return alpha;
        }

        void paint(Graphics2D g){
            decrementAlpha();
            if(alpha > 0){
                g.setColor(new Color(R, G, B, alpha));
                g.fillRect(x, y, width, height);
            }else{
                expired = true;
            }
        }

    }

}
