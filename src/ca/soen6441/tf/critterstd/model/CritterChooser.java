/**
 * 
 */
package ca.soen6441.tf.critterstd.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ca.soen6441.tf.critterstd.model.strategy.ClosestShooter;
import ca.soen6441.tf.critterstd.model.strategy.ExitingCritterShooter;
import ca.soen6441.tf.critterstd.model.strategy.Shooter;
import ca.soen6441.tf.critterstd.model.strategy.StrongestCritterShooter;
import ca.soen6441.tf.critterstd.model.strategy.WeakestCritterShooter;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * @author Vishal
 *
 */
public class CritterChooser {

	private static CritterChooser instance;
	private Shooter shooter;

	/**
	 * Create Super class for CritterChooser
	 */
	private CritterChooser() {
		super();
	}

	/**
	 * Create an object of GamePlayController
	 * 
	 * @return CritterChooser
	 */
	public static CritterChooser getCrittersContext() {
		if (instance == null)
			instance = new CritterChooser();

		return instance;
	}

	/**
	 * Perform Shooting Strategy against Critters
	 * 
	 * @param aTower
	 *            Passes Tower To Select
	 * @param critters
	 *            Passes the Critters Available on Map
	 * @return Critter Returns The Critter to Shoot
	 */
	public Critter getTheCritterToShoot(TowerInterface aTower,
			ArrayList<Critter> critters) {

		switch (aTower.getShootingStrategy()) {
		case SHOOT_CLOSEST:
			setShooter(new ClosestShooter());
			break;
		case SHOOT_EXITING:
			setShooter(new ExitingCritterShooter());
			break;
		case SHOOT_STRONGEST:
			setShooter(new StrongestCritterShooter());
			break;
		case SHOOT_WEAKEST:
			setShooter(new WeakestCritterShooter());
			break;
		default:
			break;
		}

		ArrayList<Critter> crittersInRange = getCrittersInRange(aTower,
				critters);

		if (crittersInRange.isEmpty())
			return null;

		if (crittersInRange.size() == 1)
			return crittersInRange.get(0);

		return shooter.selectCritter(aTower, crittersInRange);

	}

	/**
	 * Calculate Distance between Tower and Critters
	 * 
	 * @param tower
	 *            Passes Towers to Choose Critters In Range
	 * @param critters
	 *            Passes Critters On Map
	 * @return Returns Critters In Tower's Range
	 */
	public ArrayList<Critter> getCrittersInRange(TowerInterface tower,
			ArrayList<Critter> critters) {

		ArrayList<Critter> crittersInRange = new ArrayList<Critter>();

		if (!critters.isEmpty()) {
			for (int i = 0; i < critters.size(); i++) {
				DecimalFormat decimalFormat = new DecimalFormat("0.00");

				double distance = Double.valueOf(decimalFormat.format(Math
						.sqrt((critters.get(i).getX() - tower.getX())
								* (critters.get(i).getX() - tower.getX())
								+ (critters.get(i).getY() - tower.getY())
								* (critters.get(i).getY() - tower.getY()))));

				if (distance <= (tower.getRange() / 2)) {
					crittersInRange.add(critters.get(i));
				}
			}
		}

		return crittersInRange;
	}

	/**
	 * Set The Shooter
	 * 
	 * @param shooter
	 */
	private void setShooter(Shooter shooter) {
		this.shooter = shooter;
	}
}
