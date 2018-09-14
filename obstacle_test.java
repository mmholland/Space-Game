import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Base enemy class. Contains methods for basic movement. Pretty much everything the debris does.
 * Has methods for collision, better movement working with Doubles instead of Integers.
 * Implemented through pair programming.
 * 
 * @author David Roser-Skelton (dr311), Matthew Holland (mmh32)
 * @version 02/03/2017
 */

public class obstacle_test extends Actor
{
    
    // Let's determine whether it'll move or go straight.
    private int xmovement = Greenfoot.getRandomNumber( 2 );
    // Instances for the speed and the timer for changing direction.
    private int xspeed = 0;
    private int xtimer = 50;
    // Set how many points we'll get for a kill
    private int pointReward = 50;
    private double yspeed = Greenfoot.getRandomNumber( 20 );
    // We need these for Double setLocations.
    private double x;
    private double y;
    // Grab the enemy's base picture.
    private GifImage pic = new GifImage( "bigrock.gif" );
    // Set whether we're a boss.
    private Boolean isBoss;
    
    // Sets the enemy's downward speed and sets isBoss to false.
    public obstacle_test()
    {
        
        yspeed = Math.max( yspeed / 10, 0.5 );
        isBoss = false;
        
    }
    
    // Returns whether the enemy is a boss.
    public Boolean getBoss()
    {
        
        return isBoss;
        
    }
    
    // Sets the enemy's boss status.
    public void setBoss( Boolean isBoss )
    {
        
        this.isBoss = isBoss;
        
    }
    
    // Sets how many points we'll get when we kill the enemy
    public void setPointReward( int point )
    {
        
        pointReward = point;
        
    }
    
    // Returns the point reward.
    public int getPointReward()
    {
        
        return pointReward;
        
    }
    
    // Just make sure we do the basic stuff.
    public void act() 
    {

        // If we can't get the world, abort.
        if( getWorld() == null ) return;
        
        // Do our movement and check the collisions.
        BeAnnoying();
        CheckCollision();
        
        // Grab our picture, scale it, and set the picture to the current frame.
        GreenfootImage image = this.getImage();
        image.scale( 32, 32 );
        setImage( pic.getCurrentImage() );
        
        // If movement on the X-Axis is allowed, then count down.
        if( xtimer > 0 && xmovement >= 1 )
        {
                
            xtimer--;
                
        }
        
    }
    
    // setLocation support for doubles, allows for more adjustable speeds.
    public void setLocation( double x, double y )
    {
        
        // Set our REAL positions to the x and y variables.
        this.x = x;
        this.y = y;
        
        // Then run setLocation on the superclass with int versions, for compatability.
        super.setLocation( ( int )x, ( int )y );
        
    }
    
    // Special getX and getY methods
    public double getX2(){
        
        return x;
        
    }
    
    public double getY2(){
        
        return y;
        
    }
    
    // CheckCollision checks whether the enemy is touching a player, and ends them.
    // Otherwise, it checks whether the enemy has gone out of bounds.
    public void CheckCollision()
    {
        
        if( getWorld() == null ) return;
        
        Actor touching;
        // Grab objects that it's touching and filter them to only players
        touching = getOneObjectAtOffset( 0, 0, player_class.class );
        
        if( getWorld() == null ) return;
        
        // If it's touching something, cast it to a player instance.
        if( touching != null )
        {
            
            player_class ply = (player_class) touching;
            
            // Find if the player has a shield, if it does remove it and remove the enemy.
            if( ply.hasShield() )
            {
                
                ply.removeShield();
                World world;
                world = getWorld();
                world.removeObject( this );
                
                Greenfoot.playSound( "hit_shield_disable.mp3" );
                
            }
            // Otherwise, remove the player and the laser.
            else
            {
            
                World world;
                world = getWorld();
                world.removeObject( ply );
                world.removeObject( this );
                
                Greenfoot.playSound( "explosion_large.mp3" );
                
            }
            
        }
        
        if( getWorld() == null ) return;

        // If it's out of bounds, end it.
        if( this.getY() >= getWorld().getHeight() + 32 && this != null )
        {
            
            World world;
            world = getWorld();
            world.removeObject( this );
            
        }
        
    }
    
    // Do our base movement behaviours.
    public void BeAnnoying()
    {
        
        // If we can't get the world, abort.
        if( getWorld() == null ) return;
        
        // If the x timer has hit 0, set our speed for the X-Axis and reset the timer.
        if( xtimer <= 0 )
        {
            
            xspeed = Greenfoot.getRandomNumber( 3 ) - 1;
            xtimer = 50;
            
        }
        
        // If a player can be found, face it.
        if ( !getWorld().getObjects( player_class.class ).isEmpty() )
        {
            
            Actor ply = getWorld().getObjects( player_class.class ).get( 0 );
            
            this.turnTowards( ply.getX(), ply.getY() );
            
        }
        
        // Otherwise, just turn by 1.
        this.turn( 1 );
        // Move the enemy.
        this.setLocation( this.getX2() + xspeed, this.getY2() + yspeed );
        
        // If we're drifting off the side, reverse.
        if( this.getX() <= 32 )
        {
            
            xspeed = 1;
            
        }
        else if( this.getX() >= getWorld().getWidth() - 32 )
        {
            
            xspeed = -1;
            
        }
        
        if( getWorld() == null ) return;
        
        // Align the rotation so it's facing down, then limit it.
        this.setRotation( this.getRotation() - 120 );
        this.setRotation( Math.max( 270, Math.min( 360, this.getRotation() ) ) );
        
    }
    
}
