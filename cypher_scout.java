import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Cypher Scout is a low-tier boss with some basic behaviours.
 * Flies from side-to-side on the screen and fires at a fixed rate.
 * Amount of lasers fired and frequency increases as it takes damage.
 * Has an introductory cutscene, where it flies in and displays some text.
 *
 * @author Matthew Holland (MMH32)
 * @version 23/02/2017
 */
public class cypher_scout extends boss_test
{
    
    // Contains variables required for detecting if the cutscene is done.
    private boolean introDone = false;
    private boolean doneSpeech = false;
    private int speechtimer = 200;
    // Firing delay.
    private int attackTimer = 50;
    // Whether the speech exists.
    private boolean speech = false;
    // Defines whether it'll fly left or right.
    private boolean direction = true;
    // The HUD text.
    private textPopup bossName;
    // Get the base HP for the NPC, used for laser amount & frequency, as well as the HUD.
    private int baseHP;
    
    public cypher_scout( int phases, int hp, String name )
    {
        
        super( phases, hp, name );
        baseHP = hp;
        
    }

    public void act() 
    {
        
        // Check if the cutscene has been completed
        if( introDone )
        {
        
            // Begin the behaviour! Move around and attack.
            doMovement();
            doAttack();
            
        }
        else
        {
            
            // Otherwise, let's just do the cutscene
            doIntro();
            
        }
        
    }    
    
    // Counts down on its attack timer, when it hits 0, it fires.
    public void doAttack()
    {
        
        if( attackTimer > 0 )
        {
            
            attackTimer--;
            
        }
        else
        {
         
            // Figure out how many lasers we need to fire
            for( int i = 0; i < java.lang.Math.min( 4, ( ( baseHP + 1 ) - getHP() ) ); i++ )
            {
            
                // Offset the lasers so that they're all lined up and centered.
                getWorld().addObject( new enemy_laser(), ( getX() - 16 ) + ( 16 * i ), getY() );
            
            }
            
            // Reset the timer, make the timer bigger when it's healthier.
            attackTimer = java.lang.Math.max( 50, 100 - ( ( baseHP - getHP() ) * 10 ) );
            
        }
        
    }
    
    // Handles showing the intro cutscene.
    public void doIntro()
    {
        
        // Check if it's not had a chance to talk yet, and whether the speech even exists.
        if( !doneSpeech && !speech )
        {
            
            // Make the taunt text at the bottom, scare the player!
            textPopup textPopup = new textPopup( 32, 200 );
            getWorld().addObject( textPopup, ( getWorld().getWidth() - textPopup.getImage().getWidth() ) / 2, ( getWorld().getHeight() - textPopup.getImage().getHeight() ) - 64 );
            textPopup.setText( "\n'PREPARE TO DIE'" );
            
            // Add the name and HP to the HUD at the top.
            bossName = new textPopup( 24, -1 );
            getWorld().addObject( bossName, ( getWorld().getWidth() - textPopup.getImage().getWidth() ) / 2, 32 );
            bossName.setText( "\n" + getName() + "\n" + getHP() + "HP" );
         
            speech = true;
            
        }
        // Now we have dialogue, move the ship into place. We want it on 200.
        else if( getY() <= 200 )
        {
            
            setLocation( getX(), getY() + 1 );
            
        }
        // Now it's all in place, we can begin normal behaviour.
        else
        {
            
            introDone = true;
            
        }
        
    }
    
    // Do it's regular movement, 'bounces' across the screen.
    public void doMovement()
    {
        
        // If it's touching the edge, reverse direction.
        if( getX() < 64 )
        {
        
            direction = false;
            
        }
        else if( getX() > 572 )
        {
            
            direction = true;
            
        }
        
        // If direction is true, it'll go left.
        if( direction )
        {
            
            setLocation( getX() - 1, getY() );
            
        }
        // Otherwise, it'll go right.
        else
        {
            
            setLocation( getX() + 1, getY() );
            
        }
        
    }
    
    // Handle taking damage a bit more.
    // Makes the NPC invulnerable during the cutscene and updates the HUD text.
    public void takeDamage( int dmg )
    {
        
        if( introDone )
        {
        
            GameWorld wo = (GameWorld) getWorld();
            
            if( getHP() - 1 == 0 )
            {
                
                wo.removeObject( bossName );
                
            }
            
            super.takeDamage( dmg );
            bossName.setText( "\n" + getName() + "\n" + getHP() + "HP" );
            
        }
        
    }
        
}
