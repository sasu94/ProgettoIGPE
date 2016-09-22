package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.mat.unical.ProgettoIGPE.core.MultiGameManager;
import it.mat.unical.ProgettoIGPE.net.ConnectionManager;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	MenuPanel mp;
	GamePanel gp;
	NickPanel np;
	DialogPlayerPanel dpp;
	private EditorPanel ep;
	MultiplayerPanel multiplayerPanel;
	public MultiGamePanel mgp;

	public MainFrame() {
		mp = new MenuPanel(this);
		this.setUndecorated(true);
		this.toFront();
		drawPanel("menuPanel");
		this.setLayout(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(screenSize);
		this.pack();
		this.revalidate();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public MultiGameManager drawMultiPanel(ConnectionManager connectionManager) {
		drawPanel("multiGamePanel");
		return mgp.startNetwork(connectionManager);

	}

	public void drawPanel(String panel) {

		switch (panel) {
		case "menuPanel":
			gp = null;
			this.setContentPane(mp);
			break;
		case "editorPanel":
			ep = new EditorPanel(this);
			ep.setFocusable(true);
			ep.updateUI();
			ep.requestFocus();
			this.setContentPane(ep);
			this.revalidate();
			break;
		case "gamePanel":
			gp = new GamePanel(this);
			gp.setFocusable(true);
			gp.updateUI();
			this.setContentPane(gp);
			gp.requestFocus();
			break;
		case "loadPanel":
			JFileChooser jf = new JFileChooser(System.getProperty("user.dir").concat("/resources/coordinates"));
			int o = jf.showOpenDialog(this);
			if (o == JFileChooser.APPROVE_OPTION) {
				List<String> lines = new ArrayList<String>();
				try {
					lines = Files.readAllLines(jf.getSelectedFile().toPath());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, "an error occured!");
				}
				gp = new GamePanel(this, lines.get(0), lines.get(1));
				gp.setFocusable(true);
				gp.updateUI();
				this.setContentPane(gp);
				gp.requestFocus();
			}
			break;
		case "nickPanel":
			np = new NickPanel(this);
			np.setFocusable(true);
			np.updateUI();
			this.setContentPane(np);
			np.requestFocus();
			break;
		case "multiplayerPanel":
			multiplayerPanel = new MultiplayerPanel(this);
			multiplayerPanel.setFocusable(true);
			multiplayerPanel.updateUI();
			this.setContentPane(multiplayerPanel);
			multiplayerPanel.requestFocus();
			break;
		case "multiGamePanel":
			mgp = new MultiGamePanel(this);
			mgp.setFocusable(true);
			mgp.updateUI();
			setContentPane(mgp);
			mgp.requestFocus();
		}

		pack();
	}

	public static void main(String[] args) {
		final MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}

}
