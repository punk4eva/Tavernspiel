/*
 * Copyright 2019 Adam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package items.builders;

import blob.ParticleAnimation;
import blob.ParticleAnimation.Particle;
import blob.ParticleTrailGenerator;
import java.awt.Color;
import java.awt.Rectangle;
import static logic.Distribution.R;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class WandAnimationBuilder{

    /**
     * Generates a random Wand Animation.
     * @return
     */
    public static ParticleAnimation getRandWandAnimation(){
        Rectangle rect = new Rectangle(2+R.nextInt(4), 2+R.nextInt(4));
        ParticleAnimation pa = new ParticleAnimation(R.nextInt(9)+4, 15, new Rectangle(4, 4, 8, 8), 
                new Rectangle(4, 4, 8, 8), new WandParticle(0.2 + 0.1*R.nextDouble(), ItemBuilder.getRandomColor(),
                rect, 0.3+2*R.nextDouble(), new ParticleTrailGenerator(ItemBuilder.getRandomColor(), 
                7F+(float)(8D*R.nextDouble()),2+R.nextInt(3),4+R.nextInt(4),rect.width,rect.height)));
        pa.setMillis(4000);
        return pa;
    }
    
    private static class WandParticle extends Particle{
        
        private final double factor;

        private WandParticle(double f, ParticleAnimation e, Color col, Rectangle s, double ms){
            super(e, col, s, ms);
            factor = f;
        }

        private WandParticle(double f, ParticleAnimation e, Color col, Rectangle s, double ms, ParticleTrailGenerator g){
            super(e, col, s, ms, g);
            factor = f;
        }

        /**
         * Creates an instance.
         * @param m The motor number.
         * @param f The madness factor.
         * @param col The Color.
         * @param s The shape.
         * @param ms The maximum speed of the particle.
         */
        public WandParticle(double f, Color col, Rectangle s, double ms){
            super(col, s, ms);
            factor = f;
        }

        /**
         * Creates an instance.
         * @param col The Color.
         * @param m The motor number.
         * @param f The madness factor.
         * @param s The shape.
         * @param ms The maximum speed of the particle.
         * @param g The TrailGenerator.
         */
        public WandParticle(double f, Color col, Rectangle s, double ms, ParticleTrailGenerator g){
            super(col, s, ms, g);
            factor = f;
        }

        /**
        * Creates an instance with a custom TrailGenerator.
        * @param col The Color.
        * @param s The shape.
        * @param m The motor number.
        * @param f The madness factor.
        * @param ms The maximum speed of the particle.
        * @param i The intensity (amount of ticks until a new particle).
        * @param c The capacity (max number of particles).
        * @param fade The fade speed for the TrailGenerator
        */
        public WandParticle(double f, Color col, Rectangle s, double ms, int i, int c, float fade){
            super(col, s, ms, i, c, fade);
            factor = f;
        }
        
        @Override
        protected void motor(){
            if(velx==0 && vely==0){
                velx = R.nextDouble() - 0.5;
                vely = R.nextDouble() - 0.5;
            }
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
            x += Utils.floor(xchange);
            y += Utils.floor(ychange);
            xchange %= 1.0;
            ychange %= 1.0;
        }

        @Override
        protected WandParticle clone(){
            return new WandParticle(factor, effect, color, shape, maxSpeed, generator);
        }
        
    }
    
}
