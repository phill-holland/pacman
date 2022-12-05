package com.phillholland.app;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

class Pacman extends Animation implements KeyListener
{
	public boolean left = false,right = false;
	public boolean up = false,down = false;

	public long score = 0;
	public int lives = 3;

	private static final int leftOpenedMouth = 0;
	private static final int rightOpenedMouth = 1;
	private static final int upOpenedMouth = 2;
	private static final int downOpenedMouth = 3;

	private static final int deathOne = 4;
	private static final int deathTwo = 5;
	private static final int deathThree = 6;
	private static final int deathFour = 7;

	private static final int smallMan = 8;

	private static final int horizontialInc = 6;
	private static final int verticalInc = 6;

	private static final int totalSprites = 9;

	public boolean dieing = false;
	public boolean dead = false;
	public int dieingCount = 0;

	Images images;
	
	public Pacman()
	{
		super(totalSprites);	

		images = new Images(totalSprites);
		images.load("images/leftopenedmouth.jpg",leftOpenedMouth);
		images.load("images/rightopenedmouth.jpg",rightOpenedMouth);
		images.load("images/upopenedmouth.jpg",upOpenedMouth);
		images.load("images/downopenedmouth.jpg",downOpenedMouth);
		images.load("images/smallman.jpg",smallMan);
		images.load("images/death1.jpg",deathOne);
		images.load("images/death2.jpg",deathTwo);
		images.load("images/death3.jpg",deathThree);
		images.load("images/death4.jpg",deathFour);
		
		Sprites[leftOpenedMouth] = new Sprite(images.get(leftOpenedMouth));
		Sprites[rightOpenedMouth] = new Sprite(images.get(rightOpenedMouth));
		Sprites[upOpenedMouth] = new Sprite(images.get(upOpenedMouth));
		Sprites[downOpenedMouth] = new Sprite(images.get(downOpenedMouth));

		Sprites[deathOne] = new Sprite(images.get(deathOne));
		Sprites[deathTwo] = new Sprite(images.get(deathTwo));
		Sprites[deathThree] = new Sprite(images.get(deathThree));
		Sprites[deathFour] = new Sprite(images.get(deathFour));

		reset();
	}

	public void reset()
	{
		positionX = (Globals.mapWidth / 2) * Globals.tileWidth;
		positionY = (((Globals.mapHeight + 1) / 2) * Globals.tileHeight);

		currentIndex = rightOpenedMouth;
		dieing = false;
		dead = false;
		dieingCount = 0;
	}

	public void updatePosition(Map map)
	{
		if(dead==true) return;

		if(dieing==true)
		{
			dieingCount++;
			if((dieingCount>=0)&&(dieingCount<20)) currentIndex = deathOne;
			else if((dieingCount>=20)&&(dieingCount<40)) currentIndex = deathTwo;
			else if((dieingCount>=40)&&(dieingCount<60)) currentIndex = deathThree;
			else if((dieingCount>=60)&&(dieingCount<80)) currentIndex = deathFour;
			if(dieingCount>=80) dead = true;

			return;
		}

		boolean movement = false;

		int gridPosX = ((positionX + 15) / Globals.tileWidth);
		int gridPosY = ((positionY + 15) / Globals.tileHeight);

		if(left == true)
		{
			if(map.get(gridPosX - 1,gridPosY)==Map.blankIndex)
			{ 
				positionX -= horizontialInc; movement = true;
				currentIndex = leftOpenedMouth;
				positionY = gridPosY * Globals.tileHeight;
			}
			else
			{
				if(positionX > (gridPosX * Globals.tileWidth)) 
				{
					positionX -= horizontialInc;
					currentIndex = leftOpenedMouth;
					positionY = gridPosY * Globals.tileHeight;
				}
			}				
		}
		
		if((right == true)&&(movement==false))
		{
			if(map.get(gridPosX + 1,gridPosY)==Map.blankIndex)
			{
				positionX += horizontialInc;  movement = true;
				currentIndex = rightOpenedMouth;
				positionY = gridPosY * Globals.tileHeight;			
			}
			else
			{
				if(positionX < (gridPosX * Globals.tileWidth)) 
				{
					positionX += horizontialInc;
					currentIndex = rightOpenedMouth;
					positionY = gridPosY * Globals.tileHeight;
				}
			}			
		}
		
		if((up == true)&&(movement==false))
		{
			if(map.get(gridPosX,gridPosY - 1)==Map.blankIndex)
			{
				positionY -= verticalInc; movement = true;
				positionX = gridPosX * Globals.tileWidth;
				currentIndex = upOpenedMouth;			
			}
			else
			{
				if(positionY > (gridPosY * Globals.tileHeight)) 
				{
					positionY -= verticalInc;
					positionX = gridPosX * Globals.tileWidth;
					currentIndex = upOpenedMouth;			
				}
			}
		}
		
		if((down == true)&&(movement==false))
		{
			if(map.get(gridPosX,gridPosY + 1)==Map.blankIndex)
			{
				positionY += verticalInc;
				positionX = gridPosX * Globals.tileWidth;
				currentIndex = downOpenedMouth;
			}
			else
			{
				if(positionY < (gridPosY * Globals.tileHeight)) 
				{
					positionY += verticalInc;
					positionX = gridPosX * Globals.tileWidth;
					currentIndex = downOpenedMouth;
				}
			}
		}
	}

	public int checkPowerUpCollision(PowerUps PowerUps)
	{
		
		boolean collision = false;
		boolean powerPill = false;

		int gridPosX = ((positionX + 15) / (Globals.tileWidth / 2)) - 1;
		int gridPosY = ((positionY + 15) / (Globals.tileHeight / 2)) - 1;

		if(PowerUps.get(gridPosX,gridPosY)!=PowerUps.blankIndex)
		{	
			if(PowerUps.get(gridPosX,gridPosY)==PowerUps.powerPill) powerPill = true;
			PowerUps.set(PowerUps.blankIndex,gridPosX,gridPosY);
			score += PowerUps.scoreInc;
			collision = true;
		}

		if(PowerUps.get(gridPosX + 1,gridPosY + 1)!=PowerUps.blankIndex)
		{	
			if(PowerUps.get(gridPosX + 1,gridPosY + 1)==PowerUps.powerPill) powerPill = true;
			PowerUps.set(PowerUps.blankIndex,gridPosX + 1,gridPosY + 1);
			score += PowerUps.scoreInc;
			collision = true;
		}

		if(PowerUps.get(gridPosX,gridPosY + 1)!=PowerUps.blankIndex)
		{	
			if(PowerUps.get(gridPosX,gridPosY + 1)==PowerUps.powerPill) powerPill = true;
			PowerUps.set(PowerUps.blankIndex,gridPosX,gridPosY + 1);
			score += PowerUps.scoreInc;
			collision = true;
		}

		if(PowerUps.get(gridPosX + 1,gridPosY)!=PowerUps.blankIndex)
		{	
			if(PowerUps.get(gridPosX + 1,gridPosY)==PowerUps.powerPill) powerPill = true;
			PowerUps.set(PowerUps.blankIndex,gridPosX + 1,gridPosY);
			score += PowerUps.scoreInc;
			collision = true;
		}
		
		if(collision==true)
		{
			if(powerPill==true) return PowerUps.powerPill;
				else return PowerUps.standardPill;
		} else return PowerUps.blankIndex;
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			left = true;
			right = false;
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			right = true;
			left = false;
		}
		else if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			up = true;
			down = false;
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			down = true;
			up = false;
		}
    }        

	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			left = false;
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			right = false;
		}
		else if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			up = false;
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			down = false;
		}
	}

	public void drawInformation(Graphics g)
	{
		g.setFont(Globals.font);
		g.setColor(Color.blue);

		g.drawString("Score " + Integer.toString((int)score),25,640);

		for(int i=0;i<lives;i++)
		{
			g.drawImage(images.get(smallMan),485 + (i * 20),625,null);
		}
	}
}
