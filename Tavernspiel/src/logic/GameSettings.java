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
package logic;

import blob.ParticleAnimation;
import blob.ParticleTrailGenerator;
import blob.particles.FireParticle;
import blob.particles.FireParticle.StaticFireParticle;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.function.BiFunction;

/**
 *
 * @author Adam Whittaker
 * 
 * This class stores the settings of the Game.
 */
public class GameSettings implements Serializable{
    
    private static final long serialVersionUID = 738215212345L;
    
    private GameSettings(){}
    
    
    
    public static FireSetting FIRE_SETTING = FireSetting.MAX;
    public static TorchSetting TORCH_SETTING = TorchSetting.MAX;
    public static WaterSetting WATER_SETTING = WaterSetting.ANIMATED;
    public static VarianceSetting VARIANCE_SETTING = VarianceSetting.MAX;
    
    /**
     * The setting for Fire quality.
     */
    public static enum FireSetting{
        STATIC((col, tcol) -> new ParticleAnimation(3, 6, new Rectangle(1, 1, 14, 14), 
                new Rectangle(-2, -2, 1, 1), new StaticFireParticle(col))),
        LOW((col, tcol) -> new ParticleAnimation(3, 7, new Rectangle(1, 12, 14, 4), 
                new Rectangle(1, 0, 14, 8), new FireParticle(col, new Rectangle(2, 3), 0.5))),
        MEDIUM((col, tcol) -> new ParticleAnimation(3, 9, new Rectangle(1, 12, 14, 4), 
                new Rectangle(1, 0, 14, 8), new FireParticle(col, new Rectangle(2, 3), 0.5, new ParticleTrailGenerator(tcol, 14.5F,3,5,1,2)))),
        HIGH((col, tcol) -> new ParticleAnimation(2, 13, new Rectangle(1, 12, 14, 4), 
                new Rectangle(1, 0, 14, 8), new FireParticle(col, new Rectangle(1, 2), 1.0, new ParticleTrailGenerator(tcol, 13.5F,3,5,2,3)))),
        MAX((col, tcol) -> new ParticleAnimation(1, 20, new Rectangle(1, 12, 14, 4), 
                new Rectangle(1, 0, 14, 8), new FireParticle(col, new Rectangle(1, 2), 1.0, new ParticleTrailGenerator(tcol, 10.5F,3,5,2,3))));
        
        private final BiFunction<Color, Color, ParticleAnimation> factory;
        FireSetting(BiFunction<Color, Color, ParticleAnimation> f){
            factory = f;
        }
        public ParticleAnimation get(Color col, Color tCol){
            return factory.apply(col, tCol);
        }
    }
    
    /**
     * The setting for Torch quality.
     */
    public static enum TorchSetting{
        STATIC((col, tcol) -> new ParticleAnimation.NullAnimation()),
        LOW((col, tcol) -> new ParticleAnimation(3, 3, new Rectangle(6, 4, 4, 3), 
                new Rectangle(6, -4, 4, 3), new FireParticle(col, new Rectangle(1, 1), 0.5))),
        MEDIUM((col, tcol) -> new ParticleAnimation(3, 5, new Rectangle(6, 4, 4, 3), 
                new Rectangle(6, -4, 4, 3), new FireParticle(col, new Rectangle(1, 1), 0.5, new ParticleTrailGenerator(tcol, 14.5F,3,5,1,1)))),
        HIGH((col, tcol) -> new ParticleAnimation(2, 9, new Rectangle(6, 4, 4, 3), 
                new Rectangle(6, -4, 4, 3), new FireParticle(col, new Rectangle(1, 1), 1.0, new ParticleTrailGenerator(tcol, 13.5F,3,5,1,1)))),
        MAX((col, tcol) -> new ParticleAnimation(1, 14, new Rectangle(6, 4, 4, 3), 
                new Rectangle(6, -4, 4, 3), new FireParticle(col, new Rectangle(1, 1), 1.0, new ParticleTrailGenerator(tcol, 10.5F,3,5,1,1))));
        
        private final BiFunction<Color, Color, ParticleAnimation> factory;
        TorchSetting(BiFunction<Color, Color, ParticleAnimation> f){
            factory = f;
        }
        public ParticleAnimation get(Color col, Color tCol){
            return factory.apply(col, tCol);
        }
    }
    
    /**
     * The setting for Water quality.
     */
    public static enum WaterSetting{
        STATIC,
        ANIMATED;
    }
    
    /**
     * The setting for Tile variance.
     */
    public static enum VarianceSetting{
        OFF,
        SPECIAL_ONLY,
        MAX
    }
    
}
