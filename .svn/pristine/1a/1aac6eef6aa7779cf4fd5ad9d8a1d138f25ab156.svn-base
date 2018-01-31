/**
 * 
 */
package ca.soen6441.tf.critterstd.model.cell;

import java.awt.Color;

import ca.soen6441.tf.critterstd.model.PathCellDirection;

/**
 * This is the interface for the cells providing all cell functions.
 * 
 * @author Farzana Alam
 *
 */
public interface CellInterface {

	/**
	 * @return <tt>true</tt> if cell has no elements on it.
	 */
	public boolean isCellEmpty();

	/**
	 * @param cellEmpty
	 *            pass false if tower is set
	 */
	public void setCellEmpty(boolean cellEmpty);

	/**
	 * Indexing cells like tabular form in the grid map.
	 * 
	 * @param rowIndex
	 *            row index of the cell in the grid map.
	 * @param columnIndex
	 *            column index of the cell in the grid map.
	 */
	public void setIndices(int rowIndex, int columnIndex);

	/**
	 * To get the row and column index of the cell object.
	 * 
	 * @return an integer array with the first <tt> int </tt> as the row index
	 *         and the scond one as the column index.
	 */
	public int[] getIndices();

	/**
	 * Cell name is defined as either "PathCell" or "SceneryCell". This method
	 * returns what type of cell is the current cell object.
	 * 
	 * @return the cell name.
	 */
	public String getCellName();

	/**
	 * Disables the current cell for any interactions.
	 */
	public void disableCell();

	// for entry and exit point

	/**
	 * Marks this cell as a border element on the grid map.
	 */
	public void setEndPoint();

	/**
	 * Marks the cell as the Entry Point.
	 */
	public void setEntryPoint();

	/**
	 * Marks the cell as the Exit Point.
	 */
	public void setExitPoint();

	/**
	 * @return <tt> true </tt> if this cell object is a border element.
	 */
	public boolean isEndPoint();

	/**
	 * @return <tt> true </tt> if this cell object is an Entry Point .
	 */
	public boolean isEntryPonit();

	/**
	 * @return <tt> true </tt> if this cell object is an Exit Point.
	 */
	public boolean isExitPoint();

	/**
	 * @param element
	 *            either Tower or Critter
	 * @return <code>true</code> if elements was set
	 */
	public boolean setElementOnTheCell(Object element);

	/**
	 * @return the element of the cell
	 */
	public Object getElementOfTheCell();

	/**
	 * Remove the element from the cell
	 */
	public void removeElementOfTheCell();

	/**
	 * @return the x coordinate of the component at top left corner
	 */
	public int getX();

	/**
	 * @return the y coordinate of the component at top left corner
	 */
	public int getY();

	/**
	 * @return width of the square cell component
	 */
	public int getWidth();

	/**
	 * @return color of cell
	 */
	public Color getCellColor();

	/**
	 * @param direction
	 *            right / left / up / down
	 * @return <code>true</code> if cell doesn't have opposing directions
	 */
	public boolean isOpposingDirection(PathCellDirection direction);

}
