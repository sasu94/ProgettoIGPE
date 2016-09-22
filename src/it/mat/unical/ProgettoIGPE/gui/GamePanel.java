package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.mat.unical.ProgettoIGPE.core.Circle;
import it.mat.unical.ProgettoIGPE.core.Expand;
import it.mat.unical.ProgettoIGPE.core.GameManager;
import it.mat.unical.ProgettoIGPE.core.Player;
import it.mat.unical.ProgettoIGPE.core.SpeedUp;
import it.mat.unical.ProgettoIGPE.utils.GameImageLoader;
import it.mat.unical.ProgettoIGPE.utils.ImageLoader;

public class GamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MainFrame mf;
	int width, height;
	GameManager gm;
	Image bg;
	Image playerSkin;
	int xMouse, yMouse;
	int x, y;
	int xSfondo, ySfondo;
	int xPrec, yPrec;
	boolean map = false;
	Player player;
	boolean difficulty;

	public GamePanel(MainFrame f) {
		player = new Player();

		if (f.np.difficolta.getSelectedIndex() == 0)
			gm = new GameManager(true);
		else
			gm = new GameManager(false);
		bg = GameImageLoader.getBackground().getScaledInstance(5000, 5000, 0);
		mf = f;
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) d.getWidth();
		height = (int) d.getHeight();
		xMouse = xPrec = x = width / 2;
		yMouse = yPrec = y = height / 2;

		setPreferredSize(d);
		setLayout(null);

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				xMouse = e.getX();
				yMouse = e.getY();
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					gm.setPause(true);
					int i = JOptionPane.showConfirmDialog(f, "would you like to save the game?");
					if (i == JOptionPane.YES_OPTION) {
						List<String> s = Arrays.asList("Save", gm.saveGame());

						JFileChooser jf = new JFileChooser(
								System.getProperty("user.dir").concat("/resources/coordinates"));
						jf.setSelectedFile(new File("Save_"
								+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())));
						int o = jf.showSaveDialog(f);
						if (o == JFileChooser.APPROVE_OPTION) {
							Path file = Paths.get(jf.getSelectedFile().getPath());
							try {
								Files.write(file, s);
							} catch (IOException e1) {
								JOptionPane.showConfirmDialog(f, "an error occured");
							}
							f.drawPanel("menuPanel");
						} else
							gm.setPause(false);

					} else if (i == JOptionPane.NO_OPTION)
						gm.setGameOver(true);
					else
						gm.setPause(false);
					break;
				case KeyEvent.VK_P:
					gm.setPause(true);
					break;
				case KeyEvent.VK_W:
					gm.getPlayer().setSpeedY(-3);

					if (y > gm.getPlayer().getRay() + 20)
						y -= 3;
					break;
				case KeyEvent.VK_D:
					gm.getPlayer().setSpeedX(3);

					if (x < width - (gm.getPlayer().getRay() + 20))
						x += 3;
					break;
				case KeyEvent.VK_S:
					gm.getPlayer().setSpeedY(3);

					if (y < height - (gm.getPlayer().getRay() + 20))
						y += 3;
					break;
				case KeyEvent.VK_A:
					gm.getPlayer().setSpeedX(-3);

					if (x > gm.getPlayer().getRay() + 20)
						x -= 3;
					break;
				case KeyEvent.VK_SPACE:
					Circle c = gm.getPlayer().split();
					c.setSpeedX((-x + xMouse) / (gm.getPlayer().getRay() / 3));
					c.setSpeedY((-y + yMouse) / (gm.getPlayer().getRay() / 3));
					gm.getEnemies().add(c);
					break;
				case KeyEvent.VK_M:
					map = true;
					break;
				default:
					gm.update();
					break;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_M:
					map = false;
					break;
				case KeyEvent.VK_P:
					gm.setPause(false);
					break;
				default:
					gm.update();
					break;
				}
			}
		});

		new Thread() {
			@Override
			public void run() {
				while (!gm.isGameOver()) {
					int speedX = (-x + xMouse) / (gm.getPlayer().getRay() / 3);
					int speedY = (-y + yMouse) / (gm.getPlayer().getRay() / 3);
					gm.getPlayer().setSpeedX(speedX);
					gm.getPlayer().setSpeedY(speedY);

					if (speedX < 0 && x > width / 3)
						x += speedX;
					if (speedX > 0 && x < 2 * width / 3)
						x += speedX;

					if (speedY < 0 && y > height / 3)
						y += speedY;
					if (speedY > 0 && y < 2 * height / 3)
						y += speedY;

					gm.getPlayer().getSplitted().forEach(s -> {
						s.setSpeedX((-(s.getX() - gm.getPlayer().getX() + x - s.getRay()) + xMouse) / 50);
						s.setSpeedY((-(s.getY() - gm.getPlayer().getY() + y - s.getRay()) + yMouse) / 50);
					});
					if (gm.started)
						gm.update();

					repaint();
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(mf, "an error occured!");
					}

				}

				printLeaderBoard();

				mf.drawPanel("menuPanel");
			}

		}.start();
	}

	private void printLeaderBoard() {
		File file = new File(System.getProperty("user.dir").concat("/resources/leaderboard.txt"));
		Map<Integer, String> leaderboard = new HashMap<Integer, String>();
		Set<Integer> points = new TreeSet<Integer>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}

		});
		if (file.exists()) {
			try {
				List<String> list = Files.readAllLines(file.toPath());
				for (String s : list) {
					leaderboard.put(Integer.parseInt(s.split(" ")[0]), s.split(" ")[1]);
					points.add(Integer.parseInt(s.split(" ")[0]));
				}
				points.add(gm.getPlayer().getPoints());
				if (player.getNick().equals("")) {
					leaderboard.put(gm.getPlayer().getPoints(), "Unnamed");
					list.add(gm.getPlayer().getPoints() + " Unnamed");
				} else {
					leaderboard.put(gm.getPlayer().getPoints(), player.getNick());
					list.add(gm.getPlayer().getPoints() + " " + player.getNick());
				}
				StringBuilder sb = new StringBuilder();
				if (points.iterator().next() <= gm.getPlayer().getPoints())
					sb.append("new highscore!!!\n");
				int c = 0;
				for (int i : points) {
					if (c != 10) {
						sb.append(i + " " + leaderboard.get(i) + "\n");
						c++;
					} else {
						break;
					}
				}

				Path path = Paths.get(file.getAbsolutePath());
				Files.write(path, list);

				JOptionPane.showMessageDialog(mf, sb.toString());

			} catch (IOException e) {
				JOptionPane.showMessageDialog(mf, "an error occured!");
			}
		} else {
			List<String> s = new ArrayList<String>();
			if (player.getNick().equals("")) {
				JOptionPane.showMessageDialog(mf, "new highscore\n" + gm.getPlayer().getPoints() + " Unnamed");
				s.add(gm.getPlayer().getPoints() + " Unnamed");
			} else {
				JOptionPane.showMessageDialog(mf,
						"new highscore!!!\n" + gm.getPlayer().getPoints() + " " + player.getNick());
				s.add(gm.getPlayer().getPoints() + " " + player.getNick());
			}
			Path path = Paths.get(file.getAbsolutePath());

			try {
				Files.write(path, s);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mf, "an error occured!");
			}
		}
	}

	public GamePanel(MainFrame f, String t, String state) {
		if (t.equals("Editor")) {
			String[] s = state.split(";");
			String[] circle = s[1].split(" ");
			String[] spawns = new String[1];
			if (!s[2].equals("_"))
				spawns = s[2].split(",");
			bg = ImageLoader.getBackground(s[0]).getScaledInstance(5000, 5000, 0);
			playerSkin = ImageLoader.getPlayerSkin(circle[2]);
			if (f.np.difficolta.getSelectedIndex() == 0)
				gm = new GameManager(true, circle, spawns);
			else
				gm = new GameManager(false, circle, spawns);

		} else if (t.equals("Save")) {
			bg = ImageLoader.getBackground("0").getScaledInstance(5000, 5000, 0);
			gm = new GameManager(state);

		}

		player = new Player();
		mf = f;
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) d.getWidth();
		height = (int) d.getHeight();
		xMouse = xPrec = x = width / 2;
		yMouse = yPrec = y = height / 2;

		setPreferredSize(d);
		setLayout(null);

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				xMouse = e.getX();
				yMouse = e.getY();
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					gm.setPause(true);
					int i = JOptionPane.showConfirmDialog(f, "would you like to save the game?");
					if (i == JOptionPane.YES_OPTION) {
						List<String> s = Arrays.asList("Save", gm.saveGame());

						JFileChooser jf = new JFileChooser(
								System.getProperty("user.dir").concat("/resources/coordinates"));
						jf.setSelectedFile(new File("Save_"
								+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())));
						int o = jf.showSaveDialog(f);
						if (o == JFileChooser.APPROVE_OPTION) {
							Path file = Paths.get(jf.getSelectedFile().getPath());
							try {
								Files.write(file, s);
							} catch (IOException e1) {
								JOptionPane.showConfirmDialog(f, "an error occured");
							}
							f.drawPanel("menuPanel");
						} else
							gm.setPause(false);

					} else if (i == JOptionPane.NO_OPTION)
						gm.setGameOver(true);
					else
						gm.setPause(false);
					break;
				case KeyEvent.VK_P:
					gm.setPause(true);
					break;
				case KeyEvent.VK_W:
					gm.getPlayer().setSpeedY(-3);

					if (y > gm.getPlayer().getRay() + 20)
						y -= 3;
					break;
				case KeyEvent.VK_D:
					gm.getPlayer().setSpeedX(3);

					if (x < width - (gm.getPlayer().getRay() + 20))
						x += 3;
					break;
				case KeyEvent.VK_S:
					gm.getPlayer().setSpeedY(3);

					if (y < height - (gm.getPlayer().getRay() + 20))
						y += 3;
					break;
				case KeyEvent.VK_A:
					gm.getPlayer().setSpeedX(-3);

					if (x > gm.getPlayer().getRay() + 20)
						x -= 3;
					break;
				case KeyEvent.VK_SPACE:
					Circle c = gm.getPlayer().split();
					c.setSpeedX((-x + xMouse) / (gm.getPlayer().getRay() / 3));
					c.setSpeedY((-y + yMouse) / (gm.getPlayer().getRay() / 3));
					gm.getEnemies().add(c);
					break;
				case KeyEvent.VK_M:
					map = true;
					break;
				default:
					gm.update();
					break;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_M:
					map = false;
					break;
				case KeyEvent.VK_P:
					gm.setPause(false);
					break;
				default:
					gm.update();
					break;
				}
			}
		});

		new Thread() {
			@Override
			public void run() {
				while (!gm.isGameOver()) {
					int speedX = (-x + xMouse) / (gm.getPlayer().getRay() / 3);
					int speedY = (-y + yMouse) / (gm.getPlayer().getRay() / 3);
					gm.getPlayer().setSpeedX(speedX);
					gm.getPlayer().setSpeedY(speedY);

					if (speedX < 0 && x > width / 3)
						x += speedX;
					if (speedX > 0 && x < 2 * width / 3)
						x += speedX;

					if (speedY < 0 && y > height / 3)
						y += speedY;
					if (speedY > 0 && y < 2 * height / 3)
						y += speedY;

					gm.getPlayer().getSplitted().forEach(s -> {
						s.setSpeedX((-(s.getX() - gm.getPlayer().getX() + x - s.getRay()) + xMouse) / 50);
						s.setSpeedY((-(s.getY() - gm.getPlayer().getY() + y - s.getRay()) + yMouse) / 50);
					});
					if (gm.started)
						gm.update();

					repaint();
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				printLeaderBoard();
				mf.drawPanel("menuPanel");
			}
		}.start();

	}

	@Override
	protected synchronized void paintComponent(Graphics g) {
		boolean scaled = false;
		super.paintComponent(g);
		if (gm.getPlayer().getRay() * 2 > height / 3) {
			Graphics2D g2 = (Graphics2D) g;
			g2.scale(0.5, 0.5);
			g2.translate(width / 2, height / 2);
			scaled = true;
		}

		g.drawImage(bg, -gm.getPlayer().getX() + x, -gm.getPlayer().getY() + y, null);

		g.setColor(Color.black);

		for (Circle e : gm.getEnemies()) {
			g.setColor(e.getColor());
			g.fillOval(e.getX() - gm.getPlayer().getX() + x - e.getRay(),
					e.getY() - gm.getPlayer().getY() + y - e.getRay(), e.getRay() * 2, e.getRay() * 2);
		}

		for (Circle p : gm.getPlancton()) {
			g.setColor(p.getColor());
			g.fillOval(p.getX() - gm.getPlayer().getX() + x - p.getRay(),
					p.getY() - gm.getPlayer().getY() + y - p.getRay(), p.getRay() * 2, p.getRay() * 2);
		}

		gm.getPowers().forEach(p -> {
			if (p instanceof SpeedUp) {
				g.drawImage(GameImageLoader.getFlash(), p.getX() - gm.getPlayer().getX() + x - p.getRay(),
						p.getY() - gm.getPlayer().getY() + y - p.getRay(), p.getRay() * 2, p.getRay() * 2, null);
			}
			if (p instanceof Expand) {
				g.drawImage(GameImageLoader.getExpand(), p.getX() - gm.getPlayer().getX() + x - p.getRay(),
						p.getY() - gm.getPlayer().getY() + y - p.getRay(), p.getRay() * 2, p.getRay() * 2, null);
			}
		});

		gm.getSpawns().forEach(s -> {
			g.drawImage(GameImageLoader.getPowerSpawn(), s.getX() - gm.getPlayer().getX() + x - s.getRay(),
					s.getY() - gm.getPlayer().getY() + y - s.getRay(), s.getRay() * 2, s.getRay() * 2, null);
		});

		g.setColor(gm.getPlayer().getColor());

		gm.getPlayer().getSplitted().forEach(e -> {
			g.setColor(e.getColor());
			g.fillOval(e.getX() - gm.getPlayer().getX() + x - e.getRay(),
					e.getY() - gm.getPlayer().getY() + y - e.getRay(), e.getRay() * 2, e.getRay() * 2);
		});
		if (playerSkin != null)
			g.drawImage(playerSkin, x - gm.getPlayer().getRay(), y - gm.getPlayer().getRay(),
					gm.getPlayer().getRay() * 2, gm.getPlayer().getRay() * 2, null);
		else {
			g.fillOval(x - gm.getPlayer().getRay(), y - gm.getPlayer().getRay(), gm.getPlayer().getRay() * 2,
					gm.getPlayer().getRay() * 2);
		}
		g.setFont(new Font("Andalus", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString(player.getNick(), x + 2 - (player.getNick().length() * 5) - gm.getPlayer().getRay() / 25,
				y - gm.getPlayer().getRay() / 25);

		if (scaled) {
			Graphics2D g2 = (Graphics2D) g;
			g2.scale(2, 2);
			g2.translate(-width / 4, -height / 4);
		}

		g.setFont(new Font("Times New Roman", Font.BOLD, 20));

		g.setColor(Color.BLACK);
		g.drawString("Points:" + gm.getPlayer().getPoints(), 20, 20);
		if (gm.getPlayer().getSpeed() == 2) {
			g.setColor(Color.RED);
			g.drawString("SpeedUP!!!", 20, 40);
		}
		if (gm.getPlayer().isExpanded()) {
			g.setColor(Color.RED);
			g.drawString("Expanded", 20, 60);
		}
		if (map)
			drawMap(g);

	}

	public void drawMap(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		g2.scale(0.08, 0.08);

		g.drawImage(bg, 200, 200, null);

		g.setColor(Color.black);

		for (Circle e : gm.getEnemies()) {
			g.setColor(e.getColor());
			g.fillOval(e.getX() + 200 - e.getRay(), e.getY() + 200 - e.getRay(), e.getRay() * 2, e.getRay() * 2);
		}

		gm.getPowers().forEach(p -> {
			if (p instanceof SpeedUp) {
				g.setColor(Color.red);
				g.fillOval(p.getX() + 200 - p.getRay(), p.getY() + 200 - p.getRay(), p.getRay() * 2, p.getRay() * 2);
			}
			if (p instanceof Expand) {
				g.setColor(Color.blue);
				g.fillOval(p.getX() + 200 - p.getRay(), p.getY() + 200 - p.getRay(), p.getRay() * 2, p.getRay() * 2);
			}
		});

		gm.getSpawns().forEach(s -> {
			g.drawImage(GameImageLoader.getPowerSpawn(), s.getX() + 200 - s.getRay(), s.getY() + 200 - s.getRay(),
					s.getRay() * 2, s.getRay() * 2, null);
		});

		g.setColor(gm.getPlayer().getColor());

		gm.getPlayer().getSplitted().forEach(e -> {
			g.setColor(e.getColor());
			g.fillOval(e.getX() - gm.getPlayer().getX() + 200 - e.getRay(),
					e.getY() - gm.getPlayer().getY() + 200 - e.getRay(), e.getRay() * 2, e.getRay() * 2);
		});

		g.fillOval(200 + gm.getPlayer().getX() - gm.getPlayer().getRay(),
				200 + gm.getPlayer().getY() - gm.getPlayer().getRay(), gm.getPlayer().getRay() * 2,
				gm.getPlayer().getRay() * 2);

	}

}
