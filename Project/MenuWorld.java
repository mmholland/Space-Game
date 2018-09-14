import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A seperate world to handle the main menu. Detects 'space' and 'h' keypresses.
 * 
 * @author David Roser-Skelton (dr311), Matthew Holland (MMH32)
 * @version 09/02/2017
 */
public class MenuWorld extends World
{

    // Instance our worlds.
    private GameWorld gameWorld;
    private HelpWorld helpWorld;
    private GifImage menuImage;
    private playerPreview shipImage;
    
    // Stop the menu from rapidly switching, add a delay between keypresses.
    private int nextPress = 10;
    
    private GreenfootSound menumusic = new GreenfootSound( "menumusic.mp3" );
    
    private Boolean music = false;
    
    // Set up the menu, scale all frames of our image.
    public MenuWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 600, 1);
        
        menuImage = new GifImage( "newsplash_noship.gif" );
        
        shipImage = new playerPreview();
        addObject( shipImage, 450, 450 );
        
        // Grab each frame of the picture, scale it to fit.
        for( GreenfootImage img : menuImage.getImages() )
        {
            
            img.scale( getWidth(), getHeight() );
        
        }
        
    }
    
    public void act()
    {
        
        // Check for the keypresses.
        detectInput();
        // Set the background to the current frame.
        setBackground( menuImage.getCurrentImage() );
        
        if( !music )
        {
            
            menumusic.playLoop();
            music = true;
            
        }
        
        if( nextPress > 0 )
        {
            
            nextPress--;
            
        }
        
    }
    
    // Detects 'space' and 'h' keypresses, then sets the world to the appropriate one
    // Now also detects 'left' and 'right' keypresses, and uses these to choose the desired ship
    public void detectInput()
    {
        
        // 'space' starts a new game
        if( nextPress <= 0 )
        {
            if( Greenfoot.isKeyDown( "space" ) && gameWorld == null )
            {
                
                gameWorld = new GameWorld();
                
                Greenfoot.setWorld( gameWorld );
                
                menumusic.stop();
                
            }
            // 'h' opens up our help menu
            else if( Greenfoot.isKeyDown( "h" ) && helpWorld == null )
            {
                
                helpWorld = new HelpWorld();
                
                Greenfoot.setWorld( helpWorld );
                
                menumusic.stop();
                
            }
            // 'left' cycles through characters
            else if( Greenfoot.isKeyDown( "left" ) )
            {
                
                if( player_class.selectedShip - 1 < 0 )
                {
                    
                    player_class.selectedShip = player_class.shipLimit;
                    
                }
                else
                {
                    
                    player_class.selectedShip = player_class.selectedShip - 1;
                    
                }
                
            }
            // 'right' cycles through characters
            else if( Greenfoot.isKeyDown( "right" ) )
            {
                
                if( player_class.selectedShip + 1 > player_class.shipLimit )
                {
                    
                    player_class.selectedShip = 0;
                    
                }
                else
                {
                    
                    player_class.selectedShip = player_class.selectedShip + 1;
                    
                }
                
            }
                
            nextPress = 10;
            
        }
    }
}
