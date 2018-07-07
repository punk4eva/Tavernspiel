
package gui.mainToolbox;

import gui.pages.LoadingPage;

/**
 *
 * @author Adam Whittaker
 * 
 * This class controls which page to display on-screen.
 */
public class PageFlipper{
    
    private final LoadingPage LOADING_PAGE = new LoadingPage();
    private final Main main;
    
    /**
     * Creates an instance.
     * @param m
     */
    public PageFlipper(Main m){
        main = m;
    }
    
    /**
     * Sets the page.
     * @param name The name of the page.
     */
    public void setPage(String name){
        switch(name){
            case "loading": main.page = LOADING_PAGE;
                break;
            case "main": main.page = main;
                break;
        }
    }
    
}
