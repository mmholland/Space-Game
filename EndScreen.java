import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndScreen extends World
{

    private int curScreen;
    private int nextTransition;
    private MenuWorld menuWorld;
    
    /**
     * Constructor for objects of class EndScreen.
     * 
     */
    public EndScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 600, 1);
        
        prepare();
    }
    
    public void act()
    {
        
        if( nextTransition >= 0 )
        {
            
            nextTransition = nextTransition - 1;
            
        }
        else
        {
            
            if( curScreen == 1 )
            {
                
                setBackground( "EndSlide2.png" );
                curScreen = 2;
                nextTransition = 300;
                
            }
            else if( curScreen == 2 )
            {
                
                setBackground( "EndSlide3.png" );
                curScreen = 3;
                nextTransition = 300;
                
            }
            else if( curScreen == 3 )
            {
                
                setBackground( "EndSlide4.png" );
                curScreen = 4;
                nextTransition = 400;
            
                UserInfo playerInfo = UserInfo.getMyInfo();
                
                textPopup textPopup = new textPopup( 68, -1 );
                addObject( textPopup, ( getWidth() - textPopup.getImage().getWidth() ) / 2, 552 );
                textPopup.setText( "YOUR SCORE: " + playerInfo.getScore() );
                
            }
            else if( curScreen == 4 )
            {
                    
                menuWorld = new MenuWorld();
                Greenfoot.setWorld( menuWorld );
                
            }
            
        }
        
    }
    
    public void prepare()
    {
        
        setBackground( "EndSlide1.png" );
        curScreen = 1;
        nextTransition = 300;
        
    }
}
