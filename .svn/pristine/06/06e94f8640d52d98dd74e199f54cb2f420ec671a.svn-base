/**
 * 
 */
package ca.soen6441.tf.critterstd.model.cell;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

import ca.soen6441.tf.critterstd.model.PathCellDirection;

/**
 * This abstract class contains all the generic functions of a cell object
 * whether it is Path cell or a Scenery cell.
 * 
 * @author Farzana Alam
 *
 */
public abstract class Cell extends JButton implements CellInterface {

	private static final long serialVersionUID = 1L;

	/**
	 * This flag will restrict a cell object to have more than one elements
	 * during the game play.
	 */
	boolean cellEmpty = true;

	/**
	 * The name of the cell object which can either be "PathCell" or
	 * "SceneryCell".
	 */
	String cellName;

	// For border cells
	boolean entryPoint = false;
	boolean exitPoint = false;
	boolean endPoint = false;

	Color cellColor;

	int rowIndex;
	int columnIndex;
	final int WIDTH = 25;

	final ArrayList<PathCellDirection> directions = new ArrayList<PathCellDirection>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Cell))
			return false;

		Cell otherCell = (Cell) obj;

		if ((this.rowIndex == otherCell.rowIndex)
				&& (this.columnIndex == otherCell.columnIndex)) {

			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.CellInterface#setIndices(int, int)
	 */
	/**
	 * Set the Indices
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 */
	@Override
	public void setIndices(int rowIndex, int columnIndex) {
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#getIndices()
	 */
	/**
	 * Get The Indices
	 * 
	 * @return int[]
	 */
	@Override
	public int[] getIndices() {
		int[] indices = new int[2];
		indices[0] = rowIndex;
		indices[1] = columnIndex;
		return indices;
	}

	/*
	 * 
	 * /* (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#isCellEmpty()
	 */
	/**
	 * Return True if Cell is Empty
	 * 
	 * @return boolean
	 */
	@Override
	public synchronized boolean isCellEmpty() {
		return cellEmpty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.cell.CellInterface#setCellEmpty(boolean)
	 */
	/**
	 * Set the Cell to Empty cell
	 * 
	 * @param cellEmpty
	 */
	@Override
	public synchronized void setCellEmpty(boolean cellEmpty) {
		this.cellEmpty = cellEmpty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getX()
	 */
	/**
	 * Get the X indice
	 * 
	 * @return int X indice
	 */
	@Override
	public int getX() {
		return super.getX();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getY()
	 */
	/**
	 * Get the Y indice
	 * 
	 * @return int Y indice
	 */
	public int getY() {
		return super.getY();
	}

	/**
	 * @return cellColor value
	 */
	public Color getCellColor() {
		return cellColor;
	}

}
