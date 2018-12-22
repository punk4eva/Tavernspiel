
package designer;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.function.BiFunction;
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
    
    private static final HashMap<String, BiFunction<Location, Integer[], TileSelection>> colorMap = new HashMap<>();
    static{
        colorMap.put("255,255,255", (loc, c) -> null); //void
        colorMap.put("40,40,40", (loc, c) -> new TileSelection("floor", true, false, true));//floor
        colorMap.put("0,40,0", (loc, c) -> TileSelection.grass(false));//lowgrass
        colorMap.put("20,20,80", (loc, c) -> new TileSelection("emptywell", true, false, true));//emptywell
        colorMap.put("120,120,120", (loc, c) -> new TileSelection("wall", false, false, false));//wall
        colorMap.put("70,50,0", (loc, c) -> TileSelection.door());
        colorMap.put("50,140,0", (loc, c) -> TileSelection.openDoor());
        colorMap.put("170,70,30", (loc, c) -> TileSelection.depthEntrance());
        colorMap.put("170,30,70", (loc, c) -> TileSelection.depthExit());
        colorMap.put("50,30,30", (loc, c) -> new TileSelection("embers", true, false, true));
        colorMap.put("140,50,0", (loc, c) -> TileSelection.lockedDoor());
        colorMap.put("200,170,190", (loc, c) -> new TileSelection("pedestal", true, false, true));
        colorMap.put("150,120,120", (loc, c) -> TileSelection.specialWall(c[0], c[1]));
        colorMap.put("110,90,40", (loc, c) -> new TileSelection("barricade", false, true, false));
        colorMap.put("80,40,40", (loc, c) -> new TileSelection("specialfloor", true, false, true));
        colorMap.put("0,80,0", (loc, c) -> TileSelection.grass(true));
        colorMap.put("0,255,0", (loc, c) -> TileSelection.trap("green"));
        colorMap.put("255,120,0", (loc, c) -> TileSelection.trap("orange"));
        colorMap.put("255,255,0", (loc, c) -> TileSelection.trap("yellow"));
        colorMap.put("40,80,40", (loc, c) -> new TileSelection("decofloor", true, false, true));
        colorMap.put("170,60,140", (loc, c) -> TileSelection.depthExit(true));
        colorMap.put("170,120,140", (loc, c) -> TileSelection.depthExit(false));
        colorMap.put("255,0,255", (loc, c) -> TileSelection.trap("purple"));
        //@Unfinished
        //colorMap.put("sign", loc -> );
        colorMap.put("255,0,0", (loc, c) -> TileSelection.trap("red"));
        colorMap.put("0,0,255", (loc, c) -> TileSelection.trap("blue"));
        //@Unfinished
        //colorMap.put("well", 28);
        colorMap.put("120,150,120", (loc, c) -> new TileSelection("statue", false, false, true));
        colorMap.put("150,150,120", (loc, c) -> new TileSelection("specialstatue", false, false, true));
        colorMap.put("255,255,100", (loc, c) -> TileSelection.trap("bear"));
        colorMap.put("200,200,200", (loc, c) -> TileSelection.trap("silver"));
        colorMap.put("150,90,40", (loc, c) -> new TileSelection("bookshelf", false, true, false));
        colorMap.put("155,95,45", (loc, c) -> new TileSelection("table", true, true, true));
        colorMap.put("70,80,90", (loc, c) -> new TileSelection("sign", false, false, false));
        colorMap.put("255,200,0", (loc, c) -> TileSelection.alchemyPot());
        colorMap.put("200,100,100", (loc, c) -> TileSelection.bed("bed", 0, 0));
        colorMap.put("200,150,150", (loc, c) -> TileSelection.bed("bed", 1, 0));
        colorMap.put("50,100,100", (loc, c) -> TileSelection.bed("sarcophagus", 0, 0));
        colorMap.put("50,150,150", (loc, c) -> TileSelection.bed("sarcophagus", 1, 0));
        //@Unfinished
        //colorMap.put("floorcutoff", loc -> TileSelection.chasm(loc, "floor"));
        //colorMap.put("specialfloorcutoff", loc -> TileSelection.chasm(loc, "specialfloor"));
        //colorMap.put("wallcutoff", loc -> TileSelection.chasm(loc, "wall"));
        //colorMap.put("brokencutoff", loc -> TileSelection.chasm(loc, "broken"));
        colorMap.put("45,45,45", (loc, c) -> TileSelection.floor());
        colorMap.put("125,125,125", (loc, c) -> TileSelection.wall(c[0], c[1]));
        colorMap.put("0,60,0", (loc, c) -> TileSelection.grass(null));
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
                    ret[y][x] = colorMap.get(getString(pixel)).apply(loc, new Integer[]{x, y});
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
        System.err.println("Image parsing mode");
        String filepath = "filetesting/tomb.png", savepath = "preload/tomb.template";
        BufferedImage bi = ImageUtils.convertToBuffered(new ImageIcon(filepath));
        AreaTemplate t = parseImage(bi, Location.INDOOR_CAVES_LOCATION);
        t.serialize(savepath);
    }
    
}
