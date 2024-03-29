package com.sterbic.epicgame.entity;

import java.util.Random;

import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;
import com.sterbic.epicgame.level.Level;

public class Entity {
	
	protected double x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random r = new Random();
	
	protected Sprite sprite;
	
	public Entity() {
		
	}
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void tick() {
		
	}
	
	public void render(Screen screen) {
		if (sprite != null) screen.renderSprite((int)x, (int)y, sprite, true);
	}
	
	public void remove() {
		// Remove from level
		removed = true;
	
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level;
	}
	
}
