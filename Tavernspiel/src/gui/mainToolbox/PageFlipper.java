
package gui.mainToolbox;

import gui.pages.LoadingPage;

/**
 *
 * @author Adam Whittaker
 */
public class PageFlipper{
    
    private final Main main;
    
    public PageFlipper(Main m){
        main = m;
    }
    
    public void setPage(String name){
        switch(name){
            case "loading": main.page = new LoadingPage();
                break;
            case "main": main.page = main;
                break;
        }
    }
    
}
