import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A seperate screen to show the game over screen.
 * 
 * @author Matthew Holland (MMH32)
 * @version 07/03/2017
 */
public class GameOverWorld extends World
{

    private MenuWorld menuWorld;
    private GreenfootImage menuImage;
    private int menuTimer = 150;
    
    /**
     * Constructor for objects of class GameOverWorld.
     * 
     */
    public GameOverWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 600, 1);
        
        // Just plop a picture on it.
        menuImage = new GreenfootImage( "gameover.png" );
        menuImage.scale( getWidth(), getHeight() );
        setBackground( menuImage );
        
    }
    
    // Let's wait 150 ticks, then return to the menu.
    public void act()
    {
        
        // If we've got time, show our score at the bottom.
        if( menuTimer > 0 )
        {
            UserInfo playerInfo = UserInfo.getMyInfo();
            
            textPopup textPopup = new textPopup( 108, 1 );
            addObject( textPopup, ( getWidth() - textPopup.getImage().getWidth() ) / 2, ( getHeight() - textPopup.getImage().getHeight() ) - 48 );
            textPopup.setText( "" + playerInfo.getScore() );
            
            // Decrease that timer
            menuTimer--;
            
        }
        // Otherwise, change to the menu.
        else
        {
        
            menuWorld = new MenuWorld();
            Greenfoot.setWorld( menuWorld );
            
        }
        
    }
}
