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
package tiles.assets;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import level.Location;
import tiles.AnimatedTile;

/**
 *
 * @author Adam Whittaker
 */
public class DecoratedWall extends AnimatedTile implements Serializable{
    
    private final int x, y;
    private final String locName;
    
    public DecoratedWall(Location loc, int _x, int _y){
        super("specialwall", loc.getWallAnimation(_x, _y), false, false, false);
        image = loc.getImage("specialwall");
        locName = loc.name;
        if(locName.startsWith("v")) System.out.println("village wall");
        x = _x;
        y = _y;
    }
    
    @Override
    public void paint(Graphics2D g, int x, int y){
        g.drawImage(image.getImage(), x, y, null);
        animation.animate(g, x, y);
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        Location loc = Location.locationMap.get(locName);
        image = loc.getImage("specialwall");
        animation = loc.getWallAnimation(x, y);
    }
    
}
