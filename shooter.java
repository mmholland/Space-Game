import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The shooter enemy is a mid-tier enemy. It moves to the player and shoots when nearby.
 * 
 * @author David Roser-Skelton (dr311), Matthew Holland (MMH32)
 * @version 07/03/2017
 */
public class shooter extends obstacle_test
{
    
    // Instance our target position, firing delay, and a player.
    int targetX = 5;
    private int firetimer = 5;
    private player_class ply;
    
    // Run the superclass collision checks, decrease our timer and do all of our CLEVER stuff.
    public void act() 
    {
        
        super.CheckCollision();
        
        if( getWorld() == null ) return;
        
        BeSmart();
        
        if( firetimer > 0 )
        {
            
            firetimer--;
            
        }
        
    }
    
    // Chase the player, shoot when we're close.
    public void BeSmart()
    {
        
        if( getWorld() == null ) return;
        
        // We're always spinning, it looks pretty good.
        setRotation( getRotation() + 2 );
        
        // If we've not got our player yet, find it.
        if( ply == null )
        {
        
            if( !getWorld().getObjects( player_class.class ).isEmpty() )
            {
            
                ply = getWorld().getObjects( player_class.class ).get( 0 );
            
            }
            
        }
        // Otherwise, we'll chase them.
        else
        {
        
            // If we're not at our shooting position, slide on down.
            if( this.getY() <= 100 )
            {
                
                this.setLocation( getX(), getY() + 1 );
                
            }
            // Otherwise, hunt the player.
            else
            {
                
                // If we're within 128 units of the player, fire.
                if( this.getX() <= ply.getX() + 64 && this.getX() >= ply.getX() - 64 )
                {
                    
                    Shoot();
                    
                }
                
                // If we're to the left of the player, move right.
                if( this.getX() - ply.getX() < 0 )
                {
                        
                    this.setLocation( this.getX() + 1, this.getY() );
                        
                }
                // Otherwise, move left.
                else
                {
                        
                    this.setLocation( this.getX() - 1, this.getY() );
                        
                }
                
            }
            
        }
        
    }
    
    // Checks the fire timer and makes a laser when it hits 0.
    public void Shoot()
    {
        if( firetimer <= 0 )
        {
            getWorld().addObject( new enemy_laser(), this.getX(), this.getY() + 48 );
            firetimer = 50;
        }
    }
}
