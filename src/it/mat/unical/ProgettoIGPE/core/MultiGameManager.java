package it.mat.unical.ProgettoIGPE.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class MultiGameManager {
	World world;
	Map<String, Circle> players;
	Set<Circle> enemies;
	private CopyOnWriteArrayList<Circle> plancton;
	private Set<Circle> splitted;
	Set<PowerSpawn> spawns;
	Set<Power> powers;
	public boolean difficulty;
	Runnable runnable;
	public boolean started = false;

	boolean gameOver;

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean b) {
		gameOver = b;
	}

	public Collection<Circle> getPlayers() {
		return players.values();
	}

	public Set<Power> getPowers() {
		return powers;
	}

	public Set<PowerSpawn> getSpawns() {
		return spawns;
	}

	public World getWorld() {
		return world;
	}

	public CopyOnWriteArrayList<Circle> getPlancton() {
		return plancton;
	}

	public void setPlancton(CopyOnWriteArrayList<Circle> plancton) {
		this.plancton = plancton;
	}

	public Set<Circle> getEnemies() {
		return enemies;
	}

	public Set<Circle> getSplitted() {
		return splitted;
	}

	public void setSplitted(Set<Circle> splitted) {
		this.splitted = splitted;
	}

	public void start(Runnable runnable, List<String> names) {
		this.runnable = runnable;
		gameOver = false;
		world = new World(5000, 5000);
		players = new HashMap<String, Circle>();
		for (String s : names) {
			Circle player = new Circle(world);
			player.ray = 50;
			players.put(s, player);
		}

		splitted = new HashSet<>();
		enemies = new CopyOnWriteArraySet<Circle>();
		plancton = new CopyOnWriteArrayList<>();
		spawns = new CopyOnWriteArraySet<>();
		powers = new CopyOnWriteArraySet<>();
	}

	public void SimpleIA() {
		int minRay, xmin, ymin;
		Circle first = players.values().iterator().next();
		minRay = first.ray;
		xmin = first.x;
		ymin = first.y;

		for (Circle c : players.values()) {
			if (c.ray < minRay) {
				minRay = c.ray;
				xmin = c.x;
				ymin = c.y;
			}
		}
		for (Circle circle : enemies) {
			if (circle.ray < minRay) {
				minRay = circle.ray;
				xmin = circle.x;
				ymin = circle.y;
			}
		}

		for (Circle circle : enemies) {
			if (!(circle.ray == minRay)) {
				circle.setSpeedX((xmin - circle.x) / circle.ray);
				circle.setSpeedY((ymin - circle.y) / circle.ray);
			}
		}
	}

	public void startNetworkGame(List<String> names) {
		start(null, names);
	}

	public String statusToString() {
		StringBuilder sb = new StringBuilder();
		players.forEach((s, p) -> {
			sb.append(s + " " + p.x + " " + p.y + " " + p.ray + ";");
		});

		enemies.forEach(e -> {
			sb.append(e.x + " " + e.y + " " + e.ray + ",");
		});
		return sb.toString();

	}

	public void parseStatusFromString(String status) {
		String[] split = status.split(";");
		String[][] player = new String[2][];
		if (split[0].equals("true"))
			setGameOver(true);
		else
			setGameOver(false);
		player[0] = split[1].split(" ");
		player[1] = split[2].split(" ");
		String[] enemy = split[3].split(",");
		String[] Ps = split[4].split(",");
		String[] p = split[5].split(",");
		String[] pl = split[6].split(",");

		enemies.clear();
		spawns.clear();
		powers.clear();
		plancton.clear();

		for (int i = 0; i < 2; i++) {
			Circle value = new Circle(Integer.parseInt(player[i][1]), Integer.parseInt(player[i][2]),
					Integer.parseInt(player[i][3]), Integer.parseInt(player[i][4]), Integer.parseInt(player[i][5]),
					Integer.parseInt(player[i][6]), world);
			value.points = Integer.parseInt(player[i][7]);
			if (player[i][8].equals("true"))
				value.expanded = true;
			else
				value.expanded = false;
			value.speed = Integer.parseInt(player[i][9]);
			players.replace(player[i][0], value);
		}

		if (enemy.length != 0)
			for (String s : enemy) {
				if (!s.equals("_")) {
					String[] e = s.split(" ");
					enemies.add(new Circle(Integer.parseInt(e[0]), Integer.parseInt(e[1]), Integer.parseInt(e[2]),
							Integer.parseInt(e[3]), Integer.parseInt(e[4]), Integer.parseInt(e[5]), world));
				}
			}
		if (Ps.length != 0)
			for (String s : Ps) {
				if (!s.equals("_")) {
					String[] e = s.split(" ");
					spawns.add(new PowerSpawn(Integer.parseInt(e[0]), Integer.parseInt(e[1]), Integer.parseInt(e[2]),
							world));
				}
			}
		if (p.length != 0)
			for (String s : p) {
				if (!s.equals("_")) {
					String[] e = s.split(" ");

					if (e[0].equals("s"))
						powers.add(new SpeedUp(world, Integer.parseInt(e[1]), Integer.parseInt(e[2]),
								Integer.parseInt(e[3])));
					else
						powers.add(new Expand(world, Integer.parseInt(e[1]), Integer.parseInt(e[2]),
								Integer.parseInt(e[3])));
				}
			}
		if (pl.length != 0)
			for (String s : pl) {
				if (!s.equals("_")) {
					String[] e = s.split(" ");
					plancton.add(new Circle(Integer.parseInt(e[0]), Integer.parseInt(e[1]), Integer.parseInt(e[2]),
							Integer.parseInt(e[3]), Integer.parseInt(e[4]), Integer.parseInt(e[5]), world));
				}
			}
	}

	public Circle getPlayer(String nick) {
		return players.get(nick);

	}

}
