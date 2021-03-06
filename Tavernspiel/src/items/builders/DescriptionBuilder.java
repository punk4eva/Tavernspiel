
package items.builders;

import buffs.Injury;
import static buffs.Injury.HealingInjury.injuryText;
import creatureLogic.Description;
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
    public static final String[] redCol = {"black", "ruby", "amaranth red", "rusty", "maroon", "cherry", "scarlet", "crimson", "firy"};
    public static final String[] colorMod = {"dark ", "bright ", "clear ", "",
        "crystal clear ", "dull ", "vibrant ", "glowing ", "murderous ", "curious "};
    public static final String[] temp = {" warm", " cold", " hot", " lukewarm",
        "n ice cold", " chilly", "n icy", " bitterly cold", " torrid",
        " blazing hot"};
    public static final String[] tempB = {"warm", "cold", "hot", "lukewarm",
        "ice cold", "chilly", "icy", "bitterly cold", "torrid", "blazing hot"};
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
    public static final String[] textureB = {"frothy", "bubbly", "gelatinous",
        "thick", "creamy"};
    public static final String[] smellLike = {"perfume", "rotten eggs", "freshly cut grass", "burnt plastic", "ash", "a corpse", "some exotic plant",
        "some eccentric plant", "petrichor"};
    public static final String[] food = {"chocolate", "a strawberry", "an orange",
        "a squid", "the summer", "the winter", "valentine's chocolate", "fire",
        "wine", "chicken", "dirt"};
    public static final String[] taste = {"FOOD_PLACEHOLDER", "spicy", "bitter", 
        "sweet", "divine", "vile", "foul", "impure", "horrible", "ok",
        "wonderful", "sour", "stale", "bland"};
    public static final String[] smell = {"WOOD_PLACEHOLDER", "SLIKE_PLACEHOLDER", 
        "FOOD_PLACEHOLDER", "sweet", "aromatic", "refreshing", "pungent", "fetid",
        "unpleasant", "malodorous", "funky", "funny", "musty", "rancid", "old",
        "fragrant", "foul", "healthy", "unhealthy", "putrid", "hideous"};
    public static final String[] viscosity = {"viscous", "runny", "thick", "syrupy",
        "slimy", "gooey", "watery", "thin"};
    public static final String[] decoration = {"The container is mildly cracked.",
        "The container has lots of cracks.", "The stopper is in the shape of a skull.", "The stopper is in the shape of a heart.",
        "There is a label stuck on one side.",
        "There are strange engravings on the container."};
    public static final String[] appearance = {"hideous", "beautiful", "bleak", "pathetic", "wonderful", "dumb", "cute", "curious", "dreamy",
        "radiant", "dazzling", "mischievous"};
    public static final String[] text = {"sacred text", "spell", "mantra", "curse", "text", "charm", "conjuration", "hex"};

    public static String word(String[] ary){
        return ary[Distribution.R.nextInt(ary.length)];
    }
    
    public static String bodyPartWord(){
        return Injury.getRandomBodyPart().agnomen;
    }

    protected static String[] textureWord(){
        int n = Distribution.R.nextInt(texture.length);
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
        int n = Distribution.R.nextInt(smell.length);
        switch (n){
            case 0: return "like " + word(wood);
            case 1: return "like " + word(smellLike);
            case 2: return "like " + word(food);
        }
        return smell[n];
    }

    protected static String tasteWord(){
        int n = Distribution.R.nextInt(taste.length);
        if(n==0) return "like " + word(food);
        return smell[n];
    }
    
    protected static String replace(String str){
        for(int n=0;n<str.length()-3;n++){
            if(str.charAt(n)=='*' && str.charAt(n+3)=='*'){
                String rep = null;
                switch (str.substring(n+1, n+3)){
                    case "te": rep = word(tempB);
                        break;
                    case "sh": rep = word(shape);
                        break;
                    case "sm": rep = smellWord();
                        break;
                    case "tx": rep = word(textureB);
                        break;
                    case "cm": rep = word(colorMod);
                        break;
                    case "cr": rep = word(redCol);
                        break;
                    case "ap": rep = word(appearance);
                        break;
                }
                if(rep!=null){
                    str = str.substring(0, n) + rep + str.substring(n+4);
                    n += rep.length()-1;
                }
            }
        }
        return str;
    }
    
    public static String getInjuryDescription(String[] ary, int lvl){
        return replace(word(ary)) + " " + injuryText(lvl);
    }

    public static Description getScrollDescription(String r){
        return new Description("scrolls", "The parchment is titled with the " + word(rune) + " rune " + r + " and a " + word(appearance) + " " + word(text) + " has been inscribed on the magic paper." + 
                "You cannot hope to figure out what " + word(appearance) + " effect reading this scroll will have.");
    }
    
    public static String getPotionExtra(){
        return "It is unknown what " + word(rune) + " effect this potion is supposed to have when drunk, or what "
                + word(appearance) + " after-effects any impurities will cause.";
    }

    protected DescriptionBuilder(){}

}
