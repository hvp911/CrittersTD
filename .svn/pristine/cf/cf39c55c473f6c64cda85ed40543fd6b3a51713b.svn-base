package ca.soen6441.tf.critterstd.controller;

import ca.soen6441.tf.critterstd.model.Player;
import ca.soen6441.tf.critterstd.model.TowerHelper;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;
import ca.soen6441.tf.critterstd.utility.GameEvent;

/**
 * These file contains operation of Tower Panel.
 * 
 * @author Vishal
 *
 */
public class TowerPanelController {

	private static TowerPanelController instance;

	/**
	 * Create an object for TowerControlPanelView and TowerHelper
	 */
	private TowerHelper towerHelper = TowerHelper.getTowerHelper();

	/**
	 * Create a constructor of TowerPanelController
	 */
	private TowerPanelController() {
	}// end constructor

	/**
	 * Create an object for TowerPanelController
	 * 
	 * @return the singleton object
	 */
	public static TowerPanelController getTowerActionController() {
		if (instance == null)
			instance = new TowerPanelController();

		return instance;
	}

	/**
	 * Method upgradeTower perform Tower Upgrade operation if user want to
	 * increase tower characteristics
	 * 
	 * @param tower
	 *            tower object
	 * @param player
	 *            player object
	 */
	public void upgradeTower(TowerInterface tower, Player player) {

		int towerUpgradeCost = (int) (tower.getBuyingCost() * 0.5);

		if (tower.getLevel() < 10 && towerUpgradeCost <= player.getCurrency()) {
			towerHelper.upgradeTower(tower);
			player.setCurrency(player.getCurrency()
					- (tower.getBuyingCost() * 0.5));
			GamePlayController.getGamePlayController().upgradeTower(tower);
			return;
		}

	}// end upgrade tower

	/**
	 * Method sellTower perform Tower Selling operation if user want to sell
	 * tower.
	 * 
	 * @param rowIndexOfCell
	 *            row index of cell
	 * @param colIndexOfCell
	 *            row index of cell
	 * @param tower
	 *            tower object to sell
	 * @param player
	 *            player object
	 */
	public void sellTower(int rowIndexOfCell, int colIndexOfCell,
			TowerInterface tower, Player player) {
		// LOG: Event
		new GameEvent(tower.getType() + " (" + tower.getTowerName()
				+ ") was sold. Tower number: " + tower.getSerial() + ".",
				"player", "towers", "wave"
						+ GamePlayController.getGamePlayController()
								.getWaveNumber(), "tower" + tower.getSerial());

		towerHelper.sellTower(tower, player);
		GamePlayController.getGamePlayController().removeTower(rowIndexOfCell,
				colIndexOfCell);
	}// end sell tower
}