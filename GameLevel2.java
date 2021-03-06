import java.io.*; //I added this to test this....and the ActionListener isn't working
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

public class GameLevel2 extends JFrame {

	//public variables for buttons
	
	// scene stores our info
		// Variables
	protected Frame thisFrame;
	protected astroidsClone demo;
	
	protected Joystick joy;


	public GameLevel2(String levelName)
	{
    
	 
		thisFrame = new Frame(levelName);
 		thisFrame.setSize(800, 800);
 		thisFrame.setVisible(true);
        //thisFrame.setLayout(new GridLayout(1, 1));
		thisFrame.setLayout(null);
        thisFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    


      demo = new astroidsClone();
      thisFrame.add(demo);
      
  


	} // end constructor
  



} // end class def
