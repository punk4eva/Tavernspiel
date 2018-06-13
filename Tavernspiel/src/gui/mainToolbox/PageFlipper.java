
package gui.mainToolbox;

import gui.pages.LoadingPage;

/**
 *
 * @author Adam Whittaker
 */
public class PageFlipper{
    
    private final LoadingPage LOADING_PAGE = new LoadingPage();
    private final Main main;
    
    public PageFlipper(Main m){
        main = m;
    }
    
    public void setPage(String name){
        switch(name){
            case "loading": main.page = LOADING_PAGE;
                break;
            case "main": main.page = main;
                break;
        }
    }
    
}
