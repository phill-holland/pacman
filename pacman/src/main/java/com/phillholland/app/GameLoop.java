package com.phillholland.app;

import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import java.awt.event.*;

class GameLoop implements Runnable
{
	private Image buffer,mapImage;

	private int maxWidth = 570;
	private int maxHeight = 630;

	private int gridWidth = 30;
	private int gridHeight = 30;
	
	private Map mapGrid;
	private PowerUps powerUpGrid;
	private Pacman theMan;

	private final static int maxGhosts = 4;
	private Ghost ghosts[];

	private GamePanel game;

	public boolean finished = false;

	Thread thread;

	public GameLoop(GamePanel _game)
	{
		game = _game;

		Globals.copyBack();

		mapGrid = new Map();
		powerUpGrid = new PowerUps();

		theMan = new Pacman();

		ghosts = new Ghost[maxGhosts];
		ghosts[Ghost.redGhost] = new Ghost(Ghost.redGhost);
		ghosts[Ghost.greenGhost] = new Ghost(Ghost.greenGhost);
		ghosts[Ghost.pinkGhost] = new Ghost(Ghost.pinkGhost);
		ghosts[Ghost.orangeGhost] = new Ghost(Ghost.orangeGhost);
	
		game.addKeyListener(theMan);
		buffer = game.createImage(maxWidth,maxHeight + 30);
		
		mapImage = game.createImage(maxWidth,maxHeight + 30);
		Graphics g = mapImage.getGraphics();				
		g.setColor(Color.black);
		g.fillRect(0,0,maxWidth,maxHeight + 29);	
		mapGrid.draw(g);

		thread = new Thread(this);
		thread.start();

		draw();
		game.repaint();
	}
	
	public void run()
	{
		int i=0;

		try
		{
			while(finished==false)
			{
				if (theMan.checkPowerUpCollision(powerUpGrid)==PowerUps.powerPill)
				{
					for(i=0;i<maxGhosts;i++)
					{
						ghosts[i].updateState(Ghost.stateAliveAndScared);
					}
				}

				theMan.updatePosition(mapGrid);

				if(theMan.dieing==false)
				{
					for(i=0;i<maxGhosts;i++)
					{
						ghosts[i].updatePosition(mapGrid);	
						int previousState = ghosts[i].getState();
		
						if(ghosts[i].isCollision(theMan))
						{
							if(previousState==Ghost.stateAliveAndScared)
							{
							
								int aliveAndScaredCount = 0;
							
								for(int j=0;j<maxGhosts;j++)
								{
									if(ghosts[j].getState()==Ghost.stateAliveAndScared)
										aliveAndScaredCount++;
								}							
								theMan.score += (200 * (maxGhosts - aliveAndScaredCount));				
							}
							else if(ghosts[i].getState()==Ghost.stateAlive)
							{
								theMan.dieing = true;
							}
						}
					}
				}

				if(theMan.dieing==false)
				{
					if(theMan.lives<=0)
					{
						finished = true;
					}

					if(powerUpGrid.isEmpty()==true)
					{
						finished = true;
					}
				}
				
				game.repaint();

				if(theMan.dead==true)
				{
					theMan.reset();				
					theMan.lives -= 1;					

					for(i=0;i<maxGhosts;i++) ghosts[i].reset();					
				}

				try { Thread.sleep(20); } catch(Exception e) { }
			}
		}catch(Exception e) { }
	}

	public void draw()
	{
		Graphics g = buffer.getGraphics();

		g.drawImage(mapImage,0,0,game);
		powerUpGrid.draw(g);

		for(int i=0;i<maxGhosts;i++)
		{
			ghosts[i].draw(g);
		}

		theMan.draw(g);
		theMan.drawInformation(g);
	}

	
	public void paint(Graphics g)
	{
		draw();
		g.drawImage(buffer,0,0,game);
	}
}