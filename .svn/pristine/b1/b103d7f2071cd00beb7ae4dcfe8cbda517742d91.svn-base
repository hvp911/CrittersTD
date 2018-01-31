/**
 * 
 */
package ca.soen6441.tf.critterstd.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.imgscalr.Scalr;

import ca.soen6441.tf.critterstd.model.Critter;
import ca.soen6441.tf.critterstd.model.CritterChooser;
import ca.soen6441.tf.critterstd.model.CritterHelper;
import ca.soen6441.tf.critterstd.model.Fire;
import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.Player;
import ca.soen6441.tf.critterstd.model.Splash;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.model.tower.TowerFactory;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;
import ca.soen6441.tf.critterstd.utility.GameEvent;
import ca.soen6441.tf.critterstd.view.ControlPanelView;
import ca.soen6441.tf.critterstd.view.MainContainerView;
import ca.soen6441.tf.critterstd.view.MapView;
import ca.soen6441.tf.critterstd.view.ScorecardPanelView;

/**
 * This interface provide Game Controller for Start Game. It allows to start
 * game, add, remove and upgrade tower. Also act to update the cell property
 * after adding or removing the tower.
 * 
 * @author Farzana Alam
 *
 */
public class GamePlayController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static GamePlayController instance;

	private String towerName = "tower1";
	private TowerInterface tower;
	private int towerSerial = 0;
	private Player player;
	private int waveNumber = 0;

	private transient CritterChooser crittersContext;

	private transient String pathToTheImages;

	private ArrayList<Boolean> crittersOnTheMove = new ArrayList<Boolean>();
	private int numberOfCrittersOnTheMove = 0;

	/**
	 * this many critters will be sent per wave
	 */
	private transient final int NUMBER_OF_CRITTERS = 10;

	private ArrayList<Critter> movingCritters = new ArrayList<Critter>();
	private ArrayList<Critter> critters = new ArrayList<Critter>();
	private ArrayList<TowerInterface> towers = new ArrayList<TowerInterface>();

	private final transient Object lockTowers = new Object();
	private final transient Object lockMovingCritters = new Object();

	private transient boolean gameOver = false;

	private transient ArrayList<Splash> splashes = new ArrayList<Splash>();

	private transient final Object lockSplashes = new Object();

	private transient Timer shootingTimer;

	/**
	 * Create Constructor and call Method StartGamePlayView
	 */
	private GamePlayController() {
		super();
		pathToTheImages = GUIController.getPathToResources() + "images"
				+ File.separator;
		tower = TowerFactory.getTowerFactory()
				.getTower(towerName.toLowerCase());
		player = Player.getPlayer();
		crittersContext = CritterChooser.getCrittersContext();
		startGamePlayView();
		for (int i = 0; i < NUMBER_OF_CRITTERS; i++)
			crittersOnTheMove.add(false);

	} // end constructor

	/**
	 * Create an object of GamePlayController
	 * 
	 * @return returns the instance of class.
	 */
	public static GamePlayController getGamePlayController() {
		if (instance == null)
			instance = new GamePlayController();

		return instance;
	}

	/**
	 * Provide view after start game.
	 */
	private void startGamePlayView() {

		ScorecardPanelView.getScorecardPanel(player);
		ControlPanelView.getControlPanelView();

		for (ArrayList<CellInterface> cells : Map.getMap().getCells()) {
			for (CellInterface cell : cells) {
				cell.disableCell();
				MapView.getMapView(Map.getMap()).setCells(cell.getX(),
						cell.getY(), cell.getWidth(), cell.getCellColor());
			} // end inner loop
		} // end outer loop
		Map.getMap().notifyAllObservers();
	}

	/**
	 * When a cell is pressed to place and update tower
	 * 
	 * Method perform Buying tower operation and if currency not available then
	 * print message that Currency is not enough to buy tower...
	 * 
	 * @param aCellButton
	 *            a instance of cell which need to update.
	 */
	public void updateCellProperty(JButton aCellButton) {

		CellInterface aCell = (CellInterface) aCellButton;

		// buying tower
		if (aCell.isCellEmpty()) {
			if (tower == null)
				return;

			if (player.getCurrency() >= tower.getBuyingCost()) {

				aCellButton.setIcon(setTowerIcon(towerName + ".png"));
				aCellButton.setBackground(Color.WHITE);
				tower.setX(aCell.getX() + (aCell.getWidth() - 1) / 2);
				tower.setY(aCell.getY() + (aCell.getWidth() - 1) / 2);
				aCell.setElementOnTheCell(tower);

				tower.setSerial(++towerSerial);

				int serial = 0;
				synchronized (lockTowers) {
					serial = towers.size();
					this.towers.add(tower);
				}

				int width = (int) tower.getRange();
				int x = tower.getX() - width / 2;
				int y = tower.getY() - width / 2;

				MapView.getMapView(Map.getMap()).addTowerRanges(serial, x, y,
						width);

				// LOG: Event
				new GameEvent(tower.getType() + " (" + tower.getTowerName()
						+ ") was bought. Tower number: " + tower.getSerial()
						+ ".", "player", "towers", "wave" + waveNumber, "tower"
						+ tower.getSerial());

				player.setCurrency(player.getCurrency() - tower.getBuyingCost());

				tower = null;
				return;
			} else {

				// LOG: Event
				new GameEvent("Not enough currency to buy " + tower.getType()
						+ " (" + tower.getTowerName()
						+ ").\n Tower buying Cost: " + tower.getBuyingCost()
						+ "\n Player bank: " + player.getCurrency(), "player",
						"towers", "wave" + waveNumber, "tower"
								+ tower.getSerial());
				tower = null;
				return;
			}

		}

		tower = (TowerInterface) aCell.getElementOfTheCell();
		if (tower == null)
			return;

		ControlPanelController.getTowerActionController().setRowIndex(
				aCell.getIndices()[0]);
		ControlPanelController.getTowerActionController().setColIndex(
				aCell.getIndices()[1]);
		ControlPanelController.getTowerActionController()
				.setTowerOperationDetails(tower);

		tower = null;

	}

	/**
	 * upgrade the tower to new property values.
	 * 
	 * @param aTower
	 */
	public void upgradeTower(TowerInterface aTower) {

		int serial = 0;
		synchronized (lockTowers) {
			for (TowerInterface tmpTower : towers) {
				if (tmpTower.equals(aTower))
					break;
				serial++;
			}
		}

		int width = (int) aTower.getRange();
		int x = aTower.getX() - width / 2;
		int y = aTower.getY() - width / 2;

		MapView.getMapView(Map.getMap()).addTowerRanges(serial, x, y, width);

		// LOG: Event
		new GameEvent(aTower.getType() + " (" + aTower.getTowerName()
				+ ") was upgraded. Tower number: " + aTower.getSerial() + ".",
				"player", "towers", "wave" + waveNumber, "tower"
						+ aTower.getSerial());

	}

	/**
	 * Sets the tower icon to the cell
	 * 
	 * Take path of image file and place it on panel to represent Tower Image
	 * 
	 * @param name
	 *            of the Icon file of tower.
	 * 
	 * @return Image icon for tower.
	 * 
	 */
	private Icon setTowerIcon(String fileName) {
		try {
			BufferedImage tmpTowerImage = ImageIO.read(new File(pathToTheImages
					+ fileName));

			BufferedImage towerImage = Scalr.resize(tmpTowerImage, 25);
			return new ImageIcon(towerImage);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * SetTower with Using TowerInterface
	 * 
	 * @param tower
	 *            instance of tower.
	 */
	public void setTower(TowerInterface tower) {
		this.tower = tower;
		this.towerName = tower.getType().toLowerCase().trim();
	}

	/**
	 * This method remove the tower from the map. It is called for Sell tower
	 * operation from tower control panel view.
	 * 
	 * @param cellRowIndex
	 *            row index of the selected cell
	 * @param cellColIndex
	 *            columns index of the selected index
	 */
	public void removeTower(int cellRowIndex, int cellColIndex) {
		CellInterface aCell = Map.getMap().getCells().get(cellRowIndex)
				.get(cellColIndex);
		synchronized (lockTowers) {
			final TowerInterface aTower = (TowerInterface) aCell
					.getElementOfTheCell();

			new Thread(new Runnable() {

				@Override
				public void run() {
					while (aTower.isShooting()) {
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // end while

					int serial = 0;
					synchronized (lockTowers) {
						for (TowerInterface tmpTower : towers) {
							if (tmpTower.equals(aTower))
								break;
							serial++;
						}
						towers.remove(aTower);
					}

					MapView.getMapView(Map.getMap()).removeTowerRange(serial);
				} // end run
			}).start();
		} // end synchronized
		aCell.removeElementOfTheCell();
		JButton aButton = (JButton) aCell;
		aButton.setIcon(null);
		aButton.setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Sends a wave of critters
	 */
	public void sendWave() {
		// do nothing when a wave is already in action
		for (boolean b : crittersOnTheMove) {
			if (b)
				return;
		}

		CritterHelper.increaseLevel();

		ControlPanelView.getControlPanelView().addCritterDetails(
				CritterHelper.getCurrentCritterDetails(),
				CritterHelper.getNextCritterDetails());

		player.increaseLevel();
		waveNumber = player.getLevel();

		if(player.getLevel()>30){
			stopGame(true);
		}
		
		// LOG: Event
		new GameEvent("Wave " + waveNumber + " on the move.", "wave"
				+ waveNumber);

		movingCritters.clear();
		critters.clear();

		ArrayList<ArrayList<CellInterface>> paths = Map.getMap()
				.getValidPaths();

		int pathNumber = 0;
		for (int i = 0; i < NUMBER_OF_CRITTERS; i++) {

			if (pathNumber == paths.size())
				pathNumber = 0;

			Critter critter = new Critter(paths.get(pathNumber++), i,
					new Color(242, 199, 99)); // yellowish
			critters.add(critter);
			Thread t = new Thread(critter);
			t.start();
		} // end for

		new Thread(new Runnable() {

			@Override
			public void run() {
				startShooting();
			}
		}).start();

	}

	/**
	 * Shooting to Critters using ActionListener Methods
	 */
	private void startShooting() {

		shootingTimer = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// continue shooting while last critter is in action
				synchronized (lockMovingCritters) {
					if ((movingCritters.size() == 0)
							&& (numberOfCrittersOnTheMove == NUMBER_OF_CRITTERS)) {
						((Timer) e.getSource()).stop();
						numberOfCrittersOnTheMove = 0;
						return;
					}

				} // end synchronized lockCritters

				int numberOfTowers;
				synchronized (lockTowers) {
					numberOfTowers = towers.size();

					if (numberOfTowers == 0)
						return;

					for (Integer i = 0; i < numberOfTowers; i++) {
						TowerInterface aTower = towers.get(i);

						if (aTower.isShooting())
							continue;

						Critter critter = crittersContext.getTheCritterToShoot(
								aTower, movingCritters);

						if (critter == null)
							continue;

						Fire fire = new Fire(i, aTower, critter);
						new Thread(fire).start();

						numberOfTowers = towers.size();

					} // end for
				} // end synchronized lockTowers

			}
		});

		shootingTimer.start();
	}

	/**
	 * Critters moving on Specified Path or Directed Path
	 * 
	 * @param serial
	 * @param crittersOnTheMove
	 * @param critter
	 */
	public void setCrittersOnTheMove(int serial, boolean crittersOnTheMove,
			Critter critter) {

		if (crittersOnTheMove) {
			paintCritterOnMap(serial, critter.getX(), critter.getY(),
					critter.getColor());
			synchronized (lockMovingCritters) {
				this.crittersOnTheMove.set(serial, crittersOnTheMove);
				numberOfCrittersOnTheMove++;
				movingCritters.add(critter);
				return;
			}
		}

		removeElement(serial, true);

		synchronized (lockMovingCritters) {
			this.crittersOnTheMove.set(serial, crittersOnTheMove);
			movingCritters.remove(critter);
		}
	}

	/**
	 * Draws Critters on Map
	 *
	 * @param elementSerial
	 * @param x
	 *            Axis
	 * @param y
	 *            Axis
	 * @param elementColor
	 */
	public void paintCritterOnMap(int elementSerial, int x, int y,
			Color elementColor) {
		MapView.getMapView(Map.getMap()).addElements(elementSerial, x, y,
				elementColor, true);
	}

	/**
	 * Draws Fire ball Effect on Map while shooting critters
	 * 
	 * @param elementSerial
	 * @param x
	 * @param y
	 * @param elementColor
	 */
	public void paintFireOnMap(int elementSerial, int x, int y,
			Color elementColor) {
		MapView.getMapView(Map.getMap()).addElements(elementSerial, x, y,
				elementColor, false);
	}

	/**
	 * The method to kill the critter
	 * 
	 * @param serial
	 * @param isCritter
	 */
	public void removeElement(int serial, boolean isCritter) {
		MapView.getMapView(Map.getMap()).removeElement(serial, isCritter);
	}

	/**
	 * Player received coins when killed critters
	 * 
	 * @param reward
	 */
	public void addRewardsToPlayer(int reward) {
		player.setCurrency(player.getCurrency() + reward);
	}

	/**
	 * Represent Players Life/Health/Strength
	 * 
	 * @param strength
	 */
	public void reducePlayerLife(int strength) {
		player.reduceLife(strength);
	}

	/**
	 * Perform Stop Game Operation for loosing Game
	 */
	public void stopGame() {

		if (gameOver)
			return;

		gameOver = true;

		// LOG: Event
		new GameEvent("Game over!!");

		MapView.getMapView(Map.getMap()).stopMap();

		JFrame mainContainer = MainContainerView.getMainContainer();
		JOptionPane.showMessageDialog(mainContainer, "GAME OVER", "Game Over",
				JOptionPane.ERROR_MESSAGE);

		System.exit(0);
	}

	/**
	 * Perform Stop Game Operation for Winning Game
	 */
	public void stopGame(boolean wonGame) {

		if (wonGame) {

			if (gameOver)
				return;

			gameOver = true;

			// LOG: Event
			new GameEvent("You Won !!");

			MapView.getMapView(Map.getMap()).stopMap();

			JFrame mainContainer = MainContainerView.getMainContainer();
			JOptionPane.showMessageDialog(mainContainer, "You Won",
					"You Won", JOptionPane.ERROR_MESSAGE);

			System.exit(0);
		}
	}

	/**
	 * @param x
	 *            Axis coordinate
	 * @param y
	 *            Axis coordinate
	 * @param aTower
	 *            tower object
	 * @param critter
	 *            critter object
	 * @param color
	 *            color of the fire
	 */
	public void splashFire(int x, int y, TowerInterface aTower,
			Critter critter, Color color) {
		ArrayList<Critter> crittersInRange = new ArrayList<Critter>();

		synchronized (lockMovingCritters) {
			for (Critter aCritter : movingCritters) {
				if ((Math.sqrt(Math.pow(aCritter.getX() - critter.getX(), 2)
						+ Math.pow(aCritter.getY() - critter.getY(), 2)) <= aTower
						.getRange() / 3))
					crittersInRange.add(aCritter);
			}
		}

		synchronized (lockSplashes) {
			int serial = splashes.size();
			for (Critter aCritter : crittersInRange) {
				Splash aSplash = new Splash(serial, x, y,
						aTower.getFireLength(), aTower.getDamageCapability(),
						aCritter, color);
				splashes.add(aSplash);
				new Thread(aSplash).start();
			} // end for
		}
	}

	/**
	 * Paint the map play area.
	 * 
	 * @param serial
	 *            of the splash
	 * @param x
	 *            Axis coordinate
	 * @param y
	 *            Axis coordinate
	 * @param color
	 *            of the splash
	 */
	public void paintSplash(int serial, int x, int y, Color color) {
		MapView.getMapView(Map.getMap()).paintSplash(serial, x, y, color);
	}

	/**
	 * Remove the splash.
	 * 
	 * @param serial
	 *            serial of splash
	 * @param splash
	 *            the splash object
	 * 
	 */
	public void removeSplash(int serial, Splash splash) {
		synchronized (lockSplashes) {
			splashes.remove(splash);
		}

		MapView.getMapView(Map.getMap()).removeSplash(serial);

	}

	/**
	 * Controller to load and save game.
	 * 
	 * @param tmpController
	 */
	public void loadASavedController(GamePlayController tmpController) {
		towerName = tmpController.towerName;
		tower = tmpController.tower;
		player.loadSavedPlayerProfile(tmpController.player);
		crittersOnTheMove = tmpController.crittersOnTheMove;
		numberOfCrittersOnTheMove = tmpController.numberOfCrittersOnTheMove;
		movingCritters = tmpController.movingCritters;
		towers = tmpController.towers;
		critters = tmpController.critters;
		towerSerial = tmpController.towerSerial;
		waveNumber = tmpController.waveNumber;

		// LOG: Event
		new GameEvent("Game reloaded." + " Player Life: " + player.getLife()
				+ "; Player Level: " + player.getLevel()
				+ "; Player Currency: " + player.getCurrency()
				+ "; Player Score: " + player.getScore() + ".", "wave"
				+ waveNumber);

		for (int i = 0; i < player.getLevel(); i++)
			CritterHelper.increaseLevel();

		ControlPanelView.getControlPanelView().addCritterDetails(
				CritterHelper.getCurrentCritterDetails(),
				CritterHelper.getNextCritterDetails());

		for (Critter aCritter : critters) {
			aCritter.setStopMoving(false);
			new Thread(aCritter).start();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				startShooting();
			}
		}).start();

	}

	/**
	 * Pause the current game.
	 * 
	 * @param pauseGame
	 */
	public void pauseGame(boolean pauseGame) {

		if (pauseGame) {
			shootingTimer.stop();
			// wait till all towers finished shooting
			outer: while (true) {

				if (!pauseGame)
					break;

				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lockTowers) {
					for (TowerInterface aTower : towers) {
						if (aTower.isShooting())
							continue outer;
					} // end for
				} // end synchronized lockTowers

				break;
			} // end while

			// LOG: Event
			new GameEvent("Game paused for saving the game." + " Player Life: "
					+ player.getLife() + "; Player Level: " + player.getLevel()
					+ "; Player Currency: " + player.getCurrency()
					+ "; Player Score: " + player.getScore() + ".", "wave"
					+ waveNumber);
		} else {
			shootingTimer.restart();
			// LOG: Event
			new GameEvent("Game resuming.", "wave" + waveNumber);
		}

		// freeze critter moves
		synchronized (lockMovingCritters) {
			for (Critter aCritter : movingCritters)
				aCritter.setStopMoving(pauseGame);
		} // end synchronized lockCritter

	}

	/**
	 * Get the number of wave
	 * 
	 * @return waveNumber
	 */
	public int getWaveNumber() {
		return waveNumber;
	}

	/**
	 * Hit Point
	 * 
	 * @param hitPoint
	 */
	public void addPointsToPlayer(int hitPoint) {
		player.increasePoints(hitPoint);
	}
}