/*
 * Copyright 2018 steelr.
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
package pathfinding;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author steelr
 */
public class NavigationMesh implements Serializable{
    
    private static final long serialVersionUID = 462317L;
    
    protected HashMap<Waypoint, HashMap<Waypoint, Path>> mesh = new HashMap<>();
    
    /**
     *  Creates a new instance.
     * @param graph Requires Searcher, waypoints and map.
     */
    public NavigationMesh(Graph graph){
        for(int i=0;i<graph.waypoints.length;i++){
            //graph.searcher.initializeEndpoint(end);
            for(int j=0;j<graph.waypoints.length;j++){
                if(i==j) continue;
                
            }
        }
    }
    
}
