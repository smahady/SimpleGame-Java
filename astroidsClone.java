import java.io.*; 
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFrame;
import java.util.Random;
import java.util.Date;

public class astroidsClone extends Scene
{
	//Initial variables
	protected int MAX_LASER_AMT = 5;
	protected int STROID_NUM = 30;
	protected Sprite player;
	protected Sprite astroids[];
	protected Sprite lasers[];
	protected Sprite spriteBackground;
	protected double stroidAngleChange[];
	protected Sound soundsLaser[];
	protected Sound soundsSplosions[];
	protected int i = 0;
	protected int stroidDeaths = 0;
	protected int currStroid = 0;
	protected int currLaser = 0;
	protected int distTravelled = 0;
	protected Date laserTimerBegin = new Date();
	//Constants, constants everywhere
	protected int CANVAS_HEIGHT = 640;
	protected int CANVAS_WIDTH = 800;
	protected int STROID_DIM = 40;
	protected int STROID_INIT_SPD = 10;
	protected int LASER_HEIGHT = 30;
	protected int LASER_WIDTH = 2;
	protected int LASER_INTERVAL = 750;
	protected int PLAYER_WIDTH = 50;
	protected int PLAYER_HEIGHT = 70;
	private Random rando;
	private Joystick joy;

	public astroidsClone()
	{
		//Start the party
		super(800, 640);
		spriteBackground = new Sprite(this, "sprites/spaceBaseBackground.png", 2400, 1920);
		spriteBackground.setMoveAngle(0);
		spriteBackground.setSpeed(0);
		player = new Sprite(this, "sprites/spaceBoat.png", PLAYER_WIDTH, PLAYER_HEIGHT);
		player.setPosition(CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
		player.setSpeed(0);
		soundsLaser = new Sound[3];
		soundsLaser[0] = new Sound("music/Laser1.wav");
		soundsLaser[1] = new Sound("music/Laser2.wav");
		soundsLaser[2] = new Sound("music/Laser3.wav");
		soundsSplosions =  new Sound[4];
		soundsSplosions[0] = new Sound("music/astrDie1.wav");
		soundsSplosions[1] = new Sound("music/astrDie2.wav");
		soundsSplosions[2] = new Sound("music/astrDie3.wav");
		soundsSplosions[3] = new Sound("music/playerDeath.wav");

		int decider;
		rando = new Random();
		astroids = new Sprite[STROID_NUM];
		lasers = new Sprite[MAX_LASER_AMT];
		stroidAngleChange = new double[STROID_NUM];
		for(int i = 0; i < STROID_NUM; i++)
		{
			decider = rando.nextInt(2);
			if((decider * 3) < 1)
				astroids[i] = new Sprite(this, "sprites/stroid1.png", STROID_DIM, STROID_DIM);
			else if((decider * 3) < 2)
				astroids[i] = new Sprite(this, "sprites/stroid2.png", STROID_DIM, STROID_DIM);
			else
				astroids[i] = new Sprite(this,"sprites/stroid3.png", STROID_DIM, STROID_DIM);
			stroidAngleChange[i] = ((decider * 4) - 2);
			astroids[i].setPosition(0, 0);
			astroids[i].hide();
		}
		for(int i = 0; i < MAX_LASER_AMT; i++)
		{
			lasers[i] = new Sprite(this,"sprites/lasor.png", LASER_HEIGHT, LASER_WIDTH);
			lasers[i].setPosition(0,0);
		}
		joy = new Joystick(this);
		joy.setPosition(700,540);
		super.start();
	}
	
	public int findUnusedStroid()	
	{//Find unused astroid to spawn
		for(int j = 0; j < STROID_NUM; j++)
		{
			if(!astroids[j].visible())
				return j;
		}
		return -1;
	}

	public void update(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		this.requestFocus();
		//Read movement commands, move everything around
		if(pressed)
		{
			//Joystick movement
			int diffX = joy.getJoyDiffX();
			int diffY = joy.getJoyDiffY();
			double radius = Math.pow((diffX*diffX + diffY*diffY), .5);
			if(diffY > 0)
				player.setImgAngle(Math.acos(diffX/radius)/Math.PI*180);
			else
				player.setImgAngle(Math.acos(diffX/radius)/Math.PI*180*-1);
			spriteBackground.setDX(joy.getJoyDiffX()/5);
			spriteBackground.setDY(joy.getJoyDiffY()/5);
			int i = 0;
			while(i < STROID_NUM)
			{//Adjust astroids velocity away from ship angle 
				if(astroids[i].visible())
				{
					astroids[i].dx = (astroids[i].dx + joy.getJoyDiffX()/5);
					astroids[i].dy = (astroids[i].dy + joy.getJoyDiffY()/5);
				}
				i++;
			}//End 'stroid velocity adjustment
		}
		//Keyboard movement
		if(keysDown[K_A] || keysDown[K_LEFT])
		{
			player.changeImgAngle(-5.5);
			//playerAcc.changeImgAngleBy(-5.5);
		}
		if(keysDown[K_D] || keysDown[K_RIGHT])
		{
			player.changeImgAngle(5.5);
			//playerAcc.changeImgAngleBy(5.5);
		}
		if(keysDown[K_W] || keysDown[K_UP])
		{//Adjust space's velocities away from ships current angle if read W or up
			spriteBackground.dx = (spriteBackground.dx * .97) + (1 * Math.cos(player.getImgAngle()/180 * Math.PI));
			spriteBackground.dy = (spriteBackground.dy * .97) + (1 * Math.sin(player.getImgAngle()/180 * Math.PI));
			i = 0;
			while(i < STROID_NUM)
			{//Adjust astroids velocity away from ship angle 
				if(astroids[i].visible())
				{
					astroids[i].dx = (astroids[i].dx * .97) + (1 * Math.cos(player.getImgAngle()/180 * Math.PI));
					astroids[i].dy = (astroids[i].dy * .97) + (1 * Math.sin(player.getImgAngle()/180 * Math.PI));
				}
				i++;
			}//End 'stroid velocity adjustment
		}//End space velocity adjustment and up key read
		
		if(keysDown[K_S] || keysDown[K_DOWN])
		{//Same as above, but in opposite direction (read down or s keys)
			spriteBackground.dx = (spriteBackground.dx * .97) + (-1 * Math.cos(player.getImgAngle()/180 * Math.PI));
			spriteBackground.dy = (spriteBackground.dy * .97) + (-1 * Math.sin(player.getImgAngle()/180 * Math.PI));
			i = 0;
			while(i < STROID_NUM)
			{
				if(astroids[i].visible())
				{
					astroids[i].dx = (astroids[i].dx * .97) + (-1 * Math.cos(player.getImgAngle()/180 * Math.PI));
					astroids[i].dy = (astroids[i].dy * .97) + (-1 * Math.sin(player.getImgAngle()/180 * Math.PI));
				}
				i++;
			}
		}//End read down key
		
		if(keysDown[K_SPACE])
		{//Read space key, make "pew pew"s.
			Date laserTimerEnd = new Date();
			if(laserTimerEnd.getTime()-laserTimerBegin.getTime() > LASER_INTERVAL)
			{
				laserTimerBegin = laserTimerEnd;
				int laserPosX = (int)(CANVAS_WIDTH/2 + (PLAYER_WIDTH * (-1 * Math.cos(player.getImgAngle()/180 * Math.PI))));
				int laserPosY = (int)(CANVAS_HEIGHT/2 + (PLAYER_HEIGHT * (-1 * Math.sin(player.getImgAngle()/180 * Math.PI))));
				lasers[currLaser].show();
				lasers[currLaser].setPosition(laserPosX, laserPosY);
				lasers[currLaser].setAngle((player.getImgAngle()/180*Math.PI)/(Math.PI * 2) * 360 + 90);
				lasers[currLaser].dx = (15 * (-1 * Math.cos(player.getImgAngle()/180 * Math.PI)));
				lasers[currLaser].dy = (15 * (-1 * Math.sin(player.getImgAngle()/180 * Math.PI)));
				if((currLaser + 1) < MAX_LASER_AMT)
					currLaser++;
				else
					currLaser = 0;
				int decider = rando.nextInt(3);
				if(decider < 1)
					soundsLaser[0].playSound();
				else if(decider < 2)
					soundsLaser[1].playSound();
				else
					soundsLaser[2].playSound();
			}
		}//End shoot
		
		i = 0;
		while(i < STROID_NUM)
		{
			if(astroids[i].visible())
			{//Bounds checking.
				if((astroids[i].x + astroids[i].width/2) > CANVAS_WIDTH || (astroids[i].x - astroids[i].width/2) < 0)
				{
					astroids[i].hide();
				}
				if((astroids[i].y + astroids[i].height/2) > CANVAS_HEIGHT || (astroids[i].y - astroids[i].height/2) < 0)
				{
					astroids[i].hide();
				}
			}
			i++;
		}//End astroid bounds checks
		
		if((Math.random() < (.1 + distTravelled/100000)))
		{//Spawn astroid if player is unlucky
			if(astroids[currStroid].visible())
			{//Make sure an astroid CAN be spawned
				currStroid = findUnusedStroid();
			}
			if(currStroid != -1)
			{//Decide where it spawns and spawn it
				double decider = Math.random();
				if(decider < .25)
				{//Spawn on top
					astroids[currStroid].show();
					astroids[currStroid].setPosition((int)(CANVAS_WIDTH * Math.random()), (int)(STROID_DIM/2));
					astroids[currStroid].dx = ((Math.random() * 6) - 3) + spriteBackground.dx;
					astroids[currStroid].dy = (Math.random() * 4) + spriteBackground.dy;
				}
				else if(decider < .5)
				{//Spawn on left
					astroids[currStroid].show();
					astroids[currStroid].setPosition((int)(STROID_DIM/2), (int)(CANVAS_HEIGHT * Math.random()));
					astroids[currStroid].dx = (Math.random() * 4) + spriteBackground.dx;
					astroids[currStroid].dy = ((Math.random() * 6) - 3) + spriteBackground.dy;
				}
				else if(decider < .75)
				{//Spawn on bottom
					astroids[currStroid].show();
					astroids[currStroid].setPosition((int)(CANVAS_WIDTH * Math.random()), (int)(CANVAS_HEIGHT - (STROID_DIM/2)));
					astroids[currStroid].dx = ((Math.random() * 6) - 3) + spriteBackground.dx;
					astroids[currStroid].dy = -1 * (Math.random() * 4) + spriteBackground.dy;
				}
				else
				{//Spawn on right
					astroids[currStroid].show();
					astroids[currStroid].setPosition((int)(CANVAS_WIDTH - (STROID_DIM/2)), (int)(CANVAS_HEIGHT * Math.random()));
					astroids[currStroid].dx = -1 * (Math.random() * 4) + spriteBackground.dx;
					astroids[currStroid].dy = ((Math.random() * 6) - 3) + spriteBackground.dy;
				}
			}
		}//End astroid spawn roulette
		
		i = 0;
		while(i < MAX_LASER_AMT)
		{//Bounds checking for lasers.
			if((lasers[i].x + LASER_WIDTH/2) > CANVAS_WIDTH || (lasers[i].x - LASER_WIDTH/2) < 0)
			{
				lasers[i].hide();
			}
			if((lasers[i].y + LASER_HEIGHT/2) > CANVAS_HEIGHT || (lasers[i].y - LASER_HEIGHT/2) < 0)
			{
				lasers[i].hide();
			}
			i++;
		}//End laser bounds checks
		
		//Update visuals
		this.clear(g2);
		spriteBackground.update(g2);
		player.update(g2);
		//playerAcc.update();
		i = 0;
		while(i < STROID_NUM)
		{
			//Rotate astroids
			astroids[i].changeImgAngle(stroidAngleChange[i]);
			astroids[i].update(g2);
			i++;
		}
		if(pressed)
		{
			i = 0;
			while(i < STROID_NUM)
			{//Adjust astroids velocity away from ship angle 
				if(astroids[i].visible())
				{
					astroids[i].dx = (astroids[i].dx - joy.getJoyDiffX()/5);
					astroids[i].dy = (astroids[i].dy - joy.getJoyDiffY()/5);
				}
				i++;
			}//End 'stroid velocity adjustment
		}
		i = 0;
		while(i < MAX_LASER_AMT)
		{
			lasers[i].update(g2);
			i++;
		}//End Update visuals
		/*
		i = 0;
		while(i < STROID_NUM)
		{//Collision detection astroids with player
			if(player.collidesWith(astroids[i]))
			{
				soundsSplosions[3].playSound();
				alert("You were hit by an astroid, try again.");
				window.location.replace('someGame.html');
			}
			i++;
		}//End player collision detection
		*/
		for(int j = 0; j < MAX_LASER_AMT; j++)
		{//Laser collision detection with astroids
			for(i = 0; i < STROID_NUM; i++)
			{
				if(astroids[i].visible())
				{
					if(lasers[j].collidesWith(astroids[i]))
					{
						astroids[i].hide();
						lasers[j].hide();
						stroidDeaths++;
						soundsSplosions[rando.nextInt(2)].playSound();
					}
				}
			}
		}//End laser collisions
		joy.update(g2);
	}//End updating

	public static void main(String[] args)
	{
		Frame thisFrame = new Frame();
		thisFrame.setSize(800, 640);
		thisFrame.setVisible(true);
		thisFrame.setLayout(null);
		thisFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}        
		});
		astroidsClone game = new astroidsClone();
		thisFrame.add(game);
	}
}