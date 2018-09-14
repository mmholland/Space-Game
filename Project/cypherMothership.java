import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The cypher mothership will be the final boss i nthe game. It will remain static at the top of the screen
 * and spawn one of each of the previous bosses, each one ending a phase in its life.
 * after the final miniboss (the battleship) has been destroyed, there will be a cutscene that will indicate the game has finished
 * and the game will transition to a celebratory screen.
 * 
 * @author David Roser-Skelton (dr311)
 * @version 23/03/17
 */
public class cypherMothership extends boss_test
{
    
    private boolean introDone = false;
    private boolean doneSpeech = false;
    private int speechtimer = 200;
    private int attackTimer = 300;
    private int curMissiles = 0;
    private boolean speech = false;
    private textPopup bossName2;
    private textPopup bossDialogue;
    private int baseHP;
    private boolean mainshield;
    private boolean bossPresent = false;
    private int curBoss = 0;
    private boss_test bossNPC;
    private boolean canSpawn = true;

    public cypherMothership( int phases, int hp, String name )
    {

        super( phases, hp, name );
        baseHP = hp;
        mainshield = true;

    }
    
    public void setMainShield( boolean mainshield )
    {
        
        this.mainshield = mainshield;
        
    }
    
    public boolean getMainShield()
    {
        
        return mainshield;
        
    }

    public void act() 
    {

        if( introDone )
        {

            doAttack();
            checkBosses();

        }
        else
        {

            doIntro();

        }
        
        if( bossDialogue != null && bossName2 != null )
        { 
            if( bossPresent )
            {
                    
                bossName2.setLocation( 300, -300 );
                
            }
            else
            {
                
                bossName2.setLocation( 300, 16 );
                
            }
        
        }
            
        if ( mainshield )
        { 
            
            setImage( "Stage4BossShielded.png" );
        
        }
        else
        {
            
            setImage( "Stage4Boss.png" );
            
        }

    }
    
    public void checkBosses()
    {
        
        if( curBoss == 1 && bossPresent )
        {
            
            if( getWorld().getObjects( cypher_scout.class ).size() == 0 )
            {
                
                bossPresent = false;
                setMainShield( false );
                canSpawn = true;
                
            }
            
        }
        else if( curBoss == 2 && bossPresent )
        {
            if( getWorld().getObjects( cypherFrigate.class ).size() == 0 )
            {
                    
                bossPresent = false;
                setMainShield( false );
                canSpawn = true;
                    
            }
            
        }
        else if( curBoss == 3 && bossPresent )
        {
                
            if( getWorld().getObjects( cypherBattleship.class ).size() == 0 )
            {
                    
                bossPresent = false;
                setMainShield( false );
                canSpawn = true;
                    
            }
            
        }
            
    }

    public void doAttack()
    {

        // Only count down if there aren't any bosses.
        if( attackTimer > 0 && !bossPresent )
        {

            attackTimer--;
             
            System.out.println( "" + curBoss + " " + getHP() );
            
            if( curBoss == 1 && getHP() > 25 )
            {
            
                setMainShield( false );
                
            }
            else if( curBoss == 2 && getHP() > 20 )
            {
                
                setMainShield( false );
                
            }
            else if( curBoss >= 3 )
            {
                
                setMainShield( false );
                
            }
            else
            {
                
                setMainShield( true );
                
            }

        }
        else if( !bossPresent && canSpawn )
        {
            
            attackTimer = 300;
            
            if( curBoss == 0 )
            {
                
                bossNPC = new cypher_scout( 1, 10, "ELITE SCOUT" );
                getWorld().addObject( bossNPC, 300, 0 );
                
                bossPresent = true;
                canSpawn = false;
                curBoss = 1;
                
            }
            else if( curBoss == 1 )
            {
                
                bossNPC = new cypherFrigate( 1, 10, "ELITE FRIGATE" );
                getWorld().addObject( bossNPC, 300, 0 );
                
                bossPresent = true;
                canSpawn = false;
                curBoss = 2;
                
            }
            else if( curBoss == 2 )
            {
                
                bossNPC = new cypherBattleship( 1, 30, "ELITE BATTLESHIP" );
                getWorld().addObject( bossNPC, 300, 0 );
                
                bossPresent = true;
                canSpawn = false;
                curBoss = 3;
                
            }
            else if( curBoss == 3 )
            {
                
                canSpawn = false;
                curBoss = 4;
                
            }
            
            if( curBoss <= 3 )
            {
            
                setMainShield( true );
                
            }
            else
            {
                
                setMainShield( false );
                
            }
            
        }

    }

    public void doIntro()
    {

        if( !doneSpeech && !speech )
        {

            bossDialogue = new textPopup( 32, 200 );
            getWorld().addObject( bossDialogue, ( getWorld().getWidth() - bossDialogue.getImage().getWidth() ) / 2, ( getWorld().getHeight() - bossDialogue.getImage().getHeight() ) - 64 );
            bossDialogue.setText( "\nOUR TROOPS ARE READY\nTHEY'RE OUT FOR BLOOD" ); 

            bossName2 = new textPopup( 24, -1 );
            getWorld().addObject( bossName2, ( getWorld().getWidth() - bossName2.getImage().getWidth() ) / 2, 32 );
            bossName2.setText( "\n" + getName() + "\n" + getHP() + "HP" );

            speech = true;

        }
        else if( getY() <= 50 )
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

            if( !mainshield )
            {
            
                GameWorld wo = (GameWorld) getWorld();
    
                if( getHP() - dmg <= 0 )
                {
    
                    wo.removeObject( bossName2 );
                    
                    wo.EndScreen();
                    
                }
    
                super.takeDamage( dmg );
                bossName2.setText( "\n" + getName() + "\n" + getHP() + "HP" );
                
            }
            else
            {
                
                Greenfoot.playSound( "hit_shield_" + ( Greenfoot.getRandomNumber( 3 ) + 1 ) + ".mp3" );
                
            }

        }

    }
    
}
