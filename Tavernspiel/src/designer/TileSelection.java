
package designer;

import items.ItemProfile;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import level.Location;
import logic.Distribution;
import tiles.Door;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class TileSelection implements Serializable, Cloneable{
    
    private static final long serialVersionUID = -1384476066;
    
    final Distribution distrib;
    final String[] tiles;
    final boolean[] treadible;
    final boolean[] flammable;
    boolean boundary = false;
    final Color color;
    
    public TileSelection(int[] chances, String[] t, Color col){
        distrib = new Distribution(chances);
        tiles = t;
        color = col;
        treadible = new boolean[t.length];
        flammable = new boolean[t.length];
        initialize();
    }
    
    public TileSelection(String tile, String col){
        distrib = new Distribution(new int[]{1});
        tiles = new String[]{tile};
        color = ItemProfile.getColour(col);
        treadible = new boolean[1];
        flammable = new boolean[1];
        initialize();
    }
    
    public TileSelection(Distribution dist, String[] t, Color col, boolean b){
        distrib = dist;
        tiles = t;
        color = col;
        boundary = b;
        treadible = new boolean[t.length];
        flammable = new boolean[t.length];
        initialize();
    }
    
    public Tile getTile(Location loc){
        int n = (int)distrib.next();
        return new Tile(tiles[n], loc, treadible[n], flammable[n]);
    }
    
    public void paint(Graphics g, int x, int y){
        g.setColor(color);
        g.fillRect(x, y, 16, 16);
        if(boundary){
            g.setColor(Color.black);
            g.fillOval(x+4, y+4, 8, 8);
        }
    }
    
    static TileSelection parse(String command){
        String tls[] = command.split("|");
        String[] tiles = new String[tls.length-1];
        int chances[] = new int[tls.length-1];
        for(int n=0;n<tls.length-1;n++){
            String[] p = tls[n].split(" ");
            tiles[n] = p[0];
            chances[n] = Integer.parseInt(p[1]);
        }
        return new TileSelection(chances, tiles, ItemProfile.getColour(tls[tls.length-1]));
    }
    
    static TileSelection select(String str){
        switch(str){
            case "wall": return wall;
            case "floor": return floor;
            case "door": return door;
            default:
                System.err.println("\"" + str + "\" not recognized.");
                return null;
        }
    }
    
    private void initialize(){
        for(int n=0;n<tiles.length;n++){
            if(!tiles[n].equals("wall")&&!tiles[n].equals("specialwall")&&
                    !tiles[n].equals("bookcase")&&!tiles[n].equals("barricade"))
                treadible[n] = true;
            if(tiles[n].equals("lowgrass")||tiles[n].equals("highgrass")||
                    tiles[n].equals("barricade")||tiles[n].equals("bookcase")||
                    tiles[n].equals("door")) flammable[n] = true;
        }
    }
    
    @Override
    public TileSelection clone(){
        return new TileSelection(distrib, tiles, color, boundary);
    }
    
    final static TileSelection wall = new TileSelection(new int[]{1, 9}, new String[]{"specialwall", "wall"}, Color.DARK_GRAY);
    final static TileSelection floor = new TileSelection(new int[]{1, 9}, new String[]{"decofloor", "floor"}, Color.GRAY);
    final static TileSelection door = new TileSelection(new int[]{1, 9}, null, new Color(80, 80, 0)){
        @Override
        public Door getTile(Location loc){
            boolean b = Distribution.chance(1, 4);
            return new Door(loc, false, b);
        }
        
        @Override
        public TileSelection clone(){
            return this;
        }
        
    };
    
}
