package ca.soen6441.tf.critterstd.model.tower;

/**
 * Class TowerFactory provides multiple tower selection and call their relevant method. 
 * Tower Operation follows Factory Pattern Design Pattern.
 * 
 * @author Hardik
 */

public class TowerFactory {

	private static TowerFactory instance;

	/**
	 * Constructor TowerFactory
	 */
	private TowerFactory() {
		super();
	} // end of constructor

	/**
	 * Create an instance for TowerFactory
	 * 
	 * @return TowerFactory Returns Single Object of TowerFactory for each request.
	 */
	public static TowerFactory getTowerFactory() {
		if (instance == null)
			instance = new TowerFactory();

		return instance;
	} // end getTowerFactory

	/**
	 * Based on Tower Type selection performed their corresponding tower
	 * operations
	 * 
	 * @param towerType
	 *            Type of tower.
	 * 
	 * @return New Tower Object for the Tower Requested and generate characteristics
	 */
	public Tower getTower(String towerType) {
		towerType = towerType.toLowerCase().trim();
		switch (towerType) {
		case "tower1":
			return new TowerT1();
		case "tower2":
			return new TowerT2();
		case "tower3":
			return new TowerT3();
		default:
			return new TowerT1();
		} // end switch
	} // end getCell

}
