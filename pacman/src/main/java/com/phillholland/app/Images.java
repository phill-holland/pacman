package com.phillholland.app;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

class Images
{
	private BufferedImage images[];
	
	private int elements=0;
	private int counter=0;

	public Images(int total)
	{
		elements = total;
		images = new BufferedImage[elements];
	}

	public BufferedImage get(int index)
	{
		return images[index];
	}

	public void load(String filename,int index)
	{
		try
		{
			images[index] = ImageIO.read(new File(filename));		 
		}
		catch(IOException e)
		{
			e.printStackTrace();			
		}
	}

	public void load(String filename)
	{
		try
		{
			images[counter++] = ImageIO.read(new File(filename));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
