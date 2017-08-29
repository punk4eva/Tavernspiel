
package items.consumables;

import creatureLogic.Description;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Random;
import javax.swing.ImageIcon;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class PotionProfile{
    
    protected static BufferedImage items;
    static{
        ImageIcon ic = new ImageIcon("graphics/items.png");
        items = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = items.getGraphics();
        g.drawImage(ic.getImage(), 0, 0, null);
        g.dispose();
    }
    protected BufferedImage image;
    protected String name;
    protected String tasteMessage;
    protected Description description;
    
    private static final HashMap<String, PotionProfile> potMap = new HashMap<>();
    static{
        DescriptionBuilder builder = new DescriptionBuilder();
        potMap.put("Potion in a pyramidal tube", builder.getProfile("Potion in a narrow pyramidal tube", 0, 128));
        potMap.put("Potion in a heart shaped tube", builder.getProfile("Potion in a heart shaped tube", 16, 128));
        potMap.put("Potion in a star-shaped tube", builder.getProfile("Potion in a star-shaped tube", 32, 128));
        potMap.put("Potion in a conical vial", builder.getProfile("Potion in a thick conical vial", 48, 128));
        potMap.put("Potion in a circular vial", builder.getProfile("Potion in a thick circular vial", 64, 128));
        potMap.put("Potion in a skull shaped bottle", builder.getProfile("Potion in a skull shaped bottle", 80, 128));
        potMap.put("Potion in a cubic bottle", builder.getProfile("Potion in a cubic bottle", 96, 128));
        potMap.put("Potion in a spherical jar", builder.getProfile("Potion in a spherical jar", 112, 128));
        potMap.put("Potion in a skull shaped bottle", builder.getProfile("Potion in a skull shaped bottle", 0, 144));
        potMap.put("Potion in a skull shaped bottle", builder.getProfile("Potion in a skull shaped bottle", 16, 144));
        potMap.put("Potion in a skull shaped bottle", builder.getProfile("Potion in a skull shaped bottle", 32, 144));
    }
    
    private static Color getColour(String name){
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
            default: return new Color(255,255,255,0);
        }
    }
    
    private static BufferedImage outfitImage(BufferedImage img, Color liq, Color frag){
        WritableRaster raster = img.getRaster();
        int[] liquid = new int[]{liq.getRed(), liq.getGreen(), liq.getBlue()};
        int[] liquidRegex = new int[]{238,0,156};
        int[] shadedLiquid = shade(liquid);
        int[] shadedLiquidRegex = new int[]{165,0,107};
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                int[] pixel = raster.getPixel(x, y, (int[]) null);
                if(intArrayEquals(pixel, liquidRegex)) raster.setPixel(x, y, liquid);
                else if(intArrayEquals(pixel, shadedLiquidRegex)) raster.setPixel(x, y, shadedLiquid);
            }
        }
        if(frag==null) return img;
        int[] fragment = new int[]{frag.getRed(), frag.getGreen(), frag.getBlue()};
        int[] fragmentRegex = new int[]{238,159,153};
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                if(intArrayEquals(raster.getPixel(x, y, (int[]) null), fragmentRegex)) raster.setPixel(x, y, fragment);
            }
        }
        return img;
    }
    
    public static int[] shade(int[] pixel){
        for(int n=0;n<3;n++) pixel[n] = pixel[n]<26 ? 0 : pixel[n]-25;
        return pixel;
    }
    
    public static boolean intArrayEquals(int[] p, int[] q){
        for(int n=0;n<3;n++) if(p[n]!=q[n]) return false;
        return true;
    }
    
    private static final class DescriptionBuilder{
        private final Random r = new Random();
        private String description = "";
        private final String[] colour = {"apple green", "aquamarine", "apricot",
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
        private final String[] colourMod = {"dark ", "bright ", "clear ", "",
            "crystal clear ", "dull ", "vibrant ", "glowing ", "murderous ", "curious "};
        private final String[] temp = {" warm", " cold", " hot", " lukewarm",
            "n ice cold", " chilly", "n icy", " bitterly cold", " torrid",
            " blazing hot"};
        private final String[] shapeMod = {"", " thin", " thick", " wide", " narrow"};
        private final String[] shape = {"star-shaped", "conical", "spherical",
            "cubic", "pyramidal", "heart shaped", "skull shaped", "triangular",
            "circular"};
        private final String[] container = {"flask", "bottle", "vial", "jar", "tube"};
        private final String[] stopper = {"cork", "rubber", "wooden", "glass"};
        private final String[] texture = {"is frothy", "is bubbly", "is gelatinous",
            "is thick", "is effervescent", "is creamy",
            "contains " + word(colourMod) + word(colour) + " coloured" + word(shapeMod) + " "
            + word(shape) + " fragments",
            "contains " + word(colourMod) + word(colour) + " coloured" + word(shapeMod) + " "
            + word(shape) + " flakes"};
        private final String[] sLike = {"perfume", "rotten eggs", "freshly cut grass", "burnt plastic", "ash", "a corpse", "some exotic plant",
            "some eccentric plant", "petrichor"};
        private final String[] food = {"chocolate", "a strawberry", "an orange",
            "a squid", "the summer", "the winter", "valentine's chocolate", "fire",
            "wine", "chicken"};
        private final String[] taste = {"spicy", "bitter", "sweet", "divine",
            "like " + word(food), "vile", "foul", "impure", "horrible", "ok",
            "wonderful", "sour", "stale", "bland"};
        private final String[] smell = {"putrid", "like " + word(sLike), "like "
            + word(food), "sweet", "aromatic", "refreshing", "pungent", "fetid",
            "unpleasant", "malodorous", "funky", "funny", "musty", "rancid", "old",
            "fragrant", "foul", "healthy", "unhealthy"};
        private final String[] viscosity = {"viscous", "runny", "thick", "syrupy",
            "slimy", "gooey", "watery", "thin"};
        private final String[] decoration = {"The container is mildly cracked.",
            "The container has lots of cracks.", "The stopper is in the shape of a skull.", "The stopper is in the shape of a heart.",
            "There is a label stuck on one side.",
            "There are strange engravings on the container."};
        
        String word(String[] ary){
            return ary[r.nextInt(ary.length)];
        }
        
        String[] textureWord(){
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
        
        void temp(){
            description += "The potion is a" + word(temp) + ", ";
        }

        String colour(){
            String ret = word(colour);
            description += word(colourMod) + ret + " coloured liquid.\n";
            return ret;
        }

        String texture(){
            String[] str = textureWord();
            description += "It " + str[0];
            if(str.length==2) return str[1];
            return null;
        }

        void smell(){
            description += " and smells " + word(smell) + ".\n";
        }

        void viscosity(){
            description += "The liquid itself is a " + word(viscosity)
                    + " substance.\n";
        }

        void shapeContainer(String name){
            description += "A" + word(shapeMod) + " ";
            String[] ary = name.split(" ");
            for(int n=1;n<ary.length;n++) description += ary[n] + " "; 
            description += word(container) + " with a " + word(colour)
                    + " coloured " + word(stopper) + " stopper holds the potion.\n";
        }

        void decoration(){
            description += word(decoration) + "\n";
        }

        String taste(){
            return "The potion tasted " + word(taste) + ".";
        }
        
        PotionProfile getProfile(String name, int x, int y){
            temp();
            String colour = colour();
            String texture = texture();
            smell();
            viscosity();
            shapeContainer(name);
            decoration();
            return new PotionProfile(name, new Description("potions", description), taste(), outfitImage(getImage(x, y), getColour(colour), texture==null ? null : getColour(texture)));   
        }
        
    }
    
    PotionProfile(String nm, Description desc, String taste, BufferedImage im){
        name = nm;
        tasteMessage = taste;
        image = im;
        description = desc;
    }
    
    protected static BufferedImage getImage(int x, int y){
        return items.getSubimage(x, y, 16, 16);
    }
    
}
