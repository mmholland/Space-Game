import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The base class for bosses.
 * Contains the necessary methods for collision, multiple health points
 * 
 * @author David Roser-Skelton (dr311)
 * @version 02/03/2017
 */
public class boss_test extends obstacle_test
{
    
    // Establish integers for phases, HP and its name.
    private int phases;
    private int hp;
    private String name;
    
    public boss_test( int phases, int hp, String name )
    {
     
        this.phases = phases;
        this.hp = hp;
        this.name = name;

        // Make bosses worth 250 points.
        setPointReward( 250 );
        super.setBoss( true );
        
    }
    
    public void act() 
    {
        
        // Let's just use the collision for obstacle_test
        super.CheckCollision();
        
    }
    
    // Allows HP to be manipulated and 'kills' the NPC when its HP hits 0
    public void setHP( int newhp )
    {
        
        // Make sure we don't get a negative HP value
        hp = Math.max( newhp, 0 );
        
        // If it's dead, finish it.
        if( hp <= 0 )
        {
            
            GameWorld wo = (GameWorld) getWorld();
            
            wo.addPoints( getPointReward(), getX(), getY() );
            
            // Add an explosion effect
            explosion_effect explosion = new explosion_effect();
            getWorld().addObject( explosion, getX(), getY() );
            
            die();
            
            getWorld().removeObject( this );
            
            wo.stopBoss();
        
        }
        
    }
    
    // Let it die. Handles stage changing and adds the reward pickups
    public void die()
    {
        
        GameWorld wo = (GameWorld) getWorld();
        wo.nextStage();
        
        for( int i = 0; i < 3; i++ )
        {
            
            getWorld().addObject( new pickup(), ( getX() - 16 ) + ( 16 * i ), getY() );
            
        }
        
        Greenfoot.playSound( "explosion_large.mp3" );
        
    }
    
    // Just returns the HP
    public int getHP()
    {
        
        return hp;
        
    }
    
    // Give it a name
    public void setName( String newname )
    {
        
        name = newname;
        
    }
    
    // Get its name
    public String getName()
    {
        
        return this.name;
        
    }
    
    // Hurt the poor thing
    public void takeDamage( int dmg )
    {
        
        this.setHP( this.getHP() - dmg );
        
        Greenfoot.playSound( "hit_" + ( Greenfoot.getRandomNumber( 2 ) + 1 ) + ".mp3" );
        
    }
}
