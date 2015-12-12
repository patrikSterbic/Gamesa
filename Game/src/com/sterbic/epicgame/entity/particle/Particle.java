
package com.sterbic.epicgame.entity.particle;

import com.sterbic.epicgame.entity.Entity;
import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;

public class Particle extends Entity {
	
	private Sprite sprite;
	
	private int life;
	private int time = 0;
	
	protected double xx, yy, zz; 
	protected double xa, ya, za;
	
	public Particle(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (r.nextInt(20) - 10);
		
		sprite = Sprite.particle_normal;
		
		this.xa = r.nextGaussian();
		this.ya = r.nextGaussian();		
		this.zz = r.nextDouble() + 1.5;
	}
	
	public void tick() {
		time++;
		if(time >= 7200) {
			time = 0;
		}
		if (time > life) {
			remove();
		}
		
		if(zz < 0) {
			 zz = 0;
			 za *= -0.6;
			 xa *= 0.5;
			 ya *= 0.4;
		}
		
		za -= 0.1;
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}
	
	public boolean collision(double x, double y) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 16) / 16;
			double yt = (y - c / 2 * 16) / 16; 
			
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			
			if(level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int)xx, (int)yy - (int)zz, sprite, true);
		
	}
	
}
