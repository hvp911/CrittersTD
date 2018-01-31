package ca.soen6441.tf.critterstd.model.strategy;

/**
 * This class include closestShooter strategy to kill critters.
 */

import java.util.ArrayList;

import ca.soen6441.tf.critterstd.model.Critter;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

public class ClosestShooter implements Shooter {

	@Override
	/**
	 * Method selectCritter using TowerInterface and ArrayList<Critter> as parameters
	 * @param TowerInterface
	 * 			Supplies Tower object that is Shooting 
	 * @param ArrayList<Critter>
	 * 			Supplies ArrayList of Critters that are in Tower's Range
	 * @return Critter
	 * 			Returns Closest Critter in Tower's Range
	 */
	public Critter selectCritter(TowerInterface tower,
			ArrayList<Critter> critters) {
		double distance;
		double shorterDistance = Double.MAX_VALUE;
		Critter tempCritter = null;
		for (Critter critter : critters) {
			distance = Math.sqrt((critter.getX() - tower.getX())
					* (critter.getX() - tower.getX())
					+ (critter.getY() - tower.getY())
					* (critter.getY() - tower.getY()));

			if (distance > shorterDistance)
				continue;

			shorterDistance = distance;
			tempCritter = critter;

		}
		return tempCritter;
	}

}
