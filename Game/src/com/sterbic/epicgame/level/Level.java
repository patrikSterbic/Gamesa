package com.sterbic.epicgame.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sterbic.epicgame.entity.Entity;
import com.sterbic.epicgame.entity.mob.Player;
import com.sterbic.epicgame.entity.particle.Particle;
import com.sterbic.epicgame.entity.projectile.Projectile;
import com.sterbic.epicgame.graphics.Screen;
import com.sterbic.epicgame.level.tile.Tile;
import com.sterbic.epicgame.util.Vector2i;

public class Level {
	
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	protected int tile_size;
	
	// My Lists
	private List<Entity> entities = new ArrayList<>();
	private List<Projectile> projectiles = new ArrayList<>();
	private List<Particle> particles = new ArrayList<>();
	private List<Player> players = new ArrayList<>();
	
	// Compare two object used by sorting for example 
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n1, Node n2) {
			if (n2.fCost < n1.fCost) return 1;
			if (n2.fCost > n2.fCost) return -1;
			return 0;
		}
		
	};
	
	public static Level spawn = new SpawnLevel("/levels/spawn_level2.png");
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}
	
 	public Level(String path) {
		loadLevel(path);
		generateLevel();
		
	}
	
	protected void generateLevel() {
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
			}
		}
		tile_size = 16;
	}
	
	protected void loadLevel(String path) {
		
	}
	
	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).tick();
		}
		
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).tick();
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).tick();
		}
		remove();
	}
	
	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
		}
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	private void time() {
		
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		
		screen.setOffset(xScroll, yScroll);
		
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
	
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}
	
	/* Special collision method */
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size - xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4; 
			
			if(getTile(xt, yt).solid()) solid = true;
		}
		
		return solid;
	}
	
	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
		 	particles.add((Particle)e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile)e);
		} else if (e instanceof Player) {
			players.add((Player)e);
		} else {
			entities.add(e);
		}

	}
	
	public void addProjectile(Projectile p) {
		p.init(this);
		projectiles.add(p);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	
	public Player getPlayerAt(int index) {
		return players.get(index);
	}
	
	public Player getClientPlayer() {
		return players.get(0);
	}
	
	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<>();
		int ex = (int) e.getX();
		int ey = (int) e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.equals(e)) continue;
			
			int x = (int)entity.getX();
			int  y = (int)entity.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) {
				result.add(e);
			}
		}
		return result;
	}
	
	public List<Player> getPlayers(Entity e, int radius) {
		List<Entity> entitites = getEntities(e, radius);
		
		List<Player> result = new ArrayList<>();
		
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			
			int x = (int)player.getX();
			int y = (int)player.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) {
				result.add(player);
			}	
		}
		return result;
	}
	
	// A* Pathfinding
	public List<Node> findPath(Vector2i start, Vector2i finish) {
		List<Node> openList = new ArrayList<>();
		List<Node> closedList = new ArrayList<>();
		Node current = new Node(start, null, 0, getDistance(start, finish));
		openList.add(current);
		
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(finish)) {
				List<Node> path = new ArrayList<>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null) continue;
				if (at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95 );
				double hCost = getDistance(a, finish);
				Node node = new Node(a, current, gCost, hCost);
				if (vectorInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vectorInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}
	
	public boolean vectorInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector)) {
				return true;
			}
		}
		return false;
	}
	
	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public Tile getTile(int x, int y) {
		
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == Tile.COL_SPAWN_FLOOR) return Tile.spawn_floor;
		if (tiles[x + y * width] == Tile.COL_SPAWN_GRASS) return Tile.spawn_grass;
		if (tiles[x + y * width] == Tile.COL_SPAWN_WALL1) return Tile.spawn_wall1;
		if (tiles[x + y * width] == Tile.COL_SPAWN_WALL2) return Tile.spawn_wall2;
		if (tiles[x + y * width] == Tile.COL_SPAWN_HEDGE) return Tile.spawn_hedge;
		if (tiles[x + y * width] == Tile.COL_SPAWN_WATER) return Tile.spawn_water;
		return Tile.voidTile;
	} 
}
