package it.mat.unical.ProgettoIGPE.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.mat.unical.ProgettoIGPE.core.Circle;
import it.mat.unical.ProgettoIGPE.core.MultiGameManager;

public class ServerGameManager {
	private final Set<ClientManager> clients = new HashSet<ClientManager>();
	private final Set<ClientManager> readyClients = new HashSet<ClientManager>();
	MultiGameManager gameManager;

	public void add(final ClientManager cm) {
		clients.add(cm);
	}

	public void dispatch(final String message, final ClientManager senderClientManager) {
		for (final ClientManager cm : clients) {
			if (cm != senderClientManager) {
				cm.dispatch(message);
			}
		}
	}

	public String getConnectedClientNames() {
		final StringBuilder sb = new StringBuilder();
		for (final ClientManager cm : clients) {
			if (cm.getName() != null) {
				sb.append(cm.getName());
				sb.append(";");
			}
		}
		return sb.toString();
	}

	public void received(final String buffer) {
		final String[] split = buffer.split(" ");
		Circle player = gameManager.getPlayer(split[0]);
		player.setSpeedX(Integer.parseInt(split[1]));
		player.setSpeedY(Integer.parseInt(split[2]));

	}

	public void setReady(final ClientManager clientManager) {
		synchronized (readyClients) {
			readyClients.add(clientManager);
			if (readyClients.size() == 2) {
				dispatch("#START", null);
				System.out.println("ServerGameManager.setReady()");
			}
		}
	}

	public void startGame() throws IOException {
		final List<String> names = new ArrayList<>();
		for (final ClientManager cm : clients) {
			cm.setup();
			new Thread(cm, cm.toString()).start();
			names.add(cm.getName());
		}
		gameManager = new MultiGameManager();
		gameManager.start(new Runnable() {
			@Override
			public void run() {
				final String statusToString = gameManager.statusToString();
				dispatch(statusToString, null);
			}
		}, names);
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(50);
					} catch (final InterruptedException e) {
						// IGNORE
					}
				}
			};
		}.start();
	}

}
