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

package dialogues.assets;

import creatures.Hero;
import dialogues.TextDialogue;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 * 
 * This class controls the pop-up for the Description of a Tile.
 */
public class TileDescriptionDialogue extends TextDialogue{

    public TileDescriptionDialogue(Tile tile, Hero hero){
        super(tile.image, tile.name, tile.description.getDescription(hero.expertise));
    }

}
