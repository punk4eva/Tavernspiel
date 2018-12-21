
package designer;

import gui.Game;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.function.Function;
import javax.swing.ImageIcon;
import level.Location;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 * 
 * Parses an image into an AreaTemplate.
 */
public abstract class ImageParser{
    
    private static final HashMap<String, Function<Location, TileSelection>> colorMap = new HashMap<>();
    static{
        colorMap.put("255,255,255", loc -> null); //void
        colorMap.put("40,40,40", loc -> new TileSelection("floor", true, false, true));//floor
        colorMap.put("0,40,0", loc -> TileSelection.grass(loc, false));//lowgrass
        colorMap.put("20,20,80", loc -> new TileSelection("emptywell", true, false, true));//emptywell
        colorMap.put("120,120,120", loc -> new TileSelection("wall", false, false, false));//wall
        colorMap.put("70,50,0", loc -> TileSelection.door(loc));
        colorMap.put("50,140,0", loc -> TileSelection.openDoor(loc));
        colorMap.put("170,70,30", loc -> TileSelection.depthEntrance(loc));
        colorMap.put("170,30,70", loc -> TileSelection.depthExit(loc));
        colorMap.put("50,30,30", loc -> new TileSelection("embers", true, false, true));
        colorMap.put("140,50,0", loc -> TileSelection.lockedDoor(loc));
        colorMap.put("200,170,190", loc -> new TileSelection("pedestal", true, false, true));
        colorMap.put("150,120,120", loc -> new TileSelection("specialwall", false, false, false));
        colorMap.put("110,90,40", loc -> new TileSelection("barricade", false, true, false));
        colorMap.put("80,40,40", loc -> new TileSelection("specialfloor", true, false, true));
        colorMap.put("0,80,0", loc -> TileSelection.grass(loc, true));
        colorMap.put("0,255,0", loc -> TileSelection.trap(loc, "green"));
        colorMap.put("255,120,0", loc -> TileSelection.trap(loc, "orange"));
        colorMap.put("255,255,0", loc -> TileSelection.trap(loc, "yellow"));
        colorMap.put("40,80,40", loc -> new TileSelection("decofloor", true, false, true));
        colorMap.put("170,60,140", loc -> TileSelection.depthExit(loc, true));
        colorMap.put("170,120,140", loc -> TileSelection.depthExit(loc, false));
        colorMap.put("255,0,255", loc -> TileSelection.trap(loc, "purple"));
        //@Unfinished
        //colorMap.put("sign", loc -> );
        colorMap.put("255,0,0", loc -> TileSelection.trap(loc, "red"));
        colorMap.put("0,0,255", loc -> TileSelection.trap(loc, "blue"));
        //@Unfinished
        //colorMap.put("well", 28);
        colorMap.put("120,150,120", loc -> new TileSelection("statue", false, false, true));
        colorMap.put("150,150,120", loc -> new TileSelection("specialstatue", false, false, true));
        colorMap.put("255,255,100", loc -> TileSelection.trap(loc, "bear"));
        colorMap.put("200,200,200", loc -> TileSelection.trap(loc, "silver"));
        colorMap.put("150,90,40", loc -> new TileSelection("bookshelf", false, true, false));
        colorMap.put("155,95,45", loc -> new TileSelection("table", true, true, true));
        colorMap.put("70,80,90", loc -> new TileSelection("sign", false, false, false));
        colorMap.put("255,200,0", loc -> TileSelection.alchemyPot(loc));
        colorMap.put("200,100,100", loc -> TileSelection.bed("bed", loc, 0, 0));
        colorMap.put("200,150,150", loc -> TileSelection.bed("bed", loc, 1, 0));
        colorMap.put("50,100,100", loc -> TileSelection.bed("sarcophagus", loc, 0, 0));
        colorMap.put("50,150,150", loc -> TileSelection.bed("sarcophagus", loc, 1, 0));
        //@Unfinished
        //colorMap.put("floorcutoff", loc -> TileSelection.chasm(loc, "floor"));
        //colorMap.put("specialfloorcutoff", loc -> TileSelection.chasm(loc, "specialfloor"));
        //colorMap.put("wallcutoff", loc -> TileSelection.chasm(loc, "wall"));
        //colorMap.put("brokencutoff", loc -> TileSelection.chasm(loc, "broken"));
        colorMap.put("45,45,45", loc -> TileSelection.floor(loc));
        colorMap.put("125,125,125", loc -> TileSelection.wall(loc));
        colorMap.put("0,60,0", loc -> TileSelection.grass(loc, null));
    }
    
    private static AreaTemplate parseImage(BufferedImage bi, Location loc){
        WritableRaster raster = bi.getRaster();
        int w = bi.getWidth(), h = bi.getHeight();
        TileSelection[][] ret = new TileSelection[h][w];
        int[] pixel = new int[4];
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                pixel = raster.getPixel(x, y, pixel);
                try{
                    ret[y][x] = colorMap.get(getString(pixel)).apply(loc);
                }catch(NullPointerException e){
                    throw new IllegalStateException("Illegal color: " + 
                            getString(pixel));
                }
            }
        }
        return new AreaTemplate(ret, loc);
    }
    
    private static String getString(int[] p){
        return p[0] + "," + p[1] + "," + p[2];
    }
    
    public static void main(String... args){
        Game game = new Game();
        System.err.println("Image parsing mode");
        String filepath = "filetesting/interiorTestImage.png", savepath = "preload/interiorTest.template";
        BufferedImage bi = ImageUtils.convertToBuffered(new ImageIcon(filepath));
        AreaTemplate t = parseImage(bi, Location.VILLAGE1_LOCATION);
        t.serialize(savepath);
    }
    
}
