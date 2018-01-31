/**
 * 
 */
package ca.soen6441.tf.critterstd.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import ca.soen6441.tf.critterstd.controller.GamePlayController;
import ca.soen6441.tf.critterstd.utility.GameEvent;
import ca.soen6441.tf.critterstd.view.ViewObserver;

/**
 * Player model for the game play to store points, currency and other features
 * of the player.
 * 
 * @author Farzana Alam
 *
 */
public class Player implements ModelObservable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Observers
	private transient ArrayList<ViewObserver> observers;

	private transient String pathToPlayerDetails = System
			.getProperty("user.dir")
			+ File.separator
			+ "resources"
			+ File.separator + "player" + File.separator;

	private static Player instance;
	private double currency;
	private int level;
	private int life;
	private int score;

	// max level 30 for now
	private transient int maxLevel;

	private final transient Object lockCurrency = new Object();
	private final transient Object lockLife = new Object();
	private final transient Object lockScore = new Object();

	/**
	 * Players Characteristics
	 * 
	 * @param observers
	 * @param currency
	 * @param level
	 * @param maxLevel
	 * @param life
	 */
	private Player() {
		super();
		this.observers = new ArrayList<ViewObserver>();

		Path path = Paths.get(pathToPlayerDetails + "player.txt");
		byte[] data;
		try {
			data = Files.readAllBytes(path);
			String str = new String(data).replaceAll("\\s{2,}", "").trim();
			str = str.replaceAll("#.+?:-", "##").trim().replaceFirst("##", "");

			/*
			 * 0: Bank/Currency 1: Level 2: Life 3: Max Level
			 */
			String[] properties = str.split("##");

			int i = 0;
			this.currency = new Double(properties[i++]);
			this.level = new Integer(properties[i++]);
			this.life = new Integer(properties[i++]);
			this.maxLevel = new Integer(properties[i++]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create instance of Player follows Singleton Design Pattern
	 * 
	 * @return instance Instance of Player class.
	 */
	public static Player getPlayer() {
		if (instance == null)
			instance = new Player();

		return instance;
	}

	/**
	 * Increase Level and Notify Observers by sending Change Notification.
	 */
	public void increaseLevel() {
		if (++level > maxLevel)
			level--;
		notifyAllObservers();

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
	 * @return player's remaining currency
	 */
	public double getCurrency() {
		synchronized (lockCurrency) {
			return currency;
		}
	}

	/**
	 * Sets Player currency.
	 * 
	 * @param currency
	 *            the amount of current available currency
	 */
	public synchronized void setCurrency(double currency) {
		synchronized (lockCurrency) {
			this.currency = currency;
		}

		// LOG: Event
		new GameEvent("Player bank: " + currency + ".", "wave"
				+ GamePlayController.getGamePlayController().getWaveNumber(),
				"player");
		notifyAllObservers();

	}

	/**
	 * @return the current level of the player.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * get the life of critter
	 * 
	 * @return the remaining lives available.
	 */
	public int getLife() {
		synchronized (lockLife) {
			return life;
		}
	}

	/**
	 * Reduce Player life if and only if critters reached to Exit point.
	 * 
	 * @param strength
	 *            strength of critters
	 */
	public void reduceLife(int strength) {
		synchronized (lockLife) {
			this.life -= strength;
		}

		// LOG: Event
		new GameEvent("Remaining player life:" + life + ".", "wave"
				+ GamePlayController.getGamePlayController().getWaveNumber(),
				"player");
		notifyAllObservers();
		if (life <= 0) {
			GamePlayController.getGamePlayController().stopGame();
		}
	}

	/**
	 * @param player
	 */
	public void loadSavedPlayerProfile(Player player) {
		this.setCurrency(player.currency);
		this.level = player.level;
		this.life = player.life;
		this.score = player.score;
		notifyAllObservers();
	}

	/**
	 * @return score
	 */
	public int getScore() {
		synchronized (lockScore) {
			return score;
		}
	}

	/**
	 * @param hitPoint
	 */
	public void increasePoints(int hitPoint) {
		synchronized (lockScore) {
			score += hitPoint;
		}
		notifyAllObservers();
	}
}