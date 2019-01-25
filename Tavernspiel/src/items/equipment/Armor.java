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
package items.equipment;

import creatureLogic.Description;
import items.Apparatus;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Armor extends Apparatus{
    
    private static final long serialVersionUID = 3821903;
    
    public Armor(double q, String n, Description desc, Supplier<ImageIcon> lo, int dur, Distribution d, int st){
        super(n, desc, lo, dur, st);
        quality = q;
        blockDistrib = d;
    }
    
    private final double quality;
    private final Distribution blockDistrib;
    
    @Override
    public void upgrade(){
        level++;
        blockDistrib.add(quality);
        maxDurability += 5;
        durability = maxDurability;
        testEnchantment();
    }
    
}
