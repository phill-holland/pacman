package com.phillholland.app;

import java.awt.image.*;
import java.awt.*;

class Sprite
{
	public BufferedImage picture;

	public int positionX,positionY;	
	public int state;
	
	public Sprite(BufferedImage i)
	{
		setImage(i);	
	}

	public Sprite()
	{
		positionX = 0;
		positionY = 0;
		state = 0;
	}

	public void setImage(BufferedImage i)
	{
		picture = i;
		positionX = 0;
		positionY = 0;
		state = 0;
	}

	public int getWidth()
	{
		return picture.getWidth();
	}

	public int getHeight()
	{
		return picture.getHeight();
	}

	public boolean isCollision(Sprite s)
	{
		if(isInside(s.positionX, s.positionY)==true) return true;
		if(isInside(s.positionX + s.getWidth(), s.positionY)==true) return true;
		if(isInside(s.positionX, s.positionY + s.getHeight())==true) return true;
		if(isInside(s.positionX + s.getWidth(), s.positionY + s.getHeight())==true) return true;

		if(s.isInside(positionX, positionY)==true) return true;
		if(s.isInside(positionX + getWidth(), positionY)==true) return true;
		if(s.isInside(positionX, positionY + getHeight())==true) return true;
		if(s.isInside(positionX + getWidth(), positionY + getHeight())==true) return true;
		

		return false;
	}

	private boolean isInside(int x, int y)
	{
		if((x>=positionX)&&(x<=(positionX + getWidth())))
			if((y>=positionY)&&(y<=(positionY + getHeight()))) return true;

		return false;
	}

	public void draw(Graphics g)
	{
		g.drawImage(picture, positionX, positionY, null);
	}
}