package ca.soen6441.tf.critterstd.model.strategy;

import java.util.ArrayList;

import ca.soen6441.tf.critterstd.model.Critter;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * Identifies Weakest Critter in Tower's Range and perform shooting operation
 * 
 * @author Hardik
 *
 */

public class WeakestCritterShooter implements Shooter {

	@Override
	/**
	 * Method selectCritter using TowerInterface and ArrayList<Critter> as parameters
	 * @param TowerInterface
	 * 			Supplies Tower object that is Shooting 
	 * @param ArrayList<Critter>
	 * 			Supplies ArrayList of Critters that are in Tower's Range
	 * @return Critter
	 * 			Returns Weakest Critter in the Range
	 */
	public Critter selectCritter(TowerInterface tower,
			ArrayList<Critter> critters) {
		double life;
		double smallerLife = Double.MAX_VALUE;
		Critter tempCritter = null;

		for (Critter critter : critters) {
			life = critter.getLife();

			if (life > smallerLife)
				continue;

			smallerLife = life;
			tempCritter = critter;
		}
		return tempCritter;
	}

}
