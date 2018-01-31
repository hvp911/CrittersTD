package ca.soen6441.tf.critterstd.model.tower;

import java.awt.Color;
import java.io.Serializable;

import ca.soen6441.tf.critterstd.model.strategy.ShootingStrategy;

/**
 * Class specifies TowerInterface
 * 
 * @author Hardik
 */

public interface TowerInterface extends Serializable {

	/**
	 * To Upgrade Tower
	 */
	public void upgradeTower();

	/**
	 * @return tower name
	 */
	public String getTowerName();

	/**
	 * @return returns which type of tower is it
	 */
	public String getType();

	/**
	 * Tower Cost value
	 * 
	 * @return cost
	 */
	public double getBuyingCost();

	/**
	 * Tower sold value
	 * 
	 * @return true/false
	 */
	public boolean isSold();

	/**
	 * Tower Cuurent Value
	 * 
	 * @return value of Tower
	 */
	public double getCurrentValue();

	/**
	 * Return value of TowerSellPrice
	 * 
	 * @return Tower Selling Price
	 */
	public double getRefundValue();

	/**
	 * Set Tower Sold value
	 * 
	 * @param sold
	 */
	public void setSold(boolean sold);

	/**
	 * Display Tower Description
	 * 
	 * @return description
	 */
	public String getDescription();

	/**
	 * Tower Damage Capability
	 * 
	 * @return damage
	 */
	public double getDamageCapability();

	/**
	 * Get the value of Tower Range afrer Upgradation
	 * 
	 * @return range
	 */
	public double getRange();

	/**
	 * Return value of Tower level after upgrade
	 * 
	 * @return level
	 */
	public int getLevel();

	/**
	 * Return value of Tower X Coordinate
	 * 
	 * @return x
	 */
	public int getX();

	/**
	 * Set value of Tower X Coordinate
	 * 
	 * @param x
	 */
	public void setX(int x);

	/**
	 * Return value of Tower Y Coordinate
	 * 
	 * @return y
	 */
	public int getY();

	/**
	 * Set value of Tower Y Coordinate
	 * 
	 * @param y
	 */
	public void setY(int y);

	/**
	 * @return the fire color for the selected tower
	 */
	public Color getFireColor();

	/**
	 * @return the length of the tower specific fire
	 */
	public int getFireLength();

	/**
	 * @param shooting
	 *            <code>true</code> when it is in action
	 */
	public void setShooting(boolean shooting);

	/**
	 * @return <code>true</code> when tower is in action
	 */
	public boolean isShooting();

	/**
	 * Identify Strategy while shooting critters
	 * 
	 * @return strategy
	 */
	public ShootingStrategy getShootingStrategy();

	/**
	 * Set the Strategy for shooting
	 * 
	 * @param shootingStrategy
	 */
	public void setShootingStrategy(ShootingStrategy shootingStrategy);

	/**
	 * @return Fire Special Effect value
	 */
	public FireEffect getSpecialFire();

	/**
	 * Fire Special value
	 * 
	 * @return true/false
	 */
	public boolean isFiringSpecial();

	/**
	 * @param serial
	 */
	public void setSerial(int serial);

	/**
	 * @return serial
	 */
	public int getSerial();
}
