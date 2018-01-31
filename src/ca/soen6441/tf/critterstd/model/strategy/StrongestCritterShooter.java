/**
 * 
 */
package ca.soen6441.tf.critterstd.model.strategy;

import java.util.ArrayList;

import ca.soen6441.tf.critterstd.model.Critter;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * Identify Strongest Critter in Tower's Range and perform Shooting operation
 * 
 * @author Vishal
 *
 */
public class StrongestCritterShooter implements Shooter {

	@Override
	/**
	 * Method selectCritter using TowerInterface and ArrayList<Critter> as parameters
	 * @param TowerInterface
	 * 			Supplies Tower object that is Shooting 
	 * @param ArrayList<Critter>
	 * 			Supplies ArrayList of Critters that are in Tower's Range
	 * @return Critter
	 * 			Returns Strongest Critter in The Tower's Range
	 */
	public Critter selectCritter(TowerInterface tower,
			ArrayList<Critter> critters) {
		double life;
		double shortestLife = critters.get(0).getLife();
		Critter tempCritter = critters.get(0);
		for (Critter critter : critters) {
			life = critter.getLife();

			if (life <= shortestLife)
				continue;

			shortestLife = life;
			tempCritter = critter;
		}
		return tempCritter;
	}
}
