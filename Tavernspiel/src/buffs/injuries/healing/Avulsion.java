/* * Copyright 2019 Adam Whittaker.
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
import creatureLogic.Description;
import creatureLogic.WellBeing;
import items.builders.DescriptionBuilder;
import static logic.Distribution.getGaussianAboveZero;

/**
 *
 * @author Adam Whittaker
 */
public class Avulsion extends HealingInjury{
    
    private static final String[] SHALLOW_DESC = {"The skin here has been torn off, and *sh* pieces of it still hang around the edges. The wound feels *te* and smells *sm*.",
            "The skin is bleeding a *tx* *cm**cr* blood and it looks *ap*."};
    private static final String[] SIGNIFICANT_DESC = {"The skin and some flesh in this region is gone, and blood is flowing slowly from the wound. It smells *sm*, which is sure to attract many flies."};
    private static final String[] SEVERE_DESC = {"The skin and flesh has been completely ripped off down to the *ap* bone."};

    public Avulsion(Description desc, double[] p, int lvl){
        super("Avulsion", desc, p);
        level = lvl;
    }
    
    public Avulsion(){
        super("Avulsion");
    }

    @Override
    public HealingInjury downgradeInjury(){
        Avulsion av = new Avulsion();
        av.setLevel(level-1);
        return av;
    }

    @Override
    protected void addAction(WellBeing w){}

    @Override
    protected void removeAction(WellBeing w){}

    @Override
    public void setLevel(int lvl){
        level = lvl;
        description.layers[0] = DescriptionBuilder.getInjuryDescription(
                (level==1) ? SHALLOW_DESC : ((level==2) ? SIGNIFICANT_DESC : SEVERE_DESC), level);
        traumaLevel = ((double)lvl)*0.33;
        evLoss = getGaussianAboveZero(0.2*level, 0.2);
        accLoss = getGaussianAboveZero(0.2*level, 0.2);
        attLoss = getGaussianAboveZero(0.3*level, 0.4);
        spLoss = getGaussianAboveZero(0.03*level, 0.02);
        atspLoss = getGaussianAboveZero(0.03*level, 0.02);
        healingNum = getGaussianAboveZero(5*level, 3.5);
    }

}
