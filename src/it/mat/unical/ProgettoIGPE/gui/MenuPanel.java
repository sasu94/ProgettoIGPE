package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import it.mat.unical.ProgettoIGPE.utils.ImageLoader;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	MainFrame f;
	Image newGame;
	Image editor;
	Image exit;
	Image bg;
	int xng = 20, yng;
	int xexit = 500, yexit;
	int xmul = 250, ymul;
	int width, height;
	int r;
	boolean blockM = false;
	boolean blockNG = false;
	boolean blockE = false;

	public MenuPanel(MainFrame mf) {
		f = mf;
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) d.getWidth();
		height = (int) d.getHeight();
		r = (width - height) / 4;
		// stampa dim schermo JOptionPane.showMessageDialog(null, d.getWidth() +
		// " " + d.getHeight());
		setPreferredSize(d);
		setLayout(null);
		yng = height / 35;
		yexit = 4 * (height / 6);
		ymul = 2 * (height / 6);
		exit = ImageLoader.getExit();
		editor = ImageLoader.getEditor();
		bg = ImageLoader.getBackground();
		newGame = ImageLoader.getNewGame();
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				editor = ImageLoader.getEditor();
				blockM = false;
				exit = ImageLoader.getExit();
				blockE = false;
				newGame = ImageLoader.getNewGame();
				blockNG = false;
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getPoint().distance(xmul + r / 2, ymul + r / 2) <= r / 2) {
					editor = ImageLoader.getEditorH();
					blockM = true;
				}
				if (e.getPoint().distance(xexit + r / 2, yexit + r / 2) <= r / 2) {
					exit = ImageLoader.getExitH();
					blockE = true;
				}
				if (e.getPoint().distance(xng + r / 2, yng + r / 2) <= r / 2) {
					newGame = ImageLoader.getNewGameH();
					blockNG = true;
				}
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// JOptionPane.showMessageDialog(null, "cacca");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getPoint().distance(xmul + r / 2, ymul + r / 2) <= r / 2) {
					f.drawPanel("editorPanel");
					blockM = false;
				}
				if (e.getPoint().distance(xexit + r / 2, yexit + r / 2) <= r / 2) {
					System.exit(0);
				}
				if (e.getPoint().distance(xng + r / 2, yng + r / 2) <= r / 2) {
					f.drawPanel("nickPanel");
					blockNG = false;
				}
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				int opng = 3;
				int opmul = 2;
				int opex = 1;
				while (true) {
					if (xexit > width - r) {
						opex = -1;
					} else if (opex == -1 && xexit < 0) {
						// System.out.println(xexit + " " + (r + width / 50));
						opex = 1;
					}
					if (!blockE)
						xexit += opex;
					if (xmul > width - r) {
						opmul = -2;
					} else if (opmul == -2 && xmul < 0)
						opmul = 2;
					if (!blockM)
						xmul += opmul;
					if (xng > width - r) {
						opng = -3;
					} else if (opng == -3 && xng < 0)
						opng = 3;
					if (!blockNG)
						xng += opng;
					repaint();
					Toolkit.getDefaultToolkit().sync();
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, null);
		g.drawImage(ImageLoader.getEatingC(), (width / 2) - ImageLoader.getEatingC().getWidth(null) / 2,
				height / 2 - ImageLoader.getEatingC().getHeight(null) / 2, null);
		g.drawImage(newGame, xng, yng, r, r, null);
		g.drawImage(editor, xmul, ymul, r, r, null);
		g.drawImage(exit, xexit, yexit, r, r, null);
	}
}
