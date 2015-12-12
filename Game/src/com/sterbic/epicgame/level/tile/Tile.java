package com.sterbic.epicgame.level.tile;

import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;
import com.sterbic.epicgame.level.tile.spawn_level.SpawnFloorTile;
import com.sterbic.epicgame.level.tile.spawn_level.SpawnGrassTile;
import com.sterbic.epicgame.level.tile.spawn_level.SpawnHedgeTile;
import com.sterbic.epicgame.level.tile.spawn_level.SpawnWallTile;
import com.sterbic.epicgame.level.tile.spawn_level.SpawnWaterTile;

public class Tile {
	
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1 = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2 = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	
	public static final int COL_SPAWN_GRASS = 0xff00ff00;
	public static final int COL_SPAWN_HEDGE = 0;
	public static final int COL_SPAWN_WATER = 0;
	public static final int COL_SPAWN_WALL1 = 0xff7f7f7f;
	public static final int COL_SPAWN_WALL2 = 0xff000000;
	public static final int COL_SPAWN_FLOOR = 0xff7f0000;
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		
	}
	
	public boolean solid() {
		return false;
	}
	
}
