package ballsofwar;

import java.awt.Color;

public class Army {

	public static Army[] listOfArmies = new Army[0];
	public static int totalPlayers = 0;
	public Color color;
	public Soldier[] players;
	public int soldiersAlive = 0;
	
	public static void addArmy(int numPlayers, Color color, double health, double vit, double str, double agil, double speed, double spread, double multi, int weapon) {
		Army[] list = new Army[listOfArmies.length+1];
		for(int c = 0; c < listOfArmies.length; c++) {
			list[c] = listOfArmies[c];
		}
		listOfArmies = list;
		listOfArmies[list.length-1] = new Army(numPlayers, color, health, vit, str, agil, speed, spread, multi, weapon);
		totalPlayers += numPlayers;
	}
	
	public static void handleArmies() {
		for(int c = 0; c < listOfArmies.length; c++) {
			if(listOfArmies[c] != null) {
				listOfArmies[c].handleSoldiers();
			}
		}
	}
	
	public Army(int numPlayers, Color color, double health, double vit, double str, double agil, double speed, double spread, double multi, int weapon) {
		this.players = new Soldier[numPlayers];
		for(int c = 0; c < numPlayers; c++) {
			players[c] = new Soldier(this, new Point2D(Main.random(0, Main.screenWidth), 
					                 Main.random(0, Main.screenHeight)), c, health, vit, str, agil, speed, spread, multi, weapon);
		}
		this.color = color;
		soldiersAlive = numPlayers;
	}
	
	public static void resetAllArmies() {
		for(int c = 0; c < listOfArmies.length; c++) {
			if(listOfArmies[c] != null) {
				listOfArmies[c].reset();
			}
		}
		listOfArmies = new Army[0];
		totalPlayers = 0;
	}
	
	public void reset() {
		color = null;
		players = null;
		soldiersAlive = 0;
	}
	
	public void handleSoldiers() {
		for(int c = 0; c < players.length; c++) {
			if(players[c] != null) {
				players[c].runAI();
				players[c].draw();
			}
		}
	}

}
