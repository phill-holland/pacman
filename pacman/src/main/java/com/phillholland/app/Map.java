package com.phillholland.app;

import java.awt.image.*;
import java.awt.*;
import java.applet.*;

class Map extends MapInformation
{	
	public static final int blankIndex = 0;

	private Images images;
	
	public Map()
	{
		super(Globals.mapWidth, Globals.mapHeight, Globals.tileWidth, Globals.tileHeight, Globals.uniqueTiles);

		super.set(Globals.defaultMap);

		images = new Images(Globals.uniqueTiles);
		for(int i=1;i<Globals.uniqueTiles;i++)
		{
			images.load("images/" + Integer.toString(i) + ".jpg",i);
		}
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