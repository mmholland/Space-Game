import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Cypher Battleship is a high-tier boss with a more advanced attack pattern.
 * It fires six projectiles which track the player, and must be destroyed.
 * It will not fire when there are missiles present, and when the missiles are destroyed there is a delay.
 * To accommodate for this, it is a stationary enemy, and is very wide (400 pixels!)
 * 
 * @author David Roser-Skelton (dr311)
 * @version 08/03/2017
 */
public class cypherBattleship extends boss_test
{

    private boolean introDone = false;
    private boolean doneSpeech = false;
    private int speechtimer = 200;
    private int attackTimer = 300;
    private int curMissiles = 0;
    private boolean speech = false;
    private textPopup bossName;
    private int baseHP;
    private boolean shield;

    public cypherBattleship( int phases, int hp, String name )
    {

        super( phases, hp, name );
        baseHP = hp;

    }
    
    public void setShield( boolean shield )
    {
        
        this.shield = shield;
        
    }

    public void act() 
    {

        if( introDone )
        {

            doAttack();

        }
        else
        {

            doIntro();

        }
        
        if ( shield )
        { 
            
            setImage( "stage3boss_shield.png" );
        
        }
        else
        {
            
            setImage( "stage3boss.png" );
            
        }
        
        // Update the missile count.
        curMissiles = getWorld().getObjects( tracking_missile.class ).size();

    }    

    public void doAttack()
    {

        // Only count down if there aren't any missiles.
        if( attackTimer > 0 && curMissiles == 0 )
        {

            attackTimer--;
            
            setShield( false );

        }
        // If the timer's run out, and there aren't any missiles, make some!
        else if( curMissiles == 0 )
        {

            // Create six missiles, space them out evenly across the ship.
            for( int i = 0; i < 6; i++ )
            {

                getWorld().addObject( new tracking_missile(), ( getX() - 150 ) + ( 90 * i ), getY() );

            }

            // Reset the timer.
            attackTimer = 300;
            
            setShield( true );

        }

    }

    public void doIntro()
    {

        if( !doneSpeech && !speech )
        {

            textPopup textPopup = new textPopup( 32, 200 );
            getWorld().addObject( textPopup, ( getWorld().getWidth() - textPopup.getImage().getWidth() ) / 2, ( getWorld().getHeight() - textPopup.getImage().getHeight() ) - 64 );
            textPopup.setText( "\nYOUR PATH ENDS HERE" ); 

            bossName = new textPopup( 24, -1 );
            getWorld().addObject( bossName, ( getWorld().getWidth() - textPopup.getImage().getWidth() ) / 2, 32 );
            bossName.setText( "\n" + getName() + "\n" + getHP() + "HP" );

            speech = true;

        }
        else if( getY() <= 200 )
        {

            setLocation( getX(), getY() + 1 );

        }
        else
        {

            introDone = true;

        }

    }

    public void takeDamage( int dmg )
    {

        if( introDone )
        {

            if( !shield )
            {
            
                GameWorld wo = (GameWorld) getWorld();
    
                if( getHP() - 1 == 0 )
                {
    
                    wo.removeObject( bossName );
    
                }
    
                super.takeDamage( dmg );
                bossName.setText( "\n" + getName() + "\n" + getHP() + "HP" );
                
            }
            else
            {
                
                Greenfoot.playSound( "hit_shield_" + ( Greenfoot.getRandomNumber( 3 ) + 1 ) + ".mp3" );
                
            }

        }

    }

}
