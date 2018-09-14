import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * The player class has accessors and mutators for all powerups, as well as movement and shooting.
 * 
 * @author Matthew Holland (MMH32), David Roser-Skelton (DR311), Ryan Clark (RC831) 
 * @version (a version number or a date)
 */
public class player_class extends Actor
{
    
    // Instance our shoot timer.
    private int firetimer = 0;
    // Instance our powerup timers.
    private int doubletimer = 1200;
    private int lasertimer = 1200;
    private int speedtimer = 1200;
    // Instance our powerup bools.
    private Boolean shield = false;
    private Boolean doublePoints = false;
    private Boolean strongerLasers = false;
    private Boolean fasterShip = false;
    // Instance our ship pictures.
    private GifImage shipPic;
    private GifImage shipShieldPic;
    
    public static int selectedShip = 0;
    public static final int shipLimit = 3;
    
    // In our act we detect the movement, as well as decrease our powerup timers.
    public void act() 
    {

        // Detect the keypresses for movement.
        detectMovement();
        
        // Decrease the timer for shooting.
        if( firetimer > 0 )
        {
            
            firetimer--;
            
        }
        
        // If we have the x2 powerup, and our timer isn't 0, we'll count down.
        if( doubletimer > 0 && doublePoints )
        {
            
            doubletimer--;
            
        }
        
        // Otherwise, if the timer is 0 and x2 is active, we'll remove the x2 powerup and reset our timer.
        if( doubletimer == 0 && doublePoints )
        {
            
            removeDouble();
            doubletimer = 1200;
            
        }
        
        // Repeat the process for the rest.
        if( lasertimer > 0 && strongerLasers )
        {
            
            lasertimer--;
            
        }
        
        if( lasertimer == 0 && strongerLasers )
        {
            
            removeLasers();
            lasertimer = 1200;
            
        }
        
        if( speedtimer > 0 && fasterShip )
        {
            
            speedtimer--;
            
        }

        if( speedtimer == 0 && fasterShip )
        {
            
            removeSpeed();
            speedtimer = 1200;
            
        }
        
        // If our shield is active, we'll set the pic to its current frame
        if( shield )
        {
            
            setImage( shipShieldPic.getCurrentImage() );
        
        }
        // Otherwise, we'll use the base ship.
        else
        {
            
            setImage( shipPic.getCurrentImage() );
            
        }
    }
    
    public player_class()
    {
        
        if( selectedShip == 0 )
        {
        
            shipPic = new GifImage( "spaceship2.gif" );
            shipShieldPic = new GifImage( "newshield.gif" );
            
        }
        else if( selectedShip == 1 )
        {
            
            shipPic = new GifImage( "ufo.png" );
            shipShieldPic = new GifImage( "placeholder_shield.png" );
        
        }
        else if( selectedShip == 2 )
        {
            
            shipPic = new GifImage( "shooter.gif" );
            shipShieldPic = new GifImage( "shooter.gif" );
            
        }
        else if( selectedShip == 3 )
        {
            
            shipPic = new GifImage( "Stage1boss.png" );
            shipShieldPic = new GifImage( "Stage1boss.png" );
        
        }
            
    }
    
    // Methods to change pickup states.
    public void removeSpeed()
    {
        
        fasterShip = false;
        
    }
    
    public void addSpeed()
    {
        
        fasterShip = true;
        
    }
    
    public void addLasers()
    {
        
        strongerLasers = true;
        
    }
    
    public void removeLasers()
    {
        
        strongerLasers = false;
        
    }
    
    public void removeShield( )
    {
        
        shield = false;
        
    }
    
    public void addShield( )
    {
        
        shield = true;
        
    }
    
    public void removeDouble()
    {
        
        doublePoints = false;
        
    }
    
    public void addDouble()
    {
        
        doublePoints = true;
        
    }
    
    public Boolean hasDoubleScore()
    {
        
        return doublePoints;
        
    }
    
    public Boolean hasShield()
    {
        
        return shield;
        
    }
    
    public Boolean hasSpeed()
    {
        
        return fasterShip;
        
    }
    
    public Boolean hasStrongerLasers()
    {
        
        return strongerLasers;
        
    }
    
    // Detects arrow and space keypresses, and moves appropriately.
    public void detectMovement()
    {
        
        if( Greenfoot.isKeyDown( "left" ) ){
            
            // Make sure we don't go out of bounds.
            if( getX() - 32 > 0 )
            {
            
                // If we have our speed pickup, go faster.
                if( hasSpeed() )
                {
                    
                    setLocation( this.getX() - 3, this.getY() );
                    
                }
                else
                {
                    
                    setLocation( this.getX() - 2, this.getY() );
                    
                }
                
            }
            
        }
        else if( Greenfoot.isKeyDown( "right" ) ){
         
            if( getX() + 32 < getWorld().getWidth() )
            {

                if( hasSpeed() )
                {
                    
                    setLocation( this.getX() + 3, this.getY() );
                    
                }
                else
                {
                    
                    setLocation( this.getX() + 2, this.getY() );
                    
                }
            
            }
       
        }
        
        if( Greenfoot.isKeyDown( "up" ) ){
            
            if( getY() - 32 > 0 )
            {
            
                if( hasSpeed() )
                {
                    
                     setLocation( getX(), getY() - 3 );
                   
                }
                else
                {
                    
                    setLocation( getX(), getY() - 2 );
                    
                }
                
            }
        }
        else if( Greenfoot.isKeyDown( "down" ) ){
            
            if( getY() + 32 < getWorld().getHeight() )
            {
                
                if( hasSpeed() )
                {
                    
                     setLocation( getX(), getY() + 3 );
                   
                }
                else
                {
                    
                    setLocation( getX(), getY() + 2 );
                    
                }
            
            }
                
        }
        
        // If we're pressing space and our fire timer is 0, we'll shoot.
        if( Greenfoot.isKeyDown( "space" ) && firetimer <= 0 ){
            
            World world;
            world = getWorld();
            
            // If we've got the laser powerup, make two.
            if( strongerLasers )
            {
                
                world.addObject( new player_laser(), this.getX() - ( this.getImage().getWidth() / 4 ), this.getY() - 48 );
                world.addObject( new player_laser(), this.getX() + ( this.getImage().getWidth() / 4 ), this.getY() - 48 );
                
            }
            // Otherwise, we'll just make one.
            else
            {
                
                world.addObject( new player_laser(), this.getX(), this.getY() - 48 );
                
            }
            
            // Set the fire timer.
            firetimer = 30;
            
        }
        
    }
}
