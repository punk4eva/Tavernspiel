
package items;

import creatureLogic.Description;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 * 
 * A class that packages and builds the essential parts of an Item.
 * Not for use on non-subclassed items.
 */
public abstract class ItemProfile{
    
    protected Supplier<ImageIcon> loader;
    protected String name;
    protected Description description;
    
    protected ItemProfile(String nm, Supplier<ImageIcon> lo, Description desc){
        name = nm;
        loader = lo;
        description = desc;
    }
    
    protected static abstract class DescriptionBuilder{
        protected final Random r = new Random();
        protected String description = "";
        public final String[] colour = {"apple green", "aquamarine", "apricot",
            "lime", "sky blue", "amber", "auburn", "gold", "electrum", "silver",
            "azure", "magnolia", "banana", "orange", "blizzard blue", "blueberry",
            "cerulean", "periwinckle", "turquoise", "rose", "bubblegum", "burgundy",
            "chocolate", "coral", "cyan", "dandelion", "chestnut", "tangerine",
            "lemon", "ruby", "emerald", "forest green", "ginger", "tea", "voilet", 
            "amaranth red", "scorpion brown", "amethyst", "charcoal", "asparagus", 
            "ash", "copper", "tin", "beige", "bistre", "olive", "bronze", "sapphire",
            "purple", "boysenberry", "ochre", "maroon", "lavender", 
            "lilac", "sugar brown", "coffee", "scarlet", "crimson", "salmon", 
            "metallic", "mint", "saffron", "eggplant", "firebrick", "flame", "white wine"};
        public final String[] colourMod = {"dark ", "bright ", "clear ", "",
            "crystal clear ", "dull ", "vibrant ", "glowing ", "murderous ", "curious "};
        public final String[] temp = {" warm", " cold", " hot", " lukewarm",
            "n ice cold", " chilly", "n icy", " bitterly cold", " torrid",
            " blazing hot"};
        public final String[] shapeMod = {"", " thin", " thick", " wide", " narrow"};
        public final String[] shape = {"star-shaped", "conical", "spherical",
            "cubic", "pyramidal", "heart shaped", "skull shaped", "triangular",
            "circular"};
        public final String[] container = {"flask", "bottle", "vial", "jar", "tube"};
        public final String[] stopper = {"cork", "rubber", "wooden", "glass"};
        public final String[] texture = {"is frothy", "is bubbly", "is gelatinous",
            "is thick", "is effervescent", "is creamy",
            "contains " + word(colourMod) + word(colour) + " coloured" + word(shapeMod) + " "
            + word(shape) + " fragments",
            "contains " + word(colourMod) + word(colour) + " coloured" + word(shapeMod) + " "
            + word(shape) + " flakes"};
        public final String[] sLike = {"perfume", "rotten eggs", "freshly cut grass", "burnt plastic", "ash", "a corpse", "some exotic plant",
            "some eccentric plant", "petrichor"};
        public final String[] food = {"chocolate", "a strawberry", "an orange",
            "a squid", "the summer", "the winter", "valentine's chocolate", "fire",
            "wine", "chicken"};
        public final String[] taste = {"spicy", "bitter", "sweet", "divine",
            "like " + word(food), "vile", "foul", "impure", "horrible", "ok",
            "wonderful", "sour", "stale", "bland"};
        public final String[] smell = {"putrid", "like " + word(sLike), "like "
            + word(food), "sweet", "aromatic", "refreshing", "pungent", "fetid",
            "unpleasant", "malodorous", "funky", "funny", "musty", "rancid", "old",
            "fragrant", "foul", "healthy", "unhealthy"};
        public final String[] viscosity = {"viscous", "runny", "thick", "syrupy",
            "slimy", "gooey", "watery", "thin"};
        public final String[] decoration = {"The container is mildly cracked.",
            "The container has lots of cracks.", "The stopper is in the shape of a skull.", "The stopper is in the shape of a heart.",
            "There is a label stuck on one side.",
            "There are strange engravings on the container."};
        
        protected String word(String[] ary){
            return ary[r.nextInt(ary.length)];
        }
        
        protected String[] textureWord(){
            int n = r.nextInt(texture.length);
            if(n==6){
                String word = word(colour);
                return new String[]{"contains " + word(colourMod) + word + " coloured" + word(shapeMod) + " "
                + word(shape) + " fragments", word};
            }else if(n==7){
                String word = word(colour);
                return new String[]{"contains " + word(colourMod) + word + " coloured" + word(shapeMod) + " "
                + word(shape) + " flakes", word};
            }
            return new String[]{texture[n]};
        }
        
        protected DescriptionBuilder(){}
        
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
    public static Color getColour(String name){
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
            default: return new Color(255,255,255,255);
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
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                int[] pixel = raster.getPixel(x, y, (int[]) null);
                if(Utils.pixelColourEquals(pixel, pregex)) raster.setPixel(x, y, preplace);
            }
        }
        return img;
    }
    
    /**
     * Gets the name of this Potion.
     * @return The name.
     */
    public String getName(){
        return name;
    }
    /**
     * Gets the loader of this Potion.
     * @return The image.
     */
    public Supplier<ImageIcon> getSupplier(){
        return loader;
    }
    /**
     * Gets the description of this Potion.
     * @return The description.
     */
    public Description getDescription(){
        return description;
    }
    
}
