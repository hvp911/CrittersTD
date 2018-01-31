/**
 * 
 */
package ca.soen6441.tf.critterstd.model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import ca.soen6441.tf.critterstd.controller.GamePlayController;
import ca.soen6441.tf.critterstd.model.Critter;
import ca.soen6441.tf.critterstd.model.tower.FireEffect;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;
import ca.soen6441.tf.critterstd.utility.GameEvent;

/**
 * @author Farzana Alam Class Fire perform Fire Operation on Critters
 */
public class Fire implements Runnable {

	private int serial;

	private int x;
	private int y;

	private int length;

	private Color color;

	private TowerInterface tower;
	private Critter critter;

	// target point: center of a critter
	private int critterX;
	private int critterY;

	private int deltaX;
	private int deltaY;

	private double error;
	private double deltaError;

	/**
	 * Perform Shooting Operation by accepting Parameter serial, tower and
	 * critter
	 * 
	 * @param serial
	 *            serial number
	 * @param tower
	 *            Instance of Tower
	 * @param critter
	 *            Instance of Critters
	 */
	public Fire(int serial, TowerInterface tower, Critter critter) {
		this.serial = serial;
		this.tower = tower;
		this.critter = critter;
		this.color = tower.getFireColor();
		this.length = tower.getFireLength();
		this.x = tower.getX();
		this.y = tower.getY();

		tower.setShooting(true);
	}

	/**
	 * Bresenham's Line algorithm.
	 * 
	 * @return either true/false
	 */
	private boolean move() {

		if (critter.isReachedExitPoint() || critter.isDead())
			return false;

		if (!critter.isStartedMoving())
			return true;

		critterX = critter.getX();
		critterY = critter.getY();

		if ((Math.abs(x - critterX) <= length)
				&& (Math.abs(y - critterY) <= length)) {
			// critter is hit
			hitCritter();
			return false;
		}

		error = 0;

		deltaX = critterX - x;
		deltaY = critterY - y;

		if (deltaX == 0) {
			deltaX = critterX - tower.getX();
			deltaY = critterY - tower.getY();
		}

		if (deltaX == 0) {
			return true;
		}
		deltaError = 1.0 * deltaY / deltaX;

		int initialTmpStartX = x;
		int incrementX = (x < critterX) ? 1 : -1;
		int tmpY = y;
		int incrementY = (y < critterY) ? 1 : -1;

		for (int tmpX = x; Math.abs(tmpX - initialTmpStartX) <= length; tmpX += incrementX) {

			if (deltaError == 0) {
				continue;
			} else if (deltaError > 0) {
				error += deltaError;
				if (error >= 0.5) {
					tmpY += incrementY;
					error -= 1.0;
				} // end if
			} else if (deltaError < 0) {
				error += deltaError;
				if (error <= -0.5) {
					tmpY += incrementY;
					error += 1.0;
				} // end if
			}

			if (Math.abs(tmpX - initialTmpStartX) == length / 4) {
				x = tmpX;
				y = tmpY;
			}
		}

		return true;
	}

	/**
	 * Perform hitCritter operation and shooting to critters
	 */
	private void hitCritter() {
		if (!tower.isFiringSpecial()) {
			critter.hitCritter(tower.getDamageCapability(), color);
			return;
		}

		FireEffect specialFire = tower.getSpecialFire();
		switch (specialFire) {
		case BURN:
			critter.burn(tower.getDamageCapability());
			// LOG: Event
			new GameEvent("Tower serial: " + tower.getSerial()
					+ " is firing burning effect.", "towers", "wave"
					+ GamePlayController.getGamePlayController()
							.getWaveNumber(), "tower" + tower.getSerial());
			break;
		case SPLASH:
			GamePlayController.getGamePlayController().splashFire(x, y, tower,
					critter, color);
			// LOG: Event
			new GameEvent("Tower serial: " + tower.getSerial()
					+ " is firing splashing effect.", "towers", "wave"
					+ GamePlayController.getGamePlayController()
							.getWaveNumber(), "tower" + tower.getSerial());
			break;
		case FREEZE:
			critter.freeze(tower.getDamageCapability(), color);
			// LOG: Event
			new GameEvent("Tower serial: " + tower.getSerial()
					+ " is firing freezing effect.", "towers", "wave"
					+ GamePlayController.getGamePlayController()
							.getWaveNumber(), "tower" + tower.getSerial());
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	/**
	 * Starts Fire Thread
	 */
	@Override
	public void run() {

		Timer timer = new Timer(15, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!move()) {

					((Timer) e.getSource()).stop();
					tower.setShooting(false);
					GamePlayController.getGamePlayController().removeElement(
							serial, false);
					return;
				}

				if (critter.isStartedMoving()) {
					GamePlayController.getGamePlayController().paintFireOnMap(
							serial, x, y, color);

				} // end if
			} // end actionPerformed
		});

		timer.start();

	}
}
