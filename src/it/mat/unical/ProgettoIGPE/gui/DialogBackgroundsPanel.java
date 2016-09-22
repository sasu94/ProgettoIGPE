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

public class DialogBackgroundsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JDialog dialogBG;
	CentralEditorPanel c;
	MainFrame frame;
	Image autumn;
	int width;
	int height;
	int begin = this.getWidth() / 9;

	public DialogBackgroundsPanel(MainFrame frame, CentralEditorPanel c, EditorPanel ep) {
		this.c = c;
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) d.getWidth();
		height = (int) d.getHeight();
		begin = this.getWidth() / 9;

		this.frame = frame;

		this.dialogBG = new JDialog();

		JButton firstBackground = new JButton("s1");
		firstBackground.setIcon(new ImageIcon(ImageLoader.getBackground().getScaledInstance((int) (height * 0.2),
				(int) (height * 0.2), Image.SCALE_SMOOTH)));
		firstBackground.setBackground(Color.WHITE);
		firstBackground.setBounds(0, 0, (int) (height * 0.2), (int) (height * 0.2));
		firstBackground.setContentAreaFilled(true);
		firstBackground.setVisible(true);
		firstBackground.addActionListener(l -> ep.addBG(true));
		firstBackground.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.ChangeBGCount(0);
				c.changeBackground();
			}
		});

		this.dialogBG.add(firstBackground);

		JButton secondBG = new JButton("s2");
		secondBG.setIcon(new ImageIcon(ImageLoader.getAutumn().getScaledInstance((int) (height * 0.2),
				(int) (height * 0.2), Image.SCALE_SMOOTH)));
		secondBG.setBounds((int) (height * 0.2), 0, (int) (height * 0.2), (int) (height * 0.2));
		secondBG.setContentAreaFilled(false);
		secondBG.setVisible(true);
		secondBG.addActionListener(l -> ep.addBG(true));
		secondBG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.ChangeBGCount(1);
				c.changeBackground();
			}
		});

		this.dialogBG.add(secondBG);

		JButton thirdBG = new JButton("s3");
		thirdBG.setIcon(new ImageIcon(ImageLoader.getAbstractBG().getScaledInstance((int) (height * 0.2),
				(int) (height * 0.2), Image.SCALE_SMOOTH)));
		thirdBG.setBounds((int) (height * 0.2) * 2, 0, (int) (height * 0.2), (int) (height * 0.2));
		thirdBG.setContentAreaFilled(false);
		thirdBG.setVisible(true);

		thirdBG.addActionListener(l -> ep.addBG(true));
		thirdBG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.ChangeBGCount(2);
				c.changeBackground();
			}
		});

		this.dialogBG.add(thirdBG);

		this.dialogBG.add(this);
		this.setBackground(Color.BLACK);
		this.dialogBG.setTitle("Choose Background ");
		this.dialogBG.setMinimumSize(new Dimension((int) (width * 0.34), (int) (height * 0.2)));
		this.dialogBG.setPreferredSize(new Dimension((int) (width * 0.34), (int) (height * 0.2)));
		this.dialogBG.setBounds(begin, this.getHeight() - begin, (int) (width * 0.34), (int) (height * 0.2));
		this.dialogBG.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.dialogBG.setLocation((int) (height * 0.6), (int) (height * 0.7));
		this.dialogBG.setModal(true);
		this.dialogBG.setVisible(true);

		this.setVisible(true);
		this.frame.add(this);

	}
}
