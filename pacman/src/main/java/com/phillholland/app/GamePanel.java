package com.phillholland.app;

import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GamePanel extends Panel implements KeyListener, Runnable
{
	private Image buffer,mapImage;

	private int maxWidth = 570;
	private int maxHeight = 630;

	private int gridWidth = 30;
	private int gridHeight = 30;
	
	private Map mapGrid;
	private PowerUps powerUpGrid;

	BufferedImage menuImage;

	boolean play = false;

	private final static int maxGhosts = 4;
	private Ghost ghosts[];

	GameLoop gameLoop;
	boolean gameRunning = false;

	Thread thread;

	
	public void init()
	{
		Globals.init();

		mapGrid = new Map();
		powerUpGrid = new PowerUps();

		ghosts = new Ghost[maxGhosts];
		
		ghosts[Ghost.redGhost] = new Ghost(Ghost.redGhost);
		ghosts[Ghost.greenGhost] = new Ghost(Ghost.greenGhost);
		ghosts[Ghost.pinkGhost] = new Ghost(Ghost.pinkGhost);
		ghosts[Ghost.orangeGhost] = new Ghost(Ghost.orangeGhost);
	
		try
		{
			menuImage = ImageIO.read(new File("images/menu.gif"));		 
		}
		catch(IOException e)
		{
			e.printStackTrace();			
		}

		addKeyListener(this);
		buffer = createImage(maxWidth,maxHeight + 30);
		
		mapImage = createImage(maxWidth,maxHeight + 30);
		Graphics g = mapImage.getGraphics();				
		g.setColor(Color.black);
		g.fillRect(0,0,maxWidth,maxHeight + 29);	
		mapGrid.draw(g);

		thread = new Thread(this);
		thread.start();

		draw();
		repaint();
	}
	
	public void run()
	{
		int i=0;

		try
		{
			while(true)
			{				
				for(i=0;i<maxGhosts;i++)
				{
					ghosts[i].updatePosition(mapGrid);	
					int previousState = ghosts[i].getState();

					if(play==true)
					{
						removeKeyListener(this);
						gameLoop = new GameLoop(this);
						gameRunning = true;
	
						while(gameLoop.finished==false) { }
						
						gameRunning = false;
						play = false;	
						addKeyListener(this);					
					}
				}
			
			repaint();

			try { Thread.sleep(20); } catch(Exception e) { }
			}
		}catch(Exception e) { }
	}

	public void draw()
	{
		Graphics g = buffer.getGraphics();

		g.drawImage(mapImage,0,0,this);
		powerUpGrid.draw(g);

		for(int i=0;i<maxGhosts;i++)
		{
			ghosts[i].draw(g);
		}

		g.drawImage(menuImage,95,200,this);

	}

	public void update(Graphics g)
	{
		paint(g);
	}

	public void paint(Graphics g)
	{
		if(gameRunning==true)
		{
			gameLoop.paint(g);
		}
		else
		{
			draw();
			g.drawImage(buffer, 0, 0, this);
		}
	}

	public void keyTyped(KeyEvent e) { }

	public void keyPressed(KeyEvent e) { }

	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			play = true;
		}
	}
	
}