import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A useful class for showing text on the world.
 * It takes text size and lifetime as its arguments. 
 *
 * @author David Roser-Skelton (dr311)
 * @version 02/03/2017
 */
public class textPopup extends Actor
{
    
    // Store an empty string, base size and a base lifetime.
    public String text = "";
    public int size = 32;
    public int lifetime = 100;
    
    public textPopup( int size, int lifetime )
    {
        
        // Set the text size and how long it'll remain in the world.
        this.size = size;
        this.lifetime = lifetime;
        
    }
    
    // Creates the text image and checks whether the object should remain in the world.
    public void act() 
    {
        
        // If the object has time to live, decrease the timer.
        if( lifetime > 0 )
        {
            
            lifetime--;
            
        }
        // Otherwise, unless the lifetime is -1, remove the text.
        else if( lifetime != -1 )
        {
            
            getWorld().removeObject( this );
            
        }
        
        // Create a GreenfootImage with the text at the specified size.
        GreenfootImage popup = new GreenfootImage( text, size, java.awt.Color.WHITE, null );
        
        // Set the object's image to the text.
        this.setImage( popup );
        
    }    
    
    // Allows the text to be changed.
    public void setText( String newtext )
    {
		
		text = newtext;
        
    }
}
