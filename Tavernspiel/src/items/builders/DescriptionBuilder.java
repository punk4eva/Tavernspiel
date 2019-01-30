
package items.builders;

import java.io.Serializable;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class helps to procedurally generate descriptions for things.
 */
public abstract class DescriptionBuilder implements Serializable{
    
    private static final long serialVersionUID = 78321323455L;
    
    public static final String[] rune = {"strange", "mysterious", "wierd", 
        "curious", "enigmatic", "perplexing", "magical", "mystical", "arcane"};
    public static final String[] color = {"apple green", "aquamarine", "apricot",
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
    public static final String[] colorMod = {"dark ", "bright ", "clear ", "",
        "crystal clear ", "dull ", "vibrant ", "glowing ", "murderous ", "curious "};
    public static final String[] temp = {" warm", " cold", " hot", " lukewarm",
        "n ice cold", " chilly", "n icy", " bitterly cold", " torrid",
        " blazing hot"};
    public static final String[] wood = {"red mogle wood", "hurian titan wood", 
        "hurian goddess wood", "pinkheart wood", "spireling wood", 
        "spickle wood", "master mogle wood", "schmetterhaus wood", 
        "pingle wood", "pongle wood", "callop wood", "pesous wood",
        "shraub wood", "hulous wood", "albino mori wood", "thickbranch wood",
        "roachwood", "white magmatic wood", "crying brown magmatic wood"};
    public static final String[] shapeMod = {"", " thin", " thick", " wide", " narrow"};
    public static final String[] shape = {"star-shaped", "conical", "spherical",
        "cubic", "pyramidal", "heart shaped", "skull shaped", "triangular",
        "circular"};
    public static final String[] container = {"flask", "bottle", "vial", "jar", "tube"};
    public static final String[] stopper = {"cork", "rubber", "wooden", "glass"};
    public static final String[] texture = {"FRAGMENT_PLACEHOLDER", "FLAKE_PLACEHOLDER", "is frothy", "is bubbly", "is gelatinous",
        "is thick", "is effervescent", "is creamy"};
    public static final String[] smellLike = {"perfume", "rotten eggs", "freshly cut grass", "burnt plastic", "ash", "a corpse", "some exotic plant",
        "some eccentric plant", "petrichor"};
    public static final String[] food = {"chocolate", "a strawberry", "an orange",
        "a squid", "the summer", "the winter", "valentine's chocolate", "fire",
        "wine", "chicken"};
    public static final String[] taste = {"FOOD_PLACEHOLDER", "spicy", "bitter", 
        "sweet", "divine", "vile", "foul", "impure", "horrible", "ok",
        "wonderful", "sour", "stale", "bland"};
    public static final String[] smell = {"WOOD_PLACEHOLDER", "SLIKE_PLACEHOLDER", 
        "FOOD_PLACEHOLDER", "sweet", "aromatic", "refreshing", "pungent", "fetid",
        "unpleasant", "malodorous", "funky", "funny", "musty", "rancid", "old",
        "fragrant", "foul", "healthy", "unhealthy", "putrid"};
    public static final String[] viscosity = {"viscous", "runny", "thick", "syrupy",
        "slimy", "gooey", "watery", "thin"};
    public static final String[] decoration = {"The container is mildly cracked.",
        "The container has lots of cracks.", "The stopper is in the shape of a skull.", "The stopper is in the shape of a heart.",
        "There is a label stuck on one side.",
        "There are strange engravings on the container."};
    public static final String[] appearance = {"hideous", "beautiful", "bleak", "pathetic", "wonderful", "dumb", "cute", "curious", "dreamy",
        "radiant", "dazzling", "mischievous"};

    public static String word(String[] ary){
        return ary[Distribution.r.nextInt(ary.length)];
    }

    protected static String[] textureWord(){
        int n = Distribution.r.nextInt(texture.length);
        if(n==0){
            String word = word(color);
            return new String[]{"contains " + word(colorMod) + word + " coloured" + word(shapeMod) + " "
            + word(shape) + " fragments", word};
        }else if(n==1){
            String word = word(color);
            return new String[]{"contains " + word(colorMod) + word + " coloured" + word(shapeMod) + " "
            + word(shape) + " flakes", word};
        }
        return new String[]{texture[n]};
    }

    protected static String smellWord(){
        int n = Distribution.r.nextInt(smell.length);
        switch (n){
            case 0: return "like " + word(wood);
            case 1: return "like " + word(smellLike);
            case 2: return "like " + word(food);
        }
        return smell[n];
    }

    protected static String tasteWord(){
        int n = Distribution.r.nextInt(taste.length);
        if(n==0) return "like " + word(food);
        return smell[n];
    }

    protected DescriptionBuilder(){}

}
