package com.sterbic.epicgame.entity.mob;

import com.sterbic.epicgame.entity.Entity;
import com.sterbic.epicgame.entity.projectile.Projectile;
import com.sterbic.epicgame.entity.projectile.FireProjectile;
import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;

public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;
	
	protected enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}
	
	protected Direction dir;
	
	
	protected void move(double xa, double ya) {
		
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;
		
		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if(!collision(absoluteVal(xa), ya)) {
					this.x += absoluteVal(xa);			
				}
				xa -= absoluteVal(xa);
			} else {
				if(!collision(absoluteVal(xa), ya)) {
					this.x += xa;			
				}
				xa = 0;
			}
		}
		
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if(!collision(xa, absoluteVal(ya))) {
					this.y += absoluteVal(ya);			
				}
				ya -= absoluteVal(ya);
			} else {
				if(!collision(xa, absoluteVal(ya))) {
					this.y += ya;			
				}
				ya = 0;
			}
		}
		
	}
	
	private int absoluteVal(double value) {
		if (value < 0) {
			return -1;
		} else {
			return 1;
		}
	}
	
	protected void shoot(double x, double y, double dir) {
		//dir = dir * (180 / Math.PI); 
		Projectile p = new FireProjectile(x, y, dir);
		level.add(p);
	}
	
	public abstract void tick();
	
	private boolean collision(double xa, double ya) {
		
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 16) / 16;
			double yt = ((y + ya) - c / 2 * 16 + 15) / 16; 
			
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			
			if(level.getTile(ix, iy).solid()) solid = true;
		}
		
		return solid;
	}
	
	public abstract void render(Screen screen);
	
}
