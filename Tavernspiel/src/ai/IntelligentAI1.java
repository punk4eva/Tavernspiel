
package ai;

/**
 *
 * @author Adam Whittaker
 */
public class IntelligentAI1 extends AITemplate{

    @Override
    public IntelligentAI1 getFromFileString(String filestring){
        IntelligentAI1 ai = new IntelligentAI1();
        ai.type = EnType.valueOf(filestring.substring(0, filestring.indexOf("</ty>")));
        ai.intelligence = Integer.parseInt(filestring.substring(filestring.indexOf("</ty>")+5, filestring.indexOf("<hex>")));
        ai.magic = new MagicHexagon().getFromFileString(filestring.substring(filestring.indexOf("<hex>"), filestring.indexOf("</hex>")+6));
        String[] coords = filestring.substring(filestring.indexOf("</hex>")+6).split(":");
        ai.destinationx = Integer.parseInt(coords[0]);
        ai.destinationy = Integer.parseInt(coords[1]);
        return ai;
    }
    
}