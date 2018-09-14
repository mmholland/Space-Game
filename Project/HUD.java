import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Heads-Up Display (HUD), displays information for the player.
 * Shows fuel, player score, active powerups. 
 *
 * @author Matthew Holland (MMH32), David Roser-Skelton (DR311), Ryan Clark
 */
public class HUD extends Actor
{
    
    // Just for later reference.
    private player_class ply;
    private GreenfootImage HUD;
    private GreenfootImage HUDFrame;
    int w;
    int h;
    
    public HUD()
    {
        
        // Cast the world to a GameWorld so we can access the methods.
        GameWorld wo = (GameWorld) getWorld();
        
        // Check that we have the world, and use its size for the HUD.
        if( wo != null )
        {
            
            this.w = wo.getWidth();
            this.h = wo.getHeight();
            
        }
        // Otherwise we'll just default to 600x600.
        else
        {
            
            this.w = 600;
            this.h = 600;
            
        }
        
        // Make an image to draw our frame on.
        HUDFrame = new GreenfootImage( w, h );
        // Make an image to draw our information on.
        HUD = new GreenfootImage( w - 2, h - 2 );
        setImage( HUDFrame );
        
        HUD.setColor(java.awt.Color.WHITE);
        
    }
    
    // Draws the information to the HUD.
    public void act() 
    {
      
        // Grab the world so we can use its methods.
        GameWorld wo = (GameWorld) getWorld();
        // Get the player's information and store it.
        UserInfo playerInfo = UserInfo.getMyInfo();
        int score = playerInfo.getScore();
        int fuel = wo.getFuel();
        
        // Make the fuel bar scale. This is probably not a great way to do it.
        int barsize = ( ( h - 8 + 100 ) / ( 100 )  ) * fuel;
        
        // Clear the image so it doesn't show old data.
        HUD.clear();
        
        // Draw the frame and the bar outline.
        HUD.drawRect( 0, 0, w - 4, h - 4 );
        HUD.drawRect( 3, 2, 14, h - 5 );

        // Show the fuel amount and the score.
        HUD.drawString( "SCORE: " + score, 20, 16 );
        HUD.drawString( "FUEL: " + fuel, 20, 32 );
        
        // If we haven't got our player yet, grab him.
        if( ply == null )
        {
        
            if( !getWorld().getObjects( player_class.class ).isEmpty() )
            {
            
                ply = getWorld().getObjects( player_class.class ).get( 0 );
            
            }
        }
        // Otherwise, let's show their powerups.
        else
        {
            
            if( ply.hasDoubleScore() )
            {
                
                HUD.drawString( "x2 SCORE!", 20, 48 );
                
            }
            
            if( ply.hasStrongerLasers() )
            {
                
                HUD.drawString( "Double Lasers!", 28, 64 );
                
            }
            
            if( ply.hasSpeed() )
            {
                
                HUD.drawString( "Faster Ship!", 28, 72 );
                
            }
            
        }
        
        // Set the HUD's colour to ~green~ because sci-fi, then draw our fuel bar.
        HUD.setColor(java.awt.Color.GREEN );
        
        HUD.fillRect( 5, 4, 11, barsize );
        
        // Clear the frame, then draw the HUD inside the frame.
        HUDFrame.clear();
        HUDFrame.drawImage( HUD, 2, 2 );
        
    }    
}
