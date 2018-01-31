/**
 * 
 */
package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.soen6441.tf.critterstd.controller.MapEditorController;
import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.cell.CellFactory;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;

/**
 * @author Farzana Alam
 *
 */
public class MapSaveUtilityTest {

	private Map aMap;
	private int row;
	private int column;
	private ArrayList<CellInterface> aListOfPathCells;
	private String mapName;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		aMap = Map.getMap();
		aListOfPathCells = new ArrayList<CellInterface>();
		mapName = "testmap";

		row = 5;
		column = 10;

		aMap.setNewSizeOfMap(row, column);

		// setting a new map with a complete path
		for (int i = 0; i < row; i++) {
			CellInterface tmpCell = CellFactory.getCellFactory().getCell(
					"PathCell");
			tmpCell.setIndices(i, 1);
			aListOfPathCells.add(tmpCell);
			aMap.updateMap(i, 1, tmpCell);
		}

		aMap.validateMap();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		aMap = null;
		aListOfPathCells = null;

	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.controller.MapEditorController#saveMap(boolean, java.lang.String)}
	 * .
	 */
	@Test
	public void testSaveMap() {
		MapEditorController.getMapEditorController().saveMap(true, mapName);
		MapEditorController.getMapEditorController().loadMap(mapName + ".map");
		assertTrue(checkIfMapIsSame());
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.controller.MapEditorController#loadMap(java.lang.String)}
	 * .
	 */
	@Test
	public void testLoadMap() {
		MapEditorController.getMapEditorController().saveMap(true, mapName);
		MapEditorController.getMapEditorController().loadMap(mapName + ".map");
		assertTrue(checkIfMapIsSame());
	}

	/**
	 * @return <tt>true</tt> if maps are same
	 */
	private boolean checkIfMapIsSame() {
		ArrayList<ArrayList<CellInterface>> tmpMapCells = aMap.getCells();
		ArrayList<CellInterface> tmpPathCells = new ArrayList<CellInterface>();
		tmpPathCells.addAll(aListOfPathCells);

		for (ArrayList<CellInterface> tmpCells : tmpMapCells) {
			inner: for (CellInterface aCell : tmpCells) {
				for (CellInterface anotherCell : tmpPathCells) {
					if (tmpCells.contains(anotherCell)) {
						aListOfPathCells.remove(anotherCell);
						continue inner;
					} // end if

				} // end inner inner for
					// All other cells should be scenery cells
				if (aCell.getCellName().trim().equalsIgnoreCase("PathCell"))
					return false;

			} // end inner for
		} // end outer for

		if (aListOfPathCells.size() > 0)
			return false;

		return true;
	}

}
