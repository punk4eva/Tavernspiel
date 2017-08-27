
package logic;

import animation.GameObjectAnimator;
import buffs.Buff;
import creatureLogic.Description;
import gui.Handler;
import java.awt.Graphics;
import java.util.LinkedList;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class Gas extends GameObject{
    
    public LinkedList<Buff> buffs = new LinkedList<>();
    public int spreadNumber;
    public int duration = 10;
    private final Handler handler;
    
    public Gas(String n, Description desc, Buff b, GameObjectAnimator a, int spread, Area ac, Handler hand){
        super(n, desc, a, ac, hand);
        handler  = hand;
        buffs.add(b);
        spreadNumber = spread;
    }
    
    public Gas(Gas gas, int nx, int ny){
        super(gas.name, gas.description, gas.animator, gas.area, gas.handler);
        handler = gas.handler;
        buffs = gas.buffs;
        x = nx;
        y = ny;
        spreadNumber = gas.spreadNumber-1;
    }
    
    private void spread(){
        if(spreadNumber==0){
            area.removeObject(this);
            handler.removeObject(this);
            return;
        }
        if(area.map[y-1][x].treadable&&!area.gasPresent(x, y-1)) area.addObject(new Gas(this, x, y-1));
        if(area.map[y+1][x].treadable&&!area.gasPresent(x, y+1)) area.addObject(new Gas(this, x, y+1));
        if(area.map[y][x-1].treadable&&!area.gasPresent(x-1, y)) area.addObject(new Gas(this, x-1, y));
        if(area.map[y][x+1].treadable&&!area.gasPresent(x+1, y)) area.addObject(new Gas(this, x+1, y));
        spreadNumber--;
    }

    @Override
    public void render(Graphics g){
        standardAnimation(g);
    }

    @Override
    public void turn(double delta){
        for(double d=delta+turndelta;d>=1;d--) spread();
        turndelta = (delta+turndelta)%1.0;
    }
    
}
