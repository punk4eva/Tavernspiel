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

package ai.specialAI;

import ai.intelligence.IntelligentAI1;
import creatures.Creature;
import level.Area;

/**
 *
 * @author Adam Whittaker
 * 
 * This class handles the decisions of "passive" NPCs.
 */
public class PassiveAI extends IntelligentAI1{
        
    @Override
    public void turn(Creature c, Area area){
        throw new UnsupportedOperationException("Turn not initialized.");
    }

}
