package ca.soen6441.tf.critterstd.model;

import java.util.ArrayList;

import ca.soen6441.tf.critterstd.model.tower.Tower;
import ca.soen6441.tf.critterstd.model.tower.TowerFactory;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;
import ca.soen6441.tf.critterstd.view.ViewObserver;

/**
 * Class TowerHelper is a Model Class. Perform Upgrade tower and Sell tower
 * operation and notify observer method call.
 * 
 * @author Vishal
 */
public class TowerHelper implements ModelObservable {

	// Observers
	private transient ArrayList<ViewObserver> observers = new ArrayList<ViewObserver>();

	// for singleton pattern - only one instance is allowed
	private static TowerHelper instance;

	private transient TowerFactory towerFactory;
	private transient TowerInterface tower;

	/**
	 * Create a singleton object of TowerFactory
	 * 
	 */
	private TowerHelper() {
		towerFactory = TowerFactory.getTowerFactory();
	}

	/**
	 * @return singleton object for TowerHelper
	 */
	public static TowerHelper getTowerHelper() {
		if (instance == null)
			instance = new TowerHelper();
		return instance;
	}

	/**
	 * Get tower
	 * 
	 * @param towerType
	 *            type of the tower
	 * @return new tower
	 */
	public Tower getTower(String towerType) {
		return (Tower) towerFactory.getTower(towerType);
	}

	/**
	 * @return Tower value
	 */
	public TowerInterface getTower() {
		return tower;
	}

	/**
	 * Initialize value to tower
	 * 
	 * @param value
	 */
	private void setTower(TowerInterface value) {
		tower = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.ModelObservable#getState()
	 */
	@Override
	public ModelObservable getState() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.ModelObservable#attach(ca.soen6441.tf
	 * .critterstd.view.ViewObserver)
	 */
	@Override
	public void attach(ViewObserver observer) {
		observers.add(observer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.ModelObservable#notifyAllObservers()
	 */
	@Override
	public void notifyAllObservers() {
		for (ViewObserver observer : observers)
			observer.update();
	}

	/**
	 * Upgrade Tower and send a notification about its change state
	 * 
	 * @param value
	 *            Instance of Tower
	 */
	public void upgradeTower(TowerInterface value) {

		setTower(value);
		value.upgradeTower();
		notifyAllObservers();
	}

	/**
	 * Perform SellTower Method and send change notification
	 *
	 * @param tower
	 *            instance of Tower
	 * @param player
	 *            instance of current Player
	 */
	public void sellTower(TowerInterface tower, Player player) {

		setTower(tower);
		player.setCurrency(player.getCurrency() + tower.getRefundValue());
		notifyAllObservers();
	}
}
