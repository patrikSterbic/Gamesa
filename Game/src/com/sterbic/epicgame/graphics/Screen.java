package com.sterbic.epicgame.graphics;

import java.util.Random;

import com.sterbic.epicgame.entity.mob.ChaserMob;
import com.sterbic.epicgame.entity.mob.CleverMob;
import com.sterbic.epicgame.entity.mob.Mob;
import com.sterbic.epicgame.entity.projectile.Projectile;
import com.sterbic.epicgame.level.tile.Tile;

public class Screen {

	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	
	public int xOffset, yOffset;
	
	public int[] tiles = new int[64 * 64];

	private Random r = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = r.nextInt(0xffffff);

		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if(fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for (int y = 0; y < sheet.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheet.WIDTH; x++) {
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];
			}
		}
		
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if(fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
		
	}
	
	public void renderTile(int xp, int yp, Tile tile) {
		
		xp -= xOffset;
		yp -= yOffset;
		
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0|| ya >= height) break;
				if (xa < 0) xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}
	
	public void renderProjectile(int xp, int yp, Projectile p) {
			
		xp -= xOffset;
		yp -= yOffset;
		
		for (int y = 0; y < p.getSprintSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < p.getSprintSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSprintSize() || xa >= width || ya < 0|| ya >= height) break;
				if (xa < 0) xa = 0;
				int col = p.getSprite().pixels[x + y * p.getSprintSize()];
				if (col != 0xffff00ff) {
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void renderMob(int xp, int yp, Mob mob) {
		
		xp -= xOffset;
		yp -= yOffset;
		
		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			int ys = y;
			for (int x = 0; x < 32; x++) {
				int xa = x + xp;
				int xs = x;	
				if (xa < -32 || xa >= width || ya < 0|| ya >= height) break;
				if (xa < 0) xa = 0;
				int col = mob.getSprite().pixels[xs + ys * 32];
				if ((mob instanceof ChaserMob) && col == 0xff767676) col = 0xffe5e5e5;
				if ((mob instanceof CleverMob) && col == 0xff767676) col = 0xff311D59;
				if (col != 0xffff00ff) {
					pixels[xa + ya * width] = col;
				}
			}
		}
		
	}
	
	public void renderMob(int xp, int yp, Sprite sprite, int flip) {
		
		xp -= xOffset;
		yp -= yOffset;
		
		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			int ys = y;
			
			if (flip == 2 || flip == 3) {
				ys = 31 - y;
			}
			
			for (int x = 0; x < 32; x++) {
				int xa = x + xp;
				int xs = x;
				
				if (flip == 1 || flip == 3) {
					xs = 31 - x;
					
				}
				
				if (xa < -32 || xa >= width || ya < 0|| ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[xs + ys * 32];
				if (col != 0xffff00ff) {
					pixels[xa + ya * width] = col;
				}
			}
		}
		
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;	
	}
}