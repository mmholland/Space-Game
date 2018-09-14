import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class player_preview here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class playerPreview extends Actor
{
    /**
     * Act - do whatever the player_preview wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
        if( player_class.selectedShip == 0 )
        {
            
            setImage( "spaceship2.gif" );
            
        }
        else if( player_class.selectedShip == 1 )
        {
            
            setImage( "ufo.png" );
            
        }
        else if( player_class.selectedShip == 2 )
        {
            
            setImage( "shooter.gif" );
            
        }
        else if( player_class.selectedShip == 3 )
        {
            
            setImage( "Stage1boss.png" );
            
        }
        
        GreenfootImage pic = getImage();
        pic.scale( 200, 200 );
        
        setImage( pic );
    }    
}
