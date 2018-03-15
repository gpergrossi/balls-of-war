package ballsofwar;

import java.awt.Color;
import java.awt.Graphics;

public class Game {

	public static double a;
	public static int loopState = -1;
	public static boolean friendlyFire = false;

	public static boolean initialized = false;

	public static InterfaceGroup selectionInterface = new InterfaceGroup("Interface", new Interface[0]);
	public static int lastTeamSelect = 1;
	public static int victor = 0;
	public static int victorPlayerCount = 100000;

	public static int[] players = {25, 25, 25, 25, 25, 25, 25, 25};
	public static double[] health = {100, 100, 100, 100, 100, 100, 100, 100};
	public static double[] vitality = {1, 1, 1, 1, 1, 1, 1, 1};
	public static double[] strength = {1, 1, 1, 1, 1, 1, 1, 1};
	public static double[] agility = {1, 1, 1, 1, 1, 1, 1, 1};
	public static double[] speed = {10, 10, 10, 10, 10, 10, 10, 10};
	public static double[] spread = {1, 1, 1, 1, 1, 1, 1, 1};
	public static double[] multi = {1, 1, 1, 1, 1, 1, 1, 1};
	public static int[] weapon = {1, 1, 1, 1, 1, 1, 1, 1};
	public static Color[] colorArray = {new Color(64, 64, 255), Color.RED, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK, Color.YELLOW};

	public static InterfaceValueBox numTeams, selectedTeam, numPlayers,
			teamHealth, teamVitality, teamStrength, teamAgility, teamSpeed,
			teamSpread, teamMulti;
	public static InterfaceOptionBox teamWeaponType, friendlyFireBox;
	public static InterfaceButton goButton, batchButton;

	public static void init(Graphics g) {
		System.out.println("Initializing");
		g.setFont(Main.font);
		g.setColor(Color.WHITE);

		// interface build
		InterfaceGroup.beginGroupDeclaration();
		numTeams = new InterfaceValueBox("Number of Teams", 2, 2, 8, 1, 400, 50);
		selectedTeam = new InterfaceValueBox("Selected Team", 1, 1, 8, 1, 400, 100);
		friendlyFireBox = new InterfaceOptionBox("Friendly Fire", new String[] {"On", "Off"}, 200, 100);
		numPlayers = new InterfaceValueBox("Number of Players", 25, 1, 500, 1, 400, 150);
		teamHealth = new InterfaceValueBox("Health", 100, 1, 1000, 5, 200, 200);
		teamVitality = new InterfaceValueBox("Vitality", 1, 0, 100, 1, 400, 200);
		teamStrength = new InterfaceValueBox("Strength", 1, -50, 50, 1, 600, 200);
		teamAgility = new InterfaceValueBox("Agility", 1, 0, 30, 0.1, 200, 250);
		teamSpeed = new InterfaceValueBox("Speed", 10, 1, 100, 1, 400, 250);
		teamSpread = new InterfaceValueBox("Spread", 1, 0, 90, 0.1, 600, 250);
		teamMulti = new InterfaceValueBox("Multishot", 1, 1, 20, 1, 200, 300);
		teamWeaponType = new InterfaceOptionBox("WeaponType", new String[] {"Normal", "Orbital", "Gravitational", "Fractaline", "Reflective", "Drain"}, 400, 300);
		InterfaceGroup.endGroupDeclaration();
		selectionInterface = new InterfaceGroup("Group", InterfaceGroup.getRecentGroup());
		goButton = new InterfaceButton("Go Button", "Proceed", 760, 15);
		batchButton = new InterfaceButton("Batch", "Batch", 398, 350);

		Army.addArmy(1, null, 1, 1, 1, 1, 1, 1, 1, 1);
		Army.resetAllArmies();
		initialized = true;
	}

	public static void run(Graphics g) {
		if(initialized == false) init(g);
		Draw.setGraphics(g);
		g.setColor(Color.white);

		if(loopState == -1) {
			loopState = 0;
		}

		if(loopState == 0) {
			// Team stat selection interface
			selectionInterface.setTextColor(colorArray[(int) selectedTeam.value - 1]);
			selectedTeam.setMax(numTeams.value);
			selectionInterface.run();
			selectionInterface.draw();
			if((int) selectedTeam.value != lastTeamSelect) {
				players[lastTeamSelect - 1] = (int) numPlayers.value;
				health[lastTeamSelect - 1] = teamHealth.value;
				vitality[lastTeamSelect - 1] = teamVitality.value;
				strength[lastTeamSelect - 1] = teamStrength.value;
				agility[lastTeamSelect - 1] = teamAgility.value;
				speed[lastTeamSelect - 1] = teamSpeed.value;
				spread[lastTeamSelect - 1] = teamSpread.value;
				multi[lastTeamSelect - 1] = teamMulti.value;
				weapon[lastTeamSelect - 1] = teamWeaponType.value;
				numPlayers.value = players[(int) selectedTeam.value - 1];
				teamHealth.value = health[(int) selectedTeam.value - 1];
				teamVitality.value = vitality[(int) selectedTeam.value - 1];
				teamStrength.value = strength[(int) selectedTeam.value - 1];
				teamAgility.value = agility[(int) selectedTeam.value - 1];
				teamSpeed.value = speed[(int) selectedTeam.value - 1];
				teamSpread.value = spread[(int) selectedTeam.value - 1];
				teamMulti.value = multi[(int) selectedTeam.value - 1];
				teamWeaponType.value = weapon[(int) selectedTeam.value - 1];
			}
			lastTeamSelect = (int) selectedTeam.value;

			// batch button
			batchButton.run();
			batchButton.draw();
			if(batchButton.getValue()) {
				players[lastTeamSelect - 1] = (int) numPlayers.value;
				health[lastTeamSelect - 1] = teamHealth.value;
				vitality[lastTeamSelect - 1] = teamVitality.value;
				strength[lastTeamSelect - 1] = teamStrength.value;
				agility[lastTeamSelect - 1] = teamAgility.value;
				speed[lastTeamSelect - 1] = teamSpeed.value;
				spread[lastTeamSelect - 1] = teamSpread.value;
				multi[lastTeamSelect - 1] = teamMulti.value;
				weapon[lastTeamSelect - 1] = teamWeaponType.value;
				for(int i = 0; i < numTeams.value; i++) {
					if(i != lastTeamSelect - 1) {
						players[i] = players[lastTeamSelect - 1];
						health[i] = health[lastTeamSelect - 1];
						vitality[i] = vitality[lastTeamSelect - 1];
						strength[i] = strength[lastTeamSelect - 1];
						agility[i] = agility[lastTeamSelect - 1];
						speed[i] = speed[lastTeamSelect - 1];
						spread[i] = spread[lastTeamSelect - 1];
						multi[i] = multi[lastTeamSelect - 1];
						weapon[i] = weapon[lastTeamSelect - 1];
					}
				}
			}

			// proceed button
			goButton.run();
			goButton.draw();
			if(goButton.getValue()) {
				loopState = 1;
				players[lastTeamSelect - 1] = (int) numPlayers.value;
				health[lastTeamSelect - 1] = teamHealth.value;
				vitality[lastTeamSelect - 1] = teamVitality.value;
				strength[lastTeamSelect - 1] = teamStrength.value;
				agility[lastTeamSelect - 1] = teamAgility.value;
				speed[lastTeamSelect - 1] = teamSpeed.value;
				spread[lastTeamSelect - 1] = teamSpread.value;
				multi[lastTeamSelect - 1] = teamMulti.value;
				weapon[lastTeamSelect - 1] = teamWeaponType.value;
				if(friendlyFireBox.value == 1) {
					friendlyFire = true;
				} else {
					friendlyFire = false;
				}
			}
		}

		if(loopState == 1) {
			// Create Armies from team stat arrays
			for(int t = 0; t < (int) (numTeams.value); t++) {
				Army.addArmy(players[t], colorArray[t], health[t], vitality[t], strength[t], agility[t], speed[t], spread[t], multi[t], weapon[t]);
			}
			loopState = 2;
		}

		if(loopState == 2) {
			// Run Game
			g.setColor(Color.white);

			Army.handleArmies();
			Bullet.handleAllBullets();
			goButton.run();
			goButton.draw();
			if(goButton.getValue()) loopState = 3;
		}

		if(loopState == 3) {
			// Determine victor
			victorPlayerCount = 0;
			victor = 0;
			for(int t = 0; t < numTeams.value; t++) {
				if(Army.listOfArmies[t] != null) {
					int currentPlayerCount = Army.listOfArmies[t].soldiersAlive;
					if(currentPlayerCount > victorPlayerCount) {
						victorPlayerCount = currentPlayerCount;
						victor = t + 1;
					}
				}
			}
			loopState = 4;
		}

		if(loopState == 4) {
			// Victory / Stats Screen
			if(victor == 0) {
				Draw.centerText("Fuck you! Everyone died.", 400, 200);
			} else {
				Draw.centerText("Team " + String.valueOf(victor) + " is victorious!", 400, 200);
			}
			goButton.run();
			goButton.draw();
			if(goButton.getValue()) {
				loopState = 0;
				Army.resetAllArmies();
				Bullet.clearBullets();
			}
		}
	} // End run(Graphics g)

}
