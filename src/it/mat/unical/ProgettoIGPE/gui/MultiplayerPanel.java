package it.mat.unical.ProgettoIGPE.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.mat.unical.ProgettoIGPE.net.ConnectionManager;
import it.mat.unical.ProgettoIGPE.utils.ImageLoader;

@SuppressWarnings("serial")
public class MultiplayerPanel extends JPanel {
	JTextField nick, ip, port;
	JLabel descIp = new JLabel("Ip: "), descNick = new JLabel("Nickname: "), descPort = new JLabel("Port: ");
	JButton connect;
	MainFrame mf;
	Image bg;
	JButton cancel;

	public MultiplayerPanel(MainFrame mainFrame) {

		mf = mainFrame;

		bg = ImageLoader.getBackground();
		cancel = new JButton("Back");// tasto per tornare indietro
		cancel.setIcon(new ImageIcon(ImageLoader.getBack()));
		cancel.setBounds((mf.getWidth() / 9) * 8, 20, mf.getWidth() / 9, mf.getWidth() / 9 / 2);
		cancel.setBorderPainted(false);
		cancel.setContentAreaFilled(false);
		cancel.setFocusPainted(false);
		cancel.setRolloverIcon(new ImageIcon(ImageLoader.getBackH()));
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mf.drawPanel("nickPanel");
			}
		});

		add(cancel);

		descIp.setBounds(30, 40, 40, 20);
		add(descIp);

		ip = new JTextField("127.0.0.1");
		ip.setBounds(50, 40, 100, 20);
		add(ip);

		descPort.setBounds(165, 40, 40, 20);
		add(descPort);

		port = new JTextField("8080");
		port.setBounds(205, 40, 100, 20);
		add(port);

		descNick.setBounds(315, 40, 100, 20);
		add(descNick);

		nick = new JTextField(mf.np.txt.getText());
		nick.setBounds(390, 40, 100, 20);
		add(nick);

		connect = new JButton("connect");
		connect.setBounds(500, 40, 100, 20);
		connect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Thread() {
					@Override
					public void run() {
						try {
							connectoToServer();
							connect.setEnabled(false);
						} catch (final Exception e) {
							JOptionPane.showMessageDialog(connect,
									"Impossible to connect to " + ip.getText() + ":" + port.getText(), "Network Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}.start();

			}
		});
		add(connect);

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
					break;

				default:
					break;
				}
			}
		});

		setLayout(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(ImageLoader.getBGMultiplayer(), 0, 0, mf.getWidth(), mf.getHeight(), null);

	}

	private void connectoToServer() throws NumberFormatException, UnknownHostException, IOException {
		final Socket socket = new Socket(ip.getText(), Integer.parseInt(port.getText()));
		final ConnectionManager connectionManager = new ConnectionManager(socket, nick.getText(), mf);
		new Thread(connectionManager, "Connection Manager").start();
	};

}
