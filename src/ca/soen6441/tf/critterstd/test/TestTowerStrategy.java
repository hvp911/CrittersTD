package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.soen6441.tf.critterstd.controller.GUIController;
import ca.soen6441.tf.critterstd.model.Critter;
import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.cell.CellFactory;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.model.strategy.StrongestCritterShooter;
import ca.soen6441.tf.critterstd.model.strategy.WeakestCritterShooter;
import ca.soen6441.tf.critterstd.model.tower.TowerFactory;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * Test cases for tower shooting strategy.
 * 
 * @author Hardik
 *
 */
public class TestTowerStrategy {

	private Map aMap;
	private int row;
	private int column;
	private ArrayList<CellInterface> aListOfPathCells;
	private ArrayList<CellInterface> validPathCells;
	private Critter aCritter, bCritter;
	private ArrayList<Critter> critters;
	private TowerInterface tower;
	GUIController gc;

	@Before
	public void setup() {
		gc = GUIController.getGUIController();
		aMap = Map.getMap();
		aListOfPathCells = new ArrayList<CellInterface>();
		row = 10;
		column = 15;
		critters = new ArrayList<Critter>();
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
		tower = TowerFactory.getTowerFactory().getTower("tower2");

		aCritter = new Critter(validPathCells, 1, Color.BLACK);
		bCritter = new Critter(validPathCells, 2, Color.BLACK);

		aCritter.hitCritter(100, Color.green);
		bCritter.hitCritter(200, Color.green);
		critters.add(aCritter);
		critters.add(bCritter);

	}

	@After
	public void tear_down() {
		aMap = null;
		aListOfPathCells = null;
		validPathCells = null;
		critters = null;
		tower = null;
		aCritter = null;
		bCritter = null;
	}

	@Test
	public void testWeakestCritterShooter() {
		WeakestCritterShooter shooter = new WeakestCritterShooter();
		assertEquals(critters.get(1), shooter.selectCritter(tower, critters));
	}

	@Test
	public void testStrongestCritterShooter() {
		StrongestCritterShooter shooter = new StrongestCritterShooter();
		assertEquals(critters.get(0), shooter.selectCritter(tower, critters));
	}

}
