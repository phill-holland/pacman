package com.phillholland.app;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.applet.*;

class Animation
{
	private int totalSprites;

	public Sprite Sprites[];
	public int currentIndex;

	public int positionX = 0;
	public int positionY = 0;

	public Animation(int count)
	{
		totalSprites = count;
		Sprites = new Sprite[totalSprites];
		
		for(int i=0;i<totalSprites;i++)
		{
			Sprites[i] = new Sprite();
		}		
			
		currentIndex = 0;
	}

	public void setPosition(Coordinate pos)
	{
		positionX = pos.x;
		positionY = pos.y;
	}

	public void incFrame()
	{
		currentIndex++;
		if(currentIndex>=totalSprites) currentIndex = 0;
	}

	public void decFrame()
	{
		currentIndex--;
		if(currentIndex<0) currentIndex = totalSprites - 1;
	}

	public boolean isCollision(Animation as)
	{
		Sprite a = Sprites[0],b = as.Sprites[0];

		a.positionX = positionX; a.positionY = positionY;
		b.positionX = as.positionX; b.positionY = as.positionY;

		return a.isCollision(b);
	}

	public void draw(Graphics g)
	{
		g.drawImage(Sprites[currentIndex].picture, positionX, positionY, null);
	}
}