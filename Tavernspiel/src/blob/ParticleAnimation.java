
package blob;

import animation.TrackableAnimation;
import gui.mainToolbox.Main;
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
public class ParticleAnimation extends TrackableAnimation{

    private static final long serialVersionUID = -8216452115110348899L;
    
    @Override
    public void animate(Graphics2D g, int _x, int _y){
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
    
    public ParticleAnimation(int i, int c, Rectangle sta, Rectangle sto, Particle... particles){
        super(Main.animator);
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
    
    public HashMap<String, Particle> getParticleMap(){
        HashMap<String, Particle> ret = new HashMap<>();
        for(int n=0;n<particleSet.length;n++)
            ret.put("particle"+n, particleSet[n]);
        return ret;
    }
    
    public void setXY(int x, int y){
        int dx=x-startField.x, dy=y-startField.y;
        startField.setLocation(x, y);
        stopField.setLocation(stopField.x+dx, stopField.y+dy);
    }
    
    public static abstract class Particle implements Cloneable, Serializable{
        
        protected Color color;
        protected int x, y, destx, desty;
        protected double velx=0, xchange=0, vely=0, ychange=0, maxSpeed;
        protected final Rectangle shape;
        protected boolean expired = false;
        public ParticleAnimation effect;
        protected TrailGenerator generator;
        
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
        
        protected Particle(ParticleAnimation e, Color col, Rectangle s, double ms, TrailGenerator g){
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
            generator = new TrailGenerator(this, i, cap, fade);
        }
        
        protected Particle(Color col, Rectangle s, double ms){
            color = col;
            shape = s;
            maxSpeed = ms;
        }
        
        protected Particle(Color col, Rectangle s, double ms, TrailGenerator g){
            color = col;
            shape = s;
            maxSpeed = ms;
            generator = g.clone();
        }
        
        protected Particle(Color col, Rectangle s, double ms, int i, int c, float f){
            color = col;
            shape = s;
            maxSpeed = ms;
            generator = new TrailGenerator(this, i, c, f);
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
    
}