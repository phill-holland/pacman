package com.phillholland.app;

import java.awt.image.*;
import java.awt.*;

class PowerUps extends MapInformation
{	
	public static final int blankIndex = 0;
	private Images images;

	public static final long scoreInc = 2;

	public static final int standardPill = 1;
	public static final int powerPill = 2;
	
	public PowerUps()
	{
		super(Globals.mapWidth * 2, Globals.mapHeight * 2, Globals.tileWidth / 2, Globals.tileHeight / 2, Globals.uniquePowerUps);
		super.set(Globals.defaultPowerUps);

		images = new Images(Globals.uniquePowerUps);

		images.load("images/pill.jpg",1);
		images.load("images/powerpill.jpg",2);
	}

	public boolean isEmpty()
	{
		for(int x=0;x<width;x++)
		{
			for(int y=0;y<height;y++)
			{
				if(get(x,y)!=blankIndex) return false;
			}
		}

		return true;
	}

	public void draw(Graphics g)
	{
		for(int x=0;x<width;x++)
		{
			for(int y=0;y<height;y++)
			{
				if(get(x,y)!=blankIndex)
				{
					Image i  = images.get(get(x,y));
					g.drawImage(i, x * tileWidth, y * tileHeight, null);
				}
			}
		}
	}
}