package ca.soen6441.tf.critterstd.model.strategy;

import java.util.ArrayList;

import ca.soen6441.tf.critterstd.model.Critter;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * Critter's Shooting Strategy
 * 
 * @author Hardik
 *
 */
public class ExitingCritterShooter implements Shooter {

	@Override
	/**
	 * Method selectCritter using TowerInterface and ArrayList<Critter> as parameters
	 * @param TowerInterface
	 * 			Supplies Tower object that is Shooting 
	 * @param ArrayList<Critter>
	 * 			Supplies ArrayList of Critters that are in Tower's Range
	 * @return Critter
	 * 			Returns Closest Critter to the End point of the Map
	 */
	public Critter selectCritter(TowerInterface tower,
			ArrayList<Critter> critters) {
		int distance = Integer.MAX_VALUE;
		Critter tempCritter = null;
		for (Critter critter : critters) {

			if (critter.remainingPathSize() > distance)
				continue;

			distance = critter.remainingPathSize();
			tempCritter = critter;
		}

		return tempCritter;

	}
}
