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

package buffs.injuries.healing;

import buffs.Injury.HealingInjury;
import creatureLogic.WellBeing;
import items.builders.DescriptionBuilder;
import static logic.Distribution.getGaussianAboveZero;

/**
 *
 * @author Adam Whittaker
 */
public class Incapacitation extends HealingInjury{
    
    private static final String[] DESC = {"Many minor wounds on your body have accumulated and you are much weaker than you would once were.\n"};
    
    public Incapacitation(double hNum, String cause){
        super("Incapacitation");
        healingNum = hNum;
        level = 1;
        description.layers[0] = DescriptionBuilder.getInjuryDescription(DESC, -1);
        traumaLevel = 1.2;
        evLoss = getGaussianAboveZero(0.25, 0.3);
        accLoss = getGaussianAboveZero(0.25, 0.3);
        attLoss = getGaussianAboveZero(0.25, 0.3);
        spLoss = getGaussianAboveZero(0.25, 0.3);
        atspLoss = getGaussianAboveZero(0.25, 0.3);
        uncauterizable = true;
        switch(name){
            case "fire": evLoss += 0.2;
                description.layers[0] += "The worst of the injuries is a terrible burn covering the majority of your "
                        + DescriptionBuilder.bodyPartWord() + ", making your joints worse and reducing your evasion. "
                        + "You are plagued by a persistent smoke-cough from the fire.";
                break;
            case "poison": atspLoss += 0.2;
                description.layers[0] += "The remenants of the poison have made your muscles sluggish.";
                break;
            
        }
    }

    @Override
    public HealingInjury downgradeInjury(){
        throw new IllegalStateException("Only one level");
    }

    @Override
    protected void addAction(WellBeing w){
        w.regen *= 0.8;
        w.miscSpeed += 0.1;
    }

    @Override
    protected void removeAction(WellBeing w){
        w.regen /= 0.8;
        w.miscSpeed -= 0.1;
    }

    @Override
    public void setLevel(int lvl){/*Ignore*/}

}
