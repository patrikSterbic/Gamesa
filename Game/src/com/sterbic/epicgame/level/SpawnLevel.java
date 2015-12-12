package com.sterbic.epicgame.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sterbic.epicgame.entity.mob.AggresorMob;
import com.sterbic.epicgame.entity.mob.ChaserMob;
import com.sterbic.epicgame.entity.mob.CleverMob;

/**
 * 
 * @author sterbic
 * Spawning level class where everything is spawn or loaded
 */
public class SpawnLevel extends Level {
	
	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exeption: Could not load level file!");
		}
		//for (int i = 0; i < 5; i++) {
		//	add(new DummyMob(5, 5));
		//}
		add(new ChaserMob(25, 5));
		add(new CleverMob(27, 5));
		add(new AggresorMob(27, 5));
	}
	
	
	protected void generateLevel() {
		
	}
	
}

