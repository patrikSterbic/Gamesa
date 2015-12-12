package com.sterbic.epicgame.level.tile.spawn_level;

import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;
import com.sterbic.epicgame.level.tile.Tile;

public class SpawnWaterTile extends Tile {

	public SpawnWaterTile(Sprite sprite) {
		super(sprite);
		
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
