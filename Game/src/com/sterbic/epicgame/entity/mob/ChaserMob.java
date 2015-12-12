package com.sterbic.epicgame.entity.mob;

import java.util.List;

import com.sterbic.epicgame.graphics.AnimatedSprite;
import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;
import com.sterbic.epicgame.graphics.SpriteSheet;

/**
 * 
 * @author sterbic
 * Player chasing Mob
 */
public class ChaserMob extends Mob {
	
	private double xa = 0;
	private double ya = 0;
	private double speed = 1.2;
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	public ChaserMob(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
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
		
	}
	
	

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 4), (int)(y - 16), this);
	}
	
}
