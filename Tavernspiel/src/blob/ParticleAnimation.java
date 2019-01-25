
package blob;

import animation.TrackableAnimation;
import gui.mainToolbox.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.LinkedList;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class handles the animation for a Particle effect.
 */
public class ParticleAnimation extends TrackableAnimation{
    
    @Override
    public void animate(Graphics2D g, int _x, int _y){
        setXY(_x+xOffset, _y+yOffset);
        tick++;
        if(tick>=intensity){
            tick = 0;
            if(loaded.size()<capacity) loaded.add(createParticle());
        }
        loaded.removeIf(p -> p.expired);
        loaded.stream().forEach(p -> p.paint(g));
    }
    
    private final int xOffset, yOffset;
    public int intensity;
    public int capacity;
    private int tick;
    private LinkedList<Particle> loaded = new LinkedList<>();
    public Particle[] particleSet;
    public Rectangle startField, stopField;
    
    /**
     * Creates a Particle Animation.
     * @param i intensity
     * @param c capacity
     * @param sta start field
     * @param sto stop field
     * @param particles particle set
     */
    public ParticleAnimation(int i, int c, Rectangle sta, Rectangle sto, Particle... particles){
        super(Main.animator);
        particleSet = particles;
        capacity = c;
        startField = sta;
        stopField = sto;
        intensity = i;
        if(sta!=null){
            xOffset = sta.x;
            yOffset = sta.y;
        }else{
            xOffset = 0;
            yOffset = 0;
        }
        for(Particle particle : particleSet) particle.effect = this;
    }
    
    private Particle createParticle(){
        return particleSet[Distribution.r.nextInt(particleSet.length)].clone();
    }
    
    /**
     * Returns a labeled HashMap of the particles in this animation.
     * @return
     */
    public HashMap<String, Particle> getParticleMap(){
        HashMap<String, Particle> ret = new HashMap<>();
        for(int n=0;n<particleSet.length;n++)
            ret.put("particle"+n, particleSet[n]);
        return ret;
    }
    
    /**
     * Sets the x,y coordinates of the start/stop fields.
     * @param x
     * @param y
     */
    public void setXY(int x, int y){
        int dx=x-startField.x, dy=y-startField.y;
        startField.setLocation(x, y);
        stopField.setLocation(stopField.x+dx, stopField.y+dy);
    }
    
    /**
     * This class models a singular particle.
     */
    public static abstract class Particle implements Cloneable{
        
        protected Color color;
        protected int x, y, destx, desty;
        protected double velx=0, xchange=0, vely=0, ychange=0, maxSpeed;
        protected final Rectangle shape;
        protected boolean expired = false;
        public ParticleAnimation effect;
        protected ParticleTrailGenerator generator;
        
        protected Particle(ParticleAnimation e, Color col, Rectangle s, double ms){
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
        
        protected Particle(ParticleAnimation e, Color col, Rectangle s, double ms, ParticleTrailGenerator g){
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
        
        protected Particle(ParticleAnimation e, Color col, Rectangle s, double ms, int i, int cap, float fade){
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
            generator = new ParticleTrailGenerator(this, i, cap, fade);
        }
        
        protected Particle(Color col, Rectangle s, double ms){
            color = col;
            shape = s;
            maxSpeed = ms;
        }
        
        protected Particle(Color col, Rectangle s, double ms, ParticleTrailGenerator g){
            color = col;
            shape = s;
            maxSpeed = ms;
            generator = g.clone();
        }
        
        protected Particle(Color col, Rectangle s, double ms, int i, int c, float f){
            color = col;
            shape = s;
            maxSpeed = ms;
            generator = new ParticleTrailGenerator(this, i, c, f);
        }
        
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
        
        protected abstract void motor();
        
        @Override
        protected abstract Particle clone();
                
    }
    
    /**
     * The NullObject design pattern for ParticleAnimations.
     */
    public static class NullAnimation extends ParticleAnimation{
    
        public NullAnimation(){
            super(0, 0, null, null);
        }
        
        @Override
        public void animate(Graphics2D g, int _x, int _y){}
        
        @Override
        public void setXY(int x, int y){}
    
    }
    
}
