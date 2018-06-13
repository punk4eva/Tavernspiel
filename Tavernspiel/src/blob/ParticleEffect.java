
package blob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class ParticleEffect implements Serializable{

    private static final long serialVersionUID = -8216452115110348899L;
    
    /**
     * Paints this ParticleEffect
     * @param g The graphics.
     */
    public void paint(Graphics2D g){
        tick++;
        if(tick>=intensity){
            tick = 0;
            if(loaded.size()<capacity) loaded.add(createParticle());
        }
        loaded.removeIf(p -> p.expired);
        loaded.stream().forEach(p -> p.paint(g));
    }
    
    public int intensity;
    public int capacity;
    private int tick;
    private LinkedList<Particle> loaded = new LinkedList<>();
    public Particle[] particleSet;
    public Rectangle startField, stopField;
    
    /**
     * Creates an instance.
     * @param i The intensity (amount of ticks until a new particle).
     * @param c The capacity (max number of particles).
     * @param sta The start field.
     * @param sto The stop field.
     * @param particles The set of particles which could be generated.
     */
    public ParticleEffect(int i, int c, Rectangle sta, Rectangle sto, Particle... particles){
        particleSet = particles;
        capacity = c;
        startField = sta;
        stopField = sto;
        intensity = i;
        for(Particle particle : particleSet) particle.effect = this;
    }
    
    private Particle createParticle(){
        return particleSet[Distribution.r.nextInt(particleSet.length)].clone();
    }
    
    /**
     * Gives a map of the particles generated by this Effect.
     * @return A HashMap<String, Particle>.
     */
    public HashMap<String, Particle> getParticleMap(){
        HashMap<String, Particle> ret = new HashMap<>();
        for(int n=0;n<particleSet.length;n++)
            ret.put("particle"+n, particleSet[n]);
        return ret;
    }
    
    /**
     * Sets the location of the ParticleEffect.
     * @param x The top left x of the start field.
     * @param y The top left y of the start field.
     */
    public void setXY(int x, int y){
        int dx=x-startField.x, dy=y-startField.y;
        startField.setLocation(x, y);
        stopField.setLocation(stopField.x+dx, stopField.y+dy);
    }
    
    public static abstract class Particle implements Cloneable, Serializable{
        
        protected final Color color;
        protected int x, y, destx, desty;
        protected double velx=0, xchange=0, vely=0, ychange=0, maxSpeed;
        protected final Rectangle shape;
        private boolean expired = false;
        public ParticleEffect effect;
        protected TrailGenerator generator;
        
        /**
         * Creates an instance.
         * @param e The owning Effect.
         * @param col The Color.
         * @param s The shape.
         * @param ms The maximum speed of the particle.
         */
        protected Particle(ParticleEffect e, Color col, Rectangle s, double ms){
            color = col;
            shape = s;
            effect = e;
            int[] c = getStartCoords();
            x = c[0];
            y = c[1];
            c = getStopCoords();
            destx = c[0];
            desty = c[1];
            maxSpeed = ms;
        }
        
        /**
         * Creates an instance.
         * @param e The owning Effect.
         * @param col The Color.
         * @param s The shape.
         * @param ms The maximum speed of the particle.
         * @param g The TrailGenerator
         */
        protected Particle(ParticleEffect e, Color col, Rectangle s, double ms, TrailGenerator g){
            color = col;
            shape = s;
            if(g!=null) generator = g.clone();
            effect = e;
            int[] c = getStartCoords();
            x = c[0];
            y = c[1];
            c = getStopCoords();
            destx = c[0];
            desty = c[1];
            maxSpeed = ms;
        }
        
        /**
         * Creates an instance with a custom TrailGenerator.
         * @param e The owning Effect.
         * @param col The Color.
         * @param s The shape.
         * @param ms The maximum speed of the particle.
         * @param i The intensity (amount of ticks until a new particle).
         * @param cap The capacity (max number of particles).
         * @param fade The fade speed for the TrailGenerator
         */
        protected Particle(ParticleEffect e, Color col, Rectangle s, double ms, int i, int cap, float fade){
            color = col;
            shape = s;
            effect = e;
            int[] c = getStartCoords();
            x = c[0];
            y = c[1];
            c = getStopCoords();
            destx = c[0];
            desty = c[1];
            maxSpeed = ms;
            generator = new TrailGenerator(this, i, cap, fade);
        }
        
        /**
         * Creates a new instance.
         * @param col The Color.
         * @param s The shape.
         * @param ms The maximum speed of the particle.
         */
        protected Particle(Color col, Rectangle s, double ms){
            color = col;
            shape = s;
            maxSpeed = ms;
        }
        
        /**
         * Creates an instance.
         * @param col The Color.
         * @param s The shape.
         * @param ms The maximum speed of the particle.
         * @param g The TrailGenerator
         */
        protected Particle(Color col, Rectangle s, double ms, TrailGenerator g){
            color = col;
            shape = s;
            maxSpeed = ms;
            generator = g.clone();
        }
        
        /**
         * Creates an instance with a custom TrailGenerator.
         * @param col The Color.
         * @param s The shape.
         * @param ms The maximum speed of the particle.
         * @param i The intensity (amount of ticks until a new particle).
         * @param c The capacity (max number of particles).
         * @param f The fade speed for the TrailGenerator
         */
        protected Particle(Color col, Rectangle s, double ms, int i, int c, float f){
            color = col;
            shape = s;
            maxSpeed = ms;
            generator = new TrailGenerator(this, i, c, f);
        }
        
        /**
         * Clones a Particle.
         * @param p The parent.
         */
        protected Particle(Particle p){
            effect = p.effect;
            color = p.color;
            shape = p.shape;
            int[] c = getStartCoords();
            x = c[0];
            y = c[1];
            c = getStopCoords();
            destx = c[0];
            desty = c[1];
            maxSpeed = p.maxSpeed;
            generator = p.generator.clone();
        }
        
        private int[] getStartCoords(){
            return new int[]{
                Distribution.r.nextInt(effect.startField.width) + effect.startField.x,
                Distribution.r.nextInt(effect.startField.height) + effect.startField.y,
            };
        }

        private int[] getStopCoords(){
            return new int[]{
                Distribution.r.nextInt(effect.stopField.width) + effect.stopField.x,
                Distribution.r.nextInt(effect.stopField.height) + effect.stopField.y,
            };
        }
        
        void paint(Graphics2D g){
            g.setColor(color);
            motor();
            shape.setLocation(x, y);
            g.fill(shape);
            if(generator!=null) generator.paint(g, x, y);
            if(Math.abs(destx-x)<6&&Math.abs(desty-y)<6) expired = true;
        }
        
        /**
         * Recalculates the speed and coordinates of the Particle.
         */
        protected abstract void motor();
        
        /**
         * Clones this Particle.
         * @return A clone.
         */
        @Override
        protected abstract Particle clone();
                
    }
    
}
