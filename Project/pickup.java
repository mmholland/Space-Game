import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Creates a pickup of random type. Each pickup has its own behaviours.
 * type 1 = fuel
 * type 2 = shield
 * type 3 = x2
 * type 4 = laser
 * type 5 = speed
 * 
 * @author David Roser-Skelton (dr311), Matthew Holland (MMH32), Ryan Clark
 * @version 02/03/2017
 */
public class pickup extends Actor
{
    // We'll use a random number from 0-19 to determine what we'll drop.
    private int chance = Greenfoot.getRandomNumber( 20 );
    private int type = 0;
    // Instance the GifImages.
    private GifImage x2pickup = new GifImage( "x2powerup.gif" );
    private GifImage lazer = new GifImage( "lazer.gif" );
    private GifImage shield = new GifImage( "shield.gif" );
    private GifImage speed = new GifImage( "speed.gif" );
    
    // Scale our image, check what type the pickup is, then set its type accordingly.
    public void act() 
    {
        
        GreenfootImage image = this.getImage();
        image.scale( 32, 32 );
        
        // Fuel is the most common, with an 2-in-5 chance of it spawning.
        if( chance <= 7 )
        {
            
            this.setImage( "pixelfuel.png" );
            this.type = 1;
            
        }
        // There's a 1-in-5 chance of a shield dropping
        else if( chance <= 11 && chance > 7 )
        {
            
            this.setImage( shield.getCurrentImage() );
            this.type = 2;
            
        }
        // There's a 1-in-5 chance of a x2 dropping too.
        else if( chance <= 15 && chance > 11 )
        {
           
            this.setImage( x2pickup.getCurrentImage() );
            this.type = 3;
            
        }
        // A 1-in-10 chance of double lasers spawning
        else if( chance <= 17 && chance > 15 )
        {
            
            this.setImage( lazer.getCurrentImage() );
            this.type = 4;
            
        }
        // A 1-in-10 chance of a speed boost spawning
        else if( chance <= 19 && chance > 17 )
        {
            
            this.setImage( speed.getCurrentImage() );
            this.type = 5;
            
        }
        
        // Check if a player's touching the pickup.
        checkCollision();
        // Make sure the powerup slides down.
        slideDown();
        
    }
    
    // Slide the powerup across the screen.
    public void slideDown()
    {
       
        if( getWorld() == null ) return;
        
        setLocation( getX(), getY() + 2 );

        // If it's out of bounds, end it.
        if( this.getY() >= getWorld().getHeight() + 32 && this != null )
        {
            
            World world;
            world = getWorld();
            world.removeObject( this );
            
        }
        
    }
    
    public void forceType( int type )
    {
        
        if( type == 1 )
        {
            
            this.setImage( "pixelfuel.png" );
            
        }
        else if( type == 2 )
        {
            
            this.setImage( shield.getCurrentImage() );
            
        }
        else if( type == 3 )
        {
            
            this.setImage( x2pickup.getCurrentImage() );
            
        }
        else if( type == 4 )
        {
            
            this.setImage( lazer.getCurrentImage() );
            
        }
        else if( type == 5 )
        {
            
            this.setImage( speed.getCurrentImage() );
            
        }
        
        this.type = type;
        
    }
    
    // Checks whether this pick up is being picked up.
    public void checkCollision()
    {
        
        // Grab any players touching the object.
        Actor touching;
        touching = getOneObjectAtOffset( 0, 0, player_class.class );
        
        // If it's been grabbed, do the fun stuff.
        if( touching != null )
        {
            
            // Cast the touching Actor to player_class so we can call its methods.
            player_class ply = (player_class) touching;
            GameWorld w = (GameWorld) getWorld();
         
            // If it's fuel, add fuel.
            if( type == 1 )
            {
            
                w.addFuel( 25 );
            
            }
            // If it's a shield, add a shield.
            else if( type == 2 )
            {
                    
                ply.addShield();
                
            }
            // If it's x2, do the do.
            else if( type == 3 )
            {
                
                ply.addDouble();
                
            }
            // If it's speed, it's what we need.
            else if( type == 5 )
            {
                
                ply.addSpeed();
                
            }
            // If it's a laser, meet your maker.
            else if( type == 4 )
            {
                
                ply.addLasers();
                
            }
            
            // Then just remove the object.
            getWorld().removeObject( this );
            
        }
        
    }
}
