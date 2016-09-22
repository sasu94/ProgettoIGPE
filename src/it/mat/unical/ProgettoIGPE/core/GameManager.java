package it.mat.unical.ProgettoIGPE.core;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameManager {
	World world;
	Circle player;
	Set<Circle> enemies;
	private CopyOnWriteArrayList<Circle> plancton;
	private Set<Circle> splitted;
	Set<PowerSpawn> spawns;
	Set<Power> powers;
	private Set<Circle> toRemove;
	public boolean difficulty;
	public boolean started = false;

	boolean gameOver;
	private boolean pause;

	public boolean isGameOver() {
		return gameOver;
	}

	public void setPause(boolean b) {
		pause = b;

	}

	public void setGameOver(boolean b) {
		gameOver = b;
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

	public Circle getPlayer() {
		return player;
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

	public GameManager(boolean b) {
		startGame(b);
	}

	public GameManager(boolean d, String[] p, String[] s) {
		startEditedGame(d, p, s);
	}

	public GameManager(String state) {
		startLoadedGame(state);
	}

	private void startLoadedGame(String state) {
		gameOver = false;
		world = new World(5000, 5000);
		splitted = new HashSet<>();
		enemies = new HashSet<Circle>();
		plancton = new CopyOnWriteArrayList<>();
		spawns = new HashSet<>();
		powers = new HashSet<>();
		toRemove = new HashSet<>();
		String[] split = state.split(";");
		if (split[0].equals("true"))
			difficulty = true;
		else
			difficulty = false;
		String[] play = split[1].split(" ");
		String[] enemy = split[2].split(",");
		String[] Ps = split[3].split(",");
		String[] p = split[4].split(",");
		String[] pl = split[5].split(",");

		enemies.clear();
		spawns.clear();
		powers.clear();
		plancton.clear();

		player = new Circle(Integer.parseInt(play[0]), Integer.parseInt(play[1]), Integer.parseInt(play[2]),
				Integer.parseInt(play[3]), Integer.parseInt(play[4]), Integer.parseInt(play[5]), world);

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
		started = true;
	}

	public void startEditedGame(boolean d, String[] p, String[] s) {
		gameOver = false;
		world = new World(5000, 5000);
		splitted = new HashSet<>();
		enemies = new HashSet<Circle>();
		plancton = new CopyOnWriteArrayList<>();
		spawns = new HashSet<>();
		powers = new HashSet<>();
		toRemove = new HashSet<>();
		player = new Circle(Integer.parseInt(p[0]), Integer.parseInt(p[1]), 50, world);
		difficulty = d;
		boolean b = false;
		for (int i = 0; i < 10; i++) {
			Circle c = new Circle(world);
			c.ray = (int) (Math.random() * 500);
			b = false;
			for (Circle circle : enemies) {
				while (b != true) {
					if ((Point2D.distance(c.x, c.y, circle.x, circle.y) < c.ray + circle.ray)
							&& (Point2D.distance(c.x, c.y, player.x, player.y) < c.ray + player.ray))
						c = new Circle(world);
					else
						b = true;
				}
			}
			enemies.add(c);

		}

		for (int i = 0; i < 1000; i++) {
			getPlancton().add(new Circle(world));

		}

		if (s[0] != null)
			for (String temp : s) {
				String[] t = temp.split(" ");
				spawns.add(new PowerSpawn(Integer.parseInt(t[0]), Integer.parseInt(t[1]), 100, world));
			}

		for (Iterator<Circle> iteratorCircle = enemies.iterator(); iteratorCircle.hasNext();) {
			Circle circle = iteratorCircle.next();
			toRemove.addAll(checkEatBeetween(enemies, circle));
		}

		enemies.removeAll(toRemove);
		toRemove.clear();

		enemies.forEach(e -> {
			checkEat(plancton, e);
			checkEatSpawns(spawns, e);
		});

		while (!createPlayer(enemies, player)) {
			player = new Circle(world);
			player.ray = 50;
		}

		started = true;
	}

	public void startGame(boolean d) {
		gameOver = false;
		world = new World(5000, 5000);
		splitted = new HashSet<>();
		enemies = new HashSet<Circle>();
		plancton = new CopyOnWriteArrayList<>();
		spawns = new HashSet<>();
		powers = new HashSet<>();
		toRemove = new HashSet<>();
		player = new Circle(world);
		player.ray = 50;
		difficulty = d;
		boolean b = false;
		for (int i = 0; i < 10; i++) {
			Circle c = new Circle(world);
			c.ray = (int) (Math.random() * 500);
			for (Circle circle : enemies) {
				while (b != true) {
					if (Point2D.distance(c.x, c.y, circle.x, circle.y) < c.ray + circle.ray)
						c = new Circle(world);
					else
						b = true;
				}
			}
			enemies.add(c);

		}

		for (int i = 0; i < 1000; i++) {
			getPlancton().add(new Circle(world));

		}

		for (int i = 0; i < 10; i++) {
			spawns.add(new PowerSpawn(world));
		}

		for (Iterator<Circle> iteratorCircle = enemies.iterator(); iteratorCircle.hasNext();) {
			Circle circle = iteratorCircle.next();
			toRemove.addAll(checkEatBeetween(enemies, circle));
		}

		enemies.removeAll(toRemove);
		toRemove.clear();

		enemies.forEach(e -> {
			checkEat(plancton, e);
			checkEatSpawns(spawns, e);
		});

		while (!createPlayer(enemies, player))
			player = new Circle(world);
		player.ray = 50;

		started = true;
	}

	public void update() {
		if (started && !pause) {
			if (!difficulty)
				complexAI();
			else
				SimpleIA();
			if (enemies.size() == 0)
				gameOver = gameOver || true;
			player.update();
			powers.forEach(p -> {
				p.update();
			});

			for (Iterator<Circle> iterator = enemies.iterator(); iterator.hasNext();) {
				Circle circle = iterator.next();
				circle.update();
				checkEat(plancton, circle);
				checkEatPower(powers, circle);
				checkEatSpawns(spawns, circle);
				toRemove.addAll(checkEatBeetween(enemies, circle));
			}
			enemies.removeAll(toRemove);
			toRemove.clear();

			checkEatPlayer(player);
			player.getSplitted().forEach(e -> checkEatPlayer(e));

			for (PowerSpawn s : spawns) {
				if (Math.random() < 0.0001) {
					Power p = s.ejectPower();
					if (p instanceof SpeedUp)
						powers.add(new SpeedUp(world, s.x, s.y, s.ray / 4));
					else if (p instanceof Expand)
						powers.add(new Expand(world, s.x, s.y, s.ray / 4));
				}
			}

			if (plancton.size() < 999)
				for (int i = 0; i < 10; i++) {
					plancton.add(new Circle(world));
				}
		}
	}

	private void checkEatPlayer(Circle c) {
		gameOver = gameOver || checkEat(enemies, c);
		checkEat(plancton, c);
		checkEatPower(powers, c);
		checkEatSpawns(spawns, c);
	}

	private void checkEatSpawns(Set<PowerSpawn> spawns, Circle p) {
		for (Iterator<PowerSpawn> iteratorCircle = spawns.iterator(); iteratorCircle.hasNext();) {
			PowerSpawn circle = iteratorCircle.next();
			if (p.intersect(circle) || circle.intersect(p)) {
				p.ray = p.ray / 2;
				iteratorCircle.remove();
				p.expanded = false;
			}
		}

	}

	private void checkEatPower(Set<Power> s, Circle p) {
		for (Iterator<Power> iteratorCircle = s.iterator(); iteratorCircle.hasNext();) {
			Power circle = iteratorCircle.next();
			if (p.intersect(circle) || circle.intersect(p)) {
				p.addPower(circle);
				iteratorCircle.remove();
			}
		}
	}

	private boolean checkEat(CopyOnWriteArrayList<Circle> s, Circle p) {
		for (Circle circle : s) {
			if (p.intersect(circle) || circle.intersect(p)) {
				if (p.ray > circle.ray) {
					p.eat(circle);
					s.remove(circle);
				} else {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkEat(Set<Circle> s, Circle p) {
		for (Iterator<Circle> iteratorCircle = s.iterator(); iteratorCircle.hasNext();) {
			Circle circle = iteratorCircle.next();
			if (p.intersect(circle) || circle.intersect(p)) {
				if (p.ray > circle.ray) {
					p.eat(circle);
					iteratorCircle.remove();
				} else {
					return true;
				}
			}
		}
		return false;
	}

	private boolean createPlayer(Set<Circle> e, Circle p) {
		for (Circle enemy : e)
			if (Point2D.distance(p.x, p.y, enemy.x, enemy.y) < (p.ray + enemy.ray + 100)) {
				return false;
			}
		return true;
	}

	private Set<Circle> checkEatBeetween(Set<Circle> s, Circle p) {
		Set<Circle> set = new HashSet<>();
		for (Iterator<Circle> iteratorCircle = s.iterator(); iteratorCircle.hasNext();) {
			Circle circle = iteratorCircle.next();
			if (p.intersect(circle) || circle.intersect(p)) {
				if (p.ray > circle.ray) {
					p.eat(circle);
					set.add(circle);
				}
			}
		}
		return set;
	}

	private void complexAI() {
		for (Circle c1 : enemies) {
			c1.toFollow = player;
			for (Circle c2 : enemies) {
				if (c2.ray < c1.ray) {
					if (c2.ray > c1.toFollow.ray) {
						c1.toFollow = c2;
					}
				}
			}
		}

		for (Circle circle : enemies) {
			if (((circle.toFollow.x - circle.x) / circle.ray) > 0)
				circle.setSpeedX((circle.toFollow.x - circle.x) / circle.ray);
			else
				circle.setSpeedX((circle.toFollow.x - circle.x) / 150);
			if (((circle.toFollow.y - circle.y) / circle.ray) > 0)
				circle.setSpeedY((circle.toFollow.y - circle.y) / circle.ray);
			else
				circle.setSpeedY((circle.toFollow.y - circle.y) / 150);
		}
	}

	public void SimpleIA() {
		int minRay = player.ray;
		int xmin = player.x, ymin = player.y;
		for (Circle circle : enemies) {
			if (circle.ray < minRay) {
				minRay = circle.ray;
				xmin = circle.x;
				ymin = circle.y;
			}
		}

		for (Circle circle : enemies) {
			if (!(circle.ray == minRay)) {
				if (((xmin - circle.x) / circle.ray) > 0)
					circle.setSpeedX((xmin - circle.x) / circle.ray);
				else
					circle.setSpeedX((xmin - circle.x) / 150);
				if (((ymin - circle.y) / circle.ray) > 0)
					circle.setSpeedY((ymin - circle.y) / circle.ray);
				else
					circle.setSpeedY((ymin - circle.y) / 150);
			}
		}
	}

	public String saveGame() {
		StringBuilder sb = new StringBuilder();

		sb.append(difficulty + ";");

		sb.append(player.x + " " + player.y + " " + player.ray + " " + player.color.getRed() + " "
				+ player.color.getGreen() + " " + player.color.getBlue() + " " + player.points + " " + player.expanded
				+ " " + player.speed + ";");

		if (enemies.size() == 0)
			sb.append("_");
		else
			enemies.forEach(e -> {
				sb.append(e.x + " " + e.y + " " + e.ray + " " + e.color.getRed() + " " + e.color.getGreen() + " "
						+ e.color.getBlue() + ",");
			});
		sb.append(";");
		if (spawns.size() == 0)
			sb.append("_");
		else
			spawns.forEach(s -> {
				sb.append(s.x + " " + s.y + " " + s.ray + ",");
			});
		sb.append(";");
		if (powers.size() == 0)
			sb.append("_");
		else
			powers.forEach(p -> {
				if (p instanceof SpeedUp)
					sb.append("s " + p.x + " " + p.y + " " + p.ray + ",");
				else
					sb.append("e " + p.x + " " + p.y + " " + p.ray + ",");

			});
		sb.append(";");
		if (plancton.size() == 0)
			sb.append("_");
		else
			plancton.forEach(p -> {
				sb.append(p.x + " " + p.y + " " + p.ray + " " + p.color.getRed() + " " + p.color.getGreen() + " "
						+ p.color.getBlue() + ",");
			});
		sb.append(";");
		return sb.toString();

	}

}
