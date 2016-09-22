package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import it.mat.unical.ProgettoIGPE.core.Circle;
import it.mat.unical.ProgettoIGPE.core.PowerSpawn;
import it.mat.unical.ProgettoIGPE.utils.GameImageLoader;
import it.mat.unical.ProgettoIGPE.utils.ImageLoader;

public class CentralEditorPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MainFrame f;
	LogicEditor le;
	int width;
	int height;
	Image bg;
	Image imagePlayer;
	int playerCount;
	int bgCount;

	public CentralEditorPanel(EditorPanel ep) {
		super();

		final Dimension d = new Dimension(5000, 5000);
		this.setSize(d);
		this.setBackground(Color.BLACK);
		this.setPreferredSize(d);
		this.setLayout(null);
		this.setVisible(true);
		bg = ImageLoader.getBackground();
		bgCount = 0;
		playerCount = 0;
		le = new LogicEditor();

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

				// TODO Auto-generated method stub
				if (ep.getAddPlayer()) {
					le.addPlayer(new Circle(e.getX(), e.getY(), 50, null));
					repaint();
				}

				if (ep.getAddPow()) {
					le.getSpawn().add(new PowerSpawn(e.getX(), e.getY(), 50, null));
					repaint();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void ChangeBGCount(int bgCount) {
		this.bgCount = bgCount;
		// return bgCount;
	}

	public int getBGCount() {
		return bgCount;
	}

	public void ChangePlayerCount(int playerCount) {
		this.playerCount = playerCount;
		// return bgCount;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void changeBackground() {

		if (bgCount == 0) {
			bg = ImageLoader.getBackground();
			repaint();

		} else if (bgCount == 1) {
			bg = ImageLoader.getAutumn();
			repaint();

		} else if (bgCount == 2) {
			bg = ImageLoader.getAbstractBG();
			repaint();

		}
	}

	public void changePlayer() {

		if (playerCount == 0) {
			imagePlayer = ImageLoader.getF();
			repaint();

		} else if (playerCount == 1) {
			imagePlayer = ImageLoader.getG();
			repaint();

		} else if (playerCount == 2) {
			imagePlayer = ImageLoader.getM();
			repaint();

		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, 5000, 5000, null);
		if (le.getPlayer() != null) {

			g.drawImage(imagePlayer, le.getPlayer().getX() - le.getPlayer().getRay(),
					le.getPlayer().getY() - le.getPlayer().getRay(), le.getPlayer().getRay() * 2,
					le.getPlayer().getRay() * 2, null);

		}

		if (le.getSpawn() != null) {
			for (PowerSpawn p : le.getSpawn()) {
				g.drawImage(GameImageLoader.getPowerSpawn(), p.getX() - p.getRay(), p.getY() - p.getRay(), null);
			}
		}

	}

}
