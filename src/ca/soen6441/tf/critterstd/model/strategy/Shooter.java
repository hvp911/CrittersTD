package ca.soen6441.tf.critterstd.model.strategy;

import java.util.ArrayList;

import ca.soen6441.tf.critterstd.model.Critter;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

public interface Shooter {

	/**
	 * @param tower, critters
	 * @return array of critters
	 */
	public Critter selectCritter(TowerInterface tower,
			ArrayList<Critter> critters);
}
