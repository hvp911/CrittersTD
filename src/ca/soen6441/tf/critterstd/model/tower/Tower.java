package ca.soen6441.tf.critterstd.model.tower;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Timer;

import ca.soen6441.tf.critterstd.controller.GUIController;
import ca.soen6441.tf.critterstd.model.strategy.ShootingStrategy;
import ca.soen6441.tf.critterstd.utility.MyLock;

/**
 * These interface provide tower upgradation, range, cost, range and damage
 * properties.
 * 
 * @author Hardik
 */

public abstract class Tower implements TowerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient String pathToTheTowers = GUIController
			.getPathToResources() + "towers" + File.separator;;

	String name;
	String description;

	// 50% for tower upgrade.
	double buyingCost;
	// Increase 45% on upgrade.
	double damageCapability;
	// increase 5% on upgrade.
	int range;
	// increase 1 level on upgrade.
	int level;
	// calculate 75% of tower's current value.
	double refundValue;
	// the current value of the tower including upgrades
	double currentValue;
	// number of fires per cycle
	int rateOfFire;

	// factor when selling tower
	double depreciation;
	// factors when upgrade requested
	double upgradeFactor;
	// Upgrade damage when requested.
	double damageUpgradeFactor;
	// Upgrade Range when requested.
	double rangeUpgradeFactor;
	// Tower Cell empty or not
	boolean towerCellEmpty = true;
	// Color of tower cell.
	Color towerCellColor;
	String type;

	int x;
	int y;

	int serial = 0;

	boolean sold = false;

	int fireLength;
	Color fireColor;

	private boolean shooting = false;
	private final MyLock lockShooting = new MyLock();
	private final int millis = 2000;

	// Initialized shooting Strategy
	ShootingStrategy shootingStrategy = ShootingStrategy.SHOOT_CLOSEST;

	// Special Fire
	private FireEffect specialFire;
	private int numberOfFiresBeforeSpecialFire;
	private int currentFireNumber = 0;
	private boolean firingSpecial = false;

	/**
	 * This will set the tower properties when a new tower is created
	 * 
	 * @param fileName
	 *            tower name
	 * @throws IOException
	 *             if file not found for the corresponding tower
	 */
	void setProperties(String fileName) throws IOException {
		Path path = Paths.get(pathToTheTowers + fileName + ".txt");
		byte[] data = Files.readAllBytes(path);

		String str = new String(data).replaceAll("\\s{2,}", "").trim();
		str = str.replaceAll("#.+?:-", "##").trim().replaceFirst("##", "");

		/*
		 * 0: Tower Name 1: Tower Description 2: Tower Cost 3: Tower Damage 4:
		 * Tower Range 5: Tower Level 6: Tower Type 7: Selling price factor 8:
		 * Upgrade Factor 9: Rate of fire 10: Fire Color 11: Fire Length 12:
		 * Special Effect 13: Special effect after every n numbers of fires
		 */
		String[] properties = str.split("##");

		int i = 0;
		name = properties[i++];
		description = properties[i++];
		// i++; // for description
		buyingCost = new Double(properties[i++]);
		damageCapability = new Double(properties[i++]);
		range = new Integer(properties[i++]);
		level = new Integer(properties[i++]);
		type = properties[i++];
		depreciation = new Double(properties[i++]);
		upgradeFactor = new Double(properties[i++]);
		rateOfFire = new Integer(properties[i++]);

		String rgb = properties[i++];
		String[] rgbS = rgb.split(", ");
		int r = new Integer(rgbS[0]);
		int g = new Integer(rgbS[1]);
		int b = new Integer(rgbS[2]);
		fireColor = new Color(r, g, b);

		fireLength = new Integer(properties[i++]);
		String specialEffectString = properties[i++].trim().toLowerCase();

		switch (specialEffectString) {
		case "splash":
			specialFire = FireEffect.SPLASH;
			break;
		case "burn":
			specialFire = FireEffect.BURN;
			break;
		case "freeze":
			specialFire = FireEffect.FREEZE;
			break;
		}

		numberOfFiresBeforeSpecialFire = new Integer(properties[i]);

		// 90% of the upgrade factor
		damageUpgradeFactor = 0.9 * upgradeFactor;
		rangeUpgradeFactor = 0.1 * upgradeFactor;

		currentValue = buyingCost;
		refundValue = currentValue * depreciation;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#upgradeTower()
	 */
	@Override
	/**
	 * Performed Upgrade tower Operation.
	 */
	public void upgradeTower() {

		if (this.getLevel() < 10) {

			// Damage upgrade to 36%
			setDamageCapability((getDamageCapability() * (1.0 + damageUpgradeFactor)));
			// Range upgrade to 4 %
			setRange((int) (getRange() * (1.0 + rangeUpgradeFactor)));
			setCurrentValue(getCurrentValue()
					+ (getBuyingCost() * upgradeFactor));
			setLevel(getLevel() + 1);
			// Sell price set to 75% of total cost
			setRefundValue(getCurrentValue() * depreciation);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#isSold()
	 */
	@Override
	/**
	 * @return sold value
	 */
	public boolean isSold() {
		return sold;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#setSold(boolean)
	 */
	/**
	 * Set the Tower Sold
	 * 
	 * @param sold
	 */
	@Override
	public void setSold(boolean sold) {
		this.sold = sold;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getTowerName()
	 */
	@Override
	/**
	 * Get the Tower Name
	 * @return TowerName
	 */
	public String getTowerName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#getDescription()
	 */
	@Override
	/**
	 * Get Tower Description
	 * @return Tower description
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getCost()
	 */
	/**
	 * Get Buying Cost
	 * 
	 * @return butingCost
	 */
	@Override
	public double getBuyingCost() {
		return buyingCost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#getDamageCapability
	 * ()
	 */
	/**
	 * Get Damage Capability
	 * 
	 * @return damageCapability
	 */
	@Override
	public double getDamageCapability() {
		return damageCapability;
	}

	/**
	 * Initialize and update TowerDamage value
	 * 
	 * @param towerDamage
	 */
	void setDamageCapability(double towerDamage) {
		this.damageCapability = towerDamage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getRange()
	 */
	/**
	 * Return Tower Range
	 * 
	 * @return range
	 */
	@Override
	public double getRange() {
		return range;
	}

	/**
	 * Initialize and update TowerRange value
	 * 
	 * @param towerRange
	 */
	void setRange(int towerRange) {
		this.range = towerRange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getLevel()
	 */
	/**
	 * Return Tower Level after upgradation
	 * 
	 * @return level
	 */
	@Override
	public int getLevel() {
		return level;
	}

	/**
	 * Assign TowerLevel value
	 * 
	 * @param towerLevel
	 */
	void setLevel(int towerLevel) {
		this.level = towerLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#getCurrentValue()
	 */
	/**
	 * Return Tower Current Value
	 * 
	 * @return currentValue
	 */
	@Override
	public double getCurrentValue() {
		return currentValue;
	}

	/**
	 * Declared and Set Tower Value
	 * 
	 * @param towerValue
	 */
	void setCurrentValue(double towerValue) {
		this.currentValue = towerValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#getSellingPrice()
	 */
	/**
	 * Return Tower Refund Value
	 * 
	 * @return refundValue after selling tower
	 */
	public double getRefundValue() {
		return refundValue;
	}

	/**
	 * Set Tower Sell Price
	 * 
	 * @param towerSellPrice
	 */
	void setRefundValue(double towerSellPrice) {
		this.refundValue = towerSellPrice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getType()
	 */
	/**
	 * Return Tower Type
	 * 
	 * @return String Type
	 */
	@Override
	public String getType() {
		return this.type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getX()
	 */
	/**
	 * Return Tower X Axis value
	 * 
	 * @return X
	 */
	@Override
	public int getX() {
		return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#setX(int)
	 */
	/**
	 * @param x
	 *            Initialize Tower X Axis value
	 */
	@Override
	public void setX(int x) {
		this.x = x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getY()
	 */
	/**
	 * Return Tower Y Axis value
	 * 
	 * @return Y
	 */
	@Override
	public int getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#setY(int)
	 */
	/**
	 * Initialize Tower Y Axis value
	 * 
	 * @param y
	 */
	@Override
	public void setY(int y) {
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getFireLength()
	 */
	/**
	 * Return Tower Fire Range Value
	 * 
	 * @return fireLength
	 */
	@Override
	public int getFireLength() {
		return fireLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getFireColor()
	 */
	/**
	 * Return Tower Fire Color value
	 * 
	 * @return Color
	 */
	@Override
	public Color getFireColor() {
		return fireColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#setShooting(boolean)
	 */
	/**
	 * Method setShooting Lock Shooting and performed fire and count the number
	 * of fire before performing special fire effect
	 */
	@Override
	public void setShooting(boolean shooting) {
		if (!shooting) {
			final boolean tmpShooting = shooting;
			new Timer(millis / rateOfFire, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					((Timer) e.getSource()).stop();
					synchronized (lockShooting) {
						Tower.this.shooting = tmpShooting;
						if (firingSpecial)
							firingSpecial = false;
					}
				}
			}).start();
			return;
		}
		synchronized (lockShooting) {
			if (++currentFireNumber == numberOfFiresBeforeSpecialFire) {
				currentFireNumber = 0;
				firingSpecial = true;
			}
			this.shooting = shooting;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#isShooting()
	 */
	/**
	 * Return shooting value as boolean type
	 * 
	 * @return shooting value
	 */
	@Override
	public boolean isShooting() {
		synchronized (lockShooting) {
			return shooting;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#setShootingStrategy
	 * (ca.soen6441.tf.critterstd.model.strategy.ShootingStrategies)
	 */
	@Override
	public synchronized void setShootingStrategy(
			ShootingStrategy shootingStrategy) {
		this.shootingStrategy = shootingStrategy;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#getShootingStrategy
	 * ()
	 */
	/**
	 * Return ShootingStrategy value
	 * 
	 * @return ShootingStrategy
	 */
	@Override
	public ShootingStrategy getShootingStrategy() {
		return shootingStrategy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#getSpecialFire()
	 */
	/**
	 * Return Special fire Effect value
	 * 
	 * @return FireEffect
	 */
	@Override
	public FireEffect getSpecialFire() {
		return specialFire;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.tower.TowerInterface#isFiringSpecial()
	 */
	/**
	 * Return Special Fire value as boolean type
	 * 
	 * @return firingSpecial
	 */
	@Override
	public boolean isFiringSpecial() {
		return firingSpecial;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#setSerial(int)
	 */
	@Override
	public void setSerial(int serial) {
		this.serial = serial;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.tower.TowerInterface#getSerial()
	 */
	@Override
	public int getSerial() {
		return serial;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Tower))
			return false;

		Tower aTower = (Tower) obj;

		if ((this.serial == aTower.serial)
				|| ((this.getX() == aTower.getX()) && (this.getY() == aTower
						.getY()))) {
			return true;
		}

		return false;
	}
}
