import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Debris is the simplest enemy. Flies wherever, only exists to annoy the player.
 * It inherits nearly everything from obstacle_test.
 * 
 * @author David Roser-Skelton (dr311)
 * @version 14/02/2017
 */
public class debris extends obstacle_test
{
    
    public debris()
    {
        
        setPointReward( 50 );
        // Set the picture!
        setImage( "bigrock.gif" );
        
    }
   
    // Just run the superclass act.
    public void act() 
    {
        super.act();
    }    
}
