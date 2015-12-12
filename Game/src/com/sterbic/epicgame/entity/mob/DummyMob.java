package com.sterbic.epicgame.entity.mob;

import com.sterbic.epicgame.graphics.AnimatedSprite;
import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;
import com.sterbic.epicgame.graphics.SpriteSheet;

public class DummyMob extends Mob {
	
	private int time = 0;
	private int xa = 1;
	private int ya = 1;
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	public DummyMob(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
	}
	
	public void tick() {
		time++;

		if (time % 60 == 0) {
			xa = r.nextInt(3) - 1;
			ya = r.nextInt(3) - 1;
		}
		
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
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)x, (int)y, sprite, 0);
	}

}
