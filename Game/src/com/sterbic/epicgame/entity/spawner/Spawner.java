package com.sterbic.epicgame.entity.spawner;

import com.sterbic.epicgame.entity.Entity;
import com.sterbic.epicgame.entity.particle.Particle;
import com.sterbic.epicgame.level.Level;

public abstract class Spawner extends Entity {
	
	public enum Type {
		MOB, 
		PARTICLE;
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
		
		for (int i = 0; i < amount; i++) {
			if (type == Type.PARTICLE) {
				level.add(new Particle(x, y, 50));
			}
		}
	}
	
}
