package it.mat.unical.ProgettoIGPE.utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	public static Image newGame;
	public static Image newGameH;
	public static Image multiplayer;
	public static Image multiplayerH;
	private static Image singleplayer;
	private static Image singleplayerH;
	private static Image editor;
	private static Image editorH;
	public static Image exit;
	public static Image exitH;
	private static Image background;
	private static Image back;
	private static Image player;
	private static Image save;
	private static Image spawn;
	private static Image backgroundH;
	private static Image backH;
	private static Image playerH;
	private static Image saveH;
	private static Image spawnH;
	private static Image autumn;
	private static Image abstractBG;
	private static Image f;
	private static Image m;
	private static Image g;
	private static Image Spawner;
	private static Image BorderEditor;
	private static Image BorderNick;
	private static Image BG;
	private static Image loadGame;
	private static Image loadGameH;
	private static Image EatingCells;
	private static Image bgEditor;
	private static Image BGMultiplayer;
	private static Image eatingC;

	static {
		try {
			final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			newGame = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/NewGame.png"));
			newGameH = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/NewGameP.png"));
			multiplayer = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/Multiplayer.png"));
			multiplayerH = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/MultiplayerP.png"));
			exit = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/Exit.png"));
			exitH = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/ExitP.png"));
			singleplayer = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/SinglePlayer.png"));
			singleplayerH = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/SinglePlayerP.png"));
			loadGame = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/LoadGame.png"));
			loadGameH = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/LoadGameP.png"));
			editor = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/Editor.png"));
			editorH = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/EditorP.png"));

			background = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/background.jpg"))
					.getScaledInstance(1920, 1080, 0);
			setBGMultiplayer(ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/menu/BGMultiplayer.jpg"))
					.getScaledInstance(1920, 1080, 0));
			bgEditor = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/bgEditor.jpg"))
					.getScaledInstance(1920, 1080, 0);
			BorderNick = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/game/borderNick.jpg"));
			BorderEditor = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/game/BorderEditor.gif"))
					.getScaledInstance(1920, 1080, 0);
			autumn = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/autumn.jpg"))
					.getScaledInstance(1920, 1080, 0);
			abstractBG = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/game/abstractBG.jpg"))
					.getScaledInstance(1920, 1080, 0);
			back = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/back.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			backH = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/backH.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			EatingCells = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/game/eating.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			BG = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/BG.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			backgroundH = ImageIO
					.read(Thread.currentThread().getContextClassLoader().getResource("img/game/backgroundH.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			save = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/save.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			spawn = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/spawn.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			saveH = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/saveH.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			spawnH = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/spawnH.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			Spawner = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/Spawner.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			player = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/player.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			eatingC = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/eatingC.png"));
			playerH = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/playerH.png"))
					.getScaledInstance((int) d.getWidth() / 9, (int) (d.getWidth() / 9) / 2, 0);
			f = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/f.png"));
			g = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/g.png"));
			m = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/game/m.png"));

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Image getPlayerSkin(String c) {
		switch (c) {
		case "0":
			return f;
		case "1":
			return g;
		case "2":
			return m;
		}
		return f;
	}

	public static Image getNewGameH() {
		return newGameH;
	}

	public static Image getNewGame() {
		return newGame;
	}

	public static Image getBackground() {
		return background;
	}

	public static Image getMultiplayer() {
		return multiplayer;
	}

	public static Image getMultiplayerH() {
		return multiplayerH;
	}

	public static Image getExit() {
		return exit;
	}

	public static Image getExitH() {
		return exitH;
	}

	public static Image getBack() {
		return back;
	}

	public static void setBack(Image back) {
		ImageLoader.back = back;
	}

	public static Image getPlayer() {
		return player;
	}

	public static void setPlayer(Image player) {
		ImageLoader.player = player;
	}

	public static Image getSave() {
		return save;
	}

	public static void setSave(Image save) {
		ImageLoader.save = save;
	}

	public static Image getPowers() {
		return spawn;
	}

	public static void setPowers(Image powers) {
		ImageLoader.spawn = powers;
	}

	public static Image getSingleplayer() {
		return singleplayer;
	}

	public static void setSingleplayer(Image singleplayer) {
		ImageLoader.singleplayer = singleplayer;
	}

	public static Image getSingleplayerH() {
		return singleplayerH;
	}

	public static void setSingleplayerH(Image singleplayerH) {
		ImageLoader.singleplayerH = singleplayerH;
	}

	public static Image getEditor() {
		return editor;
	}

	public static void setEditor(Image editor) {
		ImageLoader.editor = editor;
	}

	public static Image getEditorH() {
		return editorH;
	}

	public static void setbgEditor(Image bgEditor) {
		ImageLoader.bgEditor = bgEditor;
	}

	public static Image getbgEditor() {
		return bgEditor;
	}

	public static void setEditorH(Image editorH) {
		ImageLoader.editorH = editorH;
	}

	public static Image getSpawn() {
		return spawn;
	}

	public static void setSpawn(Image spawn) {
		ImageLoader.spawn = spawn;
	}

	public static Image getAutumn() {
		return autumn;
	}

	public static void setAutumn(Image autumn) {
		ImageLoader.autumn = autumn;
	}

	public static Image getAbstractBG() {
		return abstractBG;
	}

	public static void setAbstractBG(Image abstractBG) {
		ImageLoader.abstractBG = abstractBG;
	}

	public static Image getF() {
		return f;
	}

	public static void setF(Image f) {
		ImageLoader.f = f;
	}

	public static Image getM() {
		return m;
	}

	public static void setM(Image m) {
		ImageLoader.m = m;
	}

	public static Image getG() {
		return g;
	}

	public static void setG(Image g) {
		ImageLoader.g = g;
	}

	public static Image getSpawner() {
		return Spawner;
	}

	public static void setSpawner(Image spawner) {
		ImageLoader.Spawner = spawner;
	}

	public static Image getBorderEditor() {
		return BorderEditor;
	}

	public static void setBorderEditor(Image borderEditor) {
		ImageLoader.BorderEditor = borderEditor;
	}

	public static Image getBG() {
		return BG;
	}

	public static void setEatingCells(Image eatingCells) {
		ImageLoader.EatingCells = eatingCells;
	}

	public static Image getEatingCells() {
		return EatingCells;
	}

	public static void setBG(Image bG) {
		ImageLoader.BG = bG;
	}

	public static void setBackground(Image background) {
		ImageLoader.background = background;
	}

	public static Image getBackground(String c) {
		switch (c) {
		case "0":
			return background;
		case "1":
			return autumn;
		case "2":
			return abstractBG;
		}
		return null;
	}

	public static Image getLoadGame() {
		return loadGame;
	}

	public static Image getLoadGameH() {
		return loadGameH;
	}

	public static Image getBackgroundH() {
		return backgroundH;
	}

	public static void setBackgroundH(Image backgroundH) {
		ImageLoader.backgroundH = backgroundH;
	}

	public static Image getBackH() {
		return backH;
	}

	public static void setBackH(Image backH) {
		ImageLoader.backH = backH;
	}

	public static Image getPlayerH() {
		return playerH;
	}

	public static void setPlayerH(Image playerH) {
		ImageLoader.playerH = playerH;
	}

	public static Image getSaveH() {
		return saveH;
	}

	public static void setSaveH(Image saveH) {
		ImageLoader.saveH = saveH;
	}

	public static Image getSpawnH() {
		return spawnH;
	}

	public static void setSpawnH(Image spawnH) {
		ImageLoader.spawnH = spawnH;
	}

	public static Image getBorderNick() {
		return BorderNick;
	}

	public static void setBorderNick(Image borderNick) {
		BorderNick = borderNick;
	}

	public static Image getBGMultiplayer() {
		return BGMultiplayer;
	}

	public static void setBGMultiplayer(Image bGMultiplayer) {
		BGMultiplayer = bGMultiplayer;
	}

	public static Image getEatingC() {
		return eatingC;
	}

	public static void setEatingC(Image eatingC) {
		ImageLoader.eatingC = eatingC;
	}

}
