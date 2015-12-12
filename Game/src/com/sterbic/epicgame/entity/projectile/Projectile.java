package com.sterbic.epicgame.entity.projectile;

import java.util.Random;

import com.sterbic.epicgame.entity.Entity;
import com.sterbic.epicgame.graphics.Sprite;

public abstract class Projectile extends Entity {
	
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected double nx, ny;
	protected double x, y;
	protected double distance;
	protected double speed, range, damage;
	
	protected final Random r = new Random();
	
	protected Sprite sprite;
	
	public Projectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSprintSize() {
		return sprite.SIZE;
	}
	
	protected void move() {
		
	}
	
}
