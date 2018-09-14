import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Tracking missiles for the stage 3 boss.
 * Follows the player, is treated like any other enemy. 
 *
 * @author David Roser-Skelton (dr311)
 * @version 08/03/2017
 */
public class tracking_missile extends obstacle_test
{
    
    // Instance a player to become the target.
    private player_class target;
    
    // Checks collisions and gets a target.
    public void act() 
    {
        
        super.CheckCollision();
        
        // If we can't get the world, we abort.
        if( getWorld() == null ) return;
        
        // If there's no target, find one.
        if( target == null )
        {
            
            if( !getWorld().getObjects( player_class.class ).isEmpty() )
            {
            
                target = getWorld().getObjects( player_class.class ).get( 0 );
            
            }
        
        }
        // Otherwise, follow the player.
        else
        {
            
            followPlayer();
            
        }
        
    }
    
    public void followPlayer()
    {
        
        if( getWorld() == null ) return;
        
        if( target != null )
        {
        
            // Ensure we face the player, and move towards them.
            turnTowards( target.getX(), target.getY() );
            move( 1 );
            // Add 90 to our angle to ensure we actually face them.
            setRotation( getRotation() + 90 );
            
        }
        
    }
        
}
