import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The player laser moves up and removes enemies it touches. It also drops pickups.
 * 
 * @author David Roser-Skelton (dr311), Matthew Holland (mmh32)
 * @version v3
 */
public class player_laser extends Actor
{
    
    // Instance the laser image.
    GifImage laser = new GifImage( "newlaser.gif" );
    
    // Scale the laser image.
    public player_laser()
    {
        
        GreenfootImage image = this.getImage();
        image.scale( 12, 24 );
        Greenfoot.playSound( "ply_laser.mp3" );
        
    }

    // Make sure the laser has the current frame of animation, move it, and check its collision.
    public void act() 
    {
        
        setImage( laser.getCurrentImage() ); 
        
        DoMovement();
        CheckCollision();
        
    }    
    
    // Checks for both basic enemies and bosses, then does its thing.
    public void CheckCollision()
    {
        
        Actor touching;
        Actor touching2;
        touching = getOneObjectAtOffset( 0, 0, obstacle_test.class );
        touching2 = getOneObjectAtOffset( 0, 0, boss_test.class );
        
        // If it's touching either a base enemy or a boss, have a good time.
        if( touching != null || touching2 != null )
        {
            
            GameWorld wo = (GameWorld) getWorld();
            World world;
            world = getWorld();
            
            // If it's a boss, take 1 damage away.
            if( (boss_test) touching2 != null )
            {
                
                boss_test boss = (boss_test) touching2;
                
                boss.takeDamage( 1 );
                
                // Add an impact effect
                impact_effect impact = new impact_effect();
                getWorld().addObject( impact, getX(), getY() );
                
            }
            else
            // Otherwise remove the enemy and, possibly, spawn a pickup where it was.
            {
            
                obstacle_test obstacle = (obstacle_test) touching;
                
                // Add an explosion effect
                explosion_effect explosion = new explosion_effect();
                world.addObject( explosion, obstacle.getX(), obstacle.getY() );
                
                // There's a 1-in-3 chance for a pickup to drop.
                if( Greenfoot.getRandomNumber( 3 ) == 2 )
                {
                    
                    Actor pickup = new pickup();
                    world.addObject( pickup, getX(), getY() );
                    
                }
                
                // Give the player the enemy's set points reward.
                wo.addPoints( obstacle.getPointReward(), getX(), getY() );
                
                // Remove the enemy.
                world.removeObject( obstacle );
                
                // Play an explosion sound.
                Greenfoot.playSound( "explosion_small_" + ( Greenfoot.getRandomNumber( 3 ) + 1 ) + ".mp3" );
                
            }
            
            // Remove the laser.
            world.removeObject( this );
            
        }
        
        // If we can't grab the world, abort.
        if( getWorld() == null ) return;
        
        // If we're out of bounds, remove the laser.
        if( this.isAtEdge() && this != null )
        {
            
            World world;
            world = getWorld();
            world.removeObject( this );
            
        }
        
    }
    
    // Move the laser down very quickly.
    public void DoMovement()
    {
        
        if( this != null )
        {
            
            this.setLocation( this.getX(), this.getY() - 4 );
            
        }
        
    }
}
