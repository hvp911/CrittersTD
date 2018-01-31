/**
 * 
 */
package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.assertEquals;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.soen6441.tf.critterstd.controller.GUIController;
import ca.soen6441.tf.critterstd.controller.GamePlayController;
import ca.soen6441.tf.critterstd.controller.MapEditorController;
import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.Player;
import ca.soen6441.tf.critterstd.model.cell.CellFactory;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.model.tower.TowerFactory;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * @author Farzana Alam
 *
 */
public class GameSaveTest {

	private Map aMap;
	private int row;
	private int column;
	private static GUIController gc;
	private static GamePlayController gpc;
	private TowerInterface aTower;
	private Player aPlayer;
	private final String fileName = "testGame";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gc = GUIController.getGUIController();
		gpc = GamePlayController.getGamePlayController();

		aMap = Map.getMap();

		row = 10;
		column = 15;

		aMap.setNewSizeOfMap(row, column);

		// setting a new map with a complete path
		for (int i = 0; i < row; i++) {
			CellInterface tmpCell = CellFactory.getCellFactory().getCell(
					"PathCell");
			if (i == 1)
				tmpCell.setCellEmpty(false);
			tmpCell.setIndices(i, 1);
			aMap.updateMap(i, 1, tmpCell);
		}

		aMap.validateMap();

		aTower = TowerFactory.getTowerFactory().getTower("tower2");

		MapEditorController.getMapEditorController().startGame();

		gpc.setTower(aTower);
		gpc.updateCellProperty((JButton) Map.getMap().getCells().get(4).get(3));

		gpc.sendWave();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		aMap = null;
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testSaveAndLoadGame() throws InterruptedException {
		Thread.sleep(150);
		gpc.pauseGame(true);
		Thread.sleep(150);

		aPlayer = Player.getPlayer();

		int expectedLife = aPlayer.getLife();
		int expectedLevel = aPlayer.getLevel();
		int expectedScore = aPlayer.getScore();
		double expectedCurrency = aPlayer.getCurrency();

		Thread.sleep(10000);
		gc.saveGame(fileName);
		Thread.sleep(1500);

		System.out.println("here");

		gpc.pauseGame(true);
		gc.loadGame(fileName);

		aPlayer = Player.getPlayer();

		int newLife = aPlayer.getLife();
		int newLevel = aPlayer.getLevel();
		int newScore = aPlayer.getScore();
		double newCurrency = aPlayer.getCurrency();

		assertEquals(expectedCurrency, newCurrency, 0);
		assertEquals(expectedScore, newScore);
		assertEquals(expectedLevel, newLevel);
		assertEquals(expectedLife, newLife);
	}

}
