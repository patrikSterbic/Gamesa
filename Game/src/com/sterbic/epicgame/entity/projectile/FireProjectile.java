package com.sterbic.epicgame.entity.projectile;

import com.sterbic.epicgame.entity.spawner.ParticleSpawner;
import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;

public class FireProjectile extends Projectile{

	public static final int FIRE_RATE = 15;
	
	public FireProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = r.nextInt(100) + 150;
		speed = 4;
		damage = 20;
		
		sprite = Sprite.projectile_wizard;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void tick() {
		if(level.tileCollision((int)(x + nx), (int)(y + ny), 9, 4, 5)) {
			level.add(new ParticleSpawner((int)x, (int)y, 150 , 5, level)); 
			remove();
		}
			move();
	}
	
	protected void move() {
		x += nx;
		y += ny;
		
		if(distance() > range) {
			remove();
		}
	}
	
	private double distance() {
		double dist = 0;
		
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		
		return dist;
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int)x - 12, (int)y - 2, this);
	}
	
}
