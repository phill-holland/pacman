package com.phillholland.app;

import java.awt.image.*;
import java.awt.*;
import java.applet.*;

class Ghost extends Animation
{
	public boolean left = false,right = true;
	public boolean up = false,down = false;

	public static final int redGhost = 0;
	public static final int greenGhost = 1;
	public static final int pinkGhost = 2;
	public static final int orangeGhost = 3;
	public static final int blueGhost = 4;

	private static final int ghostEyes = 5;

	private static Images images;
	private static final int totalSprites = 6;

	private static int constructed = 0;

	private int type = 0;

	public static final int stateTrapped = 0;
	public static final int stateAlive = 1;
	public static final int stateAliveAndScared = 2;
	public static final int stateDead = 3;	

	private int state = stateTrapped;

	private int aliveAndScaredTimer = 0;
	private final static int startFlashingTimer = 50;
	private final static int scaredTimer = 150;

	private long stationaryDelay = 0;
	private long stationaryCount = 0;

	private static final int horizontialInc = 6;
	private static final int verticalInc = 6;

	private int recentDecision = 0;
	
	public Ghost(int _type)
	{
		super(totalSprites);

		type = _type;
		currentIndex = type;

		if(constructed==0)
		{
			constructed++;
			images = new Images(totalSprites);
			images.load("images/redghost.jpg",redGhost);
			images.load("images/greenghost.jpg",greenGhost);
			images.load("images/pinkghost.jpg",pinkGhost);
			images.load("images/blueghost.jpg",blueGhost);
			images.load("images/orangeghost.jpg",orangeGhost);
			images.load("images/ghosteyes.jpg",ghostEyes);
		}

		Sprites[redGhost] = new Sprite(images.get(redGhost));
		Sprites[greenGhost] = new Sprite(images.get(greenGhost));
		Sprites[pinkGhost] = new Sprite(images.get(pinkGhost));
		Sprites[blueGhost] = new Sprite(images.get(blueGhost));
		Sprites[orangeGhost] = new Sprite(images.get(orangeGhost));
		Sprites[ghostEyes] = new Sprite(images.get(ghostEyes));		

		reset();
	}

	public void reset()
	{
		aliveAndScaredTimer = 0;
		stationaryCount = 0;

		if(type == redGhost)
		{
			positionX = ((Globals.mapWidth / 2) * Globals.tileWidth) - 30;
			positionY = ((Globals.mapHeight / 2) * Globals.tileHeight) - 30;
			stationaryDelay = 20;
		}
		else if(type == greenGhost)
		{
			positionX = ((Globals.mapWidth / 2) * Globals.tileWidth);
			positionY = ((Globals.mapHeight / 2) * Globals.tileHeight) - 30;
			stationaryDelay = 50;
		}
		else if(type == pinkGhost)
		{
			positionX = ((Globals.mapWidth / 2) * Globals.tileWidth) + 20;
			positionY = ((Globals.mapHeight / 2) * Globals.tileHeight) - 30;
			stationaryDelay = 80;
		}
		else if(type == orangeGhost)
		{
			positionX = ((Globals.mapWidth / 2) * Globals.tileWidth) - 20;
			positionY = ((Globals.mapHeight / 2) * Globals.tileHeight) - 30;
			stationaryDelay = 110;
		}

		currentIndex = type;
		state = stateTrapped;
		
		up = false; down = false; left = false; right = true;
	}

	public void updatePosition(Map _map)
	{
		int gridPosX,gridPosY;

		if(state==stateTrapped)
		{
			stationaryCount++;
			if(stationaryCount>=stationaryDelay)
			{
				stationaryCount = 0;
				state = stateAlive;				
			}
		}
		
		if(state==stateAliveAndScared)
		{
			aliveAndScaredTimer--;
			if(aliveAndScaredTimer<startFlashingTimer)
			{
				if((aliveAndScaredTimer % 6)==0)
				{
					if(currentIndex == type) currentIndex = blueGhost;
						else if(currentIndex == blueGhost) currentIndex = type;
					
				}
			}			
			
			if(aliveAndScaredTimer<=0)
			{
				state = stateAlive;
				currentIndex = type;
			}
		}

		if((state==stateAlive)||(state==stateAliveAndScared))
		{
			gridPosX = ((positionX + 15) / Globals.tileWidth);
			gridPosY = ((positionY + 15) / Globals.tileHeight);

			if(left == true)
			{
				positionX -= horizontialInc;
				positionY = gridPosY * Globals.tileHeight;

				if((positionX < (gridPosX * Globals.tileWidth))&&(recentDecision==0))
				{
					int upIndex = _map.get(gridPosX, gridPosY - 1);
					int downIndex = _map.get(gridPosX, gridPosY + 1);
					int leftIndex = _map.get(gridPosX - 1, gridPosY);

					if((leftIndex==Map.blankIndex)||(upIndex==Map.blankIndex)||(downIndex==Map.blankIndex))
					{
						int res[] = {leftIndex,upIndex,downIndex};
						int r = 0;
						left = false;
							
						do
						{
							r = (int)(Math.random() * 3);
						} while(res[r]!=Map.blankIndex);

						if(r==0) left = true;
						else if(r==1) up = true;
						else if(r==2) down = true;
						
						recentDecision++;
					}			
					else if((leftIndex!=Map.blankIndex)&&(upIndex!=Map.blankIndex)&&(downIndex!=Map.blankIndex))
					{
						left = false; right = true;
						recentDecision++;
					}
		
				}
			}
			else if(right == true)
			{				
				positionX += horizontialInc;
				positionY = gridPosY * Globals.tileHeight;
			
				if((positionX > (gridPosX * Globals.tileWidth))&&(recentDecision==0))
				{
					int upIndex = _map.get(gridPosX,gridPosY - 1);
					int downIndex = _map.get(gridPosX,gridPosY + 1);
					int rightIndex = _map.get(gridPosX + 1,gridPosY);

					if((rightIndex==Map.blankIndex)||(upIndex==Map.blankIndex)||(downIndex==Map.blankIndex))
					{
						int res[] = {rightIndex,upIndex,downIndex};
						int r=0;
						right = false;
					
						do
						{
							r = (int)(Math.random() * 3);
						} while(res[r]!=Map.blankIndex);

						if(r==0) right = true;
						else if(r==1) up = true;
						else if(r==2) down = true;

						recentDecision++;
					}
					else if ((rightIndex!=Map.blankIndex)&&(upIndex!=Map.blankIndex)&&(downIndex!=Map.blankIndex))
					{
						left = true;
						right = false;
						recentDecision++;
					}					
					
				}
			}
			else if(up == true)
			{
				positionY -= verticalInc;
				positionX = gridPosX * Globals.tileWidth;

				if((positionY > (gridPosY * Globals.tileHeight))&&(recentDecision==0))
				{
					int leftIndex = _map.get(gridPosX - 1,gridPosY);
					int rightIndex = _map.get(gridPosX + 1,gridPosY);
					int upIndex = _map.get(gridPosX,gridPosY - 1);

					if((leftIndex==Map.blankIndex)||(rightIndex==Map.blankIndex)||(upIndex==Map.blankIndex))
					{
						int res[] = {leftIndex,rightIndex,upIndex};
						int r=0;
						up = false;

						do
						{
							r = (int)(Math.random() * 3);
						}while(res[r]!=Map.blankIndex);
	
						if(r==0) left = true;
						else if(r==1) right = true;
						else if(r==2) up = true;	

						recentDecision++;	
					}
					else if((leftIndex!=Map.blankIndex)&&(rightIndex!=Map.blankIndex)&&(upIndex!=Map.blankIndex))
					{
						up = false;
						down = true;
						recentDecision++;
					}
				}
			}
			else if(down == true)
			{
				positionY += verticalInc;
				positionX = gridPosX * Globals.tileWidth;

				if((positionY < (gridPosY * Globals.tileHeight))&&(recentDecision==0))
				{
					int leftIndex = _map.get(gridPosX - 1,gridPosY);
					int rightIndex = _map.get(gridPosX + 1,gridPosY);
					int downIndex = _map.get(gridPosX,gridPosY + 1);

					if((leftIndex==Map.blankIndex)||(rightIndex==Map.blankIndex)||(downIndex==Map.blankIndex))
					{
						int res[] = {leftIndex,rightIndex,downIndex};
						int r=0;
						down = false;
		
						do
						{
							r = (int)(Math.random() * 3);
						} while(res[r]!=Map.blankIndex);

						if(r==0) left = true;
						else if(r==1) right = true;
						else if(r==2) down = true;

						recentDecision++;
					}
					else if((leftIndex!=Map.blankIndex)&&(rightIndex!=Map.blankIndex)&&(downIndex==Map.blankIndex))
					{
						up = true;
						down = false;
						recentDecision++;
					}

				}
			}

			if(recentDecision!=0) recentDecision++;

			if(recentDecision>5) recentDecision = 0;
		}
		else if(state==stateTrapped)
		{
			gridPosX = ((positionX + 15) / Globals.tileWidth);
			gridPosY = ((positionY + 15) / Globals.tileHeight);

			if(left == true)
			{
				positionX -= horizontialInc;
				positionY = gridPosY * Globals.tileHeight;
			
				if(_map.get(gridPosX - 1,gridPosY)!=Map.blankIndex)
					if(positionX < (gridPosX * Globals.tileWidth))
					{
						left = false;
						right = true;
					}

			}
			else if(right==true)
			{
				positionX += horizontialInc;
				positionY = gridPosY * Globals.tileHeight;

				if(_map.get(gridPosX + 1,gridPosY)!=Map.blankIndex)
					if(positionX > (gridPosX * Globals.tileWidth))
					{
						left = true;
						right = false;
					}				
			}
		}
		else if(state==stateDead)
		{		
			boolean moveLeft = false,moveRight = false;
			boolean moveUp = false,moveDown = false;

			int centerX = (Globals.mapWidth / 2) * Globals.tileWidth;
			int centerY = ((Globals.mapHeight / 2) * Globals.tileHeight) - 30;


			if(positionX<centerX) moveRight = true; 
				else moveLeft = true; 

			if(positionY<centerY) moveDown = true;  
				else moveUp = true;

			gridPosX = ((positionX + 15) / Globals.tileWidth);
			gridPosY = ((positionY + 15) / Globals.tileHeight);

			if((positionX>=centerX)&&(positionX<=(centerX+Globals.tileWidth)))
				if((positionY>=centerY)&&(positionY<=(centerY+Globals.tileHeight)))
				{
					state=stateTrapped;
					currentIndex = type;
					return;
				}

			if(left == true)
			{
				positionX -= horizontialInc;
				positionY = gridPosY * Globals.tileHeight;

				if((positionX < (gridPosX * Globals.tileWidth))&&(recentDecision==0))
				{
					int upIndex = _map.get(gridPosX,gridPosY - 1);
					int downIndex = _map.get(gridPosX,gridPosY + 1);
					int leftIndex = _map.get(gridPosX - 1,gridPosY);

					if((leftIndex==Map.blankIndex)||(upIndex==Map.blankIndex)||(downIndex==Map.blankIndex))
					{

						left = false;
						if((moveDown==true)&&(downIndex==Map.blankIndex)) down = true;
						else if((moveLeft==true)&&(leftIndex==Map.blankIndex)) left = true;
						else if((moveUp==true)&&(upIndex==Map.blankIndex)) up = true;
						
						else
						{
							if(downIndex==Map.blankIndex) down = true;
							else if(leftIndex==Map.blankIndex) left = true;
							else if(upIndex==Map.blankIndex) up = true;
							
						}
						
						recentDecision++;
					}			
					else if((leftIndex!=Map.blankIndex)&&(upIndex!=Map.blankIndex)&&(downIndex!=Map.blankIndex))
					{
						left = false; right = true;
						recentDecision++;
					}
		
				}
			}
			else if(right == true)
			{				
				positionX += horizontialInc;
				positionY = gridPosY * Globals.tileHeight;
			
				if((positionX > (gridPosX * Globals.tileWidth))&&(recentDecision==0))
				{
					int upIndex = _map.get(gridPosX,gridPosY - 1);
					int downIndex = _map.get(gridPosX,gridPosY + 1);
					int rightIndex = _map.get(gridPosX + 1,gridPosY);

					if((rightIndex==Map.blankIndex)||(upIndex==Map.blankIndex)||(downIndex==Map.blankIndex))
					{
						right = false;
						if((moveDown==true)&&(downIndex==Map.blankIndex)) down = true;
						else if((moveRight==true)&&(rightIndex==Map.blankIndex)) right = true;
						else if((moveUp==true)&&(upIndex==Map.blankIndex)) up = true;
						
						else
						{
							if(rightIndex==Map.blankIndex) right = true;
							else if(upIndex==Map.blankIndex) up = true;
							else if(downIndex==Map.blankIndex) down = true;
						}

						recentDecision++;
					}
					else if ((rightIndex!=Map.blankIndex)&&(upIndex!=Map.blankIndex)&&(downIndex!=Map.blankIndex))
					{
						left = true;
						right = false;
						recentDecision++;
					}					
					
				}
			}
			else if(up == true)
			{
				positionY -= verticalInc;
				positionX = gridPosX * Globals.tileWidth;

				if((positionY > (gridPosY * Globals.tileHeight))&&(recentDecision==0))
				{
					int leftIndex = _map.get(gridPosX - 1,gridPosY);
					int rightIndex = _map.get(gridPosX + 1,gridPosY);
					int upIndex = _map.get(gridPosX,gridPosY - 1);

					if((leftIndex==Map.blankIndex)||(rightIndex==Map.blankIndex)||(upIndex==Map.blankIndex))
					{
						up = false;
						if((moveLeft==true)&&(leftIndex==Map.blankIndex)) left = true;
						else if((moveRight==true)&&(rightIndex==Map.blankIndex)) right = true;
						else if((moveUp==true)&&(upIndex==Map.blankIndex)) up = true;
						else
						{
							if(leftIndex==Map.blankIndex) left = true;
							else if(rightIndex==Map.blankIndex) right = true;
							else if(upIndex==Map.blankIndex) up = true;
						}

						recentDecision++;	
					}
					else if((leftIndex!=Map.blankIndex)&&(rightIndex!=Map.blankIndex)&&(upIndex!=Map.blankIndex))
					{
						up = false;
						down = true;
						recentDecision++;
					}
				}
			}
			else if(down == true)
			{
				positionY += verticalInc;
				positionX = gridPosX * Globals.tileWidth;

				if((positionY < (gridPosY * Globals.tileHeight))&&(recentDecision==0))
				{
					int leftIndex = _map.get(gridPosX - 1,gridPosY);
					int rightIndex = _map.get(gridPosX + 1,gridPosY);
					int downIndex = _map.get(gridPosX,gridPosY + 1);

					if((leftIndex==Map.blankIndex)||(rightIndex==Map.blankIndex)||(downIndex==Map.blankIndex))
					{
						down = false;
						if((moveDown==true)&&(downIndex==Map.blankIndex)) down = true;
						else if((moveRight==true)&&(rightIndex==Map.blankIndex)) right = true;
						else if((moveLeft==true)&&(leftIndex==Map.blankIndex)) left = true;
						else
						{
							if(leftIndex==Map.blankIndex) left = true;
							else if(rightIndex==Map.blankIndex) right = true;
							else if(downIndex==Map.blankIndex) down = true;
						}

						recentDecision++;
					}
					else if((leftIndex!=Map.blankIndex)&&(rightIndex!=Map.blankIndex)&&(downIndex==Map.blankIndex))
					{
						up = true;
						down = false;
						recentDecision++;
					}

				}
			}

			if(recentDecision!=0) recentDecision++;

			if(recentDecision>5) recentDecision = 0;
			

			// ***************
		}		
	}

	public boolean isCollision(Pacman theMan)
	{
		boolean collision = false;

		if(state==stateAlive)
		{
			collision = super.isCollision(theMan);		
		}
		else if(state==stateAliveAndScared)
		{
			collision = super.isCollision(theMan);
			if(collision == true)
			{
				state = stateDead;
				currentIndex = ghostEyes;
			}
		}

		return collision;
	}

	public void updateState(int newState)
	{

		if(newState==stateAlive) 
		{
			currentIndex = type;
			state = newState;
		}
		else if(newState==stateAliveAndScared)
		{
			if((state==stateAlive)||(state==stateAliveAndScared))
			{
				currentIndex = blueGhost;
				aliveAndScaredTimer = scaredTimer;
				state = newState;
			}
		}
		else if(newState==stateDead)
		{
			currentIndex = ghostEyes;
			state = newState;
		}
	}

	public int getState() { return state; }
}
