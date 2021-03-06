import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/*
A note to those who use this: THIS MUST HAVE JFRAME LAYOUT OF NULL TO WORK
Any others wont make sense or work.
Right now, the joystick uses magic numbers for determining top position. These are dimensions of bottom/2.
An improvement might be the ability to use custom sprites and dimensions.
*/

public class Joystick 
{
	protected Sprite top;
	protected Sprite bottom;
	protected JPanel joyPanel;
	protected int posX = 0;
	protected int posY = 0;
	//protected boolean pressed = false; // now in Scene
	protected Scene parentScene;

	// delete if this approach isn't better
	public Joystick(Scene scene)
	{
		parentScene = scene;		
		
		//Create joystick sprite and add listener
		top = new Sprite(parentScene, "sprites/joyTop.png", 60, 60);
		bottom = new Sprite(parentScene, "sprites/joyBottom.png", 100, 100);
		
		joyPanel = new JPanel();
		joyPanel.setSize(100, 100);
		parentScene.add(joyPanel);

	}
	
	
	public void update(Graphics g2)
	{
		if (parentScene.mouseUpdate == true) {
			top.setPosition(posX, posY);
			parentScene.mouseUpdate = false;
		}		
		
		parentScene.virtKeysDown[37] = false;
		parentScene.virtKeysDown[38] = false;
		parentScene.virtKeysDown[39] = false;
		parentScene.virtKeysDown[40] = false;

		
		//Move top in relation to mouse position
		if(parentScene.pressed)
		{
			double currX = MouseInfo.getPointerInfo().getLocation().getX();
			double currY = MouseInfo.getPointerInfo().getLocation().getY();
			double dist = Math.pow(((currX-posX)*(currX-posX) + (currY-posY)*(currY-posY)), .5);
			

			if(dist > 50)
			{
				//Bound top joystick to bottom joystick (aesthetic)
				double theta = Math.acos((currX-posX)/dist);
				if(currX < posX && currY < posY)
				{
					//Quadrant II (top left)
					currX = posX + 50*Math.cos(theta);
					currY = posY - 50*Math.sin(theta);
					if (parentScene.virtKeys) {
						if (theta < (.75 * Math.PI)){
							parentScene.virtKeysDown[38] = true;						
						} else {
							parentScene.virtKeysDown[37] = true;						
						}
					}
				}
				else if(currX > posX && currY < posY)
				{
					//Quadrant I (topright)
					currX = posX + 50*Math.cos(theta);
					currY = posY - 50*Math.sin(theta);
					if (parentScene.virtKeys) {
						if (theta > (.25 * Math.PI)){
							parentScene.virtKeysDown[38] = true;						
						} else {
							parentScene.virtKeysDown[39] = true;						
						}
					}
				}
				else
				{
					currX = 50*Math.cos(theta) + posX;
					currY = 50*Math.sin(theta) + posY;
					if (theta < (.25 * Math.PI)){
						parentScene.virtKeysDown[39] = true;						
					} else if (theta > (.75 * Math.PI)){
						parentScene.virtKeysDown[37] = true;						
					} else {
						parentScene.virtKeysDown[40] = true;						
					}

				}
				
			}
			top.setPosition((int)currX, (int)currY);
		}
		

		bottom.update(g2);
		top.update(g2);
	}
	
	public void setPosition(int newPosX, int newPosY)
	{
		posX = newPosX;
		posY = newPosY;
		//joyPanel.setBounds(posX-50, posY-50, 100, 100);
		top.setPosition(posX, posY);
		bottom.setPosition(posX, posY);
	}
	
	public int getMouseDiffX()
	{
		//return x position relative to start position
		if(parentScene.pressed)
			return posX - (int)MouseInfo.getPointerInfo().getLocation().getX();
		else
			return posX;
	}
	public int getMouseDiffY()
	{
		//return y position relative to start position
		if(parentScene.pressed)
			return posY - (int)MouseInfo.getPointerInfo().getLocation().getY();
		else
			return posY;
	}
	public int getJoyDiffX()
	{
		//return top x position relative to bottoms position
		return (posX - top.x);
	}
	public int getJoyDiffY()
	{
		//return top y position relative to bottoms position
		return (posY - top.y);
	}
}