package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.mat.unical.ProgettoIGPE.core.PowerSpawn;
import it.mat.unical.ProgettoIGPE.utils.ImageLoader;

@SuppressWarnings("serial")
public class EditorPanel extends JPanel {
	MainFrame f;
	int width;
	int height;
	DialogPlayerPanel dpp;
	DialogSpawnPanel dpSpawn;
	DialogBackgroundsPanel dbg;
	CentralEditorPanel c;
	private JScrollPane pane;
	private JButton back;
	private JButton save;
	private JButton player;
	private JButton spawn;
	private JButton background;
	boolean addPlayer;
	boolean addSpawner;
	boolean addBG;
	boolean addPow;
	int begin = this.getWidth() / 11;

	public void addPanel() {

		dpp = new DialogPlayerPanel(f, this, c);

	}

	public void addPanelPow() {
		dpSpawn = new DialogSpawnPanel(f, this);

	}

	public void addPanelBG() {
		dbg = new DialogBackgroundsPanel(f, c, this);
	}

	public EditorPanel(MainFrame f) {
		super();
		this.f = f;
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) d.getWidth();
		height = (int) d.getHeight();
		this.setSize(width, height);
		this.setBackground(new Color(0, 102, 0));
		this.c = new CentralEditorPanel(this);
		this.setPreferredSize(d);
		setLayout(null);
		setupButtons();
		this.setVisible(true);

		back.addActionListener(e -> {
			this.f.drawPanel("menuPanel");
		});

		player.addActionListener(e -> {
			addPlayer = false;
			addBG = false;
			addPow = false;
			addPanel();

			this.requestFocus();

		});

		spawn.addActionListener(e -> {
			addPlayer = false;
			addBG = false;
			addPow = false;
			addPanelPow();

		});

		background.addActionListener(e -> {
			addPlayer = false;
			addBG = false;
			addPow = false;
			addPanelBG();
		});

		save.addActionListener(e -> {
			if (c.le.getPlayer() != null) {
				List<String> s = Arrays.asList("Editor", saveMap());

				JFileChooser jf = new JFileChooser(System.getProperty("user.dir").concat("/resources/coordinates"));
				jf.setSelectedFile(new File(
						"Editor_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())));
				int o = jf.showSaveDialog(f);
				if (o == JFileChooser.APPROVE_OPTION) {
					Path file = Paths.get(jf.getSelectedFile().getPath());
					try {
						Files.write(file, s);
					} catch (IOException e1) {
						JOptionPane.showConfirmDialog(f, "an error occured");
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "Insert a Player please!", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		});

		this.pane = new JScrollPane(c, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.pane.getHorizontalScrollBar().setBackground(Color.DARK_GRAY);
		this.pane.getVerticalScrollBar().setBackground(Color.DARK_GRAY);
		this.pane.setOpaque(false);
		this.pane.setSize((int) d.getWidth() - (d.width / 5), (int) d.getHeight() - (d.height / 3));
		this.pane.getViewport().setOpaque(false);
		this.pane.setBounds(width / 2 - pane.getWidth() / 2, height / 7, pane.getWidth(), pane.getHeight());
		this.pane.setVisible(true);
		this.add(pane, this.f);
	}

	private String saveMap() {
		StringBuilder sb = new StringBuilder();
		sb.append(c.bgCount + ";");
		sb.append(c.le.getPlayer().getX() + " " + c.le.getPlayer().getY() + " " + c.playerCount + ";");
		if (c.le.getSpawn().size() != 0)
			for (PowerSpawn p : c.le.getSpawn())
				sb.append(p.getX() + " " + p.getY() + ",");
		else
			sb.append("_");
		return sb.toString();
	}

	private void setupButtons() {
		back = new JButton("back");
		back.setIcon(new ImageIcon(ImageLoader.getBack()));
		back.setRolloverIcon(new ImageIcon(ImageLoader.getBackH()));
		save = new JButton("save");
		save.setIcon(new ImageIcon(ImageLoader.getSave()));
		save.setRolloverIcon(new ImageIcon(ImageLoader.getSaveH()));
		player = new JButton("player");
		player.setIcon(new ImageIcon(ImageLoader.getPlayer()));
		player.setRolloverIcon(new ImageIcon(ImageLoader.getPlayerH()));
		spawn = new JButton("Spawn");
		spawn.setIcon(new ImageIcon(ImageLoader.getSpawn()));
		spawn.setRolloverIcon(new ImageIcon(ImageLoader.getSpawnH()));
		background = new JButton("background");
		background.setIcon(new ImageIcon(ImageLoader.getBG()));
		background.setRolloverIcon(new ImageIcon(ImageLoader.getBackgroundH()));

		begin = this.getWidth() / 11;

		back.setBounds(begin * 9, this.getHeight() - (begin), begin, begin / 2);
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setFocusPainted(false);
		this.add(back);
		spawn.setBounds(begin * 3, this.getHeight() - begin, begin, begin / 2);
		spawn.setBorderPainted(false);
		spawn.setContentAreaFilled(false);
		spawn.setFocusPainted(false);
		this.add(spawn);
		player.setBounds(begin, this.getHeight() - begin, begin, begin / 2);
		player.setBorderPainted(false);
		player.setContentAreaFilled(false);
		player.setFocusPainted(false);
		this.add(player);
		background.setBounds(begin * 5, this.getHeight() - begin, begin + 5, begin / 2);
		background.setBorderPainted(false);
		background.setContentAreaFilled(false);
		background.setFocusPainted(false);
		this.add(background);
		save.setBounds(begin * 7, this.getHeight() - (begin), begin, begin / 2);
		save.setBorderPainted(false);
		save.setContentAreaFilled(false);
		save.setFocusPainted(false);
		this.add(save);
	}

	public void addPlayer(boolean addPlayer) {
		this.addPlayer = addPlayer;
	}

	public boolean getAddPlayer() {
		return this.addPlayer;
	}

	public void addBG(boolean addBG) {
		this.addBG = addBG;
	}

	public boolean getAddBG() {
		return this.addBG;
	}

	public void addPow(boolean addPow) {
		this.addPow = addPow;
	}

	public boolean getAddPow() {
		return this.addPow;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ImageLoader.getbgEditor(), 0, 0, null);
		g.drawImage(ImageLoader.getEatingCells(), (width / 3) + 50, 35, 300, 70, null);
		g.drawImage(ImageLoader.getBorderEditor(), 0, 0, width, height, null);
	}

}
