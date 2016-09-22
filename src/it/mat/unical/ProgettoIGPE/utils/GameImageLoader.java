package it.mat.unical.ProgettoIGPE.utils;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameImageLoader {
	static Image background;
	static Image powerSpawn;
	static Image back;
	static Image save;
	static Image player;
	static Image powers;
	private static Image flash;
	private static Image expand;
	static {
		try {
			background = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/background.jpg"));
			powerSpawn = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/game/Spawner.png"));
			flash = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/Flash.png"));
			expand = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/Push.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Image getBackground() {
		return background;
	}

	public static Image getPowerSpawn() {
		return powerSpawn;
	}

	public static Image getBack() {
		// TODO Auto-generated method stub
		return back;
	}

	public static Image getSave() {
		// TODO Auto-generated method stub
		return save;
	}

	public static Image getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	public static Image getPowers() {
		// TODO Auto-generated method stub
		return powers;
	}

	public static Image getFlash() {
		return flash;
	}

	public static void setFlash(Image flash) {
		GameImageLoader.flash = flash;
	}

	public static Image getExpand() {
		return expand;
	}

	public static void setExpand(Image expand) {
		GameImageLoader.expand = expand;
	}

}
