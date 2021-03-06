import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.Timer;					// gives us a timer for animation
import java.awt.geom.AffineTransform;	// gives us rotation

public class Sprite 

{
	public int x;
	public int y;
	public int drawX; // let's us know where the sprite is onscreen, in case scrolling is needed
	public int drawY;	// let's us know where the sprite is onscreen, in case scrolling is needed
	public double dx;
	public double dy;
	public Scene scene;	
	public int height;
	public int width;
	protected int cWidth;
	protected int cHeight;
	private boolean animation;
	private int hAnimations;	// stores how many columns are in an animation cycle
	private int vAnimations;	// stores how many rows are in an animation cycle
	private BufferedImage src;
	private BufferedImage[][] sheet;
	private int animationLength;
	private int animationState;
	private int animationCell;	// individual cell in a 
	private Timer animTimer;
	private int animCellWidth;
	private int animCellHeight;
	private int animSheetWidth;
	private int animSheetHeight;
	private int offsetX;
	private int offsetY;
	private String[] nameArray;
	protected boolean isVisible;
	private double currentSpeed; // for tracking speed
	private double moveAngle; // for tracking angle
	private double imgAngle;	// for rotation
	
	
	// bound actions
	protected int WRAP = 0; 
	protected int BOUNCE = 1; 
	protected int STOP = 3; 
	protected int DIE = 4; 
	protected int CONTINUE = 5;
	protected int boundAction = 0; 
	
	// rotation variables
	private boolean rotated = false;
	private AffineTransform transform = new AffineTransform();
	private BufferedImage src2;
	
	
	// constructor
	public Sprite(Scene thisScene, String imageFile, int xSize, int ySize){
		
		// set out variables		
		width = xSize;		
		height = ySize;
		animation = false;
		x = 20;
		y = 20;
		
		isVisible = true;

		scene = thisScene;
		cWidth = scene.width;
		cHeight = scene.height;
		
		
		changeImage(imageFile);
	} // end constructor
	
	// constructor for tiled sprites
	public Sprite(Scene thisScene, String imageFile[][], int xSize, int ySize){
		
		// set out variables	
		int xLength = imageFile.length;
		int yLength = imageFile[0].length;
		width = xSize * xLength;		
		height = ySize * yLength;
		animation = false;
		x = 20;
		y = 20;
		BufferedImage original;
		int type;
		
		isVisible = true;

		scene = thisScene;
		cWidth = scene.width;
		cHeight = scene.height;
		
		src = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = src.createGraphics();
		
		for (int ix = 0; ix < xLength; ix++) {
			for (int iy = 0; iy < yLength; iy++) {
				try {
					original = ImageIO.read(new File(imageFile[ix][iy]));
					//type = original.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : original.getType();
					
					
		      	
		      	g2d.drawImage(original, ix * xSize, iy * ySize, xSize, ySize, null);
		      	
				} catch (Exception ex) {
		         ex.printStackTrace();
		      }
		   }
		}
		g2d.dispose();
	} // end constructor
	
		// constructor that accepts an image instead of a file
	public Sprite(Scene thisScene, BufferedImage image, int xSize, int ySize){
		
		// set out variables		
		width = xSize;		
		height = ySize;
		animation = false;
		x = 20;
		y = 20;
		
		isVisible = true;

		scene = thisScene;
		cWidth = scene.width;
		cHeight = scene.height;
		
		
		src = new BufferedImage(xSize, ySize, image.getType());
		Graphics g = src.getGraphics();
		g.drawImage(image, 0, 0, xSize, ySize, null);
		g.dispose();
		
	} // end constructor
	
	// changeImage(imgFile) – Changes the image to the image file.  
	public void changeImage(String imageFile) {
		BufferedImage original;
		int type;	
		
		try {
			original = ImageIO.read(new File(imageFile));
			type = original.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : original.getType();
			src = new BufferedImage(width, height, type);
      	Graphics2D g2d = src.createGraphics();
      	g2d.drawImage(original, 0, 0, width, height, null);
      	g2d.dispose();
		} catch (Exception ex) {
         ex.printStackTrace();
      }
      
      
      
	} // end changeImage
	
	// loadAnimation(width, height, cellWidth, cellHeight) Sets varialbes to slice up a sprite sheet into sprite

	public void loadAnimation(int sheetWidth, int sheetHeight, int cellWidth, int cellHeight){
		animation = true;
		animationLength = 1000;
		animationState = 0;
		animTimer = new Timer(animationLength, new AnimTask());
		animCellWidth = cellWidth;
		animCellHeight = cellHeight;
		animSheetWidth = sheetWidth;
		animSheetHeight = sheetHeight;
		width = cellWidth;
		height = cellHeight;

			
	} // end loadAnimation
	
	// generateAnimationCycles() sets up animation cycles based on horizontal and vertical rows and columns.
	public void generateAnimationCycles()
	{
		// compute horizontal animations
		hAnimations = (int)(animSheetWidth / animCellWidth);
		vAnimations = (int)(animSheetHeight / animCellHeight);
		
		sheet = new BufferedImage[hAnimations][vAnimations];
		
		// go through columns and rows
		for (int i = 0; i < vAnimations; i++) {
			for (int j = 0; j < hAnimations; j++) {
				sheet[j][i] = src.getSubimage(j * animCellWidth, i * animCellHeight, animCellWidth, animCellHeight);
			}		
		}	
	}
	
	// renameCycles(cycleNameArray) This method allows you to set string names to each of the cycles. 
	// Typically these will indicate directions or behaviors.
	public void renameCycles(String[] cycleNameArray)
	{
		// this should be the same as vAnimations, but we might throw an error later
		nameArray = cycleNameArray;
		
	}	
	
	// setAnimationSpeed(speed) – This method indicates how quickly the animation will cycle. Setting a higher value 
	// will slow down the animation.
	public void setAnimationSpeed(int speed)
	{
		animationLength = speed;
		animTimer = new Timer(animationLength, new AnimTask());
	}
	
	//setCurrentCycle(cycleName) – Changes the animation cycle to the one indicated by the cycle name. 
	//Normally used to change animation state.
	public void setCurrentCycle(String cycleName)
	{		
		// find our cycle name
		for (int i=0; i < nameArray.length; i++)
		{
			if (cycleName == nameArray[i]){
				animationState = i;			
			}	// end if	
		} // end for
	}
	
	//setCurrentCycle(cycleState) – Changes the animation cycle to the one indicated by the number. 
	public void setCurrentCycle(int state)
	{
		animationState = state;
	}
	
	// playAnimation() - begins (and repeats) the currently indicated animation.
	public void playAnimation()
	{
		animTimer.start();
	}
	
	// pauseAnimation() - Pauses the animation until it is re-started with a playAnimation() command.
	public void pauseAnimation()
	{
		animTimer.stop();
	}
		
	class AnimTask implements ActionListener {
   	public void actionPerformed(ActionEvent evt) {
                animationCell++;
                if (animationCell > (hAnimations-1)) {
						animationCell = 0;                
                }
      } // end actionPerformed(ActionEvent evt)
	} // end class AnimTask
	

	
	//setImage(fileName) – Another name for changeImage(). Works exactly like changeImage()
	public void setImage(String imageFile) {
		changeImage(imageFile);	
	}
	
	// hides the sprite
	public void hide() {
		isVisible = false;
	}	
	
	// unhides the sprite
	public void show() {
		isVisible = true;
	}	
	
	// report is a utility for debugging info to the console
	public void report(){
      // Requires open terminal
      System.out.println("x: " + x + ", y:" + y + ", dx: "
      	+ dx + ", dy: " + dy + ", speed:" + currentSpeed + ", angle: " + moveAngle); 

	} // end report

	// returns the speed, does not change it
	public double speed(){
		return currentSpeed;
	} // end speed()
	
	// moves the sprite to x and y coordinates
	public void setPosition(int xNew, int yNew){
		x = xNew;
		y = yNew;
	} // end setPosition()
	
	// moves the sprite to x and y coordinates based on top left corner
	public void setCPos(int xNew, int yNew){
		x = xNew + (width/2);
		y = yNew + (height/2);
	} // end setCPos()
	
	// set X sets the x coord
	public void setX(int xNew){
		x = xNew;
	} // end setX()
	
	// setY sets the y coord
	public void setY(int yNew){
		y = yNew;
	} // end setY()
	
	// setDX sets the differential value for X
	public void setDX(int dxNew){
		dx = dxNew;
	} // end setDX()
	
	// setDY sets the differential value for Y
	public void setDY(int dyNew){
		dy = dyNew;
	} // end setDY()

	// changeXby sets the differential value for X
	public void changeXby(int dxNew){
		dx = dxNew;
	} // end changeXby()
	
	// changeYby sets the differential value for Y
	public void changeYby(int dyNew){
		dy = dyNew;
	} // end changeYby()

	// setSpeed(speed) – sets the speed to the indicated value. Speed is determined in pixels per frame. 
	// You can set speed to a positive or negative value. The speed will change immediately with this method. 
	// If you want a more realistic change in speed, use changeSpeedBy() or addVector().
	public void setSpeed(int speed) {
		currentSpeed = speed;
		calcVector();	
	} // end setSpeed()
	
	// calcVector calculates dx and dy from speeds and angles
	public void calcVector() {
		dx = (int)((double) currentSpeed * Math.cos(Math.toRadians(moveAngle)));
		dy = (int)((double) currentSpeed * Math.sin(Math.toRadians(moveAngle)));
	} // end calcVector

	// changespeedBy(double diff) - Changes the speed by the amount of diff. 
	// Positive will add to the sprite in the moveAngle direction, negative will slow or reverse it
	public void changeSpeedBy(double diff) {
		currentSpeed += diff;
		calcVector();
	}
	
	// changes the angle the image is drawn
	public void setImgAngle(double degrees) {
		// offset degrees to compensate for monitor weirdness
		degrees = degrees - 90;
		imgAngle = degrees * Math.PI / 180;
		
		// process the image
		if (imgAngle == 0) {
			rotated = false;		
		}
		else {
			rotated = true;
		}
	}
	
	// checks to see if a sprite is offscreen
	public void checkBounds() {
		// set bounds (may add in camera at a later date)
		int leftBorder = 0;
		int rightBorder = cWidth;
		int topBorder = 0;
		int bottomBorder = cHeight;
		
		// set each to false
		boolean offRight = false;
		boolean offLeft = false;
		boolean offTop = false;
		boolean offBottom = false;
		
		// check each border
		if (x > rightBorder) {
			offRight = true;
		}
		if (x < leftBorder) {
			offLeft = true;
		}
		if (y > bottomBorder) {
			offBottom = true;
		}
		if (y < topBorder) {
			offTop = true;		
		}
		
		// call action we need
		if (boundAction == WRAP) {
			if (offLeft) {
				x = rightBorder;
			} // end if 	
			if (offTop) {
				y = bottomBorder;
			} // end if 
			if (offRight) {
				x = leftBorder;
			} // end if 	
			if (offBottom) {
				y = topBorder;
			} // end if 
		} // end if
		
		// deflects
		if (boundAction == BOUNCE) {
			if (offLeft) {
				dx *= -1;
				calcSpeedAngle();
				imgAngle = moveAngle;
			} // end if 	
			if (offTop) {
				dy *= -1;
				calcSpeedAngle();
				imgAngle = moveAngle;
			} // end if 
			if (offRight) {
				dx *= -1;
				calcSpeedAngle();
				imgAngle = moveAngle;
			} // end if 	
			if (offBottom) {
				dy *= -1;
				calcSpeedAngle();
				imgAngle = moveAngle;
			} // end if 		
		} // end if
		
		// halts
		if (boundAction == STOP) {
      	if (offLeft || offRight || offTop || offBottom){
				setSpeed(0);
      	}	
		} // end if
		if (boundAction == DIE) {
			if (offLeft || offRight || offTop || offBottom){
				setSpeed(0);
				hide();		
			}
		} // end if
		
	} // end checkBounds()
	
	// adds to the angle
	public void changeImgAngle(double degrees) {
		// offset degrees to compensate for monitor weirdness
		imgAngle += (degrees * Math.PI / 180);
		
		// process the image
		if (imgAngle == 0) {
			rotated = false;		
		}
		else {
			rotated = true;
		}
		
	} // end setImgAngle(degrees)
	
	// returns the sprite's angle in degrees
	public double getImgAngle() {
		double degrees = imgAngle * 180 / Math.PI;
		degrees = degrees + 90; 
		return degrees;	
	} // end getImgAngle()
	
	// setMoveAngle(double degrees) sets the sprite's motion angle without setting its rotation angle
	public void setMoveAngle(double degrees) {
		// offset from tradition coords to ours		
		degrees = degrees - 90;
		
		// convert to radians
		moveAngle = degrees * Math.PI / 180;
		calcVector();
		
	} // end setMoveangle(double degrees)
	
	// changeMoveAngleBy(degrees) changes the movement angle by the indicated amount
	public void changeMoveAngle(double degrees) {
		// convert to radians
		moveAngle += (degrees * Math.PI / 180);
		calcVector();
		
	} // end changeMoveangle(double degrees)
	
	// calcSpeedAngle() sets speed and moveAngle based on dx and dy
	protected void calcSpeedAngle(){
		currentSpeed = Math.sqrt((dx * dx) + (dy * dy));
		moveAngle = Math.atan2(dy, dx);
	} // end calcSpeedAngle()

	//getMoveAngle() returns the sprite's motion angle in degrees
	public double getMoveAngle() {
		double degrees = moveAngle * 180 / Math.PI;
		degrees = degrees + 90;
		return degrees;	
	} // end getMoveangle()
	
	// setAngle(double degrees) changes both the image and motion angle. Allows both to go in same direction.
	public void setAngle(double degrees) {
		setMoveAngle(degrees);
		setImgAngle(degrees);
	} // end setAngle()
	
	// changeangleBy(double degrees) changes both the image and motion angles by degrees
	public void changeAngleBy(double degrees) {
		changeMoveAngle(degrees);
		changeImgAngle(degrees);	
	} // end changeMoveAngle

	// turnBy(degrees) calls changeangleBy(degrees)
	public void turnBy(double degrees) {
		changeAngleBy(degrees);	
	} // end turnBy(degrees)
	
	// addVector(degrees, thrust) - allows you to add a motion vector to the sprite.
	public void addVector(double degrees, double thrust) {
		double angle;		
		double newDX;
		double newDY;
		
		// offset angle by 90 degrees, because the original does this but I should ask andy why
		degrees -= 90;
		
		// convert degrees to radians
		angle = degrees * Math.PI / 180;
		
		// calculate new dx and dy
		newDX = (thrust * Math.cos(angle));
		newDY = (thrust * Math.sin(angle));
		dx += newDX;
		dy += newDY;
		
		calcSpeedAngle();
	} // end addVector(degrees, thrust)

	// setBoundAction(action) sets the boundAction to one of the integers supplied in both classes
	public void setBoundAction(int action) {
		boundAction = action;	
	} // end setBoundAction

	// collideswith(Sprite) - checks for collision with another sprite
	public boolean collidesWith(Sprite sprite) {
		// coollisions only happen when both are visible
		boolean collision = false;
		
		if (isVisible) {
			if (sprite.visible()) {
				// check borders
				int left = x - (width / 2);
				int top = y - (height / 2);
				int right = x + (width / 2);
				int bottom = y + (height / 2);
				int spriteLeft = sprite.x - (sprite.width / 2);
				int spriteTop = sprite.y - (sprite.height / 2);
				int spriteRight = sprite.x + (sprite.width / 2);
				int spriteBottom = sprite.y + (sprite.height / 2);
				
				// assume collision
				collision = true;
				
				// determine if there's a miss
				if ((left > spriteRight) || (top > spriteBottom) || (right < spriteLeft) || (bottom < spriteTop)) {
					collision = false;	
				} // end if
			} // end if		
		} // end if
		
		return collision;
	} // end collideswith(sprite)
	
	// visible tells us if the sprite is visible
	public boolean visible() {
		return isVisible;
	} // end visible()

	// distanceTo(sprite) determines the distance between two sprites, in pixels
	public int distanceTo(Sprite sprite) {
		int distance;
		distance = (int)(Math.sqrt((sprite.x - x)^2 + (sprite.y - y)^2));
		
		return distance;
	} // end distanceTo(sprite)
	
	// angleto(sprite) returns the angle (in degrees) from the current sprite to the given sprite
	public double angleTo(Sprite sprite) {
		double radians;
		double degrees;
		
		radians = Math.atan2((y - sprite.y),(x - sprite.x));
		degrees = radians * 180 / Math.PI;
		
		return degrees;
	} // end angleTo(sprite)

	// draw(Graphics g)
	public void update(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		x += dx;
		y += dy;
		
		checkBounds();
		
		// display is it hasn't been hidden		
		if (isVisible) {
			if (animation == false) {
				draw(g2, src);
			} else {
				draw(g2, sheet[animationCell][animationState]);		
			}
		}
	}
	
	// draw(Graphics g)
	public void update(Graphics g, int offX, int offY) {
		Graphics2D g2 = (Graphics2D) g;
		
		offsetX = offX;
		offsetY = offY;
		
		x += dx;
		y += dy;
		
		checkBounds();
		
		// display is it hasn't been hidden		
		if (isVisible) {
			if (animation == false) {
				draw(g2, src);
			} else {
				draw(g2, sheet[animationCell][animationState]);		
			}
		}
	}
	
	// draw (Graphics g, BufferedImage img)
	protected void draw(Graphics2D g, BufferedImage img) {	
		// make sure center is on origin
		drawX = x - (width / 2) - offsetX;
		drawY = y - (height / 2) - offsetY;		
		
		
		// if we don't need to bother with rotations let's not		
		if (rotated) {			
			
			// rotates our image
			transform.setToTranslation(drawX, drawY); // need to adjust this to the right x and y
			transform.rotate(imgAngle, width / 2, height / 2);
			
			// draw iwth our transforms
			g.drawImage(img, transform, null);
		} else {		
			g.drawImage(img, drawX, drawY, null);
		}
	} // end draw(Graphics g)
	
	// certian kinds of sprites can be stood on, but not the default
	public boolean standingOn(Sprite sprite) {
		return false;	
	}




}