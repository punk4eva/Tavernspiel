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
package level;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 */
public abstract class RoomStructure extends Area{
    
    private List<Room> rooms = new LinkedList<>();
    
    public RoomStructure(Dimension dim, Location loc){
        super(dim, loc);
    }
    
    public abstract void generate();
    
    public class Hallway extends RoomStructure{

        public Hallway(Dimension dim, Location loc){
            super(dim, loc);
        }

        @Override
        public void generate(){
            
        }
        
    }
    
    public class Cave extends RoomStructure{

        public Cave(Dimension dim, Location loc){
            super(dim, loc);
        }

        @Override
        public void generate(){
        }
        
    }
    
    public class Complex extends RoomStructure{

        public Complex(Dimension dim, Location loc){
            super(dim, loc);
        }

        @Override
        public void generate(){
        }
        
    }
    
}
