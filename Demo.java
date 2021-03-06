import java.io.*; 
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;				
import javax.imageio.ImageIO;				
import java.io.File;							

public class Demo extends Scene
{
	private int playerState;
	private int score;
	private int gameSpeed;
	private int angle;
	private Sprite cat;
	private Background background;
	private Background middleground;
	private Background foreground;
	private Sprite blocks[] = new Sprite[70];
	private Sprite tops[] = new Sprite[35];
	private Ramp ramp[] = new Ramp[3];
	private Sprite sprite[] = new Sprite[3];
	private Sprite stairs[] = new Sprite[4];
	private Sprite mouse;
	private Sprite crystal;	
	private int offsetX;
	private int offsetY;
	
	// states
	private int jumpright = 0;
	private int jumpleft = 1;
	private int standright = 2;
	private int standleft = 3;
	private int walkright = 4;
	private int walkleft = 5;
	
	
	//private Sound BOOM;
	private Joystick joy;
	

	public Demo(){
		super(800, 800);
		
		String spriteMaker[][];
				
		playerState = 0;
		score = 0;
		gameSpeed = 1;
		angle = 0;
		
		offsetX = 0;
		offsetY = 0;
		
		super.setBG(new Color(170, 170, 170));
		
		
		// Artwork created by Luis Zuno (@ansimuz) from opengameart.org
		// License for Everyone.
		background = new Background(this, "sprites/background.png", 1066, 800, .25, 0);
		middleground = new Background(this, "sprites/middleground.png", 1066, 800, .5, 0);
		
		//background.setPosition(2133, 400); // we no longer need to setPosition this
		foreground = new Background(this, "sprites/foreground.png", 1422, 800, 2, 0);
		//foreground.setPosition(711, 400); // we no longer need to setPosition this
		
		// change the size of our scene to match our bg
		changeBoundSize(4096, 1632);
		
		// Cat sprites from https://opengameart.org/content/cat-sprites by Shepardskin
		cat = new Sprite(this, "sprites/catsheetnew.png", 480, 240);
		cat.setPosition(100,800);
		
		// bricks by Florian R. A. Angermeier (fraang) is licensed under the Creative Commons Attribution - ShareAlike 3.0 Unported License.
		// from openGameArt.org
		for (int i = 0; i < 68; i++) {
			blocks[i] = new Block(this, "sprites/brick-moss-subsea.png", 64, 64);
		}	

		blocks[0].setPosition(32,1568);
		blocks[1].setPosition(96,1568);
		blocks[2].setPosition(160,1568);
		blocks[3].setPosition(224,1568);
		blocks[4].setPosition(288,1568);
		blocks[5].setPosition(352,1568);
		blocks[6].setPosition(416,1568);
		blocks[7].setPosition(480,1568);
		blocks[8].setPosition(544,1568);
		blocks[9].setPosition(608,1568);
		blocks[10].setPosition(672,1568);
		blocks[11].setPosition(736,1568);
		blocks[12].setPosition(800,1568);
		blocks[13].setPosition(864,1568);
		blocks[14].setPosition(928,1568);

		blocks[15].setPosition(1120,1568);
		blocks[16].setPosition(1184,1568);
		blocks[17].setPosition(1248,1568);
		blocks[18].setPosition(1312,1568);
		blocks[19].setPosition(1376,1568);
		blocks[20].setPosition(1440,1568);
		blocks[21].setPosition(1504,1568);
		blocks[22].setPosition(1568,1568);
		blocks[23].setPosition(1632,1568);
		blocks[24].setPosition(1696,1568);
		blocks[25].setPosition(1760,1568);
		blocks[26].setPosition(1824,1568);
		blocks[27].setPosition(1888,1568);
		blocks[28].setPosition(1952,1568);
		blocks[29].setPosition(2016,1568);
		blocks[30].setPosition(2080,1568);
		blocks[31].setPosition(2144,1568);
		
		
		blocks[32].setPosition(2208,1440);
		blocks[33].setPosition(2272,1440);
		blocks[34].setPosition(2336,1440);
		blocks[35].setPosition(2400,1440);
		
		blocks[36].setPosition(2464,1312);
		blocks[37].setPosition(2528,1312);
		blocks[38].setPosition(2592,1312);
		blocks[39].setPosition(2656,1312);
		
		blocks[40].setPosition(2720,1440);
		blocks[41].setPosition(2784,1440);
		blocks[42].setPosition(2848,1440);
		blocks[43].setPosition(2912,1440);

		blocks[44].setPosition(2976,1568);
		blocks[45].setPosition(3040,1568);

		blocks[46].setPosition(3232,1568);
		blocks[47].setPosition(3296,1568);
		blocks[48].setPosition(3360,1568);
		blocks[49].setPosition(3424,1568);
		blocks[50].setPosition(3488,1568);
		blocks[51].setPosition(3552,1568);
		blocks[52].setPosition(3616,1568);
		blocks[53].setPosition(3680,1568);
		blocks[54].setPosition(3744,1568);
		blocks[55].setPosition(3808,1568);
		blocks[56].setPosition(3872,1568);
		blocks[57].setPosition(3936,1568);
		blocks[58].setPosition(4000,1568);
		blocks[59].setPosition(4064,1568);
		
		blocks[60].setPosition(544,928);
		blocks[61].setPosition(608,928);
		blocks[62].setPosition(672,928);
		blocks[63].setPosition(736,928);
		blocks[64] = new Platform(this, "sprites/brick-moss-subsea.png", 64, 64);
		blocks[64].setPosition(800,928);
		blocks[65] = new Platform(this, "sprites/brick-moss-subsea.png", 64, 64);
		blocks[65].setPosition(864,928);
		
	
		// make an array to try out our tiled constructor
		spriteMaker = new String[3][1];
		for (int i = 0; i < 3; i++) {
			spriteMaker[i][0] = "sprites/brick-moss-subsea.png";		
		}
		blocks[66] = new Block(this, spriteMaker, 64, 64);
		blocks[66].setCPos(576, 1056);
		blocks[67] = new Block(this, spriteMaker, 64, 64);
		blocks[67].setCPos(960, 1056);
		blocks[68] = new Platform(this, spriteMaker, 64, 64);
		blocks[68].setCPos(768, 1056);
		
		// make an array to try out our tiled constructor
		spriteMaker = new String[3][3];
		spriteMaker[0][0] = "sprites/clear.png";		
		spriteMaker[0][1] = "sprites/clear.png";		
		spriteMaker[0][2] = "sprites/slope.png";		
		spriteMaker[1][0] = "sprites/clear.png";		
		spriteMaker[1][1] = "sprites/slope.png";		
		spriteMaker[1][2] = "sprites/brick-moss-subsea.png";		
		spriteMaker[2][0] = "sprites/slope.png";		
		spriteMaker[2][1] = "sprites/brick-moss-subsea.png";		
		spriteMaker[2][2] = "sprites/brick-moss-subsea.png";		
		blocks[69] = new Ramp(this, spriteMaker, 64, 64, SLOPE_RIGHT);	
		blocks[69].setCPos(1152, 864);
		

		// stairs array starts at 480, 1568
		stairs[0] = new Sprite(this, "sprites/stairs-bottom-upright.png", 32, 64);		
		stairs[0].setCPos(448, 1472);
		stairs[1] = new Sprite(this, "sprites/stairs-middle-upright.png", 32, 64);		
		stairs[1].setCPos(480, 1440);
		stairs[2] = new Sprite(this, "sprites/stairs-middle-upright.png", 32, 64);		
		stairs[2].setCPos(512, 1408);
		stairs[3] = new Stairs(this, "sprites/longstairsright.png", 384, 384, SLOPE_RIGHT);		
		stairs[3].setCPos(512, 1056);

		
		for (int i = 0; i < 20; i++) {
			tops[i] = new Sprite(this, "sprites/top.png", 48, 21);
			tops[i].setPosition((24 + (48*i)),702);
		}
		for (int i = 20; i < 28; i++) {
			tops[i] = new Sprite(this, "sprites/top.png", 48, 21);
			tops[i].setPosition((24 + (48*i)),507);
		}
		for (int i = 28; i < 35; i++) {
			tops[i] = new Sprite(this, "sprites/top.png", 48, 21);
			tops[i].setPosition((24 + (48*i)),702);
		}
		
		// slope from Green Platform Tileset by Mauriku on opengameArt.org
		ramp[0] = new Ramp(this, "sprites/slope.png", 64, 64, SLOPE_RIGHT);
		ramp[1] = new Ramp(this, "sprites/slope.png", 64, 64, SLOPE_RIGHT);
		ramp[2] = new Ramp(this, "sprites/slope.png", 64, 64, SLOPE_RIGHT);
		ramp[0].setPosition(800, 681);
		ramp[1].setPosition(864, 617);
		ramp[2].setPosition(928, 553);
		
		sprite[0] = new Block(this, "sprites/brick-moss-subsea.png", 64, 64);
		sprite[1] = new Block(this, "sprites/brick-moss-subsea.png", 64, 64);
		sprite[2] = new Block(this, "sprites/brick-moss-subsea.png", 64, 64);
		sprite[0].setPosition(864, 681);
		sprite[1].setPosition(928, 681);
		sprite[2].setPosition(928, 617);
		
		// crystal by FunwithPixels		
		// https://opengameart.org/content/red-crystal-cluster
		crystal = new Sprite(this, "sprites/redcrystal.png", 64, 64);
		crystal.setPosition(1200, 480);
		
		
		// Mouse sprites from https://opengameart.org/content/loyalty-lies-monsters-chaos-monster-03 by Emerald
		mouse = new Sprite(this, "sprites/ratsheet.png", 148, 74);
		mouse.loadAnimation(148, 74, 74, 74);
		mouse.generateAnimationCycles();
		mouse.setPosition(3700, 1510);

		
		cat.loadAnimation(480, 240, 80, 60);
		cat.generateAnimationCycles();
		cat.setAnimationSpeed(100);
		cat.setBoundAction(STOP);
		cat.playAnimation();
		
		// sound test
		//BOOM = new Sound("music/boom5.wav");

		// joystick demo
		virtKeys = true;
      joy = new Joystick(this);
      joy.setPosition(400,300);
		
		super.start();
		
		//BOOM.playSound();
	}
	
	// this is similar to update in simpleGame.js
   public void update(Graphics g) 
   {
   	
   	
		Graphics2D g2 = (Graphics2D) g;
		
		background.update(g, offsetX, offsetY);
		middleground.update(g, offsetX, offsetY);
		for (int i = 0; i < tops.length; i++) {
			tops[i].update(g, offsetX, offsetY);
		}
		for (int i = 0; i < blocks.length; i++) {
			blocks[i].update(g, offsetX, offsetY);
		}
		for (int i = 0; i < stairs.length; i++) {
			stairs[i].update(g, offsetX, offsetY);
		}
		for (int i = 0; i < sprite.length; i++) {
			sprite[i].update(g, offsetX, offsetY);
		}
		for (int i = 0; i < ramp.length; i++) {
			ramp[i].update(g, offsetX, offsetY);
		}
		
		angle++;
		crystal.setAngle(angle);
		crystal.update(g, offsetX, offsetY);
		
		for (int i = 0; i < blocks.length; i++) {
			blocks[i].collidesWith(cat);
			if (blocks[i].standingOn(cat)){
				if (playerState == jumpright) {
					playerState = standright;	
					cat.setSpeed(0);
					cat.setCurrentCycle(1);
					cat.pauseAnimation();
				}	
				if (playerState == jumpleft) {
					playerState = standleft;	
					cat.setSpeed(0);
					cat.setCurrentCycle(3);
					cat.pauseAnimation();
				}			
			}
		}
		
		for (int i = 0; i < stairs.length; i++) {
			stairs[i].collidesWith(cat);
			if (stairs[i].standingOn(cat)){
				if (playerState == jumpright) {
					playerState = standright;	
					cat.setSpeed(0);
					cat.setCurrentCycle(1);
					cat.pauseAnimation();
				}	
				if (playerState == jumpleft) {
					playerState = standleft;	
					cat.setSpeed(0);
					cat.setCurrentCycle(3);
					cat.pauseAnimation();
				}			
			}
		}
		
		for (int i = 0; i < ramp.length; i++) {
			ramp[i].collidesWith(cat);

			if (ramp[i].standingOn(cat)){
				if (playerState == jumpright) {
					playerState = standright;	
					cat.setSpeed(0);
					cat.setCurrentCycle(1);
					cat.pauseAnimation();
				}	
				if (playerState == jumpleft) {
					playerState = standleft;	
					cat.setSpeed(0);
					cat.setCurrentCycle(3);
					cat.pauseAnimation();
				}			
			}
		}

		
		
		
		if (crystal.collidesWith(cat)) {        	
        	Font font = new Font("Arial", Font.PLAIN, 60);
        	g.setFont(font);
        	g.setColor(new Color(255, 255, 255));
			g.drawString("You Lose", 300, 100);
			stop();			
		
		}
		
		if (mouse.collidesWith(cat)) {        	
        	Font font = new Font("Arial", Font.PLAIN, 60);
        	g.setFont(font);
        	g.setColor(new Color(255, 255, 255));
			g.drawString("You Win", 300, 100);
			stop();			
		
		}
		
		
		if (cat.y > 1632) {        	
        	Font font = new Font("Arial", Font.PLAIN, 60);
        	g.setFont(font);
        	g.setColor(new Color(255, 255, 255));
			g.drawString("You Lose", 300, 100);
			stop();			
		}

		if(joy != null)
			joy.update(g);

		
		
		checkKeys();

		checkMove();
		
		
		mouse.update(g, offsetX, offsetY);
		
		cat.update(g, offsetX, offsetY);
		
		foreground.update(g, offsetX, offsetY);
		

   }
   
   // checkMove 
   public void checkMove(){
		//if (playerState == jumpright || playerState == jumpleft) {
		// actually, let's always have gravity on
		cat.addVector(180, 2);	
		//}
	
		
		// move the offset if needed
		if (cat.drawX > 500 && offsetY < 3296) {
			offsetX += 6;		
		}
		if (cat.drawX < 300 && offsetX > 0) {
			offsetX -= 6;		
		}

		if (cat.drawY > 500 && offsetY < 800) {
			offsetY += 6;		
		}
		if (cat.drawY < 300 && offsetY > 0) {
			offsetY -= 6;		
		}
		
		
		if (cat.x < 100) {
			cat.setX(100);		
		}
	}   
	
	// checkKeys()
	public void checkKeys(){
		boolean doAnything = false;		
		
		// move right		
		if (keysDown[K_RIGHT]) {
			cat.setDX(7);
			if (playerState == jumpleft) {
				playerState = jumpright;
				cat.setCurrentCycle(0);
			}	
			if (playerState == standleft || playerState == standright || playerState == walkleft) {
				playerState = walkright;
				cat.playAnimation();
				cat.setCurrentCycle(0);
			}		
		} else if (playerState == walkright) {
			playerState = standright;
			cat.setSpeed(0);
			cat.pauseAnimation();
			cat.setCurrentCycle(1);		
		}
		
		// move left		
		if (keysDown[K_LEFT]) {
			cat.setDX(-7);
			if (playerState == jumpright) {
				playerState = jumpleft;
				cat.setCurrentCycle(2);	
			}	
			if (playerState == standleft || playerState == standright || playerState == walkright) {
				playerState = walkleft;
				cat.playAnimation();
				cat.setCurrentCycle(2);
			}		
		} else if (playerState == walkleft) {
			playerState = standleft;
			cat.setSpeed(0);
			cat.pauseAnimation();
			cat.setCurrentCycle(3);		
		}
		
		// jump		
		if (keysDown[K_SPACE]) {
			if (playerState == walkleft || playerState == walkright || playerState == standright || playerState == standleft) {
				cat.addVector(0, 35);		
			}
			
			if (playerState == walkleft || playerState == standleft) {
				playerState = jumpleft;	
				cat.setCurrentCycle(2);	
				cat.playAnimation();	
			} 
			
			if (playerState == walkright || playerState == standright) {
				playerState = jumpright;
				cat.setCurrentCycle(0);	
				cat.playAnimation();		
			} 


		
		}
		
		
		

	}

}



	
