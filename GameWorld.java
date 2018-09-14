import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;

/**
 * The main world. Handles the player's fuel, backgrounds, bosses, points, NPC spawning and the game over.
 * 
 * @author David Roser-Skelton (dr311), Matthew Holland (MMH32), Ryan Clark
 * @version 07/03/2017
 */
public class GameWorld extends World
{
    // The NPC spawn rate.
    private int nextSpawn = 60;
    private boolean playing = true;
    // Instance the player for easy access.
    private player_class player_class;
    // Initial fuel amount, it'll be full.
    private int fuel = 100;
    // The drain timers.
    private int initdrain = 50;
    private int fueldrain = 50;
    // How much fuel should be drained per timer.
    private int drainAmount = 1;
    // Easy access to the current stage.
    private int stage = 0;
    // The two background objects.
    private background_scroll bg1;
    private background_scroll bg2;
    // A HashMap of our stages, takes its number and the image file.
    static HashMap< Integer, GifImage > stages;
    // Allows us to turn NPC spawns on and off. When true no NPCs spawn. Useful for testing individual objects.
    private boolean test = false;
    // If a boss battle is active, NPCs won't spawn unless part of the enemy's behaviour.
    private boolean bossBattle = false;
    // If it's the final boss, don't use normal boss behaviours!
    private boolean finalBoss = false;
    // create new object for game music
    private GreenfootSound gamemusic = new GreenfootSound("introsong.mp3");
    // Make an object for the ending slideshow
    private EndScreen EndScreen;
    
    public GameWorld()
    {    
        // Calls on the superclass to create a 600x600 world.
        super(600, 600, 1, false);
        // Create our stage HashMap, populate it with our current stages.
        stages = new HashMap< Integer, GifImage >();

        addStage( 1, "Stage1backtest.png" );
        addStage( 2, "Stage2back.png" );
        addStage( 3, "Stage3back.png" );
        addStage( 4, "Stage4back.png" );

        // Prepare the stage.
        prepare();

        // Get our player's information and reset their score.
        UserInfo playerInfo = UserInfo.getMyInfo();
        playerInfo.setScore( 0 );

    }

    // Adds the stage to the HashMap, but only if it's not already present.
    public void addStage( int stage, String bg )
    {

        if( stages.get( stage ) == null )
        {

            stages.putIfAbsent( stage, new GifImage( bg ) );

        }

    }

    
    // Returns the HashMap
    public HashMap getStages()
    {

        return stages;

    }

    // Returns the specific stage's image.
    public GifImage getStage( int stage )
    {

        return stages.get( stage );

    }

    // Advances the stage by one, delays spawns for 400 ticks and adds a nice message to the screen.
    public void nextStage()
    {

        // As long as it's not the final boss, change the stage.
        if( !finalBoss )
        {
        
            // Get the current stage, add one.
            setStage( getNumStage() + 1 );
    
            // Add our nice message, let the player know what stage they're on. Lasts 300 seconds.
            textPopup textPopup = new textPopup( 64, 300 );
            addObject( textPopup, ( getWidth() - textPopup.getImage().getWidth() ) / 2, ( getHeight() - textPopup.getImage().getHeight() ) / 2 );
            textPopup.setText( "\nSTAGE " + getNumStage() );
    
            // Delay enemy spawns, let the player have a break.
            nextSpawn = 400;
            
        }

    }

    // Get the numeric stage.
    public int getNumStage()
    {

        return stage;

    }

    public void act()
    {

        // Count down to the next spawn.
        if( nextSpawn > 0 )
        {

            nextSpawn--;

        }

        // Just to be safe, if 'space' is down and the game isn't playing, set the world.
        if( !playing && Greenfoot.isKeyDown( "space" ) )
        {

            Greenfoot.setWorld( this );

        }

        // If we aren't testing objects, do the painful stuff.
        if( !test )
        {

            // Handle NPC spawns.
            startThePain();

            // Count down to our fuel being decreased.        
            if( fueldrain > 0 )
            {

                fueldrain--;

            }
            // Otherwise, reset our timer and take away our fuel.
            else if( fueldrain <= 0 )
            {

                fueldrain = initdrain;
                takeFuel( drainAmount );

            }

        }

        // If the player doesn't exist, end the game.
        if( this.getObjects( player_class.class ).isEmpty() )
        {

            gameOver();

        }

    }

    // Handle NPC spawns.
    private void startThePain()
    {

        // If it's time to spawn, and there isn't a boss battle in progress, spawn NPCs.
        if( nextSpawn <= 0 && !bossBattle )
        {

            // Set the next spawn time to anything from 0 to 299.
            nextSpawn = Math.min( Greenfoot.getRandomNumber( 300 ), 150 );
            // Determine the type of NPC being spawned, limit it to three.
            int spawnType = Greenfoot.getRandomNumber( Math.min( getNumStage(), 4 ) );
            // Determine when on the screen it'll spawn.
            int spawnW = Greenfoot.getRandomNumber( this.getWidth() );

            // Determine which enemy to spawn from our three base NPCs.
            if( spawnType == 0 )
            {

                this.addObject( new debris(), spawnW, 0 );

            }
            else if( spawnType == 1 )
            {

                this.addObject( new shooter(), spawnW, 0 );

            }
            else if( spawnType == 2 )
            {

                this.addObject( new kamikaze( player_class.getX(), player_class.getY() ), spawnW, 0 );

            }
            else if( spawnType == 3 )
            {
                
                this.addObject( new tracking_missile(), spawnW, 0 );
                
            }

        }

    }

    // Allows us to set both backgrounds to transition.
    private void setStage( int stage )
    {

        // Grab all available backgrounds in a list.
        java.util.List bgs = getObjects( background_scroll.class );

        // If the first two results exist, begin the transition.
        if( bgs.get( 0 ) != null && bgs.get( 1 ) != null )
        {

            background_scroll bg_1 = (background_scroll) bgs.get(0);
            background_scroll bg_2 = (background_scroll) bgs.get(1);

            bg_1.transitionStage( stage );
            bg_2.transitionStage( stage );

            // Set our numeric stage to the correct one as well.
            this.stage = stage;

        }

    }

    // Force the current stage.
    private void forceStage( String stage )
    {

        // Grab the backgrounds in a list.   
        java.util.List bgs = getObjects( background_scroll.class );

        // Make sure they're valid.
        if( bgs.get( 0 ) != null && bgs.get( 1 ) != null )
        {

            background_scroll bg_1 = (background_scroll) bgs.get(0);
            background_scroll bg_2 = (background_scroll) bgs.get(1);

            // Force the backgrounds.
            bg_1.forceBG( stage );
            bg_2.forceBG( stage );

        }

    }

    // Start a boss battle.
    private void startBoss( )
    {

        bossBattle = true;

    }

    // End a boss battle.
    public void stopBoss( )
    {

        if( !finalBoss )
        {
        
            bossBattle = false;
            
        }

    }

    // Give the player some ponts. Accommodates for their x2 powerup.
    public void addPoints( int points, int x, int y )
    {

        // If the player has a x2, double how many they get.
        if( player_class.hasDoubleScore() )
        {

            points = points * 2;

        }

        // Get the player's info and set their score to the new score.
        UserInfo playerInfo = UserInfo.getMyInfo();
        playerInfo.setScore( playerInfo.getScore() + points );

        // Make the point popup.
        textPopup point = new textPopup( 16, 50 );
        addObject( point, x, y );
        point.setText( "" + points + " points!" );

        // Handle boss spawning. If the player has 1500 or 1550 (to accommodate for differing point rewards on NPCs), then initiate a boss battle.
        if( playerInfo.getScore() == 1500 || playerInfo.getScore() == 1550 && !bossBattle )
        {

            cypher_scout boss = new cypher_scout( 1, 5, "CYPHER SCOUT" );
            addObject( boss, 300, -50 );

            startBoss();

        }
        // Ditto, but for 2500 and 2550.
        else if( playerInfo.getScore() == 2500 || playerInfo.getScore() == 2550 && !bossBattle )
        {

            cypherFrigate boss = new cypherFrigate( 2, 7, "CYPHER FRIGATE" );
            addObject( boss, 300, -50);

            startBoss();

        }
        // And once more.
        else if( playerInfo.getScore() == 3500 || playerInfo.getScore() == 3550 && !bossBattle )
        {

            cypherBattleship boss = new cypherBattleship( 2, 20, "CYPHER BATTLESHIP" );
            addObject( boss, 300, -50);

            startBoss();

        }
        else if( playerInfo.getScore() == 6000 || playerInfo.getScore() == 6050 && !bossBattle )
        {
            
            cypherMothership boss = new cypherMothership( 2, 30, "CYPHER MOTHERSHIP" );
            addObject( boss, 300, -50 );
            
            startBoss();
            finalBoss = true;
            
        }

    }
    
    public void EndScreen()
    {
        
        // We aren't playing anymore...
        playing = false;
        
        // Stop the music!
        gamemusic.stop();
        
        // Start the slides.
        Greenfoot.setWorld( new EndScreen() );
    
    }

    // Handle the game over screen.
    // For now it just puts text on the screen and freezes the game for 200 ticks.
    public void gameOver()
    {

        // We aren't playing anymore...
        playing = false;
        
        // Stop the music!
        gamemusic.stop();

        // Return to the menu.
        Greenfoot.setWorld( new GameOverWorld() );

    }

    // Takes away fuel.
    public void takeFuel( int fuel )
    {

        // Keep it positive, don't allow fuel to go into negatives.
        if( this.fuel - fuel >= 0 )
        {

            this.fuel = this.fuel - fuel;

        }
        // Remove the player if we're out of fuel.
        else
        {

            this.removeObjects( this.getObjects( player_class.class ) );

        }

    }

    // Adds fuel.
    public void addFuel( int fuels )
    {

        // Limits it to 100.
        this.fuel = Math.min( this.fuel + fuels, 100 );

    }

    // Return the fuel.
    public int getFuel()
    {

        return fuel;

    }

    // Setup our initial objects.
    private void prepare()
    {
        // Two background_scrolls for our bg.
        background_scroll bg1 = new background_scroll();
        background_scroll bg2 = new background_scroll();

        addObject( bg1, this.getWidth() / 2, -this.getHeight() + 310 );
        addObject( bg2, this.getWidth() / 2, 300 );

        // Add the HUD.
        HUD HUD = new HUD();
        addObject( HUD, this.getWidth() / 2, this.getHeight() / 2 );

        // Add our player.
        player_class = new player_class();
        addObject(player_class,313,565);

        // Set up our intro background, the earth BG and space.
        forceStage( "introscreen.png" );
        bg1.forceBG( "stage1_rsz.gif" );
        setStage( 1 );

        // Introduce the player to stage 1.
        textPopup textPopup = new textPopup( 64, 300 );
        addObject( textPopup, ( getWidth() - textPopup.getImage().getWidth() ) / 2, ( getHeight() - textPopup.getImage().getHeight() ) / 2 );
        textPopup.setText( "\nSTAGE 1" );

        // Delay the first NPC spawn, let them get acquainted.
        nextSpawn = 600;
        
        // Start the tunes.
        gamemusic.playLoop();

    }
}
