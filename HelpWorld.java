import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Just a seperate screen to show the help menu.
 * Detects the 'H' keypress.
 * 
 * @author Matthew Holland (MMH32), David Roser-Skelton (dr311)
 * @version 07/03/2017
 */
public class HelpWorld extends World
{

    private MenuWorld menuWorld;
    private GreenfootImage menuImage;
    
    // Stop the menu from rapidly switching, add a delay between keypresses.
    private int nextPress = 10;
    
    /**
     * Constructor for objects of class HelpWorld.
     * 
     */
    public HelpWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 600, 1);
        
        // Just plop a picture on it.
        menuImage = new GreenfootImage( "helpmenu.png" );
        menuImage.scale( getWidth(), getHeight() );
        setBackground( menuImage );
        
    }
    
    // Let's check for the 'h' key.
    public void act()
    {
        
        detectSpace();
        
       if( nextPress > 0 )
       {
           
           nextPress--;
           
       }
        
    }
    
    public void detectSpace()
    {
        
        if( nextPress <= 0 )
        {
        
            // If 'h' has been pressed, and the menuWorld doesn't exist yet, we'll make one and set to it.    
            if( Greenfoot.isKeyDown( "h" ) && menuWorld == null )
            {
                
                menuWorld = new MenuWorld();
                
                Greenfoot.setWorld( menuWorld );
                
                nextPress = 10;
                
            }
            
        }
        
    }
}
