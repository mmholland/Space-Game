import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Cypher Frigates are mid-tier bosses. They 'jump' by 80 units in all directions and fire at a fixed rate.
 * 
 * @author Ryan Clark
 * @version 08/03/2017
 */
public class cypherFrigate extends boss_test
{

    private boolean introDone = false;
    private boolean doneSpeech = false;
    private int speechtimer = 200;
    private int attackTimer = 50;
    private int teleTimer = 100;
    private int teleDistance = 160;
    private boolean speech = false;
    private boolean direction = true;
    private textPopup bossName;
    private int baseHP;

    public cypherFrigate( int phases, int hp, String name )
    {

        super( phases, hp, name );
        baseHP = hp;

    }

    /**
     * Act - do whatever the space_crop wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {

        if( introDone )
        {

            doMovement();
            doAttack();

        }
        else
        {

            doIntro();

        }

    }    

    public void doAttack()
    {

        if( attackTimer > 0 )
        {

            attackTimer--;

        }
        else
        {

            for( int i = 0; i < java.lang.Math.min( 5, ( ( baseHP + 1 ) - getHP() ) ); i++ )// Takes it's base HP, adds one to that then takes away the current/base HP. It then fires that amount of shots. Ryan
            {

                getWorld().addObject( new enemy_laser(), ( getX() - 8 * i ) + ( 16 * i ), getY() ); //Makes sure that the lazers are lined up. Adds the new enemy_lazer object, spaces the lazers out by 16 pixels.Ryan

            }

            attackTimer = java.lang.Math.max( 50, 100 - ( ( baseHP - getHP() ) * 10 ) );  //Changes the attack timers based on the amount of HP.Ryan

        }

    }

    public void doIntro()
    {

        if( !doneSpeech && !speech )
        {

            textPopup textPopup = new textPopup( 32, 200 );
            getWorld().addObject( textPopup, ( getWorld().getWidth() - textPopup.getImage().getWidth() ) / 2, ( getWorld().getHeight() - textPopup.getImage().getHeight() ) - 64 );
            textPopup.setText( "\nSO YOU THINK YOU CAN BEAT ME?" ); 

            bossName = new textPopup( 24, -1 );
            getWorld().addObject( bossName, ( getWorld().getWidth() - textPopup.getImage().getWidth() ) / 2, 32 );
            bossName.setText( "\n" + getName() + "\n" + getHP() + "HP" );

            speech = true;

        }
        else if( getY() <= 200 )
        {

            setLocation( getX(), getY() + 1 ); //Moves the 

        }
        else
        {

            introDone = true;

        }

    }

    public void doMovement()
    {
        if ( teleTimer > 0 ) 
        {

            teleTimer--;
            
        }
        else 
        {
            int direction = Greenfoot.getRandomNumber(2);
            //0 = Left
            //1 = Right
            
            if ( direction == 0 ) 
            {
                
                if (( getX() - teleDistance ) > teleDistance/2 )
                {

                    setLocation( getX() - teleDistance, getY() );

                }
            }
            else if ( direction == 1 )
            {
                
                if (( getX() + teleDistance ) < teleDistance/2 )
                {

                    setLocation( getX() + teleDistance, getY() );

                }
            }

            teleTimer = 75;

        }

    }

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
