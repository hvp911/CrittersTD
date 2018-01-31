package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.cell.CellFactory;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;

public class MapValidationTest {

	private Map aMap;
	private int row;
	private int column;
	private CellInterface aSceeryCell;
	private CellInterface aPathCell;
	private ArrayList<CellInterface> aListOfPathCells;
	private String validationMessage;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		aMap = Map.getMap();
		aListOfPathCells = new ArrayList<CellInterface>();
		validationMessage = "Invalid Map. \n";

		aSceeryCell = CellFactory.getCellFactory().getCell("SceneryCell");
		aPathCell = CellFactory.getCellFactory().getCell("PathCell");

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
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		aMap = null;
		aListOfPathCells = null;
		aPathCell = null;
		aSceeryCell = null;
	}

	/**
	 * If only one End point is selected.
	 */
	@Test
	public void testOneEndPoint() {

		validationMessage += "Maps must have one Entry and one Exit Point "
				+ "and cannot have more than two End Points!! ";

		aSceeryCell.setIndices(aListOfPathCells.get(0).getIndices()[0],
				aListOfPathCells.get(0).getIndices()[1]);
		aMap.updateMap(aSceeryCell.getIndices()[0],
				aSceeryCell.getIndices()[1], aSceeryCell);

		assertFalse(aMap.validateMap());
		assertTrue(aMap.getMapValidationMessage().equalsIgnoreCase(
				validationMessage));

	}

	/**
	 * If more than 2 end points were selected
	 */
	@Test
	public void testMoreThanTwoEndPoints() {
		validationMessage += "Maps must have one Entry and one Exit Point "
				+ "and cannot have more than two End Points!! ";

		aPathCell.setIndices(0, 2);
		aMap.updateMap(aPathCell.getIndices()[0], aPathCell.getIndices()[1],
				aPathCell);

		assertFalse(aMap.validateMap());
		assertTrue(aMap.getMapValidationMessage().equalsIgnoreCase(
				validationMessage));

	}

	/**
	 * Tests if at least one complete is found
	 */
	@Test
	public void testIncompletePath() {
		validationMessage += "No complete path found!!";

		/*
		 * A complete path
		 */
		assertTrue(aMap.validateMap());

		aSceeryCell.setIndices(aListOfPathCells.get(3).getIndices()[0],
				aListOfPathCells.get(3).getIndices()[1]);
		aMap.updateMap(aSceeryCell.getIndices()[0],
				aSceeryCell.getIndices()[1], aSceeryCell);

		/*
		 * An incomplete path
		 */
		assertFalse(aMap.validateMap());
		assertTrue(aMap.getMapValidationMessage().equalsIgnoreCase(
				validationMessage));
	}

}
