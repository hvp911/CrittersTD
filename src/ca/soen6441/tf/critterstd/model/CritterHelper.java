/**
 * 
 */
package ca.soen6441.tf.critterstd.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ca.soen6441.tf.critterstd.controller.GUIController;

/**
 * @author Farzana Alam
 *
 */
public abstract class CritterHelper {

	private static String pathToCritterDetails = GUIController
			.getPathToResources() + "critter" + File.separator;

	private static int _strength;
	private static int _speed;
	private static int _level;
	private static double _life;
	private static int _maxLevel;
	private static int _reward;
	private static int _hitPoint;

	private static int strength;
	private static int speed;
	private static int level;
	private static double life;
	private static int reward;
	private static int hitPoint;

	private static int currentMultiplier = 0;

	static {
		try {
			setProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read Text file from System and Create Map
	 * 
	 * @throws IOException
	 */
	public static void setProperties() throws IOException {
		Path path = Paths.get(pathToCritterDetails + "critter.txt");
		byte[] data = Files.readAllBytes(path);

		String str = new String(data).replaceAll("\\s{2,}", "").trim();
		str = str.replaceAll("#.+?:-", "##").trim().replaceFirst("##", "");

		/*
		 * 0: Points to reduce player life 1: Speed 2: Level 3: Strength (Life)
		 * 4: Max Level 5: Reward 6: Hit points
		 */
		String[] properties = str.split("##");

		int i = 0;
		_strength = new Integer(properties[i++]);
		_speed = new Integer(properties[i++]);
		_level = new Integer(properties[i++]);
		_life = new Double(properties[i++]);
		_maxLevel = new Integer(properties[i++]);
		_reward = new Integer(properties[i++]);
		_hitPoint = new Integer(properties[i]);

	}

	/**
	 * Increase Level of Player
	 */
	public static void increaseLevel() {

		currentMultiplier += 1;

		if (currentMultiplier > _maxLevel) {
			currentMultiplier -= 1;
			return;
		}

		strength = _strength * currentMultiplier;
		speed = _speed * currentMultiplier;
		level = _level * currentMultiplier;
		life = _life * currentMultiplier;
		reward = _reward;
		hitPoint = _hitPoint;

	}

	/**
	 * Display Current Critters Details
	 * 
	 * @return getCurrentDescription
	 */
	public static String getCurrentCritterDetails() {
		String critterDetails = "Current Critter Wave Details : \nCritter Level : "
				+ getLevel()
				+ "\nPlayer Damage : "
				+ getStrength()
				+ "\nReward Points : "
				+ getReward()
				+ "\nCritter Life : "
				+ getLife() + "\nRelative Speed : " + getSpeed();

		return critterDetails;
	}

	/**
	 * Display Next Critters Details
	 * 
	 * @return getCritterDescription
	 */
	public static String getNextCritterDetails() {
		if (currentMultiplier == _maxLevel)
			return getCurrentCritterDetails();

		String critterDetails = "\n\n Next Critter Wave Details : \nCritter Level : "
				+ getLevel()
				* (currentMultiplier + 1)
				+ "\nPlayer Damage : "
				+ getStrength()
				* (currentMultiplier + 1)
				+ "\nReward Points : "
				+ getReward()
				+ "\nCritter Life : "
				+ getLife()
				* (currentMultiplier + 1)
				+ "\nRelative Speed : "
				+ getSpeed() * (currentMultiplier + 1);

		return critterDetails;
	}

	public static String generateDatails(int multiplier) {
		return null;
	}

	/**
	 * @return strength value
	 */
	public static int getStrength() {
		return strength;
	}

	/**
	 * Return Critters speed
	 * 
	 * @return speed
	 */
	public static int getSpeed() {
		return speed;
	}

	/**
	 * Return Level
	 * 
	 * @return level
	 */
	public static int getLevel() {
		return level;
	}

	/**
	 * Return Critters life value
	 * 
	 * @return life
	 */
	public static double getLife() {
		return life;
	}

	/**
	 * Return Reward Value after killing Critters
	 * 
	 * @return reward
	 */
	public static int getReward() {
		return reward;
	}

	/**
	 * @return hitPoint
	 */
	public static int getHitPoint() {
		return hitPoint;
	}
}
