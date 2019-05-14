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

package blob.assets;

import animation.GasAnimator;
import blob.Blob;
import buffs.BuffBuilder;
import creatureLogic.Description;
import logic.ConstantFields;
import logic.GameSettings;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a cloud of toxic gas.
 */
public class ToxicGas extends Blob{
    
    private final double damage;

    public ToxicGas(double dam, int spread, int nx, int ny){
        super("toxic gas", new Description("gas", "A poisonous green vapour makes it dangerous to breathe here."), 
                BuffBuilder.toxicGas(dam), new GasAnimator(GameSettings.TOXIC_GAS_SETTING.get(ConstantFields.toxicGasColor, 
                        ConstantFields.toxicGasTrailColor)), spread, nx, ny);
        damage = dam;
    }

    @Override
    protected void spread(){
        if(spreadNumber==0){
            dead = true;
            return;
        }
        spreadNumber--;
        if(area.map[y-1][x].flammable&&!area.gameObjectPresent(x, y-1, name)) area.addObject(new ToxicGas(damage, spreadNumber, x, y-1));
        if(area.map[y+1][x].flammable&&!area.gameObjectPresent(x, y+1, name)) area.addObject(new ToxicGas(damage, spreadNumber, x, y+1));
        if(area.map[y][x-1].flammable&&!area.gameObjectPresent(x-1, y, name)) area.addObject(new ToxicGas(damage, spreadNumber, x-1, y));
        if(area.map[y][x+1].flammable&&!area.gameObjectPresent(x+1, y, name)) area.addObject(new ToxicGas(damage, spreadNumber, x+1, y));
    }

}
