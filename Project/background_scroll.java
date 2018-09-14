import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;

/**
 * Scrolling background. Constantly slides down and tiles when it goes off-screen.
 * Supports GIFs by default, can be set to transition once it's disappeared
 * 
 * @author David Roser-Skelton (dr311), Matthew Holland (MMH32), Ryan Clark
 * @version 01/03/2017
 */
public class background_scroll extends Actor
{
    
    // Set up our variables, make sure that we can get when it's about to transition.
    private Boolean transition = false;
    // Let's be able to grab the current stage.
    private int curstage = 0;
    // Make sure we know what it's transitioning to.
    private int transitionstage = 0;
    // Make sure we can grab the current frame of the image.
    private GreenfootImage curframe;
    /**
     * Act - do whatever the background_scroll wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
        // Check the current frame of the stage, change to animate.
        checkStage();
        
        // Scroll the image.
        scroll();
        
    }
    
    public void scroll()
    {
        
        // If it's off screen, check for a transition.
        if( this.getY() > getWorld().getHeight() * 1.5 )
        {
            
            if( !transition && transitionstage != curstage )
            {
                
                curstage = transitionstage;
                transition = true;
                
            }
            
            // Otherwise, put it back on the top.
            this.setLocation( this.getX(), -getWorld().getHeight() / 2 + 20 );
            
        }
        else
        {
        
            // If it's not off screen, just keep on scrolling.
            this.setLocation( this.getX(), this.getY() + 2 );
            
        }
        
    }
    
    // Force the background image   
    public void forceBG( String bg )
    {
     
        setImage( bg );
        
    }
    
    // For transitions, checks if the current stage is the desired stage
    // If not, and it's ready to transition, set its image.
    public void checkStage()
    {
        
        GameWorld wo = (GameWorld) getWorld();
        
        if( wo.getStages().get( curstage ) != null && transition )
        {
            
            curframe = wo.getStage( curstage ).getCurrentImage();
            
            setImage( curframe );
        
        }
        
    }
    
    // Begins the transition process.
    public void transitionStage( int stage )
    {
        
        // Resets current transition status, sets the desired stage
        transition = false;
        transitionstage = stage;
        
    }
}
