package mfb;
import robocode.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
* Lui - a robot by (your name here)
*/
public class Lui extends Robot{
	public void run() {
		//Bare gå slik i starten til man finner en robot
		while(true) {
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}
	}

/**
* onScannedRobot: What to do when you see another robot
*/
	public void onScannedRobot(ScannedRobotEvent e) {
		if (e.getDistance() > 160) {
			turnRight(e.getBearing());
			ahead(e.getDistance() - 150);
			return;
		}
		// Snu seg mot den andre roboten
		turnRight(e.getBearing());
		//Skuyet med tanke på hvor svekka den andre roboten er
		if (e.getEnergy() > 16) {
			fire(3);
		} else if (e.getEnergy() > 10) {
			fire(2);
		} else if (e.getEnergy() > 4) {
			fire(1);
		} else if (e.getEnergy() > 2) {
			fire(.5);
		}else{
			fire(2);
		}

		// Gå bakover hvis man er for nær
		if (e.getDistance() < 120) {
			if (e.getBearing() > -90 && e.getBearing() <= 90) {
			back(60);}
			 else {
			ahead(20);}
		}
		scan();
	}
}