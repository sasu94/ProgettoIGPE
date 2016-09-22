package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.mat.unical.ProgettoIGPE.core.Circle;
import it.mat.unical.ProgettoIGPE.core.Expand;
import it.mat.unical.ProgettoIGPE.core.MultiGameManager;
import it.mat.unical.ProgettoIGPE.core.Player;
import it.mat.unical.ProgettoIGPE.core.SpeedUp;
import it.mat.unical.ProgettoIGPE.net.ConnectionManager;
import it.mat.unical.ProgettoIGPE.utils.GameImageLoader;

@SuppressWarnings("serial")
public class MultiGamePanel extends JPanel {
	MultiGameManager gm;
	ConnectionManager cm;
	Player player;
	int x, y;
	Image bg;
	MainFrame f;
	int xMouse, yMouse;
	private boolean map;

	public MultiGamePanel(MainFrame f) {
		xMouse = x = f.getWidth() / 2;
		yMouse = y = f.getHeight() / 2;
		bg = GameImageLoader.getBackground().getScaledInstance(5000, 5000, 0);
		this.f = f;
		player = new Player();
		player.setNick(f.multiplayerPanel.nick.getText());

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
					gm.setGameOver(true);
					break;
				case KeyEvent.VK_SPACE:
					Circle c = gm.getPlayer(player.getNick()).split();
					c.setSpeedX((-x + xMouse) / (gm.getPlayer(player.getNick()).getRay() / 3));
					c.setSpeedY((-y + yMouse) / (gm.getPlayer(player.getNick()).getRay() / 3));
					gm.getEnemies().add(c);
					break;
				case KeyEvent.VK_M:
					map = true;
					break;
				default:

				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_M:
					map = false;
					break;
				default:
				}
			}
		});

	}

	synchronized MultiGameManager startNetwork(final ConnectionManager connectionManager) {
		this.cm = connectionManager;
		System.out.println("GamePanel.startNetwork() " + player.getNick());
		requestFocus();
		gm = new MultiGameManager();
		gm.startNetworkGame(connectionManager.getAllPlayerNames());

		new Thread() {
			@Override
			public void run() {
				while (gm == null) {
				}
				while (!gm.isGameOver()) {
					if (gm.getPlayers().size() == 2 && gm.getPlayer(player.getNick()) != null) {
						int speedX = (-x + xMouse) / (gm.getPlayer(player.getNick()).getRay() / 3);
						int speedY = (-y + yMouse) / (gm.getPlayer(player.getNick()).getRay() / 3);

						cm.dispatch(player.getNick() + " " + speedX + " " + speedY);

						// if (speedX < 0 && x > f.getWidth() / 3)
						// x += speedX;
						// if (speedX > 0 && x < 2 * f.getWidth() / 3)
						// x += speedX;
						//
						// if (speedY < 0 && y > f.getHeight() / 3)
						// y += speedY;
						// if (speedY > 0 && y < 2 * f.getHeight() / 3)
						// y += speedY;

					}

				}
				if (gm.isGameOver()) {
					for (String s : cm.getAllPlayerNames())
						if (!s.equals(player.getNick())) {
							if (gm.getPlayer(player.getNick()).getPoints() > gm.getPlayer(s).getPoints())
								JOptionPane.showMessageDialog(null,
										"you won with " + gm.getPlayer(player.getNick()).getPoints() + " points");
							else if (gm.getPlayer(player.getNick()).getPoints() == gm.getPlayer(s).getPoints())
								JOptionPane.showMessageDialog(null, "you drew");
							else
								JOptionPane.showMessageDialog(null, "you lose");

							f.drawPanel("menuPanel");
						}
				}
			};
		}.start();

		return gm;
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		boolean scaled = false;
		super.paintComponent(g);
		if (gm.getPlayer(player.getNick()).getRay() * 2 > f.getHeight() / 3) {
			Graphics2D g2 = (Graphics2D) g;
			g2.scale(0.5, 0.5);
			g2.translate(f.getWidth() / 2, f.getHeight() / 2);
			scaled = true;
		}
		// int vX = (gm.getWorld().getWidth() / bg.getWidth(null));
		// int vY = (gm.getWorld().getHeight() / bg.getHeight(null));
		//
		// for (int i = 0; i <= vX; i++)
		// for (int j = 0; j <= vY; j++)
		// g.drawImage(bg, -gm.getPlayer().getX() + (i * bg.getWidth(null)) + x,
		// -gm.getPlayer().getY() + (j * bg.getHeight(null)) + y, null);

		if (gm == null || cm.getAllPlayerNames().size() != 2)
			return;

		g.drawImage(bg, -gm.getPlayer(player.getNick()).getX() + x, -gm.getPlayer(player.getNick()).getY() + y, null);

		g.setColor(Color.black);

		for (Circle e : gm.getEnemies()) {
			g.setColor(e.getColor());
			g.fillOval(e.getX() - gm.getPlayer(player.getNick()).getX() + x - e.getRay(),
					e.getY() - gm.getPlayer(player.getNick()).getY() + y - e.getRay(), e.getRay() * 2, e.getRay() * 2);
		}

		for (Circle p : gm.getPlancton()) {
			g.setColor(p.getColor());
			g.fillOval(p.getX() - gm.getPlayer(player.getNick()).getX() + x - p.getRay(),
					p.getY() - gm.getPlayer(player.getNick()).getY() + y - p.getRay(), p.getRay() * 2, p.getRay() * 2);
		}

		gm.getPowers().forEach(p -> {
			if (p instanceof SpeedUp) {
				g.drawImage(GameImageLoader.getFlash(),
						p.getX() - gm.getPlayer(player.getNick()).getX() + x - p.getRay(),
						p.getY() - gm.getPlayer(player.getNick()).getY() + y - p.getRay(), p.getRay() * 2,
						p.getRay() * 2, null);
			}
			if (p instanceof Expand) {
				g.drawImage(GameImageLoader.getExpand(),
						p.getX() - gm.getPlayer(player.getNick()).getX() + x - p.getRay(),
						p.getY() - gm.getPlayer(player.getNick()).getY() + y - p.getRay(), p.getRay() * 2,
						p.getRay() * 2, null);
			}
		});

		gm.getSpawns().forEach(s -> {
			g.drawImage(GameImageLoader.getPowerSpawn(),
					s.getX() - gm.getPlayer(player.getNick()).getX() + x - s.getRay(),
					s.getY() - gm.getPlayer(player.getNick()).getY() + y - s.getRay(), s.getRay() * 2, s.getRay() * 2,
					null);
		});

		cm.getAllPlayerNames().forEach(s -> {
			g.setColor(gm.getPlayer(s).getColor());

			gm.getPlayer(s).getSplitted().forEach(e -> {
				g.setColor(e.getColor());
				g.fillOval(e.getX() - gm.getPlayer(s).getX() + x - e.getRay(),
						e.getY() - gm.getPlayer(s).getY() + y - e.getRay(), e.getRay() * 2, e.getRay() * 2);
			});
			if (!(s.equals("") || s.equals("Insert your nickname here")
					|| Toolkit.getDefaultToolkit().getImage("resources/img/game/" + s + ".png").getWidth(null) == -1))
				g.drawImage(Toolkit.getDefaultToolkit().getImage("resources/img/game/" + s + ".png"),
						x + gm.getPlayer(s).getX() - gm.getPlayer(player.getNick()).getX() - gm.getPlayer(s).getRay(),
						y + gm.getPlayer(s).getY() - gm.getPlayer(player.getNick()).getY() - gm.getPlayer(s).getRay(),
						gm.getPlayer(s).getRay() * 2, gm.getPlayer(s).getRay() * 2, null);
			else {
				g.fillOval(
						x + gm.getPlayer(s).getX() - gm.getPlayer(player.getNick()).getX() - gm.getPlayer(s).getRay(),
						y + gm.getPlayer(s).getY() - gm.getPlayer(player.getNick()).getY() - gm.getPlayer(s).getRay(),
						gm.getPlayer(s).getRay() * 2, gm.getPlayer(s).getRay() * 2);
			}
			g.setFont(new Font("Andalus", Font.BOLD, 20));
			g.setColor(Color.BLACK);
			g.drawString(s,
					x + gm.getPlayer(s).getX() - gm.getPlayer(player.getNick()).getX() + 2 - (s.length() * 5)
							- gm.getPlayer(s).getRay() / 25,
					y + gm.getPlayer(s).getY() - gm.getPlayer(player.getNick()).getY() - gm.getPlayer(s).getRay() / 25);
		});

		g.setFont(new Font("Times New Roman", Font.BOLD, 20));

		if (scaled) {
			Graphics2D g2 = (Graphics2D) g;
			g2.scale(2, 2);
			g2.translate(-f.getWidth() / 4, -f.getHeight() / 4);
		}

		g.setColor(Color.BLACK);
		g.drawString("Points:" + gm.getPlayer(player.getNick()).getPoints(), 20, 20);
		if (gm.getPlayer(player.getNick()).getSpeed() == 2) {
			g.setColor(Color.RED);
			g.drawString("SpeedUP!!!", 20, 40);
		}
		if (gm.getPlayer(player.getNick()).isExpanded()) {
			g.setColor(Color.RED);
			g.drawString("Expanded", 20, 60);
		}
		if (map)
			disegnaMappa(g);

	}

	public void disegnaMappa(Graphics g) {

		if (gm == null)
			return;

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

		gm.getPlayers().forEach(p -> {
			g.setColor(p.getColor());

			p.getSplitted().forEach(e -> {
				g.setColor(e.getColor());
				g.fillOval(e.getX() - gm.getPlayer(player.getNick()).getX() + 200 - e.getRay(),
						e.getY() - gm.getPlayer(player.getNick()).getY() + 200 - e.getRay(), e.getRay() * 2,
						e.getRay() * 2);
			});

			// g.drawImage(Toolkit.getDefaultToolkit().getImage("resources/img/game/g.png"),x
			// - gm.getPlayer(player.getNick()).getRay(), y -
			// gm.getPlayer(player.getNick()).getRay(),gm.getPlayer(player.getNick()).getRay()
			// * 2, gm.getPlayer(player.getNick()).getRay() * 2, null);
			g.fillOval(200 + p.getX() - p.getRay(), 200 + p.getY() - p.getRay(), p.getRay() * 2, p.getRay() * 2);
		});

	}
}
