package com.sterbic.epicgame.entity.mob;

import com.sterbic.epicgame.Game;
import com.sterbic.epicgame.entity.projectile.Projectile;
import com.sterbic.epicgame.entity.projectile.FireProjectile;
import com.sterbic.epicgame.graphics.AnimatedSprite;
import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;
import com.sterbic.epicgame.graphics.SpriteSheet;
import com.sterbic.epicgame.input.Keyboard;
import com.sterbic.epicgame.input.Mouse;


/**
 * 
 * @author sterbic
 * Player class
 */
public class Player extends Mob {
	
	private Keyboard input;
	private Sprite sprite;
	
	private boolean walking = false;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private int fireRate = 0;
	
	
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player_forward;
		fireRate = FireProjectile.FIRE_RATE;
	}
	
	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player_forward;
		fireRate = FireProjectile.FIRE_RATE;
	}
	
	public void tick() {
		
		//List<Entity> e = level.getEntities(this, 150);
		
		if (walking) animSprite.tick();
		else animSprite.setFrame(0);
		
		if(fireRate > 0) fireRate--;
		double xa = 0, ya = 0;
		
		double speed = 1.9;
		
		if (input.up) {
			ya-= speed;
			animSprite = up;
		}
		else if (input.down) {
			ya+= speed;
			animSprite = down;
		}
		else if (input.left) {
			xa-= speed;
			animSprite = left;
		}
		else if (input.right) {
			xa+= speed;
			animSprite = right;
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
		shootingTick();
		clear();
		
	}
	
	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}
		
	}

	private void shootingTick() {
		
		if(Mouse.getB() == 1 && fireRate <= 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = FireProjectile.FIRE_RATE;
		}
		
	}

	public void render(Screen screen) {
		
		int flip = 0;
		
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16), (int)(y - 16), sprite, flip);
	}
	
}
