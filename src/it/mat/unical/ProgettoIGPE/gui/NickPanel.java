package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.mat.unical.ProgettoIGPE.utils.ImageLoader;

public class NickPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MainFrame f;
	int width;
	int height;

	Image multiplayer;
	private Image singlePlayer;
	private Image load;

	JTextField txt;
	private JButton cancel;
	JComboBox<Object> difficolta;

	int xmp;
	int ymp;

	int xsp;
	int ysp;

	int xlg;
	int ylg;

	public NickPanel(MainFrame mf) {
		f = mf;
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) d.getWidth();
		height = (int) d.getHeight();

		multiplayer = ImageLoader.getMultiplayer();
		singlePlayer = ImageLoader.getSingleplayer();
		load = ImageLoader.getLoadGame();

		ymp = width / 3;// coordinate
		// multiplayer
		xmp = (4 * (height / 6)) + ImageLoader.getMultiplayer().getWidth(null) / 7;

		ysp = width / 5;// coordinate singleP
		xsp = 3 * (height / 6);

		ylg = width / 5;// coordinate load game
		xlg = 6 * (height / 6);

		setPreferredSize(d);
		setLayout(null);
		this.setVisible(true);
		cancel = new JButton();
		cancel.setIcon(new ImageIcon(ImageLoader.getBack()));
		cancel.setRolloverIcon(new ImageIcon(ImageLoader.getBackH()));
		cancel.setBounds(20, 20, (int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2);
		cancel.setBorderPainted(false);
		cancel.setContentAreaFilled(false);
		cancel.setFocusPainted(false);
		txt = new JTextField(30);
		txt.setText("Insert your nickname here");

		txt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mouseClicked(arg0);
				txt.setText("");

			}
		});

		txt.setBounds((width / 2) - 100, 110, 400, 60);

		txt.setVisible(true);
		txt.setOpaque(false);
		txt.setBorder(null);
		cancel.setVisible(true);

		difficolta = new JComboBox<>();
		difficolta.addItem(new String("facile"));
		difficolta.addItem(new String("difficile"));
		difficolta.setBounds(width / 2 + 200, 130, 100, 20);

		this.add(difficolta);
		this.add(txt);
		this.add(cancel);

		cancel.addActionListener(e -> {
			f.drawPanel("menuPanel");
		});

		addMouseListener(new MouseListener() {
			// problema: click sovrapposti
			@Override
			public void mouseReleased(MouseEvent e) {
				singlePlayer = ImageLoader.getSingleplayer();
				multiplayer = ImageLoader.getMultiplayer();
				load = ImageLoader.getLoadGame();
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getPoint().distance(xsp + 150 / 2, ysp + 150 / 2) <= 150 / 2) {
					singlePlayer = ImageLoader.getSingleplayerH();
				}

				if (e.getPoint().distance(xmp + 150 / 2, ymp + 150 / 2) <= 150 / 2) {
					multiplayer = ImageLoader.getMultiplayerH();
				}
				if (e.getPoint().distance(xlg + 150 / 2, ylg + 150 / 2) <= 150 / 2) {
					load = ImageLoader.getLoadGameH();
				}
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getPoint().distance(xmp + 150 / 2, ymp + 150 / 2) <= 150 / 2) {
					multiplayer = ImageLoader.getMultiplayer();
					if (txt.getText().equals("Insert your nickname here"))
						txt.setText("");
					if (!txt.getText().isEmpty())
						f.drawPanel("multiplayerPanel");
					else
						JOptionPane.showMessageDialog(null, "insert Nickname for Multiplayer");
				}
				if (e.getPoint().distance(xlg + 150 / 2, ylg + 150 / 2) <= 150 / 2) {
					f.drawPanel("loadPanel");
				}

				if (e.getPoint().distance(xsp + 150 / 2, ysp + 150 / 2) <= 150 / 2) {
					f.drawPanel("gamePanel");
					if (txt.getText().equals("Insert your nickname here"))
						mf.gp.player.setNick("");
					else
						mf.gp.player.setNick(txt.getText());
				}
				repaint();
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(ImageLoader.getBackground(), 0, 0, null);
		g.drawImage(singlePlayer, xsp, ysp, 150, 150, null);
		g.drawImage(load, xlg, ylg, 150, 150, null);
		g.drawImage(multiplayer, xmp, ymp, 150, 150, null);
		g.drawImage(ImageLoader.getBorderNick(), (width / 2) - 250, 45, 450, 200, null);
	}
}
