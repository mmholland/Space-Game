import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Displays an explosion sprite with a fixed lifetime of two seconds.
 * 
 * @author David Roser-Skelton (dr311)
 * @version 04/04/2017
 */
public class impact_effect extends Actor
{

	private int lifetime = 20;
	
    /**
     * Act - do whatever the explosion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
    	if( lifetime > 0 )
    	{

    		lifetime--;

    	}
    	else
    	{

    		getWorld().removeObject( this );

    	}

    }    
}
