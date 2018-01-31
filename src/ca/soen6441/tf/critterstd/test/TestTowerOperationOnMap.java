/**
 * 
 */
package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.soen6441.tf.critterstd.controller.GUIController;
import ca.soen6441.tf.critterstd.controller.GamePlayController;
import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.Player;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.model.tower.TowerFactory;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * This class contains the test cases related to operation on tower like add, remove and upgrade tower.
 * 
 * @author Vishal
 *
 */
public class TestTowerOperationOnMap {

	private Map aMap;
	JButton aButton;
	GamePlayController gamePlayController;
	GUIController gc;
	CellInterface aCell;

	private Player player;
	private TowerInterface tower;

	@Before
	public void setUp() throws Exception {
		gc = GUIController.getGUIController();
		aMap = Map.getMap();
		aMap.setNewSizeOfMap(10, 15);
		gamePlayController = GamePlayController.getGamePlayController();
		player = Player.getPlayer();
		tower = TowerFactory.getTowerFactory().getTower("tower1");
		aButton = (JButton) aMap.getCells().get(1).get(1);
		player.setCurrency(1000);

	}

	@After
	public void tearDown() throws Exception {
		aMap = null;
		aButton = null;
		gamePlayController = null;
		player = null;
		tower = null;
		aButton = null;
	}

	@Test
	public void testPlayerCurrency() {
		gamePlayController.setTower(tower);
		// First Place the tower at above defined cell location
		gamePlayController.updateCellProperty(aButton);
		assertEquals(player.getCurrency(), 850, 0);

	}

	@Test
	public void testAddTower() {
		gamePlayController.setTower(tower);
		// 1. Add the tower at above defined cell location
		gamePlayController.updateCellProperty(aButton);
		aCell = (CellInterface) aButton;

		// 2. Check if cell is not empty.
		assertFalse(aCell.isCellEmpty());
	}

	@Test
	public void testRemoveTower() {
		gamePlayController.setTower(tower);
		// 1. Place the tower at above defined cell location
		gamePlayController.updateCellProperty(aButton);

		// 2. get the location in aCell object and remove it.
		aCell = Map.getMap().getCells().get(1).get(1);
		gamePlayController.removeTower(1, 1);

		// 3. Check if the cell is empty to verify this test case.
		assertTrue(aCell.isCellEmpty() == true);
	}

	@Test
	public void testUpgradeTower() {
		double rangeBeforeUpgrade = tower.getRange();
		gamePlayController.setTower(tower);
		// 1. Place the tower at above defined cell location
		gamePlayController.updateCellProperty(aButton);
		gamePlayController.upgradeTower(tower);
		tower.upgradeTower();
		assertEquals(rangeBeforeUpgrade * 1.04, tower.getRange(), 0);
	}

}
