/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creatures;

import animation.Animation;
import creatureLogic.Attributes;

/**
 *
 * @author Adam Whittaker
 */
public class Hero extends Creature{
    
    public int exp = 0;
    
    public Hero(Attributes atb, Animation an){
        super("Hero", "UNWRITTEN", atb, an);
    }
    
}
