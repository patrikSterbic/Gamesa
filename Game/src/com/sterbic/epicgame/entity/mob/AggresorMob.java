package com.sterbic.epicgame.entity.mob;

import java.util.List;

import com.sterbic.epicgame.entity.Entity;
import com.sterbic.epicgame.entity.projectile.FireProjectile;
import com.sterbic.epicgame.graphics.AnimatedSprite;
import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;
import com.sterbic.epicgame.graphics.SpriteSheet;
import com.sterbic.epicgame.util.Vector2i;

public class AggresorMob extends Mob {
	
	private double xa = 0;
	private double ya = 0;
	private double speed = 1.2;
	private int fireRate = 0;
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	public AggresorMob(double x, double y) {
		this.x = (int) x << 4;
		this.y = (int) y << 4;
		sprite = Sprite.dummy;
		fireRate = FireProjectile.FIRE_RATE;
	}
	
	private void moveMob () {
		xa = 0;
		ya = 0;
		List<Player> players  = level.getPlayers(this, 180);
		
		if (players.size() > 0) {
			Player player = players.get(0);			
			if (Math.floor(x) == Math.floor(player.getX())) xa = 0;
			if (Math.floor(y) == Math.floor(player.getY())) ya = 0;
			if ((int)x < (int)player.getX()) xa+=speed;
			if ((int)x > (int)player.getX()) xa-=speed;
			if ((int)y < (int)player.getY()) ya+=speed;
			if ((int)y > (int)player.getY()) ya-=speed;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}
	
	public void tick() {
		moveMob();
		
		if (walking) animSprite.tick();
		else animSprite.setFrame(0);
		
		if (ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		}
		else if (ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;
		}
		else if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		}
		else if (xa > 0) {
			animSprite = right;
			dir = Direction.RIGHT;
		}
		
		//shootProjectile();
		
	}
	
	private void shootProjectile() {
		List<Entity> entities = level.getEntities(this, 50);
		Player player= level.getClientPlayer();
		entities.add(player);
		
		double min = 0;
		Entity closest = null;
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			double distance = Vector2i.getDistance(new Vector2i((int)x, (int)y), new Vector2i((int)e.getX(), (int)e.getY()));
			if ( i == 0 || distance < min) {
				min = distance;
				closest = e;
			}
		}
		
		if (closest != null) {
			double dirX = closest.getX() - x;
			double dirY = closest.getY() - y;
			double direction = Math.atan2(dirY, dirX);
			shoot(x + 16, y + 16, direction);
		}	
	}
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)x, (int)y, this);
	}
	
}
