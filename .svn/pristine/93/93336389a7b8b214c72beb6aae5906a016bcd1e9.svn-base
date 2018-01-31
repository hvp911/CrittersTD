/**
 * 
 */
package ca.soen6441.tf.critterstd.model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import ca.soen6441.tf.critterstd.controller.GamePlayController;

/**
 * @author Farzana Alam
 *
 */
public class Splash implements Runnable {

	private int serial;

	private int x;
	private int y;

	private Color color;

	private Critter critter;

	private int critterX;
	private int critterY;

	private int deltaX;
	private int deltaY;

	private double error;
	private double deltaError;

	private double damage;
	private int length;

	private int previousX;

	public Splash(int serial, int x, int y, int length, double damage,
			Critter critter, Color color) {
		this.serial = serial;
		this.x = x;
		this.y = y;
		this.critter = critter;
		this.color = color;
		this.damage = damage;
		this.length = length;
	}

	/**
	 * Move performed Critter Moving Operation and change their direction
	 * 
	 * @return true/false
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
			critter.hitCritter(damage, color);
			return false;
		}

		error = 0;

		deltaX = critterX - x;
		deltaY = critterY - y;

		if (deltaX == 0) {
			if (x < previousX) {
				x--;
			} else
				x++;
			return true;
		}
		deltaError = 1.0 * deltaY / deltaX;

		int initialTmpStartX = x;
		int incrementX = (x < critterX) ? 1 : -1;
		int tmpY = y;
		int incrementY = (y < critterY) ? 1 : -1;
		previousX = x;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		Timer timer = new Timer(30, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!move()) {

					((Timer) e.getSource()).stop();
					GamePlayController.getGamePlayController().removeSplash(
							serial, Splash.this);
					return;
				}

				if (critter.isStartedMoving() && deltaError != 0) {
					GamePlayController.getGamePlayController().paintSplash(
							serial, x, y, color);

				} // end if
			} // end actionPerformed
		});

		timer.start();

	}

}
