package com.sterbic.epicgame.entity.mob;

import java.util.List;

import com.sterbic.epicgame.graphics.AnimatedSprite;
import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.graphics.Sprite;
import com.sterbic.epicgame.graphics.SpriteSheet;
import com.sterbic.epicgame.level.Node;
import com.sterbic.epicgame.util.Vector2i;

/**
 * 
 * @author sterbic
 * The special mob following A* pathfinding algorithm
 */
public class CleverMob extends Mob {
	
	private double xa = 0;
	private double ya = 0;
	private double speed = 1.0;
	private int time = 0;
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;
	
	private List<Node> path;
	
	public CleverMob(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
	}
	
	private void moveMob () {
		xa = 0;
		ya = 0;
		
		int px = (int) level.getPlayerAt(0).getX();
		int py = (int) level.getPlayerAt(0).getY();
		
		Vector2i start = new Vector2i((int) getX() >> 4, (int) getY() >> 4);
		Vector2i finish = new Vector2i(px >> 4, py >> 4);
		
		if (time % 5 == 0) path = level.findPath(start, finish);
		
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if (x < vec.getX() << 4) xa += speed ;
				if (x > vec.getX() << 4) xa -= speed ;
				if (y < vec.getY() << 4) ya += speed ;
				if (y > vec.getY() << 4) ya -= speed ;
			}
		}
		// OLD CODE
		//List<Player> players  = level.getPlayers(this, 180);
		
		//if (players.size() > 0) {
//			Player player = players.get(0);			
//			if (Math.floor(x) == Math.floor(player.getX())) xa = 0;
//			if (Math.floor(y) == Math.floor(player.getY())) ya = 0;
//			if ((int)x < (int)player.getX()) xa+=speed;
//			if ((int)x > (int)player.getX()) xa-=speed;
//			if ((int)y < (int)player.getY()) ya+=speed;
//			if ((int)y > (int)player.getY()) ya-=speed;
		//}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}
	
	public void tick() {
		time++;
		
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
