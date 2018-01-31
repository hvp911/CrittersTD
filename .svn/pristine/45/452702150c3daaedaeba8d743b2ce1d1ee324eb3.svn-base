/**
 * File Contains Creation of Map, Validation , Loading and Saving Map Methods
 */
package ca.soen6441.tf.critterstd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;

import ca.soen6441.tf.critterstd.model.cell.CellFactory;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.view.ViewObserver;

/**
 * This is the skeleton of the grid map. Map consists of {@link CellInterface}
 * cells.
 * 
 * @author Farzana Alam
 *
 */
public class Map implements Serializable, ModelObservable {

	private transient static final long serialVersionUID = 1L;

	/**
	 * Default map will have 10 rows.
	 */
	private transient static final int DEFAULT_NUM_ROWS = 10;

	/**
	 * Default map will have 15 columns.
	 */
	private transient static final int DEFAULT_NUM_COLUMNS = 15;

	/**
	 * List of observers that will be observing this model.
	 */
	private transient ArrayList<ViewObserver> observers = new ArrayList<ViewObserver>();

	// for cells
	private transient CellFactory cellFactory;
	private transient CellInterface cell;
	private ArrayList<ArrayList<CellInterface>> cells = new ArrayList<ArrayList<CellInterface>>();

	// for map size
	private int numOfRows;
	private int numOfColumns;

	// for Map validations
	private transient String mapValidationMessage = "Invalid Map. \n";
	private transient int numberOfEndPoints = 0;
	private transient ArrayList<Integer> endPointRowIndices;
	private transient ArrayList<Integer> endPointColumnIndices;
	private transient ArrayList<CellInterface> pathCells;
	private transient CellInterface entryCell;

	private transient CellInterface exitCell;

	private transient ArrayList<ArrayList<CellInterface>> validPaths = new ArrayList<ArrayList<CellInterface>>();
	// for singleton pattern - only one instance is allowed
	private transient static Map instance;

	// De-serialized map
	private transient Map savedMap;

	private boolean invalidPathcell;
	private boolean mapInstantiated = false;

	// For Saving New Map
	boolean MapSaveStatus;

	private String mapName = "anonymous";

	private Map() {
		super();
		this.numOfRows = DEFAULT_NUM_ROWS;
		this.numOfColumns = DEFAULT_NUM_COLUMNS;
		cellFactory = CellFactory.getCellFactory();

		createMap();
		mapInstantiated = true;

	}

	/**
	 * @return the instance of the singleton Map.
	 */
	public static Map getMap() {
		if (instance == null)
			instance = new Map();

		return instance;
	}

	/**
	 * Creates a map with cell objects
	 */
	private void createMap() {

		cells = new ArrayList<ArrayList<CellInterface>>();
		ArrayList<CellInterface> localCells;

		/*
		 * element will be identified as Xij where i is the row index and j is
		 * the column index
		 */
		for (int i = 0; i < numOfRows; i++) {
			localCells = new ArrayList<CellInterface>();
			for (int j = 0; j < numOfColumns; j++) {

				cell = cellFactory.getCell("SceneryCell");
				cell.setIndices(i, j);
				localCells.add(j, cell);

			} // end inner for

			cells.add(i, localCells);
		} // end outer for

		pathCells = new ArrayList<CellInterface>();

		if (mapInstantiated)
			notifyAllObservers();
	}

	/**
	 * Validates a map. A valid map will have the following criteria: <br>
	 * 
	 * <ul>
	 * <li>Only one Entry point
	 * <li>Only one Exit point
	 * <li>A complete path
	 * <li>No incomplete path branches. That means a path branch will have to
	 * coincide with the main path so that Critters will flow only in one
	 * direction. From Entry point to Exit point.
	 * </ul>
	 * 
	 * @return <tt><b>true</b></tt> if the new map is a valid map.
	 */
	public boolean validateMap() {

		endPointRowIndices = new ArrayList<Integer>();
		endPointColumnIndices = new ArrayList<Integer>();

		mapValidationMessage = "Invalid Map. \n";

		setEndPointIndices();

		// Map can only have one entry & one exit points
		if (numberOfEndPoints != 2) {
			mapValidationMessage += "Maps must have one Entry and one Exit Point "
					+ "and cannot have more than two End Points!! ";
			createMap();
			return false;
		} // end if

		// Set Entry and Exit Points in a Map
		setEntryAndExitPoints();
		
		// Return isPathComplete method
		return isPathComplete();

	}

	/**
	 * Checks wheather path is complete or not / also check map have only one
	 * entry and exit points
	 * 
	 * @return true/false
	 */
	private boolean isPathComplete() {

		entryCell = null;
		exitCell = null;
		validPaths.clear();
		mapValidationMessage = "Invalid Map. \n";

		CellInterface openPathCell = null;

		// Find the entry point
		for (CellInterface tmpCell : pathCells) {
			if (tmpCell.isEntryPonit()) {

				openPathCell = tmpCell;
				entryCell = tmpCell;
			} else if (tmpCell.isExitPoint()) {
				exitCell = tmpCell;
			} // end else if
		} // end for

		// if missing entry or exit point return false
		assert (entryCell != null && exitCell != null) : "Entry or Exit Point is missing!! Check code!!";

		// Entry and Exit points cannot be adjacent
		if (((Math.abs(entryCell.getIndices()[0] - exitCell.getIndices()[0]) == 1) && (entryCell
				.getIndices()[1] == exitCell.getIndices()[1]))
				|| ((Math.abs(entryCell.getIndices()[1]
						- exitCell.getIndices()[1]) == 1) && (entryCell
						.getIndices()[0] == exitCell.getIndices()[0]))) {
			mapValidationMessage += "Entry and Exit points cannot be adjacent!!";
			createMap();
			return false;

		}

		ArrayList<CellInterface> neighbouringCells = new ArrayList<CellInterface>();
		ArrayList<CellInterface> validPathCells = new ArrayList<CellInterface>();

		ArrayList<PathBranch> openPathBranches = new ArrayList<Map.PathBranch>();
		ArrayList<PathBranch> closedPathBranches = new ArrayList<Map.PathBranch>();
		ArrayList<CellInterface> closedPathCells = new ArrayList<CellInterface>();

		// finding complete paths
		do {

			PathBranch currentBranch = null;

			if (openPathBranches.size() > 0) {

				currentBranch = openPathBranches.get(0);
				closedPathCells = currentBranch
						.getClosedPathCellsToReachCurrentCell();
				openPathCell = currentBranch.getOpenPathCellOfThisBranch();

			} // end if

			// if entry point
			if (openPathCell.isEntryPonit()) {
				closedPathCells.add(openPathCell);
				for (CellInterface aCell : getNeighboringCells(openPathCell,
						closedPathCells)) {
					closedPathCells.add(aCell);
					openPathBranches
							.add(new PathBranch(aCell, closedPathCells));
				} // end for
				continue;
			} // end if

			// if exit point
			if (openPathCell.isExitPoint()) {

				currentBranch.setPathComplete(true);

				openPathBranches.remove(currentBranch);
				closedPathBranches.add(currentBranch);
				continue;
			} // end if

			int numberOfNeighbours = 0;
			neighbouringCells = getNeighboringCells(openPathCell,
					closedPathCells);

			if (neighbouringCells.isEmpty()) {
				currentBranch.setPathComplete(false);
				openPathBranches.remove(currentBranch);
				closedPathBranches.add(currentBranch);
				continue;
			} // end if

			for (CellInterface aCell : neighbouringCells) {

				numberOfNeighbours++;

				if (numberOfNeighbours > 1) {
					ArrayList<CellInterface> tmpClosedCells = new ArrayList<CellInterface>();
					for (CellInterface tmpCell : closedPathCells) {
						tmpClosedCells.add(tmpCell);
					}
					tmpClosedCells.remove(tmpClosedCells.size() - 1);
					tmpClosedCells.add(aCell);
					openPathBranches.add(new PathBranch(aCell, tmpClosedCells));
					continue;
				} // end if

				currentBranch.setOpenPathCellOfThisBranch(aCell);
				closedPathCells.add(aCell);

			} // end for

		} while (!openPathBranches.isEmpty());

		boolean onePathComplete = false;

		if (closedPathBranches.isEmpty()) {
			mapValidationMessage += "No continuous path was found!!";
			createMap();
			return false;
		}

		for (PathBranch br : closedPathBranches) {
			if (br.isPathComplete()) {
				onePathComplete = true;
				validPathCells
						.addAll(br.getClosedPathCellsToReachCurrentCell());

				// removing duplicates
				HashSet<CellInterface> hs = new HashSet<CellInterface>();
				hs.addAll(validPathCells);
				validPathCells.clear();
				validPathCells.addAll(hs);

				ArrayList<CellInterface> tmpPathCells = new ArrayList<CellInterface>();
				for (CellInterface tmpCell : br
						.getClosedPathCellsToReachCurrentCell()) {
					tmpPathCells.add(tmpCell);
				} // end for
				validPaths.add(tmpPathCells);
			} // end if
		} // end for

		for (CellInterface tmpPathCell : pathCells) {
			CellInterface aCell = tmpPathCell;
			if (validPathCells.contains(aCell))
				continue;
			invalidPathcell = true;
			updateMap(tmpPathCell.getIndices()[0], tmpPathCell.getIndices()[1],
					cellFactory.getCell("SceneryCell"));
			invalidPathcell = false;
		} // end for

		if (!onePathComplete) {
			mapValidationMessage += "No complete path found!!";
			createMap();
			return false;
		}

		removeOpposingPaths();

		return true;

	}

	/**
	 * This method removed the paths that will allow critters to flow in
	 * opposing directions
	 */
	private void removeOpposingPaths() {

		ArrayList<ArrayList<CellInterface>> newPaths = new ArrayList<ArrayList<CellInterface>>();

		outer: for (ArrayList<CellInterface> aPath : validPaths) {
			// start from 3rd cell in the path
			for (int j = 2; j < aPath.size() - 1; j++) {
				CellInterface currentCell = aPath.get(j);
				CellInterface nextCell = aPath.get(j + 1);

				// go down
				if (currentCell.getIndices()[0] < nextCell.getIndices()[0]) {
					if (currentCell.isOpposingDirection(PathCellDirection.DOWN)
							|| nextCell
									.isOpposingDirection(PathCellDirection.DOWN)) {
						newPaths.add(aPath);
						continue outer;
					} // end if
				} else if (currentCell.getIndices()[0] > nextCell.getIndices()[0]) {
					// go up
					if (currentCell.isOpposingDirection(PathCellDirection.UP)
							|| nextCell
									.isOpposingDirection(PathCellDirection.UP)) {
						newPaths.add(aPath);
						continue outer;
					} // end if
				} // end else if

				// go right
				if (currentCell.getIndices()[1] < nextCell.getIndices()[1]) {
					if (currentCell
							.isOpposingDirection(PathCellDirection.RIGHT)
							|| nextCell
									.isOpposingDirection(PathCellDirection.RIGHT)) {
						newPaths.add(aPath);
						continue outer;
					} // end if
				} else if (currentCell.getIndices()[1] > nextCell.getIndices()[1]) {
					// go left
					if (currentCell.isOpposingDirection(PathCellDirection.LEFT)
							|| nextCell
									.isOpposingDirection(PathCellDirection.LEFT)) {
						newPaths.add(aPath);
						continue outer;
					} // end if
				} // end else if
			} // end inner for
		} // end outer for

		for (ArrayList<CellInterface> aPath : newPaths) {
			validPaths.remove(aPath);
		} // end for

	}

	/**
	 * @param currentCell
	 * 
	 * @param closedPathCells
	 * @return ArrayList<CellInterface> Returns ArrayList of Cell
	 * 
	 */
	private ArrayList<CellInterface> getNeighboringCells(
			CellInterface currentCell, ArrayList<CellInterface> closedPathCells) {
		ArrayList<CellInterface> neighbours = new ArrayList<CellInterface>();
		int row = 0;
		int col = 1;

		for (CellInterface tmpPathCell : pathCells) {

			if (tmpPathCell.equals(currentCell)) {
				continue;
			}

			if (!closedPathCells.isEmpty()
					&& closedPathCells.contains(tmpPathCell))
				continue;

			if ((Math.abs(tmpPathCell.getIndices()[row]
					- currentCell.getIndices()[row]) <= 1)
					&& (Math.abs(tmpPathCell.getIndices()[col]
							- currentCell.getIndices()[col]) <= 1)) {

				// diagonal movement is not allowed
				if ((tmpPathCell.getIndices()[row] == currentCell.getIndices()[row])
						|| (tmpPathCell.getIndices()[col] == currentCell
								.getIndices()[col])) {
					neighbours.add(tmpPathCell);
				} // end inner if

			} // end outer if
		} // end for

		return neighbours;
	}

	/**
	 * Check Path if it is complete or not
	 */
	private class PathBranch {

		private CellInterface openPathCellOfThisBranch;

		private ArrayList<CellInterface> closedPathCellsToReachCurrentCell;
		private boolean pathComplete = false;

		/**
		 * Class to store the independent complete paths
		 * 
		 * @param openCell
		 * @param closedCells
		 */
		public PathBranch(CellInterface openCell,
				ArrayList<CellInterface> closedCells) {
			super();
			this.openPathCellOfThisBranch = openCell;
			this.closedPathCellsToReachCurrentCell = closedCells;
		}

		/**
		 * @param openPathCellOfThisBranch
		 */
		public void setOpenPathCellOfThisBranch(
				CellInterface openPathCellOfThisBranch) {
			this.openPathCellOfThisBranch = openPathCellOfThisBranch;
		}

		/**
		 * @return pathComplete value
		 */
		public boolean isPathComplete() {
			return pathComplete;
		}

		/**
		 * @param pathComplete
		 */
		public void setPathComplete(boolean pathComplete) {
			this.pathComplete = pathComplete;
		}

		/**
		 * @return openPathCellOfThisBrance
		 */
		public CellInterface getOpenPathCellOfThisBranch() {
			return openPathCellOfThisBranch;
		}

		/**
		 * @return closedPathCellsToReachCurrentCell
		 */
		public ArrayList<CellInterface> getClosedPathCellsToReachCurrentCell() {
			return closedPathCellsToReachCurrentCell;
		}
	}

	/*
	 * This will mark the cell which will be the entry and exit points for the
	 * critters to flow. These method automatically identify an entry and exit
	 * points.
	 */
	private void setEntryAndExitPoints() {
		int i = 0;

		// distance squared
		double distanceToFirstEndPoint = Math.pow(endPointRowIndices.get(i), 2)
				+ Math.pow(endPointColumnIndices.get(i), 2);
		double distanceToSecondEndPoint = Math.pow(
				endPointRowIndices.get(i + 1), 2)
				+ Math.pow(endPointColumnIndices.get(i + 1), 2);

		if (distanceToFirstEndPoint < distanceToSecondEndPoint) {
			// first end point is closest to (0,0) and hence entry

			cells.get(endPointRowIndices.get(i))
					.get(endPointColumnIndices.get(i)).setEntryPoint();
			cells.get(endPointRowIndices.get(i + 1))
					.get(endPointColumnIndices.get(i + 1)).setExitPoint();

		} else if (distanceToFirstEndPoint > distanceToSecondEndPoint) {
			// second end point is closest to (0,0) and hence entry

			cells.get(endPointRowIndices.get(i + 1))
					.get(endPointColumnIndices.get(i + 1)).setEntryPoint();
			cells.get(endPointRowIndices.get(i))
					.get(endPointColumnIndices.get(i)).setExitPoint();
		} else if (endPointRowIndices.get(i) < endPointRowIndices.get(i + 1)) {
			// first end point is close to top, hence becomes the entry point

			cells.get(endPointRowIndices.get(i))
					.get(endPointColumnIndices.get(i)).setEntryPoint();
			cells.get(endPointRowIndices.get(i + 1))
					.get(endPointColumnIndices.get(i + 1)).setExitPoint();
		} else {
			// second end point is close to top, hence becomes the entry point

			cells.get(endPointRowIndices.get(i + 1))
					.get(endPointColumnIndices.get(i + 1)).setEntryPoint();
			cells.get(endPointRowIndices.get(i))
					.get(endPointColumnIndices.get(i)).setExitPoint();
		} // end else

	}

	/*
	 * This will find out which cells are at the borders of the grid map.
	 */
	private void setEndPointIndices() {
		numberOfEndPoints = 0;

		// Identifying cells that are border elements or end points
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {

				cell = cells.get(i).get(j);

				if ((cell.getCellName().equalsIgnoreCase("pathcell"))
						& ((i == 0) || (j == 0) || (i == numOfRows - 1) || (j == numOfColumns - 1))) {
					cell.setEndPoint();
					numberOfEndPoints++;
					endPointRowIndices.add(i);
					endPointColumnIndices.add(j);
				} // end if
			} // end inner for
		} // end outer for

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.ModelObservable#getState()
	 */
	@Override
	public ModelObservable getState() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.ModelObservable#attach(ca.soen6441.tf
	 * .critterstd.view.ViewObserver)
	 */
	@Override
	public void attach(ViewObserver observer) {
		observers.add(observer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.ModelObservable#notifyAllObservers()
	 */
	/**
	 * These method notify observer whenever it change its state based on Model
	 * View Controller
	 */
	@Override
	public void notifyAllObservers() {
		for (ViewObserver observer : observers)
			observer.update();
	}

	/**
	 * @param rowIndex
	 *            the row number of the cell that was selected
	 * @param colIndex
	 *            the column number of the cell that was selected
	 * @param cell
	 *            the {@link CellInterface} object that was selected
	 */
	public void updateMap(int rowIndex, int colIndex, CellInterface cell) {
		cell.setIndices(rowIndex, colIndex);
		cells.get(rowIndex).set(colIndex, cell);
		notifyAllObservers();

		if (invalidPathcell) {
			cell.disableCell();
			return;
		}

		if (cell.getCellName().equalsIgnoreCase("PathCell")) {
			pathCells.add(cell);
		} else {
			for (CellInterface aCell : pathCells) {
				if ((aCell.getIndices()[0] == cell.getIndices()[0])
						&& (aCell.getIndices()[1] == cell.getIndices()[1])) {
					pathCells.remove(aCell);
					break;
				} // end if
			} // end for
		} // end else
	}

	/**
	 * Changes the grid map model with the new size as the input.
	 * 
	 * @param numOfRows
	 *            the number of rows the grid map should have.
	 * @param numOfColumns
	 *            the number of columns the grid map should have.
	 */
	public void setNewSizeOfMap(int numOfRows, int numOfColumns) {
		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;
		createMap();
		notifyAllObservers();
	}

	/**
	 * Loads previously saved map.
	 * 
	 * @param aMap
	 *            previously stored grid map
	 */
	public void loadMap(Map aMap) {
		this.savedMap = aMap;
		this.numOfRows = savedMap.numOfRows;
		this.numOfColumns = savedMap.numOfColumns;
		this.cells = savedMap.getCells();
		this.mapName = savedMap.mapName;

		pathCells.clear();

		for (ArrayList<CellInterface> tmpCells : cells) {
			for (CellInterface aCell : tmpCells) {
				if (aCell.getCellName().equalsIgnoreCase("PathCell")) {
					pathCells.add(aCell);
				}
			}
		}
		/*
		 * send notification
		 */
		notifyAllObservers();
	}

	/**
	 * @return a 2 dimensional list of cells from the current map as a tabular
	 *         format
	 */
	public ArrayList<ArrayList<CellInterface>> getCells() {
		return cells;
	}

	/**
	 * @return default number of rows for the map.
	 */
	public int getDEFAULT_NUM_ROWS() {
		return DEFAULT_NUM_ROWS;
	}

	/**
	 * @return default number of columns for the map.
	 */
	public int getDEFAULT_NUM_COLUMNS() {
		return DEFAULT_NUM_COLUMNS;
	}

	/**
	 * @return the number of rows of the current map.
	 */
	public int getNumOfRows() {
		return numOfRows;
	}

	/**
	 * @return the number of columns of the current map.
	 */
	public int getNumOfColumns() {
		return numOfColumns;
	}

	/**
	 * @return the map validation message
	 */
	public String getMapValidationMessage() {
		return mapValidationMessage;
	}

	/**
	 * Changes the cell from path to scenery or vice versa
	 * 
	 * @param aCellButton
	 *            the cell that needs to be changed
	 */
	public void changeCell(JButton aCellButton) {
		CellInterface oldCell = (CellInterface) aCellButton;
		CellInterface newCell = cellFactory.changeCell(oldCell.getCellName());
		updateMap(oldCell.getIndices()[0], oldCell.getIndices()[1], newCell);
	}

	/**
	 * @return the paths that are valid
	 */
	public ArrayList<ArrayList<CellInterface>> getValidPaths() {
		validateMap();
		return validPaths;
	}

	/**
	 * @param mapName
	 */
	public void setMapName(String mapName) {
		if (mapName.toLowerCase().startsWith("game")) {
			return;
		}
		this.mapName = mapName;
	}

	/**
	 * @return mapName
	 */
	public String getMapName() {
		return mapName;
	}

}
