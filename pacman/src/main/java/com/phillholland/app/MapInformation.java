package com.phillholland.app;

class MapInformation
{
	private int mapDetail[];

	public int width = 0,height = 0;
	public int tileWidth = 0,tileHeight = 0;
	public int uniqueTiles = 0;

	public MapInformation(int map_width, int map_height, int tile_width, int tile_height, int tiles)
	{

		width = map_width;
		height = map_height;
		tileWidth = tile_width;
		tileHeight = tile_height;
		
		uniqueTiles = tiles;
		
		mapDetail = new int[width * height];
	}

	int get(int x, int y)
	{
		return mapDetail[(y * width) + x];
	}

	void set(int i,int x,int y)
	{
		mapDetail[(y * width) + x] = i;
	}	

	void set(int details[])
	{
		mapDetail = details;
	}
}
