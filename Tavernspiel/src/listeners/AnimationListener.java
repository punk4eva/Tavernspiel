
package listeners;

import animation.Animation;

/**
 *
 * @author Adam Whittaker
 * 
 * An Object that needs to know when an Animation is complete.
 */
public interface AnimationListener{
    void animationDone(Animation a);
}
