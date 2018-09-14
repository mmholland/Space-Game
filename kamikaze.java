import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shooters are higher-tier enemies. They rapidly fly towards a target destination in hopes of hitting the player.
 * They aren't smart, but unlucky spawns will still make them dangerous.
 * 
 * @author David Roser-Skelton (dr311)
 * @version 07/03/2017
 */
public class kamikaze extends obstacle_test
{
    
    // Make sure we can tell if the enemy has already fired.
    Boolean hasfired;
    private int x;
    private int y;
    
    // Takes target coordinates.
    public kamikaze( int targetX, int targetY )
    {
        
        // They're quite hard to hit, so we'll give the player a treat.
        setPointReward( 100 );
        
        // We've not fired yet.
        hasfired = false;
        
        // Store the coordinates.
        x = targetX;
        y = targetY;
        
    }
    
    // Moves towards the target coordinates.
    public void act() 
    {
        
        // Check collisions
        super.CheckCollision();
        
        // If the world doesn't exist for some reason, best abort.
        if( getWorld() == null ) return;
        
        // If we're above the player, look at it. Otherwise just face wherever.
        if( this.getY() < y )
        {
        
            turnTowards( x, y );
            
        }

        // I didn't expect this to do what it did. But it looks REALLY neat.
        getImage().rotate( 2 );
        
        // VERY fast enemy running at incredible hihg speed
        this.move( 2 );
        
    }
}
