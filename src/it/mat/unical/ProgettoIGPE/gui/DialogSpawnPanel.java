package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import it.mat.unical.ProgettoIGPE.utils.ImageLoader;

public class DialogSpawnPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JDialog dialogSpawn;
	CentralEditorPanel c;
	MainFrame frame;
	int width;
	int height;
	int begin = this.getWidth() / 9;

	public DialogSpawnPanel(MainFrame frame, EditorPanel ep) {
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) d.getWidth();
		height = (int) d.getHeight();
		begin = this.getWidth() / 9;

		this.frame = frame;

		this.dialogSpawn = new JDialog();

		JButton Spawn = new JButton("spawn");
		Spawn.setIcon(new ImageIcon(ImageLoader.getSpawner().getScaledInstance((int) (height * 0.2),
				(int) (height * 0.2), Image.SCALE_SMOOTH)));
		Spawn.setBounds(0, 0, (int) (height * 0.2), (int) (height * 0.2));
		Spawn.setContentAreaFilled(false);
		Spawn.setVisible(true);
		Spawn.addActionListener(l -> ep.addPow(true));
		this.dialogSpawn.add(Spawn);

		this.dialogSpawn.add(this);
		this.setBackground(Color.BLACK);
		this.dialogSpawn.setTitle("Spawn ");
		this.dialogSpawn.setMinimumSize(new Dimension((int) (width * 0.12), (int) (height * 0.23)));
		this.dialogSpawn.setPreferredSize(new Dimension((int) (width * 0.12), (int) (height * 0.23)));
		this.dialogSpawn.setBounds(begin, this.getHeight() - begin, (int) (width * 0.12), (int) (height * 0.23));
		this.dialogSpawn.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.dialogSpawn.setLocation((int) (height * 0.5), (int) (height * 0.7));
		this.dialogSpawn.setModal(true);
		this.dialogSpawn.setVisible(true);

		this.setVisible(true);
		this.frame.add(this);
	}
}
