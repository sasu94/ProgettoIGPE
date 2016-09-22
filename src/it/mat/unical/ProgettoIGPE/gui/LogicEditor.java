package it.mat.unical.ProgettoIGPE.gui;

import java.util.ArrayList;

import it.mat.unical.ProgettoIGPE.core.Circle;
import it.mat.unical.ProgettoIGPE.core.PowerSpawn;

public class LogicEditor {

	private Circle player;
	private ArrayList<PowerSpawn> spawn = new ArrayList<>();

	public void addSpawn(ArrayList<PowerSpawn> spawn) {
		this.spawn = spawn;
	}

	public ArrayList<PowerSpawn> getSpawn() {
		// System.out.println(spawn.size() + " ");
		return spawn;
	}

	public void addPlayer(Circle c) {
		this.player = c;
	}

	public Circle getPlayer() {
		return player;

	}

}
