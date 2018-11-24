
package items.builders;

import items.Item;
import items.consumables.Potion;
import items.consumables.Scroll;
import items.equipment.Ring;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.List;
import javax.swing.ImageIcon;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 * 
 * A static convenience class for building Items.
 */
public final class ItemBuilder{
    
    private static final BufferedImage items;
    static{
        ImageIcon ic = new ImageIcon("graphics/items.png");
        items = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = items.getGraphics();
        g.drawImage(ic.getImage(), 0, 0, null);
        g.dispose();
    }
    
    private ItemBuilder(){}
    
    public static Item amulet(){
        //return new Item("amulet", "Description.", new ImageIcon("graphics/amulet.png"));
        throw new UnsupportedOperationException("e");
    }

    public static Item get(String substring){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static List<Ring> getListOfAllRings(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static List<Potion> getListOfAllPotions(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static List<Scroll> getListOfAllScrolls(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static ImageIcon getIcon(int x, int y){
        return new ImageIcon(items.getSubimage(x, y, 16, 16));
    }
    
    public static Item genRandom(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Shades the given pixel.
     * @param pixel The pixel to shade.
     * @return A shaded int array representing a pixel.
     */
    public static int[] shade(int[] pixel){
        for(int n=0;n<3;n++) pixel[n] = pixel[n]<26 ? 0 : pixel[n]-25;
        return pixel;
    }
    
    /**
     * Brightens the given pixel.
     * @param pixel The pixel to shade.
     * @return A brightened int array representing a pixel.
     */
    public static int[] brighten(int[] pixel){
        for(int n=0;n<3;n++) pixel[n] = pixel[n]>229 ? 255 : pixel[n]+25;
        return pixel;
    }
    
    /**
     * Gets the Image at the given coordinates in the item tileset.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The Image.
     */
    public static BufferedImage getImage(int x, int y){
        return ItemBuilder.items.getSubimage(x, y, 16, 16);
    }
    
    /**
     * Gets the colour represented by the given name.
     * @param name The name of the colour.
     * @return The Color.
     */
    public static Color getColor(String name){
        switch(name){
            case "apple green": return Color.decode("#00ff1d");
            case "aquamarine": return Color.decode("#7FFFD4");
            case     "apricot": return Color.decode("#FBCEB1");
            case "lime": return Color.decode("#BFFF00");
            case     "sky blue": return Color.decode("#87CEEB");
            case     "amber": return Color.decode("#FFBF00");
            case     "auburn": return Color.decode("#A52A2A");
            case     "gold": return Color.decode("#FFD700");
            case     "electrum": return Color.decode("#fef3d4");
            case     "silver": return Color.decode("#C0C0C0");
            case "azure": return Color.decode("#007FFF");
            case     "magnolia": return Color.decode("#F4E9D8");
            case     "banana": return Color.decode("#e7f26c");
            case     "orange": return Color.decode("#FF8000");
            case     "blizzard blue": return Color.decode("#50BFE6");
            case     "blueberry": return Color.decode("#4570E6");
            case "cerulean": return Color.decode("#1DACD6");
            case     "periwinckle": return Color.decode("#C3CDE6");
            case     "turquoise": return Color.decode("#6CDAE7");
            case     "rose": return Color.decode("#ED0A3F");
            case     "bubblegum": return Color.decode("#FC80A5");
            case     "burgundy": return Color.decode("#900020");
            case "chocolate": return Color.decode("#AF593E");
            case     "coral": return Color.decode("#FF7F50");
            case     "cyan": return Color.decode("#00FFFF");
            case     "dandelion": return Color.decode("#FED85D");
            case     "chestnut": return Color.decode("#954535");
            case     "tangerine": return Color.decode("#FF9966");
            case "lemon": return Color.decode("#FFFF9F");
            case     "ruby": return Color.decode("#AA4069");
            case     "emerald": return Color.decode("#14A989");
            case     "forest green": return Color.decode("#5FA777");
            case     "ginger": return Color.decode("#f78614");
            case     "tea": return Color.decode("#995006");
            case     "voilet": return Color.decode("#8359A3");
            case "amaranth red": return Color.decode("#E52B50");
            case     "scorpion brown": return Color.decode("#513315");
            case     "amethyst": return Color.decode("#9966CC");
            case     "charcoal": return Color.decode("#36454F");
            case     "asparagus": return Color.decode("#87A96B");
            case "ash": return Color.decode("#919191");
            case     "copper": return Color.decode("#B87333");
            case     "tin": return Color.decode("#b5a14a");
            case     "beige": return Color.decode("#F5F5DC");
            case     "bistre" : return Color.decode("#3D2B1F");
            case     "olive" : return Color.decode("#808000");
            case     "bronze": return Color.decode("#CD7F32");
            case     "sapphire": return Color.decode("#0F52BA");
            case "purple" : return Color.decode("#800080");
            case     "boysenberry": return Color.decode("#910a0a");
            case     "ochre" : return Color.decode("#CC7722");
            case     "maroon" : return Color.decode("#800000");
            case     "lavender" : return Color.decode("#E6E6FA");
            case "lilac" : return Color.decode("#C8A2C8");
            case     "sugar brown": return Color.decode("#aa5500");
            case     "coffee": return Color.decode("#6F4E37");
            case     "scarlet" : return Color.decode("#FF2400");
            case     "crimson" : return Color.decode("#FF003F");
            case     "salmon" : return Color.decode("#FA8072");
            case "metallic": return Color.decode("#bbbbbb");
            case     "mint" : return Color.decode("#3EB489");
            case     "saffron" : return Color.decode("#F4C430");
            case     "eggplant": return Color.decode("#614051");
            case     "firebrick": return Color.decode("#ff5400");
            case     "flame": return Color.decode("#f84400");
            case     "white wine": return Color.decode("#dae8a9");
            case     "red" : return Color.decode("#ff0000");
            case "red mogle wood": return Color.decode("#872f10");
            case "hurian titan wood": return Color.decode("#eddbd5");
            case "hurian goddess wood": return Color.decode("#edd5e3");
            case "pinkheart wood": return Color.decode("#edb2af");
            case "spireling wood": return Color.decode("#602d00");
            case "spickle wood": return Color.decode("#603a19");
            case "master mogle wood": return Color.decode("#9e734d");
            case "schmetterhaus wood": return Color.decode("#639b41");
            case "pingle wood": return Color.decode("#9b7741");
            case "pongle wood": return Color.decode("#6d593a");
            case "callop wood": return Color.decode("#a2adaa");
            case "pesous wood": return Color.decode("#490b40");
            case "shraub wood": return Color.decode("#110c00");
            case "hulous wood": return Color.decode("#7a4d09");
            case "albino mori wood": return Color.decode("#ede4aa");
            case "thickbranch wood": return Color.decode("#6b4a00");
            case "roachwood": return Color.decode("#556b00");
            case "crying brown magmatic wood": return Color.decode("#5b0606");
            case "white magmatic wood": return Color.decode("#ffffff");
            default: throw new IllegalStateException("Illegal color: " + name);
        }
    }
    
    /**
     * Replaces the given regex on the given image with the given colour.
     * @param img The image.
     * @param replace The replacement colour.
     * @param regex The regex colour.
     * @return The altered image.
     */
    public static BufferedImage replaceColour(BufferedImage img, Color replace, Color regex){
        WritableRaster raster = img.getRaster();
        int[] preplace = new int[]{replace.getRed(), replace.getGreen(), replace.getBlue()};
        int[] pregex = new int[]{regex.getRed(), regex.getGreen(), regex.getBlue()};
        int[] pixel = new int[3];
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                pixel = raster.getPixel(x, y, pixel);
                if(Utils.pixelColourEquals(pixel, pregex)) raster.setPixel(x, y, preplace);
            }
        }
        return img;
    }
    
}
