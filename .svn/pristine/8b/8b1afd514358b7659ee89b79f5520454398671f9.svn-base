/**
 * 
 */
package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.cell.CellFactory;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;

/**
 * This test case will examine the following features of Map operations:
 * 
 * <ul>
 * <li>Change a cell and verify the change (updateMap())
 * <li>Creating a new map with desired number of rows and columns
 * <li>After creating new map checking if number of rows and columns comply with
 * the desired size of the grid map
 * </ul>
 * 
 * @author Farzana Alam
 *
 */
public class MapOperationsTest {

	private Map aMap;
	private CellInterface aSceneryCell;
	private CellInterface aPathCell;
	private int row;
	private int column;
	private ArrayList<ArrayList<CellInterface>> aListOfCells;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		aMap = Map.getMap();

		// a scenery cell at (2,2) position
		aSceneryCell = CellFactory.getCellFactory().getCell("SceneryCell");
		aSceneryCell.setIndices(2, 2);

		// a path cell at (3,3) position
		aPathCell = CellFactory.getCellFactory().getCell("PathCell");
		aPathCell.setIndices(3, 3);

		row = 5;
		column = 10;

		aMap.setNewSizeOfMap(row, column);
		aListOfCells = aMap.getCells();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		aMap = null;
		aSceneryCell = null;
		aPathCell = null;
		aListOfCells = null;
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Map#updateMap(int, int, ca.soen6441.tf.critterstd.model.cell.CellInterface)}
	 * .
	 */
	@Test
	public void testUpdateMap() {
		aMap.updateMap(aPathCell.getIndices()[0], aPathCell.getIndices()[1],
				aPathCell);

		String cellName = aListOfCells.get(aPathCell.getIndices()[0])
				.get(aPathCell.getIndices()[1]).getCellName();

		assertTrue(cellName.trim().equalsIgnoreCase("PathCell"));
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Map#setNewSizeOfMap(int, int)}.
	 */
	@Test
	public void testSetNewSizeOfMap() {

		boolean numberOfRowsMatches = true;
		boolean numberOfColumnsMatches = true;

		if (aListOfCells.size() != row)
			numberOfRowsMatches = false;

		for (ArrayList<CellInterface> tmpCells : aListOfCells) {
			if (tmpCells.size() != column) {
				numberOfColumnsMatches = false;
				break;
			} // end if
		} // end for

		assertTrue(numberOfRowsMatches && numberOfColumnsMatches);
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Map#getNumOfRows()}.
	 */
	@Test
	public void testGetNumOfRows() {
		assertTrue(aMap.getNumOfRows() == row);
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Map#getNumOfColumns()}.
	 */
	@Test
	public void testGetNumOfColumns() {
		assertTrue(aMap.getNumOfColumns() == column);
	}

}
