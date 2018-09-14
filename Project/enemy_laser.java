import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An enemy laser projectile, used by bosses and shooters.
 * Detects if it's touching a player and either kills them or removes their shield. 
 *
 * @author Matthew Holland (MMH32), David Roser-Skelton (dr311), Ryan Clark
 * @version (a version number or a date)
 */
public class enemy_laser extends Actor
{
    
    GifImage laser = new GifImage( "newlaser.gif" );
    
    public enemy_laser()
    {
        
        // Scale the laser.
        GreenfootImage image = this.getImage();
        image.scale( 8, 20 );
        
        Greenfoot.playSound( "ene_laser_" + ( Greenfoot.getRandomNumber( 3 ) + 1 ) + ".mp3" );
        
    }
    
    /**
     * Act - do whatever the enemy_laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
        // Let's animate it!
        setImage( laser.getCurrentImage() ); 
        
        DoMovement();
        CheckCollision();
        
    }    
    
    // CheckCollision checks whether the laser is touching a player, and ends them.
    // Otherwise, it checks whether the laser has gone out of bounds.
    public void CheckCollision()
    {
        
        Actor touching;
        // Grab objects that it's touching and filter them to only players
        touching = getOneObjectAtOffset( 0, 0, player_class.class );
        
        // If it's touching something, cast it to a player instance.
        if( touching != null )
        {
            
            player_class ply = (player_class) touching;
            
            // Find if the player has a shield, if it does remove it and remove the laser.
            if( ply.hasShield() )
            {
                
                ply.removeShield();
                World world;
                world = getWorld();
                world.removeObject( this );
                
            }
            // Otherwise, remove the player and the laser.
            else
            {
            
                World world;
                world = getWorld();
                world.removeObject( ply );
                world.removeObject( this );
                
            }
            
        }
        
        if( getWorld() == null ) return;
        
        // If it's out of bounds, end it.
        if( this.isAtEdge() && this != null )
        {
            
            World world;
            world = getWorld();
            world.removeObject( this );
            
        }
        
    }
    
    // Fly down the screen
    public void DoMovement()
    {
        
        if( this != null )
        {
            
            this.setLocation( this.getX(), this.getY() + 3 );
            
        }
        
    }
}
