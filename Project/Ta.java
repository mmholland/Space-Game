/**
 * Write a description of class Ass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ta extends obstacle_test  
{
    // instance variables - replace the example below with your own
    int targetX = 10;
    private int firetimer = 1;
    private player_class ply;

    /**
     * Constructor for objects of class Ass
     */
    public void act()
    {
        super.CheckCollision();
        
        if( getWorld() == null ) return;
        
        BeSmart();
        
        if ( firetimer > 0 )
        {
           firetimer--; 
            
        }
       
    }

    public void BeSmart()
    {
        
        if( getWorld() == null ) return;
        
        // We're always spinning, it looks pretty good.
       
        setRotation( getRotation()+ 2 ) ;
        
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
   