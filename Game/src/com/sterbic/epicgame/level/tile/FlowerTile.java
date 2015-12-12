package com.sterbic.epicgame.level.tile;

import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;

public class FlowerTile extends Tile {

	public FlowerTile(Sprite sprite) {
		super(sprite);
		
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
