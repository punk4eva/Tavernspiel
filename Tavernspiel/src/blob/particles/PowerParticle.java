
package blob.particles;

import blob.ParticleEffect;
import blob.ParticleEffect.Particle;
import blob.TrailGenerator;
import java.awt.Color;
import java.awt.Rectangle;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class PowerParticle extends Particle{
    
    private final double mult;

    private PowerParticle(ParticleEffect e, Color col, Rectangle s, double ms, double f){
        super(e, col, s, ms);
        mult = f;
    }
    
    private PowerParticle(ParticleEffect e, Color col, Rectangle s, double ms, double f, TrailGenerator g){
        super(e, col, s, ms, g);
        mult = f;
    }
    
    /**
     * Creates an instance.
     * @param col The Color.
     * @param s The shape.
     * @param f The madness coefficient.
     * @param ms The maximum speed of the particle.
     */
    public PowerParticle(Color col, Rectangle s, double ms, double f){
        super(col, s, ms);
        mult = f;
    }
    
    /**
     * Creates an instance.
     * @param col The Color.
     * @param s The shape.
     * @param f The madness coefficient.
     * @param ms The maximum speed of the particle.
     * @param g The TrailGenerator.
     */
    public PowerParticle(Color col, Rectangle s, double ms, double f, TrailGenerator g){
        super(col, s, ms, g);
        mult = f;
    }
    
    /**
    * Creates an instance with a custom TrailGenerator.
    * @param col The Color.
    * @param s The shape.
    * @param f The madness coefficient.
    * @param ms The maximum speed of the particle.
    * @param i The intensity (amount of ticks until a new particle).
    * @param c The capacity (max number of particles).
    * @param fade The fade speed for the TrailGenerator
    */
    public PowerParticle(Color col, Rectangle s, double ms, double f, int i, int c, float fade){
        super(col, s, ms, i, c, fade);
        mult = f;
    }

    @Override
    protected void motor(){
        double factor = Distribution.r.nextDouble() * mult;
        if(desty < y && vely > -maxSpeed){
            if(vely - factor < -maxSpeed){
                vely = -maxSpeed;
            }else{
                vely -= factor;
            }
        }else if(desty > y && vely < maxSpeed){
            if(vely + factor > maxSpeed){
                vely = maxSpeed;
            }else{
                vely += factor;
            }
        }
        if(destx < x && velx > -maxSpeed){
            if(velx - factor < -maxSpeed){
                velx = -maxSpeed;
            }else{
                velx -= factor;
            }
        }else if(destx > x && velx < maxSpeed){
            if(velx + factor > maxSpeed){
                velx = maxSpeed;
            }else{
                velx += factor;
            }
        }
        xchange += velx;
        ychange += vely;
        x += Math.floor(xchange);
        y += Math.floor(ychange);
        xchange %= 1.0;
        ychange %= 1.0;
    }

    @Override
    protected PowerParticle clone(){
        return new PowerParticle(effect, color, shape, maxSpeed, mult, generator);
    }
    
    /**
     * The static default instance of a PowerParticle effect.
     */
    public static ParticleEffect EFFECT = new ParticleEffect(1, 50, new Rectangle(128, 128, 48, 48), 
                new Rectangle(132, 132, 40, 40), new PowerParticle(Color.red, new Rectangle(8, 8), 5, 0.5));
    
}
