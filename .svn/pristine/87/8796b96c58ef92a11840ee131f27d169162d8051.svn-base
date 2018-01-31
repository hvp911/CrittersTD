/**
 * 
 */
package ca.soen6441.tf.critterstd.controller;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.utility.MapEvent;
import ca.soen6441.tf.critterstd.utility.MapUtility;
import ca.soen6441.tf.critterstd.view.MainContainerView;

/**
 * These file contains method of Map Creation, validation and update process.
 * 
 * @author Farzana Alam
 *
 */
public class MapEditorController {

	private static MapEditorController instance;

	/**
	 * Number of Columns in map
	 */
	private Integer mapWidth;

	/**
	 * Number of Rows in map
	 */
	private Integer mapHeight;

	/**
	 * Constructor for this class.
	 */
	private MapEditorController() {
		super();
	}

	/**
	 * Create an object for MapEditorController.
	 * 
	 * @return singleton object
	 */
	public static MapEditorController getMapEditorController() {
		if (instance == null)
			instance = new MapEditorController();

		return instance;

	}

	/**
	 * Initialize Map Width
	 * 
	 * @param mapWidth
	 *            map width
	 * 
	 * @return true
	 */
	public boolean setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
		return true;
	}

	/**
	 * Initialize Map Height
	 * 
	 * @param mapHeight
	 *            map height
	 * 
	 * @return true
	 */
	public boolean setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
		return true;
	}

	/**
	 * Update Map View and Set Map with user defined rows and columns
	 * 
	 * @param numOfRows
	 *            number of rows
	 * @param numOfColumns
	 *            number of rows
	 * 
	 * @return return false if operation failed or true.
	 */
	public boolean updateMapView(String numOfRows, String numOfColumns) {
		if (numOfColumns == null || numOfRows == null
				|| numOfRows.equalsIgnoreCase("0")
				|| numOfColumns.equalsIgnoreCase("0")
				|| numOfColumns.equalsIgnoreCase("")
				|| numOfRows.equalsIgnoreCase(""))
			return false;

		int rows = new Integer(numOfRows);
		int columns = new Integer(numOfColumns);

		setMapHeight(Math.min(rows, columns));
		setMapWidth(Math.max(rows, columns));

		Map.getMap().setNewSizeOfMap(this.mapHeight, this.mapWidth);
		return true;
	}

	/**
	 * Saving Map if validMap process works correctly
	 * 
	 * @param validMap
	 *            flag to show if it is a valid map
	 * @param fileName
	 *            the map file
	 * 
	 * @return true If operation is successful
	 * 
	 */
	public boolean saveMap(boolean validMap, String fileName) {
		if (!validMap)
			return false;

		Map.getMap().setMapName(fileName);

		new MapUtility().saveMap(fileName);
		return true;
	}

	/**
	 * Loading a saved map which are created by users
	 * 
	 * @param fileName
	 *            the file
	 */
	public void loadMap(String fileName) {
		Map aMap = new MapUtility().loadAMap(fileName);
		Map.getMap().loadMap(aMap);
		// LOG: Event
		new MapEvent("Map Loaded: " + Map.getMap().getMapName(), Map.getMap()
				.getMapName());
	}

	/**
	 * Perform Start Game operation after completing validating process
	 * 
	 * @return either true or false
	 */
	public boolean startGame() {

		if (!isValidMap()) {
			return false;
		}
		GUIController.getGUIController().startGame();
		return true;

	}

	/**
	 * Call ValidateMap method for Map validation
	 * 
	 * @return true if it is valid map.
	 * 
	 */
	public boolean isValidMap() {
		return Map.getMap().validateMap();
	}

	/**
	 * if any error occurs in validateMap method then pop-up error message
	 * 
	 * @param mainContainer
	 *            the main container
	 */
	public void showErrorMessage(MainContainerView mainContainer) {
		String errorMessage = Map.getMap().getMapValidationMessage();
		JOptionPane.showMessageDialog(mainContainer, errorMessage,
				"Invalid Map", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Changes a cell from Path cell to Scenery cell or vice versa
	 * 
	 * @param aCellButton
	 *            the cell to be changed
	 */
	public void changeCell(JButton aCellButton) {
		Map.getMap().changeCell(aCellButton);
	}
}
