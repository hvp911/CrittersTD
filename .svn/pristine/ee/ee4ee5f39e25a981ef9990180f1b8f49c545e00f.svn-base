/**
 * Describes Critters Characteristics
 */
package ca.soen6441.tf.critterstd.model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Timer;

import ca.soen6441.tf.critterstd.controller.GamePlayController;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.utility.GameEvent;
import ca.soen6441.tf.critterstd.utility.MyLock;

/**
 * This class is the blueprint of the critters
 * 
 * @author Farzana Alam
 *
 */
public class Critter implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// points to be deducted from Player's life
	private int strength;
	private int speed;
	private double life;
	private double initialLife;
	private Color color;
	private int reward;
	private int level;
	private int hitPoint;

	// for location
	private final int WIDTH;
	private int x = 0;
	private int y = 0;
	private boolean foundEntryPoint = false;
	private boolean lastMoveWasHorizontal = false;
	private boolean lastCellTraversed = false;
	private int lastDirection;
	private int critterSerial;
	private boolean myLockToCell = false;
	private boolean startedMoving = false;
	private boolean reachedExitPoint = false;
	private boolean dead = false;

	// Cells for path locations
	private CellInterface currentCell;
	private CellInterface nextCell;

	// Valid paths
	private ArrayList<CellInterface> pathCells = new ArrayList<CellInterface>();

	private boolean burning = false;
	private int numberOfBurns = 0;
	private boolean frozen = false;

	private final MyLock lockDead = new MyLock();
	private final MyLock lockLife = new MyLock();
	private final MyLock lockMoving = new MyLock();
	private final MyLock lockBurning = new MyLock();
	private final MyLock lockFreezing = new MyLock();
	private final MyLock lockPathCells = new MyLock();
	private boolean stopMoving = false;

	/**
	 * @param validPathCells
	 * @param critterSerial
	 * @param color
	 */
	public Critter(ArrayList<CellInterface> validPathCells, int critterSerial,
			Color color) {
		super();

		this.color = color;
		this.critterSerial = critterSerial;
		this.pathCells.addAll(validPathCells);
		this.WIDTH = validPathCells.get(0).getWidth();

		this.strength = CritterHelper.getStrength();
		this.speed = CritterHelper.getSpeed();
		this.life = CritterHelper.getLife();
		this.reward = CritterHelper.getReward();
		this.level = CritterHelper.getLevel();
		this.hitPoint = CritterHelper.getHitPoint();

		if (speed > 30)
			speed = 30;

		initialLife = life;

	}

	/**
	 * @return <code>true</code> when critter completed the whole path
	 */
	private boolean move() {

		synchronized (lockPathCells) {
			// for first entry cell
			if (!foundEntryPoint) {
				currentCell = pathCells.get(0);
				if (!currentCell.isCellEmpty())
					return true;
				currentCell.setCellEmpty(false);
				nextCell = pathCells.get(1);
				setEntryXY(currentCell);
			}
			if (currentCell.isEntryPonit()
					&& (x > currentCell.getX() || y > currentCell.getY())
					&& !startedMoving) {
				synchronized (lockMoving) {
					startedMoving = true;
				}
				GamePlayController.getGamePlayController()
						.setCrittersOnTheMove(critterSerial, startedMoving,
								this);
			}
			// if cell is not empty wait for it to be empty
			if (!nextCell.isCellEmpty() && !nextCell.isExitPoint()
					&& !myLockToCell) {
				return true;
			}
			if (frozen)
				return true;
			// if exit cell reached
			if (currentCell.isExitPoint()) {

				// cell completely traversed by the critter
				if ((x == currentCell.getX() + WIDTH)
						|| (y == currentCell.getY() + WIDTH)
						|| (x == currentCell.getX() - WIDTH)
						|| (y == currentCell.getY() - WIDTH)) {

					if (!lastCellTraversed) {
						lastCellTraversed = true;
						return true;
					}

					currentCell.setCellEmpty(true);
					if (pathCells.size() > 0)
						pathCells.remove(0);
					return false;
				}

				if (lastMoveWasHorizontal) {
					moveHorizontally();
					return true;
				}

				moveVertically();
				return true;

			}
			// check if next cell is not empty
			if (!nextCell.isCellEmpty() && !myLockToCell) {
				return true;
			}
			// lock next cell
			nextCell.setCellEmpty(false);
			myLockToCell = true;
			// cell completely traversed by the critter
			if ((x == currentCell.getX() + WIDTH)
					|| (y == currentCell.getY() + WIDTH)
					|| (x == currentCell.getX() - WIDTH)
					|| (y == currentCell.getY() - WIDTH)) {

				// all paths traversed already
				if (pathCells.isEmpty())
					return false;

				currentCell.setCellEmpty(true);
				pathCells.remove(0);
				currentCell = pathCells.get(0);
				currentCell.setCellEmpty(false);
				if (pathCells.size() > 1) {
					nextCell = pathCells.get(1);
					myLockToCell = false;
				}
			}
			if (currentCell.getX() == nextCell.getX()) {
				moveVertically();
				return true;
			}
			moveHorizontally();
			return true;
		} // end synchronized
	}

	/**
	 * critter moves vertically on path
	 */
	private void moveVertically() {
		if (currentCell.getY() == nextCell.getY()) {
			y += lastDirection;
			return;
		}

		if (currentCell.getY() < nextCell.getY()) {
			y += 1;
			lastMoveWasHorizontal = false;
			lastDirection = 1;
			return;
		}

		y -= 1;
		lastDirection = -1;
		lastMoveWasHorizontal = false;
	}

	/**
	 * critter moves horizontally on path
	 */
	private void moveHorizontally() {
		if (currentCell.getX() == nextCell.getX()) {
			x += lastDirection;
			return;
		}

		if (currentCell.getX() < nextCell.getX()) {
			x += 1;
			lastMoveWasHorizontal = true;
			lastDirection = 1;
			return;
		}

		x -= 1;
		lastDirection = -1;
		lastMoveWasHorizontal = true;
	}

	/**
	 * sets the x & y coordinates of the critter
	 * 
	 * @param currentCell
	 *            the cell in which the critter will enter
	 */
	private void setEntryXY(CellInterface currentCell) {
		x += currentCell.getX();
		y += currentCell.getY();
		foundEntryPoint = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	/**
	 * Run the Thread
	 */
	@Override
	public void run() {

		Timer timer = new Timer(31 - speed, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (stopMoving) {
					return;
				}

				if (!move()) {
					((Timer) e.getSource()).stop();
					synchronized (lockMoving) {
						startedMoving = false;
					}
					GamePlayController.getGamePlayController()
							.setCrittersOnTheMove(critterSerial, startedMoving,
									Critter.this);

					// LOG: Event
					new GameEvent("Critter" + critterSerial
							+ " reached exit point.", "player", "wave"
							+ GamePlayController.getGamePlayController()
									.getWaveNumber());

					GamePlayController.getGamePlayController()
							.reducePlayerLife(strength);

					reachedExitPoint = true;

					return;
				} // end if

				synchronized (lockLife) {

					if ((life <= 0)) {
						((Timer) e.getSource()).stop();

						synchronized (lockMoving) {
							startedMoving = false;
						}
						synchronized (lockDead) {
							dead = true;
						}

						currentCell.setCellEmpty(true);
						if (myLockToCell)
							nextCell.setCellEmpty(true);
						GamePlayController.getGamePlayController()
								.setCrittersOnTheMove(critterSerial,
										startedMoving, Critter.this);

						// LOG: Event
						new GameEvent("Critter" + critterSerial + " is dead.",
								"player", "wave"
										+ GamePlayController
												.getGamePlayController()
												.getWaveNumber());

						GamePlayController.getGamePlayController()
								.addRewardsToPlayer(reward);
						return;
					} // end if
				}

				setColor();

				synchronized (lockMoving) {
					if (startedMoving) {
						GamePlayController.getGamePlayController()
								.paintCritterOnMap(critterSerial, x, y, color);
					} // end if
				}

			} // end actionPerformed
		});

		timer.start();

	}

	/**
	 * Change color of a critter depending upon residual life
	 */
	protected void setColor() {

		synchronized (lockFreezing) {
			if (frozen) {
				color = new Color(98, 0, 255);
				return;
			}
		}

		synchronized (lockBurning) {
			if (burning) {
				if (++numberOfBurns > 5) {
					numberOfBurns = 0;
					color = new Color(255, 255, 0);
					return;
				}

				color = new Color(255, 0, 0);
				return;
			}
		}

		numberOfBurns = 0;

		color = new Color(245, 230, 135); // yellowish

		synchronized (lockLife) {
			if (life < (initialLife / 3)) {
				color = new Color(245, 126, 120);
			} else if (life < (initialLife * 2 / 3)) {
				color = new Color(237, 184, 128);
			}
		}
	}

	/**
	 * @return x coordinate of the critter
	 */
	public int getX() {
		return x + (WIDTH - 1) / 2;
	}

	/**
	 * @return y coordinate of the critter
	 */
	public int getY() {
		return y + (WIDTH - 1) / 2;
	}

	/**
	 * Return the state of Critter Moving or not
	 * 
	 * @return staredMoving
	 */
	public boolean isStartedMoving() {
		synchronized (lockMoving) {
			return startedMoving;
		}
	}

	/**
	 * if Critters reached exit point then decreased the players life
	 * 
	 * @return reachedexitPoint
	 */
	public boolean isReachedExitPoint() {
		return reachedExitPoint;
	}

	/**
	 * @param damageCapability
	 *            Damage Capability After hit of Critter
	 * @param colorAfterHit
	 *            Change the Color after every Hit
	 */
	public synchronized void hitCritter(double damageCapability,
			Color colorAfterHit) {

		synchronized (lockLife) {
			this.life -= damageCapability;
		}
		GamePlayController.getGamePlayController().paintCritterOnMap(
				critterSerial, x, y, colorAfterHit);
		GamePlayController.getGamePlayController().addPointsToPlayer(hitPoint);

	}

	/**
	 * Dead value returned if critters dead and player received reward
	 * 
	 * @return dead
	 */
	public boolean isDead() {
		synchronized (lockDead) {
			return dead;
		}
	}

	/**
	 * @return Color Value
	 */
	public synchronized Color getColor() {
		return color;
	}

	/**
	 * @return life. Life of critter
	 */
	public double getLife() {
		return life;
	}

	/**
	 * @param damageCapability
	 */
	public void burn(double damageCapability) {
		final double damage = damageCapability;
		synchronized (lockBurning) {
			burning = true;

			new Timer(300, new ActionListener() {

				int numberOfDamage = 0;

				@Override
				public void actionPerformed(ActionEvent e) {
					synchronized (lockLife) {
						if (++numberOfDamage > 2 || life < 0) {
							burning = false;
							((Timer) e.getSource()).stop();
							return;
						}
						synchronized (lockLife) {
							life -= damage;
						}

						GamePlayController.getGamePlayController()
								.addPointsToPlayer(hitPoint);
					}
				}
			}).start();
		}

	}

	/**
	 * @param damageCapability
	 */
	public void freeze(double damageCapability, Color color) {

		hitCritter(damageCapability, color);

		synchronized (lockFreezing) {
			if (frozen)
				return;

			frozen = true;

			new Timer(2000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					((Timer) e.getSource()).stop();
					frozen = false;

				}
			}).start();
		}

	}

	/**
	 * @return PathCells Size
	 */
	public int remainingPathSize() {
		synchronized (lockPathCells) {
			return pathCells.size();
		}
	}

	/**
	 * @return CritterSerial Value
	 */
	public int getSerial() {
		return critterSerial;
	}

	public void setStopMoving(boolean stopMoving) {
		this.stopMoving = stopMoving;
	}

	/**
	 * @return level
	 */
	public int getLevel() {
		return level;
	}

}
