package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import it.mat.unical.ProgettoIGPE.utils.ImageLoader;

public class DialogPlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	MainFrame frame;
	int width;
	int height;
	int begin = this.getWidth() / 9;

	public DialogPlayerPanel(MainFrame frame, EditorPanel ep, CentralEditorPanel c) {
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) d.getWidth();
		height = (int) d.getHeight();
		begin = this.getWidth() / 9;

		this.frame = frame;

		this.dialog = new JDialog();

		JButton firstPlayer = new JButton("player1");
		firstPlayer.setIcon(new ImageIcon(
				ImageLoader.getF().getScaledInstance((int) (height * 0.2), (int) (height * 0.2), Image.SCALE_SMOOTH)));
		firstPlayer.setBounds(0, 0, (int) (height * 0.2), (int) (height * 0.2));
		firstPlayer.setContentAreaFilled(false);
		firstPlayer.setVisible(true);
		firstPlayer.addActionListener(l -> ep.addPlayer(true));
		firstPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.ChangePlayerCount(0);
				c.changePlayer();
			}
		});

		this.dialog.add(firstPlayer);

		JButton secondPlayer = new JButton("player2");
		secondPlayer.setIcon(new ImageIcon(
				ImageLoader.getG().getScaledInstance((int) (height * 0.2), (int) (height * 0.2), Image.SCALE_SMOOTH)));
		secondPlayer.setBounds((int) (height * 0.2), 0, (int) (height * 0.2), (int) (height * 0.2));
		secondPlayer.setContentAreaFilled(false);
		secondPlayer.setVisible(true);
		secondPlayer.addActionListener(l -> ep.addPlayer(true));
		secondPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.ChangePlayerCount(1);
				c.changePlayer();
			}
		});
		this.dialog.add(secondPlayer);

		JButton thirdPlayer = new JButton("player3");
		thirdPlayer.setIcon(new ImageIcon(
				ImageLoader.getM().getScaledInstance((int) (height * 0.2), (int) (height * 0.2), Image.SCALE_SMOOTH)));
		thirdPlayer.setBounds((int) (height * 0.2) * 2, 0, (int) (height * 0.2), (int) (height * 0.2));
		thirdPlayer.setContentAreaFilled(false);
		thirdPlayer.setVisible(true);
		thirdPlayer.addActionListener(l -> ep.addPlayer(true));
		thirdPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.ChangePlayerCount(2);
				c.changePlayer();
			}
		});
		this.dialog.add(thirdPlayer);

		this.dialog.add(this);
		this.setBackground(Color.BLACK);
		this.dialog.setTitle("Choose Player ");
		this.dialog.setMinimumSize(new Dimension((int) (width * 0.34), (int) (height * 0.25)));
		this.dialog.setPreferredSize(new Dimension((int) (width * 0.34), (int) (height * 0.25)));
		this.dialog.setBounds(begin, this.getHeight() - begin, (int) (width * 0.34), (int) (height * 0.25));
		this.dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.dialog.setLocation((int) (height * 0.1), (int) (height * 0.7));
		this.dialog.setModal(true);
		this.dialog.setVisible(true);

		this.setVisible(true);
		this.frame.add(this);
	}

}
