
package designer;

import blob.ParticleEffect;
import blob.ParticleEffect.Particle;
import blob.particles.PowerParticle;
import fileLogic.FileHandler;
import gui.MainClass;
import static gui.MainClass.HEIGHT;
import static gui.MainClass.WIDTH;
import gui.Window;
import items.ItemProfile;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Adam Whittaker
 * 
 * A class meant to design particle effects.
 */
public class ParticleDesigner extends MainClass implements ActionListener{
    
    
    CommandLibrary command = new CommandLibrary();
    ParticleEffect effect;
    boolean capOverInt = false;
    private final Timer timer;
    private final HashMap<String, Particle> particles = new HashMap<>();
    {
        particles.put("first", new PowerParticle(Color.red, new Rectangle(8, 8), 5, 0.5));
        timer = new Timer(5, this);
    }
    
    /**
     * Creates an instance.
     * Type "/load" at the first question to load an existing project from a 
     * file.
     */
    public ParticleDesigner(){
        Rectangle[] rect = getBounding();
        if(rect==null){
            System.out.println("Filepath...");
            effect = (ParticleEffect) FileHandler.deserialize(new Scanner(System.in).nextLine());
        }else{
            effect = new ParticleEffect(4, 10, rect[0], rect[1], particles.get("first"));
        }
        window = new Window(WIDTH, HEIGHT, "Particle Designer", this);
    }
    
    private static Rectangle[] getBounding(){
        System.out.println("Define the startField...");
        String next = new Scanner(System.in).nextLine();
        if(next.startsWith("/load")) return null;
        String[] p = next.split(", ");
        Rectangle rect = new Rectangle(Integer.parseInt(p[0]), 
                Integer.parseInt(p[1]),
                Integer.parseInt(p[2]), 
                Integer.parseInt(p[3]));
        System.out.println("Define the stopField...");
        next = new Scanner(System.in).nextLine();
        p = next.split(", ");
        return new Rectangle[]{rect, new Rectangle(Integer.parseInt(p[0]), 
                Integer.parseInt(p[1]),
                Integer.parseInt(p[2]), 
                Integer.parseInt(p[3]))};
    }

    /**
     * Paints the graphics every 5 milliseconds.
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(4);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        effect.paint(g);
        g.dispose();
        bs.show();
    }
    
    /**
     * A text-based user interface.
     */
    class CommandLibrary{
        
        String commands[] = new String[]{"/delete ", "/particle ",
                "/capacity", "/intensity", "/save ", "/exit", "/start ", "/stop "};
        Scanner scan = new Scanner(System.in);
        
        /**
         * /delete <particleName> 
         *      Deletes a particle with the given name. To delete the default
         *      particle, type "/delete first".
         * /particle <name,color,width,height,maxSpeed,craziness>
         *      Creates a new particle.
         * /capacity
         *      Switches to capacity mode.
         * /intensity
         *      Switches to intensity mode.
         * /save <filepath>
         *      Saves the ParticleEffect into the given filepath.
         * /exit
         *      Quits the project.
         * /start <width,height>
         *      Sets the width and height of the start field.
         * /stop <width,height>
         *      Sets the width and height of the stop field.
         * 
         * @see ItemProfile.getColour()
         */
        
        public void activate(){
            String command = scan.nextLine();
            for(String str : commands) if(command.startsWith(str)){
                switch(str){
                    case "/delete ": particles.remove(command.substring(str.length()));
                        effect.particleSet = particles.values().toArray(new Particle[particles.size()]);
                        return;
                    case "/particle ": particle(command.substring(str.length()));
                        return;
                    case "/start ": start(command.substring(str.length()));
                        return;
                    case "/stop ": stop(command.substring(str.length()));
                        return;
                    case "/capacity": capOverInt = true;
                        return;
                    case "/intensity": capOverInt = false;
                        return;
                    case "/save ": save(command.substring(str.length()));
                        return;
                    case "/exit": stop();
                        System.exit(0);
                        return;
                }
            }
            System.err.println("Invalid command \"" + command + "\"");
        }
        
    }
    
    private void particle(String str){
        String p[] = str.split(",");
        try{
            Particle part = new PowerParticle(ItemProfile.getColour(p[1]), 
                new Rectangle(Integer.parseInt(p[2]),
                Integer.parseInt(p[3])),
                Integer.parseInt(p[4]), Integer.parseInt(p[5]));
            particles.put(p[0], part);
            effect.particleSet = particles.values().toArray(new Particle[particles.size()]);
        }catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Invalid particle command");
        }
        
    }
    
    private void save(String filepath){
        FileHandler.serialize(effect, filepath);
    }
    
    private void start(String str){
        String p[] = str.split(",");
        effect.startField.setSize(Integer.parseInt(p[0]), 
                Integer.parseInt(p[1]));
    }
    
    private void stop(String str){
        String p[] = str.split(",");
        effect.stopField.setSize(Integer.parseInt(p[0]), 
                Integer.parseInt(p[1]));
    }
    
    @Override
    public void run(){
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        this.requestFocus();
        timer.start();
    }
    
    public static void main(String... args){
        ParticleDesigner designer = new ParticleDesigner();
        while(true) designer.command.activate();
    }
    
    /**
     * Sets the location of the stop field (left-mouse) or the start field
     * (right-mouse).
     * @param me
     */
    @Override
    public void mouseDragged(MouseEvent me){
        if(System.currentTimeMillis()%25!=0) return;
        if(SwingUtilities.isLeftMouseButton(me)){
            effect.stopField.setLocation(me.getX(), me.getY());
        }else if(SwingUtilities.isRightMouseButton(me)){
            effect.startField.setLocation(me.getX(), me.getY());
        }
    }
    
    /**
     * Sets the location of the stop field (left-mouse) or the start field
     * (right-mouse).
     * @param me
     */
    @Override
    public void mouseClicked(MouseEvent me){
        if(SwingUtilities.isLeftMouseButton(me)){
            effect.stopField.setLocation(me.getX(), me.getY());
        }else if(SwingUtilities.isRightMouseButton(me)){
            effect.startField.setLocation(me.getX(), me.getY());
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent me){
    
    }
    
    /**
     * Changes the intensity (inverse particle production rate) if on intensity 
     * mode and the capacity (maximum number of particles) if on capacity mode.
     * @param me
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent me){
        if(capOverInt){
            switch(me.getWheelRotation()){
                case -1: effect.capacity -= 1;
                    break;
                default: effect.capacity += 1;
            }
            System.out.println("Capacity: " + effect.capacity);
        }else{
            switch(me.getWheelRotation()){
                case -1: effect.intensity -= 1;
                    break;
                default: effect.intensity += 1;
            }
            System.out.println("Intensity: " + effect.intensity);
        }
    }
    
    
}
