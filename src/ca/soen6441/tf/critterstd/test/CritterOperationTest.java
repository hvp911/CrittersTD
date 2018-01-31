/**
 * 
 */
package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.soen6441.tf.critterstd.controller.GUIController;
import ca.soen6441.tf.critterstd.controller.GamePlayController;
import ca.soen6441.tf.critterstd.model.Critter;
import ca.soen6441.tf.critterstd.model.CritterHelper;
import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.TowerHelper;
import ca.soen6441.tf.critterstd.model.cell.CellFactory;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * @author Farzana Alam
 *
 */
public class CritterOperationTest {

	private Map aMap;
	private int row;
	private int column;
	private ArrayList<CellInterface> aListOfPathCells;
	private ArrayList<CellInterface> validPathCells;
	private Critter aCritter;
	private int serial = 5;
	@SuppressWarnings("unused")
	private static GUIController gc;
	private Thread t;

	/**
	 * 
	 */
	@BeforeClass
	public static void initialize() {
		CritterHelper.increaseLevel();
		gc = GUIController.getGUIController();
	}

	/**
	 * 
	 */
	@AfterClass
	public static void dispose() {
		gc = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		aMap = Map.getMap();
		aListOfPathCells = new ArrayList<CellInterface>();

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
			aListOfPathCells.add(tmpCell);
			aMap.updateMap(i, 1, tmpCell);
		}

		aMap.validateMap();

		validPathCells = aMap.getValidPaths().get(0);

		aCritter = new Critter(validPathCells, serial, Color.BLACK);
		t = new Thread(aCritter);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		aMap = null;
		aListOfPathCells = null;
		validPathCells = null;
		aCritter = null;
		t.interrupt();

	}

	/**
	 * Test method for {@link ca.soen6441.tf.critterstd.model.Critter#getX()}.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testGetX() throws InterruptedException {
		validPathCells.get(1).setCellEmpty(false);
		t.start();

		Thread.sleep(100);
		assertEquals(validPathCells.get(0).getX() + 13, aCritter.getX());
		validPathCells.get(1).setCellEmpty(true);
	}

	/**
	 * Test method for {@link ca.soen6441.tf.critterstd.model.Critter#getY()}.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testGetY() throws InterruptedException {
		validPathCells.get(1).setCellEmpty(false);
		t.start();

		Thread.sleep(100);
		assertEquals(validPathCells.get(0).getY() + 13, aCritter.getY());
		validPathCells.get(1).setCellEmpty(true);
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Critter#hitCritter(double, java.awt.Color)}
	 * .
	 */
	@Test
	public void testHitCritter() {
		double initialLife = aCritter.getLife();
		double expected = initialLife - 100;
		aCritter.hitCritter(100, Color.RED);
		assertEquals(expected, aCritter.getLife(), 0);
	}

	/**
	 * Test method for {@link ca.soen6441.tf.critterstd.model.Critter#getLife()}
	 * .
	 */
	@Test
	public void testGetLife() {
		double initialLife = aCritter.getLife();
		double expected = initialLife - 100;
		aCritter.hitCritter(100, Color.RED);
		assertEquals(expected, aCritter.getLife(), 0);
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Critter#getSerial()}.
	 */
	@Test
	public void testGetSerial() {
		assertEquals(5, aCritter.getSerial());
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void testIsDead() throws InterruptedException {
		double initialLife = aCritter.getLife();
		aCritter.hitCritter(initialLife, Color.BLACK);
		t.start();
		Thread.sleep(100);

		assertTrue(aCritter.isDead());
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testSetStopMoving() throws InterruptedException {
		aCritter.setStopMoving(true);
		t.start();
		Thread.sleep(50);

		int expectedX = aCritter.getX();
		int expectedY = aCritter.getY();

		Thread.sleep(100);

		assertEquals(expectedX, aCritter.getX());
		assertEquals(expectedY, aCritter.getY());

		aCritter.setStopMoving(false);
		Map.getMap().getCells().get(1).get(1).setCellEmpty(true);
		Thread.sleep(100);

		assertTrue(aCritter.getX() != expectedX);
		assertTrue(aCritter.getY() != expectedY);

	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testRemainingPathSize() throws InterruptedException {
		int initialPathSize = validPathCells.size();
		t.start();
		validPathCells.get(1).setCellEmpty(true);
		Thread.sleep(1500);

		assertTrue(aCritter.remainingPathSize() < initialPathSize);

	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void testFreeze() throws InterruptedException {
		t.start();
		Thread.sleep(100);
		aCritter.freeze(100.0, Color.BLACK);
		int x = aCritter.getX();
		int y = aCritter.getY();
		Thread.sleep(100);

		assertEquals(x, aCritter.getX());
		assertEquals(y, aCritter.getY());

		assertEquals(200, aCritter.getLife(), 0);

	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void testBurn() throws InterruptedException {
		double initialLife = aCritter.getLife();
		t.start();
		Thread.sleep(100);
		aCritter.burn(100);

		Thread.sleep(1000);
		assertEquals(initialLife - 200, aCritter.getLife(), 0);

	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testSplash() throws InterruptedException {
		final TowerInterface aTower = TowerHelper.getTowerHelper().getTower(
				"tower3");
		GamePlayController.getGamePlayController().setTower(aTower);
		GamePlayController.getGamePlayController().updateCellProperty(
				(JButton) Map.getMap().getCells().get(3).get(4));
		GamePlayController.getGamePlayController().sendWave();
		Thread.sleep(50);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (aTower.isFiringSpecial()) {
						assertTrue(aTower.isFiringSpecial());
						break;
					}
				}

			}
		}).start();

	}
}
