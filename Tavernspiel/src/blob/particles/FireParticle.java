/*
 * Copyright 2018 Adam Whittaker.
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
package blob.particles;

import blob.ParticleAnimation;
import blob.ParticleAnimation.Particle;
import blob.TrailGenerator;
import java.awt.Color;
import java.awt.Rectangle;
import logic.Distribution;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class FireParticle extends Particle{
    
    private final double courseCorrectionFactor = 0.01;
    
    public FireParticle(Color col, Rectangle s, double ms, TrailGenerator g){
        super(col, s, ms, g);
    }
    
    public FireParticle(Color col, Rectangle s, double ms){
        super(col, s, ms);
    }
    
    private FireParticle(ParticleAnimation e, Color col, Rectangle s, double ms, TrailGenerator g){
        super(e, col, s, ms, g);
        vely = -Distribution.r.nextDouble()*ms;
        velx = 0.25*(Distribution.r.nextDouble()*ms - ms);
    }
    
    private FireParticle(ParticleAnimation e, Color col, Rectangle s, double ms){
        super(e, col, s, ms);
        vely = -Distribution.r.nextDouble()*ms;
        velx = 0.25*(Distribution.r.nextDouble()*ms - ms);
    }

    @Override
    protected void motor(){
        courseCorrection();
        xchange += velx;
        ychange += vely;
        x += Utils.floor(xchange);
        if(x<effect.stopField.x||x>effect.stopField.x+effect.stopField.width){
            expired = true;
            return;
        }
        y += Utils.floor(ychange);
        xchange %= 1.0;
        ychange %= 1.0;
    }
    
    private void courseCorrection(){
        if(desty<y){
            if(vely-courseCorrectionFactor<-maxSpeed){
                vely = -maxSpeed;
            }else vely -= courseCorrectionFactor;
        }else if(desty-3>y){
            expired = true;
        }
    }

    @Override
    protected FireParticle clone(){
        return new FireParticle(effect, color, shape, maxSpeed, generator);
    }
    
    public static class StaticFireParticle extends FireParticle{
    
        public StaticFireParticle(Color col){
            super(col, new Rectangle(2, 3), 0);
        }
        
        private StaticFireParticle(Color col, ParticleAnimation e){
            super(e, col, new Rectangle(2, 3), 0);
        }
        
        @Override
        protected void motor(){}
        
        @Override
        protected StaticFireParticle clone(){
            return new StaticFireParticle(color, effect);
        }
        
    }
    
}
