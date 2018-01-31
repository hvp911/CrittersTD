package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.soen6441.tf.critterstd.controller.GUIController;
import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.cell.CellFactory;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.model.tower.TowerFactory;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * @author Hardik
 *
 */
public class CellOperationTest {
	private Map aMap;
	private int row;
	private int column;
	private ArrayList<CellInterface> aListOfPathCells;
	GUIController gc;

	@Before
	public void setUp() throws Exception {
		gc = GUIController.getGUIController();
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

		aMap.getValidPaths().get(0);

	}

	@After
	public void tearDown() throws Exception {
		aMap = null;
		aListOfPathCells = null;
		gc = null;

	}

	@Test
	public void testSetElementOnTheCell() {
		TowerInterface tower = TowerFactory.getTowerFactory()
				.getTower("tower1");
		CellInterface tmpCell = aMap.getCells().get(0).get(0);
		assertTrue(tmpCell.setElementOnTheCell(tower));
	}

	@Test
	public void testIsCellEmpty() {
		TowerInterface tower = TowerFactory.getTowerFactory()
				.getTower("tower1");
		CellInterface tmpCell = aMap.getCells().get(0).get(0);
		tmpCell.setElementOnTheCell(tower);
		assertTrue(!tmpCell.isCellEmpty());
	}

	@Test
	public void testElementOfTheCell() {
		TowerInterface tower = TowerFactory.getTowerFactory()
				.getTower("tower1");
		CellInterface tmpCell = aMap.getCells().get(0).get(0);
		tmpCell.setElementOnTheCell(tower);
		assertEquals(tower, tmpCell.getElementOfTheCell());
	}

	@Test
	public void testRemoveElementOfTheCell() {
		TowerInterface tower = TowerFactory.getTowerFactory()
				.getTower("tower1");
		CellInterface tmpCell = aMap.getCells().get(0).get(0);
		tmpCell.setElementOnTheCell(tower);
		tmpCell.removeElementOfTheCell();
		assertTrue(tmpCell.isCellEmpty());
	}

	@Test
	public void testSetExitPoint() {
		CellInterface tmpCell = aMap.getCells().get(0).get(1);
		tmpCell.setExitPoint();
		assertTrue(tmpCell.isExitPoint());
	}

	@Test
	public void testSetEntryPoint() {
		CellInterface tmpCell = aMap.getCells().get(0).get(1);
		tmpCell.setEntryPoint();
		assertTrue(tmpCell.isEntryPonit());
	}

}
