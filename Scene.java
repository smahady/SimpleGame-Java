import java.io.*; 
import java.awt.*;
import java.awt.event.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Scene extends JPanel implements KeyListener, MouseListener
{
	
	public int height;
	public int width;
	public boolean pressed;
	public boolean mouseUpdate = false;
	public boolean virtKeys = false;	
	private Timer timer;
	
	protected Graphics g2;
	
	// bound actions
	protected int WRAP = 0; 
	protected int BOUNCE = 1; 
	protected int STOP = 3; 
	protected int DIE = 4; 
	protected int CONTINUE = 5;
	
	// key variables
	protected boolean keysDown[] = new boolean[256];
	protected boolean boardKeysDown[] = new boolean[256];
	protected boolean virtKeysDown[] = new boolean[256];
	protected int K_ESC = 27;
	protected int K_SPACE = 32;
	protected int K_PGUP = 33;
	protected int K_PGDOWN = 34;
	protected int K_END = 35;
	protected int K_HOME = 36;
	protected int K_LEFT = 37;
	protected int K_UP = 38;
	protected int K_RIGHT = 39;
	protected int K_DOWN = 40;
	protected int K_0 = 48;
	protected int K_1 = 49;
	protected int K_2 = 50;
	protected int K_3 = 51;
	protected int K_4 = 52;
	protected int K_5 = 53;
	protected int K_6 = 54;
	protected int K_7 = 55;
	protected int K_8 = 56;
	protected int K_9 = 57;
	protected int K_A = 65;
	protected int K_B = 66;
	protected int K_C = 67;
	protected int K_D = 68;
	protected int K_E = 69;
	protected int K_F = 70;
	protected int K_G = 71;
	protected int K_H = 72;
	protected int K_I = 73;
	protected int K_J = 74;
	protected int K_K = 75;
	protected int K_L = 76;
	protected int K_M = 77;
	protected int K_N = 78;
	protected int K_O = 79;
	protected int K_P = 80;
	protected int K_Q = 81;
	protected int K_R = 82;
	protected int K_S = 83;
	protected int K_T = 84;
	protected int K_U = 85;
	protected int K_V = 86;
	protected int K_W = 87;
	protected int K_X = 88;
	protected int K_Y = 89;
	protected int K_Z = 90;
	protected boolean SLOPE_RIGHT = true;
	protected boolean SLOPE_LEFT = false;

	

	public Scene() 
	{
		setBackground(Color.YELLOW);
		height = 400;
		width = 400;
		setSize (new Dimension(width, height)); 
		timer = new Timer(50, new UpdateTask());
		initKeys();
		initMouse();	
   }

   public Scene(int xSize, int ySize) 
   {
      setBackground(Color.YELLOW);
		height = ySize;
		width = xSize;
		setSize (new Dimension(width, height));  
		timer = new Timer(50, new UpdateTask()); 
		initKeys();
		initMouse();
   }

   
	// clear the canvas
   public void clear(Graphics g) 
   {
   	g.clearRect(0, 0, width, height);
   }
   

	// this should be overridden by your game
   public void paintComponent(Graphics g) 
   {
   	super.paintComponent(g); 

		// good time to compare key components
		// check for either being true
		for (int i = 0; i < 256; i++){
			keysDown[i] = (boardKeysDown[i] || virtKeysDown[i]);
		}   	
   	
      update(g);
   }
   
   // start ()
   public void start()
   {
		//timer.schedule(new UpdateTask(), 1000, 50);
		timer.start();   
   }
   
   public void stop()
   {
		//timer.cancel();  
		timer.stop(); 
   }
   
   public void setBG(Color color)
   {
   	setBackground(color);
   }
   
   class UpdateTask implements ActionListener {
   	public void actionPerformed(ActionEvent evt) {

               repaint();
      }
    //public void run() {


   //   repaint();



    //}
	}
  
	// initializes key values to false
	private void initKeys(){
		for (int i = 0; i < 256; i++) {
			keysDown[i] = false;
		} // end for
		for (int i = 0; i < 256; i++) {
			boardKeysDown[i] = false;
		} // end for
		setFocusable(true);
		addKeyListener(this);
	} // end initKeys()  

	// initializes key values to false
	private void initMouse() {
		for (int i = 0; i < 256; i++) {
			virtKeysDown[i] = false;
		} // end for
		addMouseListener(this);
	} // end initKeys()  
  
	// turn a key on when it is pressed
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		
		// put this into the keysDown array
		boardKeysDown[event.getKeyCode()] = true;		
		
	} // turn key on

	public void keyReleased(KeyEvent event) {
		// put this into the keysDown array
		boardKeysDown[event.getKeyCode()] = false;
	} // turn key off
	
	
   public void keyTyped(KeyEvent event) {
        // I need to do this
	} // end keyTyped

	// used to define scenes bigger than viewport
   public void changeBoundSize(int newWidth, int newHeight) {
   	width = newWidth;
   	height = newHeight;
	} // end keyTyped

	public void mousePressed(MouseEvent e)
	{
		//Move top in relation to mouse position
		pressed = true;
		mouseUpdate = true;
	}
	
	public void mouseReleased(MouseEvent e)
	{
		//Return to original position
		pressed = false;
		mouseUpdate = true;
	}

	public void mouseEntered(MouseEvent e)
	{
		//Do nothing
	}

	public void mouseExited(MouseEvent e)
	{
		//Do nothing. Commented code below snaps top back to original position.
		//pressed = false;
		//top.setPosition(posX, posY);
	}

	public void mouseClicked(MouseEvent e)
	{
		//Do nothing.
	}

 		
 		
 	// NOT IMPLEMENTED
 	// istouchable();
 	// canvas();
 	// getcontext();
 	// setPos();
 	// setSizePos();
 	// hideCursor();
 	// showCursor();
 	// getMouseX();
 	// getMouseY();
 	// top, left
 	
 	
 	// already defined
 	// setSize();	
 		

 
} // end Scene class def
